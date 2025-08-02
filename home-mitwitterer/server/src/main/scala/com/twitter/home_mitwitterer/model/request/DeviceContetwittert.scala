package com.twitter.home_mitwitterer.model.request

import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.{timelineservice => tls}

case class DeviceContetwittert(
  isPolling: Option[Boolean],
  requestContetwittert: Option[String],
  latestControlAvailable: Option[Boolean],
  autoplayEnabled: Option[Boolean]) {

  lazy val requestContetwittertValue: Option[DeviceContetwittert.RequestContetwittert.Value] =
    requestContetwittert.flatMap { value =>
      val normalizedValue = value.trim.toLowerCase()
      DeviceContetwittert.RequestContetwittert.values.find(_.toString == normalizedValue)
    }

  def toTimelineServiceDeviceContetwittert(clientContetwittert: ClientContetwittert): tls.DeviceContetwittert =
    tls.DeviceContetwittert(
      countryCode = clientContetwittert.countryCode,
      languageCode = clientContetwittert.languageCode,
      clientAppId = clientContetwittert.appId,
      ipAddress = clientContetwittert.ipAddress,
      guestId = clientContetwittert.guestId,
      sessionId = None,
      timezone = None,
      userAgent = clientContetwittert.userAgent,
      deviceId = clientContetwittert.deviceId,
      isPolling = isPolling,
      requestProvenance = requestContetwittert,
      referrer = None,
      tfeAuthHeader = None,
      mobileDeviceId = clientContetwittert.mobileDeviceId,
      isSessionStart = None,
      displaySize = None,
      isURTRequest = Some(true),
      latestControlAvailable = latestControlAvailable,
      guestIdMarketing = clientContetwittert.guestIdMarketing,
      isInternalOrTwoffice = clientContetwittert.isTwoffice,
      browserNotificationPermission = None,
      guestIdAds = clientContetwittert.guestIdAds,
    )
}

object DeviceContetwittert {
  val Empty: DeviceContetwittert = DeviceContetwittert(
    isPolling = None,
    requestContetwittert = None,
    latestControlAvailable = None,
    autoplayEnabled = None
  )

  /**
   * Constants which reflect valid client request provenances (why a request was initiated, encoded
   * by the "request_contetwittert" HTTP parameter).
   */
  object RequestContetwittert etwittertends Enumeration {
    val Auto = Value("auto")
    val Foreground = Value("foreground")
    val Gap = Value("gap")
    val Launch = Value("launch")
    val ManualRefresh = Value("manual_refresh")
    val Navigate = Value("navigate")
    val Polling = Value("polling")
    val PullToRefresh = Value("ptr")
    val Signup = Value("signup")
    val TweetSelfThread = Value("tweet_self_thread")
    val BackgroundFetch = Value("background_fetch")
  }
}

trait HasDeviceContetwittert {
  def deviceContetwittert: Option[DeviceContetwittert]
}
