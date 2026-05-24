package com.twitter.tweet_mixer.candidate_source.UTG

import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.recos.user_tweet_graph.thriftscala.ProducerBasedRelatedTweetRequest
import com.twitter.recos.user_tweet_graph.thriftscala.UserTweetGraph
import com.twitter.servo.util.Transformer
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.candidate_source.cached_candidate_source.MemcachedCandidateSource
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserTweetGraphProducerBasedCandidateSource @Inject() (
  utgClient: UserTweetGraph.MethodPerEndpoint,
  memcacheClient: MemcacheStitchClient)
    extends MemcachedCandidateSource[
      UTGProducerBasedRequest,
      ProducerBasedRelatedTweetRequest,
      (Long, Double),
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "UserTweetGraphProducerBasedCandidateSource"
  )

  override val TTL = Utils.randomizedTTL(600) //10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: ProducerBasedRelatedTweetRequest): String = {
    "UTG:" +
      key.producerId.toString +
      key.minScore.toString +
      key.maxResults.toString +
      key.maxNumFollowers.toString +
      key.maxTweetAgeInHours.toString +
      key.minCooccurrence.toString +
      key.similarityAlgorithm.toString
  }

  val valueTransformer: Transformer[Seq[(Long, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer

  override def enableCache(request: UTGProducerBasedRequest): Boolean = request.enableCache

  override def getKeys(
    request: UTGProducerBasedRequest
  ): Stitch[Seq[ProducerBasedRelatedTweetRequest]] = {
    Stitch.value {
      request.seedUserIds.map { seedUserId =>
        ProducerBasedRelatedTweetRequest(
          seedUserId,
          maxResults = request.maxResults,
          minCooccurrence = request.minCooccurrence,
          minScore = request.minScore,
          maxNumFollowers = request.maxNumFollowers,
          maxTweetAgeInHours = request.maxTweetAgeInHours,
          similarityAlgorithm = request.similarityAlgorithm
        )
      }
    }
  }

  override def getCandidatesFromStore(
    key: ProducerBasedRelatedTweetRequest
  ): Stitch[Seq[(Long, Double)]] = {
    Stitch
      .callFuture(utgClient.producerBasedRelatedTweets(key))
      .map { response =>
        response.tweets.map { relatedTweet =>
          (relatedTweet.tweetId, relatedTweet.score)
        }
      }
  }

  override def postProcess(
    request: UTGProducerBasedRequest,
    keys: Seq[ProducerBasedRelatedTweetRequest],
    resultsSeq: Seq[Seq[(Long, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val utgCandidates = keys.zip(resultsSeq).map {
      case (key, results) =>
        val seedId = key.producerId
        results
          .map {
            case (id, score) => TweetMixerCandidate(id, score, seedId)
          }
    }
    TweetMixerCandidate.interleave(utgCandidates)
  }
}
