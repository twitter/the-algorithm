package com.X.follow_recommendations.controllers

import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.ClientContextConverter
import com.X.follow_recommendations.common.models.DebugOptions
import com.X.follow_recommendations.common.models.DisplayLocation
import com.X.follow_recommendations.models.DebugParams
import com.X.follow_recommendations.models.ScoringUserRequest
import com.X.timelines.configapi.Params
import javax.inject.Inject
import javax.inject.Singleton
import com.X.follow_recommendations.{thriftscala => t}
import com.X.gizmoduck.thriftscala.UserType
import com.X.stitch.Stitch

@Singleton
class ScoringUserRequestBuilder @Inject() (
  requestBuilderUserFetcher: RequestBuilderUserFetcher,
  candidateUserDebugParamsBuilder: CandidateUserDebugParamsBuilder,
  statsReceiver: StatsReceiver) {
  private val scopedStats = statsReceiver.scope(this.getClass.getSimpleName)
  private val isSoftUserCounter = scopedStats.counter("is_soft_user")

  def fromThrift(req: t.ScoringUserRequest): Stitch[ScoringUserRequest] = {
    requestBuilderUserFetcher.fetchUser(req.clientContext.userId).map { userOpt =>
      val isSoftUser = userOpt.exists(_.userType == UserType.Soft)
      if (isSoftUser) isSoftUserCounter.incr()

      val candidateUsersParamsMap = candidateUserDebugParamsBuilder.fromThrift(req)
      val candidates = req.candidates.map { candidate =>
        CandidateUser
          .fromUserRecommendation(candidate).copy(params =
            candidateUsersParamsMap.paramsMap.getOrElse(candidate.userId, Params.Invalid))
      }

      ScoringUserRequest(
        clientContext = ClientContextConverter.fromThrift(req.clientContext),
        displayLocation = DisplayLocation.fromThrift(req.displayLocation),
        params = Params.Empty,
        debugOptions = req.debugParams.map(DebugOptions.fromDebugParamsThrift),
        recentFollowedUserIds = None,
        recentFollowedByUserIds = None,
        wtfImpressions = None,
        similarToUserIds = Nil,
        candidates = candidates,
        debugParams = req.debugParams.map(DebugParams.fromThrift),
        isSoftUser = isSoftUser
      )
    }
  }

}
