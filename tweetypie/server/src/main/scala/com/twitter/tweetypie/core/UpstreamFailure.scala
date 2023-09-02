package com.twitter.tweetypie.core

import scala.util.control.NoStackTrace

/**
 * Parent exception class for failures while talking to upstream services.  These will
 * be counted and then converted to servo.ServerError.DependencyError
 */
sealed abstract class UpstreamFailure(msg: String) extends Exception(msg) with NoStackTrace

object UpstreamFailure {
  case class SnowflakeFailure(t: Throwable) extends UpstreamFailure(t.toString)

  case object UserProfileEmptyException extends UpstreamFailure("User.profile is empty")

  case object UserViewEmptyException extends UpstreamFailure("User.view is empty")

  case object UserSafetyEmptyException extends UpstreamFailure("User.safety is empty")

  case class TweetLookupFailure(t: Throwable) extends UpstreamFailure(t.toString)

  case class UserLookupFailure(t: Throwable) extends UpstreamFailure(t.toString)

  case class DeviceSourceLookupFailure(t: Throwable) extends UpstreamFailure(t.toString)

  case class TFlockLookupFailure(t: Throwable) extends UpstreamFailure(t.toString)

  case class UrlShorteningFailure(t: Throwable) extends UpstreamFailure(t.toString)

  case object MediaShortenUrlMalformedFailure
      extends UpstreamFailure("Media shortened url is malformed")

  case object MediaExpandedUrlNotValidFailure
      extends UpstreamFailure("Talon returns badInput on media expanded url")

  case class MediaServiceServerError(t: Throwable) extends UpstreamFailure(t.toString)
}
