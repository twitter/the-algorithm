package com.X.tweetypie
package repository

import com.X.context.thriftscala.Viewer
import com.X.featureswitches.Recipient
import com.X.featureswitches.TOOClient
import com.X.featureswitches.UserAgent
import com.X.tweetypie.StatsReceiver
import com.X.tweetypie.User
import com.X.tweetypie.UserId
import com.X.tweetypie.client_id.ClientIdHelper
import com.X.tweetypie.repository.UserViewerRecipient.UserIdMismatchException

/**
 * Provides a Recipient backed by a Gizmoduck User and XContext Viewer for
 * use in FeatureSwitch validation.
 */
object UserViewerRecipient {
  object UserIdMismatchException extends Exception

  def apply(user: User, viewer: Viewer, stats: StatsReceiver): Option[Recipient] = {
    // This is a workaround for thrift API clients that allow users to Tweet on behalf
    // of other X users. This is similar to go/contributors, however some platforms
    // have enabled workflows that don't use the go/contributors auth platform, and
    // therefore the XContext Viewer isn't set up correctly for contributor requests.
    if (viewer.userId.contains(user.id)) {
      Some(new UserViewerRecipient(user, viewer))
    } else {
      val mismatchScope = stats.scope(s"user_viewer_mismatch")
      ClientIdHelper.default.effectiveClientIdRoot.foreach { clientId =>
        mismatchScope.scope("client").counter(clientId).incr()
      }
      mismatchScope.counter("total").incr()
      None
    }
  }
}

class UserViewerRecipient(
  user: User,
  viewer: Viewer)
    extends Recipient {

  if (!viewer.userId.contains(user.id)) {
    throw UserIdMismatchException
  }

  override def userId: Option[UserId] = viewer.userId

  override def userRoles: Option[Set[String]] = user.roles.map(_.roles.toSet)

  override def deviceId: Option[String] = viewer.deviceId

  override def guestId: Option[Long] = viewer.guestId

  override def languageCode: Option[String] = viewer.requestLanguageCode

  override def signupCountryCode: Option[String] = user.safety.flatMap(_.signupCountryCode)

  override def countryCode: Option[String] = viewer.requestCountryCode

  override def userAgent: Option[UserAgent] = viewer.userAgent.flatMap(UserAgent(_))

  override def isManifest: Boolean = false

  override def isVerified: Option[Boolean] = user.safety.map(_.verified)

  override def clientApplicationId: Option[Long] = viewer.clientApplicationId

  @Deprecated
  override def isTwoffice: Option[Boolean] = None

  @Deprecated
  override def tooClient: Option[TOOClient] = None

  @Deprecated
  override def highWaterMark: Option[Long] = None
}
