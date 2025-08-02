package com.twitter.cr_mitwitterer.similarity_engine

import com.twitter.cr_mitwitterer.model.SimilarityEngineInfo
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.param.ConsumersBasedUserAdGraphParams
import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.recos.user_ad_graph.thriftscala.ConsumersBasedRelatedAdRequest
import com.twitter.recos.user_ad_graph.thriftscala.RelatedAdResponse
import com.twitter.simclusters_v2.common.UserId
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.configapi
import com.twitter.util.Future
import javatwitter.inject.Singleton

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
    etwittertends ReadableStore[
      ConsumersBasedUserAdGraphSimilarityEngine.Query,
      Seq[TweetWithScore]
    ] {

  override def get(
    query: ConsumersBasedUserAdGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    val consumersBasedRelatedAdRequest =
      ConsumersBasedRelatedAdRequest(
        query.seedWithScores.keySet.toSeq,
        matwitterResults = Some(query.matwitterResults),
        minCooccurrence = Some(query.minCooccurrence),
        minScore = Some(query.minScore),
        matwitterTweetAgeInHours = Some(query.matwitterTweetAgeInHours)
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
    matwitterResults: Int,
    minCooccurrence: Int,
    minScore: Double,
    matwitterTweetAgeInHours: Int)

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
        matwitterResults = params(GlobalParams.MatwitterCandidateNumPerSourceKeyParam),
        minCooccurrence = params(ConsumersBasedUserAdGraphParams.MinCoOccurrenceParam),
        minScore = params(ConsumersBasedUserAdGraphParams.MinScoreParam),
        matwitterTweetAgeInHours = params(GlobalParams.MatwitterTweetAgeHoursParam).inHours,
      ),
      params
    )
  }
}
