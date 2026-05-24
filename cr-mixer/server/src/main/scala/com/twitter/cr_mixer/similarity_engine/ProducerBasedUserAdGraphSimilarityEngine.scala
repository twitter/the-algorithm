package com.twitter.cr_mixer.similarity_engine

import com.twitter.cr_mixer.model.SimilarityEngineInfo
import com.twitter.cr_mixer.model.TweetWithScore
import com.twitter.cr_mixer.param.GlobalParams
import com.twitter.cr_mixer.param.ProducerBasedUserAdGraphParams
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.recos.user_ad_graph.thriftscala.ProducerBasedRelatedAdRequest
import com.twitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javax.inject.Singleton
import com.twitter.cr_mixer.param.GlobalParams
import com.twitter.cr_mixer.thriftscala.SimilarityEngineType
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
    extends ReadableStore[ProducerBasedUserAdGraphSimilarityEngine.Query, Seq[
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
              maxResults = Some(query.maxResults),
              minCooccurrence = Some(query.minCooccurrence),
              minScore = Some(query.minScore),
              maxNumFollowers = Some(query.maxNumFollowers),
              maxTweetAgeInHours = Some(query.maxTweetAgeInHours),
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
    maxResults: Int,
    minCooccurrence: Int, // require at least {minCooccurrence} lhs user engaged with returned tweet
    minScore: Double,
    maxNumFollowers: Int, // max number of lhs users
    maxTweetAgeInHours: Int)

  def fromParams(
    sourceId: InternalId,
    params: configapi.Params,
  ): EngineQuery[Query] = {
    EngineQuery(
      Query(
        sourceId = sourceId,
        maxResults = params(GlobalParams.MaxCandidateNumPerSourceKeyParam),
        minCooccurrence = params(ProducerBasedUserAdGraphParams.MinCoOccurrenceParam),
        maxNumFollowers = params(ProducerBasedUserAdGraphParams.MaxNumFollowersParam),
        maxTweetAgeInHours = params(GlobalParams.MaxTweetAgeHoursParam).inHours,
        minScore = params(ProducerBasedUserAdGraphParams.MinScoreParam)
      ),
      params
    )
  }
}
