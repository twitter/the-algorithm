package com.twitter.tweet_mixer.candidate_source.ndr_ann

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.servo.util.Transformer
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.candidate_source.cached_candidate_source.MemcachedCandidateSource
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.utils.BucketSnowflakeIdAgeStats
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import com.twitter.tweet_mixer.utils.Utils.TweetId
import com.twitter.vecdb.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DeepRetrievalTweetTweetANNCandidateSource @Inject() (
  @Named(ModuleNames.VecDBAnnServiceClient)
  annClient: t.VecDB.MethodPerEndpoint,
  memcacheClient: MemcacheStitchClient,
  statsReceiver: StatsReceiver)
    extends MemcachedCandidateSource[
      DRMultipleANNQuery,
      DRANNKey,
      (TweetId, Double),
      TweetMixerCandidate
    ] {

  private val stats: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val tweetScoreStats = statsReceiver.stat("tweetScore")
  private val tweetSizePerSignalStats = statsReceiver.stat("tweetSizePerSignal")
  private val tweetAgeStats =
    BucketSnowflakeIdAgeStats[TweetMixerCandidate](
      BucketSnowflakeIdAgeStats.MillisecondsPerHour,
      _.tweetId)(stats.scope("tweetAge"))

  private val annStats = stats.scope("ANN")
  private val annResultAgeStats =
    BucketSnowflakeIdAgeStats[(TweetId, Double)](
      BucketSnowflakeIdAgeStats.MillisecondsPerHour,
      _._1)(annStats)
  private val annScoreStats = annStats.stat("score")

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("DeepRetrievalTweetTweetANNCandidateSource")

  override val TTL: Int = Utils.randomizedTTL(600) // 10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: DRANNKey): String =
    f"dr_tweet_${key.collectionName}_${key.id.toString}_${key.scoreThreshold.toString}"

  val valueTransformer: Transformer[Seq[(TweetId, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer

  override def enableCache(request: DRMultipleANNQuery): Boolean = request.enableCache

  override def getKeys(request: DRMultipleANNQuery): Stitch[Seq[DRANNKey]] =
    Stitch.value(request.annKeys)

  override def getCandidatesFromStore(
    key: DRANNKey
  ): Stitch[Seq[(TweetId, Double)]] = {
    val futureResult = annClient
      .searchById(
        dataset = key.collectionName,
        id = key.id,
        params = Some(
          t.SearchParams(
            scoreThreshold = Some(key.scoreThreshold),
            limit = Some(key.maxCandidates))))
      .map { response: t.SearchResponseV2 =>
        val result = response.points match {
          case points: Seq[t.ScoredPointV2] =>
            points.map { point =>
              val score = point.score
              annScoreStats.add(score.toFloat * 1000)
              (point.id, score)
            }
          case _ =>
            Seq.empty
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
