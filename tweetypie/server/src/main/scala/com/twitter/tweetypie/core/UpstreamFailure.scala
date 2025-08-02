package com.twitter.tweetypie.core

import scala.util.control.NoStackTrace

/**
 * Parent etwitterception class for failures while talking to upstream services.  These will
 * be counted and then converted to servo.ServerError.DependencyError
 */
sealed abstract class UpstreamFailure(msg: String) etwittertends Etwitterception(msg) with NoStackTrace

object UpstreamFailure {
  case class SnowflakeFailure(t: Throwable) etwittertends UpstreamFailure(t.toString)

  case object UserProfileEmptyEtwitterception etwittertends UpstreamFailure("User.profile is empty")

  case object UserViewEmptyEtwitterception etwittertends UpstreamFailure("User.view is empty")

  case object UserSafetyEmptyEtwitterception etwittertends UpstreamFailure("User.safety is empty")

  case class TweetLookupFailure(t: Throwable) etwittertends UpstreamFailure(t.toString)

  case class UserLookupFailure(t: Throwable) etwittertends UpstreamFailure(t.toString)

  case class DeviceSourceLookupFailure(t: Throwable) etwittertends UpstreamFailure(t.toString)

  case class TFlockLookupFailure(t: Throwable) etwittertends UpstreamFailure(t.toString)

  case class UrlShorteningFailure(t: Throwable) etwittertends UpstreamFailure(t.toString)

  case object MediaShortenUrlMalformedFailure
      etwittertends UpstreamFailure("Media shortened url is malformed")

  case object MediaEtwitterpandedUrlNotValidFailure
      etwittertends UpstreamFailure("Talon returns badInput on media etwitterpanded url")

  case class MediaServiceServerError(t: Throwable) etwittertends UpstreamFailure(t.toString)
}
