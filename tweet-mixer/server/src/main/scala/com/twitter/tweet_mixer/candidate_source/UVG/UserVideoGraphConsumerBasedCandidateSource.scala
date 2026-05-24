package com.twitter.tweet_mixer.candidate_source.UVG

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.util.DefaultTimer
import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.twitter.recos.user_video_graph.thriftscala.UserVideoGraph
import com.twitter.servo.util.Transformer
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.candidate_source.cached_candidate_source.MemcachedCandidateSource
import com.twitter.tweet_mixer.candidate_source.engaged_users.RecentEngagedUsersCandidateSource
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserVideoGraphConsumerBasedCandidateSource @Inject() (
  uvgClient: UserVideoGraph.MethodPerEndpoint,
  recentEngagedUsersCandidateSource: RecentEngagedUsersCandidateSource,
  memcacheClient: MemcacheStitchClient)
    extends MemcachedCandidateSource[
      UVGTweetBasedRequest,
      (ConsumersBasedRelatedTweetRequest, Long),
      (Long, Double),
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "UserVideoGraphConsumerBasedCandidateSource"
  )

  private val defaultVersion: Long = 0

  implicit val timer = DefaultTimer

  override val TTL = Utils.randomizedTTL(600) //10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: (ConsumersBasedRelatedTweetRequest, Long)): String = {
    key match {
      case (request, seedId) =>
        "UVGExpansion:" +
          seedId.toString +
          request.minScore.toString +
          request.maxResults.toString +
          request.maxTweetAgeInHours.toString +
          request.minCooccurrence.toString +
          request.similarityAlgorithm.toString
    }
  }

  val valueTransformer: Transformer[Seq[(Long, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer

  override def enableCache(request: UVGTweetBasedRequest): Boolean = request.enableCache

  override def getKeys(
    request: UVGTweetBasedRequest
  ): Stitch[Seq[(ConsumersBasedRelatedTweetRequest, Long)]] = {
    Stitch.traverse(request.seedTweetIds) { seedId =>
      val recentEngagedUsersStitch =
        recentEngagedUsersCandidateSource
          .apply((seedId, defaultVersion))
          .within(50.millis)
          .handle { case _ => Seq.empty }
      val consumerBasedRequestStitch = recentEngagedUsersStitch.map { recentEngagedUsers =>
        ConsumersBasedRelatedTweetRequest(
          recentEngagedUsers.take(request.maxConsumerSeeds.getOrElse(0)),
          maxResults = request.maxResults,
          minCooccurrence = request.minCooccurrence,
          excludeTweetIds = Some(Seq(seedId)),
          minScore = request.minScore,
          maxTweetAgeInHours = request.maxTweetAgeInHours,
          similarityAlgorithm = request.similarityAlgorithm
        )
      }
      consumerBasedRequestStitch.map {
        (_, seedId)
      }
    }
  }

  override def getCandidatesFromStore(
    key: (ConsumersBasedRelatedTweetRequest, Long)
  ): Stitch[Seq[(Long, Double)]] = {
    Stitch
      .callFuture(uvgClient.consumersBasedRelatedTweets(key._1))
      .map { response =>
        response.tweets.map { relatedTweet =>
          (relatedTweet.tweetId, relatedTweet.score)
        }
      }
  }

  override def postProcess(
    request: UVGTweetBasedRequest,
    keys: Seq[(ConsumersBasedRelatedTweetRequest, Long)],
    resultsSeq: Seq[Seq[(Long, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val uvgCandidates = keys.zip(resultsSeq).map {
      case (key, results) =>
        val seedId = key._2
        results
          .map {
            case (id, score) => TweetMixerCandidate(id, score, seedId)
          }
    }
    TweetMixerCandidate.interleave(uvgCandidates)
  }
}
