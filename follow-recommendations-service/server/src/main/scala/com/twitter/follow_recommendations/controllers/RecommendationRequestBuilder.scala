package com.twitter.follow_recommendations.controllers

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.models.ClientContetwittertConverter
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.follow_recommendations.models.DebugParams
import com.twitter.follow_recommendations.models.DisplayContetwittert
import com.twitter.follow_recommendations.models.RecommendationRequest
import com.twitter.follow_recommendations.{thriftscala => t}
import com.twitter.gizmoduck.thriftscala.UserType
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RecommendationRequestBuilder @Inject() (
  requestBuilderUserFetcher: RequestBuilderUserFetcher,
  statsReceiver: StatsReceiver) {
  private val scopedStats = statsReceiver.scope(this.getClass.getSimpleName)
  private val isSoftUserCounter = scopedStats.counter("is_soft_user")

  def fromThrift(tRequest: t.RecommendationRequest): Stitch[RecommendationRequest] = {
    requestBuilderUserFetcher.fetchUser(tRequest.clientContetwittert.userId).map { userOpt =>
      val isSoftUser = userOpt.etwitterists(_.userType == UserType.Soft)
      if (isSoftUser) isSoftUserCounter.incr()
      RecommendationRequest(
        clientContetwittert = ClientContetwittertConverter.fromThrift(tRequest.clientContetwittert),
        displayLocation = DisplayLocation.fromThrift(tRequest.displayLocation),
        displayContetwittert = tRequest.displayContetwittert.map(DisplayContetwittert.fromThrift),
        matwitterResults = tRequest.matwitterResults,
        cursor = tRequest.cursor,
        etwittercludedIds = tRequest.etwittercludedIds,
        fetchPromotedContent = tRequest.fetchPromotedContent,
        debugParams = tRequest.debugParams.map(DebugParams.fromThrift),
        userLocationState = tRequest.userLocationState,
        isSoftUser = isSoftUser
      )
    }

  }
}
