package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.model.SimilarityEngineInfo
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.param.ConsumersBasedUserVideoGraphParams
import com.X.cr_mixer.param.GlobalParams
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.X.recos.user_video_graph.thriftscala.RelatedTweetResponse
import com.X.simclusters_v2.common.UserId
import com.X.storehaus.ReadableStore
import com.X.timelines.configapi
import com.X.util.Future
import javax.inject.Singleton

/**
 * This store uses the graph based input (a list of userIds)
 * to query consumersBasedUserVideoGraph and get their top engaged tweets
 */
@Singleton
case class ConsumersBasedUserVideoGraphSimilarityEngine(
  consumersBasedUserVideoGraphStore: ReadableStore[
    ConsumersBasedRelatedTweetRequest,
    RelatedTweetResponse
  ],
  statsReceiver: StatsReceiver)
    extends ReadableStore[
      ConsumersBasedUserVideoGraphSimilarityEngine.Query,
      Seq[TweetWithScore]
    ] {

  override def get(
    query: ConsumersBasedUserVideoGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    val consumersBasedRelatedTweetRequest =
      ConsumersBasedRelatedTweetRequest(
        query.seedWithScores.keySet.toSeq,
        maxResults = Some(query.maxResults),
        minCooccurrence = Some(query.minCooccurrence),
        minScore = Some(query.minScore),
        maxTweetAgeInHours = Some(query.maxTweetAgeInHours)
      )
    consumersBasedUserVideoGraphStore
      .get(consumersBasedRelatedTweetRequest)
      .map { relatedTweetResponseOpt =>
        relatedTweetResponseOpt.map { relatedTweetResponse =>
          relatedTweetResponse.tweets.map { tweet =>
            TweetWithScore(tweet.tweetId, tweet.score)
          }
        }
      }
  }
}

object ConsumersBasedUserVideoGraphSimilarityEngine {

  case class Query(
    seedWithScores: Map[UserId, Double],
    maxResults: Int,
    minCooccurrence: Int,
    minScore: Double,
    maxTweetAgeInHours: Int)

  def toSimilarityEngineInfo(
    score: Double
  ): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.ConsumersBasedUserVideoGraph,
      modelId = None,
      score = Some(score))
  }

  def fromParamsForRealGraphIn(
    seedWithScores: Map[UserId, Double],
    params: configapi.Params,
  ): EngineQuery[Query] = {

    EngineQuery(
      Query(
        seedWithScores = seedWithScores,
        maxResults = params(GlobalParams.MaxCandidateNumPerSourceKeyParam),
        minCooccurrence =
          params(ConsumersBasedUserVideoGraphParams.RealGraphInMinCoOccurrenceParam),
        minScore = params(ConsumersBasedUserVideoGraphParams.RealGraphInMinScoreParam),
        maxTweetAgeInHours = params(GlobalParams.MaxTweetAgeHoursParam).inHours
      ),
      params
    )
  }
}
