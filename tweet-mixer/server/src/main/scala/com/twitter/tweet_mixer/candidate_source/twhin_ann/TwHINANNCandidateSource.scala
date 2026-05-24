package com.twitter.tweet_mixer.candidate_source.twhin_ann

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.servo.util.Transformer
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.clients.ann.ANNQdrantGRPCClient
import com.twitter.tweet_mixer.candidate_source.cached_candidate_source.MemcachedCandidateSource
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.utils.BucketSnowflakeIdAgeStats
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TwHINANNCandidateSource @Inject() (
  @Named(ModuleNames.TwHINTweetEmbeddingStratoStore)
  tweetEmbeddingStore: ReadableStore[TweetId, TwhinTweetEmbedding],
  @Named(ModuleNames.TwHINANNServiceClient)
  annClient: ANNQdrantGRPCClient,
  memcacheClient: MemcacheStitchClient,
  statsReceiver: StatsReceiver)
    extends MemcachedCandidateSource[
      Seq[TweetId],
      TweetId,
      (TweetId, Double),
      TweetMixerCandidate
    ] {

  import TwHINANNCandidateSource._

  private val scopedStats: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val tweetScoreStats = scopedStats.stat("tweetScore")
  private val tweetSizePerSignalStats = scopedStats.stat("tweetSizePerSignal")
  private val tweetAgeStats =
    BucketSnowflakeIdAgeStats[TweetMixerCandidate](
      BucketSnowflakeIdAgeStats.MillisecondsPerHour,
      _.tweetId)(scopedStats.scope("tweetAge"))
  private val missingQueryEmbeddingCounter = scopedStats.counter("missingQueryEmbedding")
  private val emptyResultCounter = scopedStats.counter("emptyResult")

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("TwHINANNCandidateSource")

  override val TTL: Int = Utils.randomizedTTL(600) //10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: TweetId): String = {
    "TwHIN:" + key.toString
  }

  val valueTransformer: Transformer[Seq[(Long, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer
  override def getKeys(request: Seq[TweetId]): Stitch[Seq[TweetId]] = Stitch.value(request)

  override def getCandidatesFromStore(
    key: TweetId
  ): Stitch[Seq[(TweetId, Double)]] = {
    val futureResult = for {
      tweetEmbedding <- tweetEmbeddingStore.get(key).map(_.map(toVector))
      response <- tweetEmbedding match {
        case Some(embedding) =>
          annClient.searchANN(
            embedding = embedding,
            topK = 100,
            collectionName = "twhin-prod",
            timeout = 20.millis)
        case None =>
          missingQueryEmbeddingCounter.incr()
          Future.Nil
      }
    } yield {
      val result = response.map {
        case (tweetId, score) =>
          (tweetId, score.toDouble)
      }
      result
    }
    Stitch.callFuture(futureResult)
  }

  override def postProcess(
    request: Seq[TweetId],
    keys: Seq[TweetId],
    resultsSeq: Seq[Seq[(TweetId, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val twHINCandidates = keys.zip(resultsSeq).map {
      case (key, results) =>
        results
          .map {
            case (id, score) =>
              tweetScoreStats.add(score.toFloat * 1000)
              TweetMixerCandidate(id, score, key)
          }
    }
    twHINCandidates.foreach { seq =>
      val seqSize = seq.size
      if (seqSize == 0) {
        emptyResultCounter.incr()
      }
      tweetSizePerSignalStats.add(seqSize)
    }
    val result = TweetMixerCandidate.interleave(twHINCandidates)
    tweetAgeStats.count(result)
    result
  }
}

object TwHINANNCandidateSource {
  def toVector(embedding: TwhinTweetEmbedding): Seq[Float] = {
    embedding.embedding.map(_.toFloat)
  }
}
