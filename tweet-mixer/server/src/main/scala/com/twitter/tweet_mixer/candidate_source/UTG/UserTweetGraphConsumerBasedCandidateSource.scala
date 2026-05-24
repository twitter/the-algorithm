package com.twitter.tweet_mixer.candidate_source.UTG

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.util.DefaultTimer
import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.recos.user_tweet_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.twitter.recos.user_tweet_graph.thriftscala.UserTweetGraph
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
class UserTweetGraphConsumerBasedCandidateSource @Inject() (
  utgClient: UserTweetGraph.MethodPerEndpoint,
  recentEngagedUsersCandidateSource: RecentEngagedUsersCandidateSource,
  memcacheClient: MemcacheStitchClient)
    extends MemcachedCandidateSource[
      UTGTweetBasedRequest,
      (ConsumersBasedRelatedTweetRequest, Long),
      (Long, Double),
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "UserTweetGraphConsumerBasedCandidateSource"
  )

  private val defaultVersion: Long = 0

  implicit val timer = DefaultTimer

  override val TTL = Utils.randomizedTTL(600) //10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: (ConsumersBasedRelatedTweetRequest, Long)): String =
    key match {
      case (request, seedId) =>
        "UTGExpansion:" +
          seedId.toString +
          request.minScore.toString +
          request.maxResults.toString +
          request.maxTweetAgeInHours.toString +
          request.minCooccurrence.toString +
          request.similarityAlgorithm.toString
    }

  override val valueTransformer: Transformer[Seq[(Long, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer

  override def enableCache(request: UTGTweetBasedRequest): Boolean = request.enableCache

  override def getKeys(
    request: UTGTweetBasedRequest
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
      .callFuture(utgClient.consumersBasedRelatedTweets(key._1))
      .map { response =>
        response.tweets.map { relatedTweet =>
          (relatedTweet.tweetId, relatedTweet.score)
        }
      }
  }

  override def postProcess(
    request: UTGTweetBasedRequest,
    keys: Seq[(ConsumersBasedRelatedTweetRequest, Long)],
    resultsSeq: Seq[Seq[(Long, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val utgCandidates = keys.zip(resultsSeq).map {
      case (key, results) =>
        val seedId = key._2
        results
          .map {
            case (id, score) => TweetMixerCandidate(id, score, seedId)
          }
    }
    TweetMixerCandidate.interleave(utgCandidates)
  }
}
