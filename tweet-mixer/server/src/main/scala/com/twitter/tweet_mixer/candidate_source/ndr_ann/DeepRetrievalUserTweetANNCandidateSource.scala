package com.twitter.tweet_mixer.candidate_source.ndr_ann

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.hydra.common.utils.{Utils => HydraUtils}
import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.servo.util.Transformer
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.candidate_source.cached_candidate_source.MemcachedCandidateSource
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.module.GPURetrievalHttpClient
import com.twitter.tweet_mixer.utils.BucketSnowflakeIdAgeStats
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import com.twitter.tweet_mixer.utils.Utils.TweetId
import com.twitter.util.Future
import com.twitter.vecdb.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class DeepRetrievalUserTweetANNCandidateSource(
  annClient: t.VecDB.MethodPerEndpoint,
  gpuRetrievalHttpClient: GPURetrievalHttpClient,
  memcacheClient: MemcacheStitchClient,
  statsReceiver: StatsReceiver,
  sourceIdentifier: String)
    extends MemcachedCandidateSource[
      DRMultipleANNQuery,
      DRANNKey,
      (TweetId, Double),
      TweetMixerCandidate
    ] {

  private val scopedStats: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val tweetScoreStats = scopedStats.stat("tweetScore")
  private val tweetSizePerSignalStats = scopedStats.stat("tweetSizePerSignal")
  private val tweetAgeStats =
    BucketSnowflakeIdAgeStats[TweetMixerCandidate](
      BucketSnowflakeIdAgeStats.MillisecondsPerHour,
      _.tweetId)(scopedStats.scope("tweetAge"))

  private val annStats = scopedStats.scope("ANN")
  private val annResultAgeStats =
    BucketSnowflakeIdAgeStats[(TweetId, Double)](
      BucketSnowflakeIdAgeStats.MillisecondsPerHour,
      _._1)(annStats)
  private val annScoreStats = annStats.stat("score")

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(sourceIdentifier)

  override val TTL: Int = Utils.randomizedTTL(600) // 10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: DRANNKey): String =
    f"${sourceIdentifier}_${key.collectionName}_${key.id.toString}_${key.scoreThreshold.toString}"

  val valueTransformer: Transformer[Seq[(TweetId, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer

  override def enableCache(request: DRMultipleANNQuery): Boolean = request.enableCache

  override def getKeys(request: DRMultipleANNQuery): Stitch[Seq[DRANNKey]] =
    Stitch.value(request.annKeys)

  private def getCandidatesFromVecDB(
    embedding: Seq[Int],
    collectionName: String,
    maxCandidates: Int,
    scoreThreshold: Double,
    filter: Option[t.Filter]
  ) = {
    annClient
      .search(
        dataset = collectionName,
        vector = HydraUtils.intBitsSeqToFloatSeq(embedding).map(_.toDouble),
        params =
          Some(t.SearchParams(scoreThreshold = Some(scoreThreshold), limit = Some(maxCandidates))),
        filter = filter
      ).map { response: t.SearchResponse =>
        response.points match {
          case points: Seq[t.ScoredPoint] =>
            points.map { point =>
              (point.id, point.score)
            }
          case _ =>
            Seq.empty
        }
      }
  }

  private def getCandidatesFromGPU(embedding: Seq[Int]) = {
    gpuRetrievalHttpClient.getNeighbors(embedding)
  }

  override def getCandidatesFromStore(
    key: DRANNKey
  ): Stitch[Seq[(TweetId, Double)]] = {
    val categoryFilter = key.category.map { cat =>
      t.FieldPredicate(
        key = t.Key.PayloadField("category"),
        condition = t.ValueCondition.EqStr(cat))
    }
    val highQualityFilter = key.isHighQuality.map { isHighQuality =>
      t.FieldPredicate(
        key = t.Key.PayloadField("isHighQuality"),
        condition = t.ValueCondition.EqBool(isHighQuality))
    }
    val lowNegEngRatioFilter = key.isLowNegEngRatio.map { isLowNegEngRatio =>
      t.FieldPredicate(
        key = t.Key.PayloadField("isHighNegEngRatio"),
        condition = t.ValueCondition.EqBool(isLowNegEngRatio))
    }
    val tierFilter = key.tier.map { tier =>
      t.FieldPredicate(key = t.Key.PayloadField("tier"), condition = t.ValueCondition.EqStr(tier))
    }
    val filters = categoryFilter.toSeq ++ highQualityFilter ++ lowNegEngRatioFilter ++ tierFilter
    val filter = if (filters.nonEmpty) {
      Some(t.Filter.All(filters))
    } else None

    val futureResult = for {
      response <- key.embedding match {
        case Some(embedding) =>
          if (key.enableGPU) getCandidatesFromGPU(embedding)
          else
            getCandidatesFromVecDB(
              embedding,
              key.collectionName,
              key.maxCandidates,
              key.scoreThreshold,
              filter)
        case None =>
          Future.Nil
      }
    } yield {
      val result = response.map {
        case (tweetId, score) =>
          annScoreStats.add(score.toFloat * 1000)
          (tweetId, score)
      }
      annResultAgeStats.count(result)
      result
    }
    Stitch.callFuture(futureResult)
  }

  override def postProcess(
    request: DRMultipleANNQuery,
    keys: Seq[DRANNKey],
    resultsSeq: Seq[Seq[(TweetId, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val candidates = keys.zip(resultsSeq).map {
      case (key, results) =>
        results
          .map {
            case (id, score) =>
              tweetScoreStats.add(score.toFloat * 1000)
              TweetMixerCandidate(id, score, key.id)
          }
    }
    candidates.foreach { seq =>
      tweetSizePerSignalStats.add(seq.size)
    }
    val result = TweetMixerCandidate.interleave(candidates)
    tweetAgeStats.count(result)
    result
  }
}

@Singleton
class DeepRetrievalUserTweetANNCandidateSourceFactory @Inject() (
  @Named(ModuleNames.VecDBAnnServiceClient)
  annClient: t.VecDB.MethodPerEndpoint,
  @Named(ModuleNames.GPURetrievalProdHttpClient)
  gpuRetrievalHttpClient: GPURetrievalHttpClient,
  memcacheClient: MemcacheStitchClient,
  statsReceiver: StatsReceiver) {

  def build(identifier: String): DeepRetrievalUserTweetANNCandidateSource = {
    new DeepRetrievalUserTweetANNCandidateSource(
      annClient,
      gpuRetrievalHttpClient,
      memcacheClient,
      statsReceiver,
      identifier
    )
  }
}
