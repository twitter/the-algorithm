package com.X.frigate.pushservice.store

import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.X.follow_recommendations.thriftscala.Recommendation
import com.X.follow_recommendations.thriftscala.RecommendationRequest
import com.X.follow_recommendations.thriftscala.RecommendationResponse
import com.X.follow_recommendations.thriftscala.UserRecommendation
import com.X.inject.Logging
import com.X.storehaus.ReadableStore
import com.X.util.Future

case class FollowRecommendationsStore(
  frsClient: FollowRecommendationsThriftService.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    extends ReadableStore[RecommendationRequest, RecommendationResponse]
    with Logging {

  private val scopedStats = statsReceiver.scope(getClass.getSimpleName)
  private val requests = scopedStats.counter("requests")
  private val valid = scopedStats.counter("valid")
  private val invalid = scopedStats.counter("invalid")
  private val numTotalResults = scopedStats.stat("total_results")
  private val numValidResults = scopedStats.stat("valid_results")

  override def get(request: RecommendationRequest): Future[Option[RecommendationResponse]] = {
    requests.incr()
    frsClient.getRecommendations(request).map { response =>
      numTotalResults.add(response.recommendations.size)
      val validRecs = response.recommendations.filter {
        case Recommendation.User(_: UserRecommendation) =>
          valid.incr()
          true
        case _ =>
          invalid.incr()
          false
      }

      numValidResults.add(validRecs.size)
      Some(
        RecommendationResponse(
          recommendations = validRecs
        ))
    }
  }
}
