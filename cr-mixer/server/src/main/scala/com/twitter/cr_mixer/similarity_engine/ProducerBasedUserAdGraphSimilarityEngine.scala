package com.ExTwitter.cr_mixer.similarity_engine

import com.ExTwitter.cr_mixer.model.SimilarityEngineInfo
import com.ExTwitter.cr_mixer.model.TweetWithScore
import com.ExTwitter.cr_mixer.param.GlobalParams
import com.ExTwitter.cr_mixer.param.ProducerBasedUserAdGraphParams
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.recos.user_ad_graph.thriftscala.ProducerBasedRelatedAdRequest
import com.ExTwitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.ExTwitter.simclusters_v2.thriftscala.InternalId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future
import javax.inject.Singleton
import com.ExTwitter.cr_mixer.param.GlobalParams
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.frigate.common.util.StatsUtil
import com.ExTwitter.timelines.configapi

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
