package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.model.SimilarityEngineInfo
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.param.ConsumersBasedUserAdGraphParams
import com.X.cr_mixer.param.GlobalParams
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.recos.user_ad_graph.thriftscala.ConsumersBasedRelatedAdRequest
import com.X.recos.user_ad_graph.thriftscala.RelatedAdResponse
import com.X.simclusters_v2.common.UserId
import com.X.storehaus.ReadableStore
import com.X.timelines.configapi
import com.X.util.Future
import javax.inject.Singleton

/**
 * This store uses the graph based input (a list of userIds)
 * to query consumersBasedUserAdGraph and get their top engaged ad tweets
 */
@Singleton
case class ConsumersBasedUserAdGraphSimilarityEngine(
  consumersBasedUserAdGraphStore: ReadableStore[
    ConsumersBasedRelatedAdRequest,
    RelatedAdResponse
  ],
  statsReceiver: StatsReceiver)
    extends ReadableStore[
      ConsumersBasedUserAdGraphSimilarityEngine.Query,
      Seq[TweetWithScore]
    ] {

  override def get(
    query: ConsumersBasedUserAdGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    val consumersBasedRelatedAdRequest =
      ConsumersBasedRelatedAdRequest(
        query.seedWithScores.keySet.toSeq,
        maxResults = Some(query.maxResults),
        minCooccurrence = Some(query.minCooccurrence),
        minScore = Some(query.minScore),
        maxTweetAgeInHours = Some(query.maxTweetAgeInHours)
      )
    consumersBasedUserAdGraphStore
      .get(consumersBasedRelatedAdRequest)
      .map { relatedAdResponseOpt =>
        relatedAdResponseOpt.map { relatedAdResponse =>
          relatedAdResponse.adTweets.map { tweet =>
            TweetWithScore(tweet.adTweetId, tweet.score)
          }
        }
      }
  }
}

object ConsumersBasedUserAdGraphSimilarityEngine {

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
      similarityEngineType = SimilarityEngineType.ConsumersBasedUserAdGraph,
      modelId = None,
      score = Some(score))
  }

  def fromParams(
    seedWithScores: Map[UserId, Double],
    params: configapi.Params,
  ): EngineQuery[Query] = {

    EngineQuery(
      Query(
        seedWithScores = seedWithScores,
        maxResults = params(GlobalParams.MaxCandidateNumPerSourceKeyParam),
        minCooccurrence = params(ConsumersBasedUserAdGraphParams.MinCoOccurrenceParam),
        minScore = params(ConsumersBasedUserAdGraphParams.MinScoreParam),
        maxTweetAgeInHours = params(GlobalParams.MaxTweetAgeHoursParam).inHours,
      ),
      params
    )
  }
}
