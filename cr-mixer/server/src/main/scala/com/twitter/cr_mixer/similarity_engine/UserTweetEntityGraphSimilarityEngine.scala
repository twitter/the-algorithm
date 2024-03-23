package com.ExTwitter.cr_mixer.similarity_engine

import com.ExTwitter.recos.recos_common.thriftscala.SocialProofType
import com.ExTwitter.cr_mixer.model.SimilarityEngineInfo
import com.ExTwitter.cr_mixer.model.TweetWithScoreAndSocialProof
import com.ExTwitter.cr_mixer.param.UtegTweetGlobalParams
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.recos.user_tweet_entity_graph.thriftscala.TweetEntityDisplayLocation
import com.ExTwitter.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityGraph
import com.ExTwitter.recos.user_tweet_entity_graph.thriftscala.RecommendTweetEntityRequest
import com.ExTwitter.recos.user_tweet_entity_graph.thriftscala.RecommendationType
import com.ExTwitter.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityRecommendationUnion.TweetRec
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.timelines.configapi
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Future
import javax.inject.Singleton

@Singleton
case class UserTweetEntityGraphSimilarityEngine(
  userTweetEntityGraph: UserTweetEntityGraph.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    extends ReadableStore[
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
        maxResultsByType = Some(Map(RecommendationType.Tweet -> query.maxUtegCandidates)),
        maxTweetAgeInMillis = Some(query.maxTweetAge.inMilliseconds),
        excludedTweetIds = query.excludedTweetIds,
        maxUserSocialProofSize = Some(UserTweetEntityGraphSimilarityEngine.MaxUserSocialProofSize),
        maxTweetSocialProofSize =
          Some(UserTweetEntityGraphSimilarityEngine.MaxTweetSocialProofSize),
        minUserSocialProofSizes = Some(Map(RecommendationType.Tweet -> 1)),
        tweetTypes = None,
        socialProofTypes = query.socialProofTypes,
        socialProofTypeUnions = None,
        tweetAuthors = None,
        maxEngagementAgeInMillis = None,
        excludedTweetAuthors = None,
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

  private val MaxUserSocialProofSize = 10
  private val MaxTweetSocialProofSize = 10

  def toSimilarityEngineInfo(score: Double): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.Uteg,
      modelId = None,
      score = Some(score))
  }

  case class Query(
    userId: UserId,
    seedsWithWeights: Map[UserId, Double],
    excludedTweetIds: Option[Seq[Long]] = None,
    maxUtegCandidates: Int,
    maxTweetAge: Duration,
    socialProofTypes: Option[Seq[SocialProofType]])

  def fromParams(
    userId: UserId,
    seedsWithWeights: Map[UserId, Double],
    excludedTweetIds: Option[Seq[TweetId]] = None,
    params: configapi.Params,
  ): EngineQuery[Query] = {
    EngineQuery(
      Query(
        userId = userId,
        seedsWithWeights = seedsWithWeights,
        excludedTweetIds = excludedTweetIds,
        maxUtegCandidates = params(UtegTweetGlobalParams.MaxUtegCandidatesToRequestParam),
        maxTweetAge = params(UtegTweetGlobalParams.CandidateRefreshSinceTimeOffsetHoursParam),
        socialProofTypes = Some(Seq(SocialProofType.Favorite))
      ),
      params
    )
  }
}
