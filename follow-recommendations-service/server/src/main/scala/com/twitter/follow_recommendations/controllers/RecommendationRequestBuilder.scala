package com.ExTwitter.follow_recommendations.controllers

import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.models.ClientContextConverter
import com.ExTwitter.follow_recommendations.common.models.DisplayLocation
import com.ExTwitter.follow_recommendations.models.DebugParams
import com.ExTwitter.follow_recommendations.models.DisplayContext
import com.ExTwitter.follow_recommendations.models.RecommendationRequest
import com.ExTwitter.follow_recommendations.{thriftscala => t}
import com.ExTwitter.gizmoduck.thriftscala.UserType
import com.ExTwitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationRequestBuilder @Inject() (
  requestBuilderUserFetcher: RequestBuilderUserFetcher,
  statsReceiver: StatsReceiver) {
  private val scopedStats = statsReceiver.scope(this.getClass.getSimpleName)
  private val isSoftUserCounter = scopedStats.counter("is_soft_user")

  def fromThrift(tRequest: t.RecommendationRequest): Stitch[RecommendationRequest] = {
    requestBuilderUserFetcher.fetchUser(tRequest.clientContext.userId).map { userOpt =>
      val isSoftUser = userOpt.exists(_.userType == UserType.Soft)
      if (isSoftUser) isSoftUserCounter.incr()
      RecommendationRequest(
        clientContext = ClientContextConverter.fromThrift(tRequest.clientContext),
        displayLocation = DisplayLocation.fromThrift(tRequest.displayLocation),
        displayContext = tRequest.displayContext.map(DisplayContext.fromThrift),
        maxResults = tRequest.maxResults,
        cursor = tRequest.cursor,
        excludedIds = tRequest.excludedIds,
        fetchPromotedContent = tRequest.fetchPromotedContent,
        debugParams = tRequest.debugParams.map(DebugParams.fromThrift),
        userLocationState = tRequest.userLocationState,
        isSoftUser = isSoftUser
      )
    }

  }
}
