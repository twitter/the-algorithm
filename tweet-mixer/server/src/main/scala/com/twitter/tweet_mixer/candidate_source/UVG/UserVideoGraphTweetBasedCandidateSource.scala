package com.twitter.tweet_mixer.candidate_source.UVG

import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.recos.user_video_graph.thriftscala.TweetBasedRelatedTweetRequest
import com.twitter.recos.user_video_graph.thriftscala.UserVideoGraph
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
class UserVideoGraphTweetBasedCandidateSource @Inject() (
  uvgClient: UserVideoGraph.MethodPerEndpoint,
  memcacheClient: MemcacheStitchClient)
    extends MemcachedCandidateSource[
      UVGTweetBasedRequest,
      TweetBasedRelatedTweetRequest,
      (Long, Double),
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "UserVideoGraphTweetBasedCandidateSource"
  )

  override val TTL = Utils.randomizedTTL(600) //10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: TweetBasedRelatedTweetRequest): String = {
    "UVG:" +
      key.tweetId.toString +
      key.minScore.toString +
      key.maxResults.toString +
      key.maxTweetAgeInHours.toString +
      key.minCooccurrence.toString +
      key.similarityAlgorithm.toString +
      key.maxNumSamplesPerNeighbor +
      key.maxRightNodeDegree +
      key.maxLeftNodeDegree +
      key.sampleRHSTweets +
      key.degreeExponent
  }

  val valueTransformer: Transformer[Seq[(Long, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer

  override def enableCache(request: UVGTweetBasedRequest): Boolean = request.enableCache

  override def getKeys(
    request: UVGTweetBasedRequest
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
          maxNumSamplesPerNeighbor = request.maxNumSamplesPerNeighbor,
          maxLeftNodeDegree = request.maxLeftNodeDegree,
          maxRightNodeDegree = request.maxRightNodeDegree,
          sampleRHSTweets = request.sampleRHSTweets,
          degreeExponent = request.degreeExponent
        )
      }
    }
  }

  override def getCandidatesFromStore(
    key: TweetBasedRelatedTweetRequest
  ): Stitch[Seq[(Long, Double)]] = {
    Stitch
      .callFuture(uvgClient.tweetBasedRelatedTweets(key))
      .map { response =>
        response.tweets.map { relatedTweet =>
          (relatedTweet.tweetId, relatedTweet.score)
        }
      }
  }

  override def postProcess(
    request: UVGTweetBasedRequest,
    keys: Seq[TweetBasedRelatedTweetRequest],
    resultsSeq: Seq[Seq[(Long, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val uvgCandidates = keys.zip(resultsSeq).map {
      case (key, results) =>
        val seedId = key.tweetId
        results
          .map {
            case (id, score) => TweetMixerCandidate(id, score, seedId)
          }
    }
    TweetMixerCandidate.interleave(uvgCandidates)
  }
}
