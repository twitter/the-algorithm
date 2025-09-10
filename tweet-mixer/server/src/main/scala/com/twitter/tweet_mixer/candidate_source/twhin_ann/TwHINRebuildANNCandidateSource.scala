package com.twitter.tweet_mixer.candidate_source.twhin_ann

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.servo.util.Transformer
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.VersionId
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.tweet_mixer.candidate_source.cached_candidate_source.MemcachedCandidateSource
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.utils.BucketSnowflakeIdAgeStats
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import com.twitter.util.Future
import com.twitter.vecdb.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TwHINRebuildANNCandidateSource @Inject() (
  @Named(ModuleNames.TwHINRebuildTweetEmbeddingStratoStore)
  tweetEmbeddingStore: ReadableStore[(TweetId, VersionId), TwhinTweetEmbedding],
  @Named(ModuleNames.VecDBAnnServiceClient)
  annClient: t.VecDB.MethodPerEndpoint,
  memcacheClient: MemcacheStitchClient,
  statsReceiver: StatsReceiver)
    extends MemcachedCandidateSource[
      Seq[TwHINRebuildANNKey],
      TwHINRebuildANNKey,
      (TweetId, Double),
      TweetMixerCandidate
    ] {

  import TwHINRebuildANNCandidateSource._

  private val scopedStats: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val tweetScoreStats = scopedStats.stat("tweetScore")
  private val tweetSizePerSignalStats = scopedStats.stat("tweetSizePerSignal")
  private val tweetAgeStats =
    BucketSnowflakeIdAgeStats[TweetMixerCandidate](
      BucketSnowflakeIdAgeStats.MillisecondsPerHour,
      _.tweetId)(scopedStats.scope("tweetAge"))
  private val foundQueryEmbeddingCounter = scopedStats.counter("foundQueryEmbedding")
  private val missingQueryEmbeddingCounter = scopedStats.counter("missingQueryEmbedding")
  private val successVecDBRequestsCounter = scopedStats.counter("successVecDBRequests")
  private val failedVecDBRequestsCounter = scopedStats.counter("failedVecDBRequests")

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("TwHINRebuildANN")

  override val TTL: Int = Utils.randomizedTTL(600) //10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: TwHINRebuildANNKey): String = {
    "TwHINRebuild:" + key.toString
  }

  val valueTransformer: Transformer[Seq[(Long, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer
  override def getKeys(request: Seq[TwHINRebuildANNKey]): Stitch[Seq[TwHINRebuildANNKey]] =
    Stitch.value(request)

  override def getCandidatesFromStore(
    key: TwHINRebuildANNKey
  ): Stitch[Seq[(TweetId, Double)]] = {
    val futureResult =
      tweetEmbeddingStore
        .get((key.id, key.versionId)).map(_.map(toVector)).flatMap {
          case Some(embedding) =>
            foundQueryEmbeddingCounter.incr()
            annClient
              .search(
                dataset = key.dataset,
                vector = embedding,
                params = Some(t.SearchParams(limit = Some(key.maxCandidates)))
              ).map { response: t.SearchResponse =>
                response.points match {
                  case points: Seq[t.ScoredPoint] =>
                    successVecDBRequestsCounter.incr()
                    points.map { point =>
                      (point.id, point.score)
                    }
                  case _ =>
                    failedVecDBRequestsCounter.incr()
                    Seq.empty
                }
              }
          case None =>
            missingQueryEmbeddingCounter.incr()
            Future.Nil
        }.rescue({
          case _: Exception =>
            failedVecDBRequestsCounter.incr()
            Future.Nil
        })
    Stitch.callFuture(futureResult)
  }

  override def postProcess(
    request: Seq[TwHINRebuildANNKey],
    keys: Seq[TwHINRebuildANNKey],
    resultsSeq: Seq[Seq[(TweetId, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val twHINCandidates = keys.zip(resultsSeq).map {
      case (key, results) =>
        results
          .map {
            case (id, score) =>
              tweetScoreStats.add(score.toFloat * 1000)
              TweetMixerCandidate(id, score, key.id)
          }
    }
    twHINCandidates.foreach { seq =>
      val seqSize = seq.size
      tweetSizePerSignalStats.add(seqSize)
    }
    val result = TweetMixerCandidate.interleave(twHINCandidates)
    tweetAgeStats.count(result)
    result
  }
}

object TwHINRebuildANNCandidateSource {
  def toVector(embedding: TwhinTweetEmbedding): Seq[Double] = {
    embedding.embedding
  }
}
