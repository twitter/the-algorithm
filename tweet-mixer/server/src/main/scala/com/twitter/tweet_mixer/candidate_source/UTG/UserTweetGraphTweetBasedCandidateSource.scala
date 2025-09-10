package com.twitter.tweet_mixer.candidate_source.UTG

import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.recos.user_tweet_graph.thriftscala.TweetBasedRelatedTweetRequest
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
class UserTweetGraphTweetBasedCandidateSource @Inject() (
  utgClient: UserTweetGraph.MethodPerEndpoint,
  memcacheClient: MemcacheStitchClient)
    extends MemcachedCandidateSource[
      UTGTweetBasedRequest,
      TweetBasedRelatedTweetRequest,
      (Long, Double),
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "UserTweetGraphTweetBasedCandidateSource"
  )

  override val TTL = Utils.randomizedTTL(600) //10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: TweetBasedRelatedTweetRequest): String = {
    "UTG:" +
      key.tweetId.toString +
      key.minScore.toString +
      key.maxResults.toString +
      key.maxTweetAgeInHours.toString +
      key.minCooccurrence.toString +
      key.similarityAlgorithm.toString +
      key.degreeExponent
  }

  val valueTransformer: Transformer[Seq[(Long, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer

  override def enableCache(request: UTGTweetBasedRequest): Boolean = request.enableCache

  override def getKeys(
    request: UTGTweetBasedRequest
  ): Stitch[Seq[TweetBasedRelatedTweetRequest]] = {
    Stitch.value {
      request.seedTweetIds.map { seedTweetId =>
        TweetBasedRelatedTweetRequest(
          seedTweetId,
          maxResults = request.maxResults,
          minCooccurrence = request.minCooccurrence,
          excludeTweetIds = Some(Seq(seedTweetId)),
          minScore = request.minScore,
          maxTweetAgeInHours = request.maxTweetAgeInHours,
          similarityAlgorithm = request.similarityAlgorithm,
          degreeExponent = request.degreeExponent
        )
      }
    }
  }

  override def getCandidatesFromStore(
    key: TweetBasedRelatedTweetRequest
  ): Stitch[Seq[(Long, Double)]] = {
    Stitch
      .callFuture(utgClient.tweetBasedRelatedTweets(key))
      .map { response =>
        response.tweets.map { relatedTweet =>
          (relatedTweet.tweetId, relatedTweet.score)
        }
      }
  }

  override def postProcess(
    request: UTGTweetBasedRequest,
    keys: Seq[TweetBasedRelatedTweetRequest],
    resultsSeq: Seq[Seq[(Long, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val utgCandidates = keys.zip(resultsSeq).map {
      case (key, results) =>
        val seedId = key.tweetId
        results
          .map {
            case (id, score) => TweetMixerCandidate(id, score, seedId)
          }
    }
    TweetMixerCandidate.interleave(utgCandidates)
  }
}
