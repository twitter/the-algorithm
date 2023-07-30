package com.X.timelineranker.common

import com.X.servo.util.FutureArrow
import com.X.timelineranker.model.RecapQuery
import com.X.timelines.clients.gizmoduck.GizmoduckClient
import com.X.timelines.clients.gizmoduck.UserProfileInfo
import com.X.timelines.util.FailOpenHandler
import com.X.util.Future

object UserProfileInfoTransform {
  val EmptyUserProfileInfo: UserProfileInfo = UserProfileInfo(None, None, None, None)
  val EmptyUserProfileInfoFuture: Future[UserProfileInfo] = Future.value(EmptyUserProfileInfo)
}

/**
 * FutureArrow which fetches user profile info
 * It should be run in parallel with the main pipeline which fetches and hydrates CandidateTweets
 */
class UserProfileInfoTransform(handler: FailOpenHandler, gizmoduckClient: GizmoduckClient)
    extends FutureArrow[RecapQuery, UserProfileInfo] {
  import UserProfileInfoTransform._
  override def apply(request: RecapQuery): Future[UserProfileInfo] = {
    handler {
      gizmoduckClient.getProfileInfo(request.userId).map { profileInfoOpt =>
        profileInfoOpt.getOrElse(EmptyUserProfileInfo)
      }
    } { _: Throwable => EmptyUserProfileInfoFuture }
  }
}
