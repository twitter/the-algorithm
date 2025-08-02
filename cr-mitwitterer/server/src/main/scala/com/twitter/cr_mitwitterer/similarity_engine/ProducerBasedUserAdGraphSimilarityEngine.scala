package com.twitter.cr_mitwitterer.similarity_engine

import com.twitter.cr_mitwitterer.model.SimilarityEngineInfo
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.cr_mitwitterer.param.ProducerBasedUserAdGraphParams
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.recos.user_ad_graph.thriftscala.ProducerBasedRelatedAdRequest
import com.twitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javatwitter.inject.Singleton
import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.frigate.common.util.StatsUtil
import com.twitter.timelines.configapi

/**
 * This store looks for similar tweets from UserAdGraph for a Source ProducerId
 * For a query producerId,User Tweet Graph (UAG),
 * lets us find out which ad tweets the query producer's followers co-engaged
 */
@Singleton
case class ProducerBasedUserAdGraphSimilarityEngine(
  userAdGraphService: UserAdGraph.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    etwittertends ReadableStore[ProducerBasedUserAdGraphSimilarityEngine.Query, Seq[
      TweetWithScore
    ]] {

  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val fetchCandidatesStat = stats.scope("fetchCandidates")

  override def get(
    query: ProducerBasedUserAdGraphSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    query.sourceId match {
      case InternalId.UserId(producerId) =>
        StatsUtil.trackOptionItemsStats(fetchCandidatesStat) {
          val relatedAdRequest =
            ProducerBasedRelatedAdRequest(
              producerId,
              matwitterResults = Some(query.matwitterResults),
              minCooccurrence = Some(query.minCooccurrence),
              minScore = Some(query.minScore),
              matwitterNumFollowers = Some(query.matwitterNumFollowers),
              matwitterTweetAgeInHours = Some(query.matwitterTweetAgeInHours),
            )

          userAdGraphService.producerBasedRelatedAds(relatedAdRequest).map { relatedAdResponse =>
            val candidates =
              relatedAdResponse.adTweets.map(tweet => TweetWithScore(tweet.adTweetId, tweet.score))
            Some(candidates)
          }
        }
      case _ =>
        Future.value(None)
    }
  }
}

object ProducerBasedUserAdGraphSimilarityEngine {

  def toSimilarityEngineInfo(score: Double): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.ProducerBasedUserAdGraph,
      modelId = None,
      score = Some(score))
  }

  case class Query(
    sourceId: InternalId,
    matwitterResults: Int,
    minCooccurrence: Int, // require at least {minCooccurrence} lhs user engaged with returned tweet
    minScore: Double,
    matwitterNumFollowers: Int, // matwitter number of lhs users
    matwitterTweetAgeInHours: Int)

  def fromParams(
    sourceId: InternalId,
    params: configapi.Params,
  ): EngineQuery[Query] = {
    EngineQuery(
      Query(
        sourceId = sourceId,
        matwitterResults = params(GlobalParams.MatwitterCandidateNumPerSourceKeyParam),
        minCooccurrence = params(ProducerBasedUserAdGraphParams.MinCoOccurrenceParam),
        matwitterNumFollowers = params(ProducerBasedUserAdGraphParams.MatwitterNumFollowersParam),
        matwitterTweetAgeInHours = params(GlobalParams.MatwitterTweetAgeHoursParam).inHours,
        minScore = params(ProducerBasedUserAdGraphParams.MinScoreParam)
      ),
      params
    )
  }
}
