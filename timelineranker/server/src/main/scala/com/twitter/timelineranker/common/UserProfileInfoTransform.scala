package com.twitter.timelineranker.common

import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.model.RecapQuery
import com.twitter.timelines.clients.gizmoduck.GizmoduckClient
import com.twitter.timelines.clients.gizmoduck.UserProfileInfo
import com.twitter.timelines.util.FailOpenHandler
import com.twitter.util.Future

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
