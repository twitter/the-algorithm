package com.twitter.cr_mitwitterer.similarity_engine

import com.twitter.recos.recos_common.thriftscala.SocialProofType
import com.twitter.cr_mitwitterer.model.SimilarityEngineInfo
import com.twitter.cr_mitwitterer.model.TweetWithScoreAndSocialProof
import com.twitter.cr_mitwitterer.param.UtegTweetGlobalParams
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.recos.user_tweet_entity_graph.thriftscala.TweetEntityDisplayLocation
import com.twitter.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityGraph
import com.twitter.recos.user_tweet_entity_graph.thriftscala.RecommendTweetEntityRequest
import com.twitter.recos.user_tweet_entity_graph.thriftscala.RecommendationType
import com.twitter.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityRecommendationUnion.TweetRec
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.configapi
import com.twitter.util.Duration
import com.twitter.util.Future
import javatwitter.inject.Singleton

@Singleton
case class UserTweetEntityGraphSimilarityEngine(
  userTweetEntityGraph: UserTweetEntityGraph.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    etwittertends ReadableStore[
      UserTweetEntityGraphSimilarityEngine.Query,
      Seq[TweetWithScoreAndSocialProof]
    ] {

  override def get(
    query: UserTweetEntityGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScoreAndSocialProof]]] = {
    val recommendTweetEntityRequest =
      RecommendTweetEntityRequest(
        requesterId = query.userId,
        displayLocation = TweetEntityDisplayLocation.HomeTimeline,
        recommendationTypes = Seq(RecommendationType.Tweet),
        seedsWithWeights = query.seedsWithWeights,
        matwitterResultsByType = Some(Map(RecommendationType.Tweet -> query.matwitterUtegCandidates)),
        matwitterTweetAgeInMillis = Some(query.matwitterTweetAge.inMilliseconds),
        etwittercludedTweetIds = query.etwittercludedTweetIds,
        matwitterUserSocialProofSize = Some(UserTweetEntityGraphSimilarityEngine.MatwitterUserSocialProofSize),
        matwitterTweetSocialProofSize =
          Some(UserTweetEntityGraphSimilarityEngine.MatwitterTweetSocialProofSize),
        minUserSocialProofSizes = Some(Map(RecommendationType.Tweet -> 1)),
        tweetTypes = None,
        socialProofTypes = query.socialProofTypes,
        socialProofTypeUnions = None,
        tweetAuthors = None,
        matwitterEngagementAgeInMillis = None,
        etwittercludedTweetAuthors = None,
      )

    userTweetEntityGraph
      .recommendTweets(recommendTweetEntityRequest)
      .map { recommendTweetsResponse =>
        val candidates = recommendTweetsResponse.recommendations.flatMap {
          case TweetRec(recommendation) =>
            Some(
              TweetWithScoreAndSocialProof(
                recommendation.tweetId,
                recommendation.score,
                recommendation.socialProofByType.toMap))
          case _ => None
        }
        Some(candidates)
      }
  }
}

object UserTweetEntityGraphSimilarityEngine {

  private val MatwitterUserSocialProofSize = 10
  private val MatwitterTweetSocialProofSize = 10

  def toSimilarityEngineInfo(score: Double): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.Uteg,
      modelId = None,
      score = Some(score))
  }

  case class Query(
    userId: UserId,
    seedsWithWeights: Map[UserId, Double],
    etwittercludedTweetIds: Option[Seq[Long]] = None,
    matwitterUtegCandidates: Int,
    matwitterTweetAge: Duration,
    socialProofTypes: Option[Seq[SocialProofType]])

  def fromParams(
    userId: UserId,
    seedsWithWeights: Map[UserId, Double],
    etwittercludedTweetIds: Option[Seq[TweetId]] = None,
    params: configapi.Params,
  ): EngineQuery[Query] = {
    EngineQuery(
      Query(
        userId = userId,
        seedsWithWeights = seedsWithWeights,
        etwittercludedTweetIds = etwittercludedTweetIds,
        matwitterUtegCandidates = params(UtegTweetGlobalParams.MatwitterUtegCandidatesToRequestParam),
        matwitterTweetAge = params(UtegTweetGlobalParams.CandidateRefreshSinceTimeOffsetHoursParam),
        socialProofTypes = Some(Seq(SocialProofType.Favorite))
      ),
      params
    )
  }
}
