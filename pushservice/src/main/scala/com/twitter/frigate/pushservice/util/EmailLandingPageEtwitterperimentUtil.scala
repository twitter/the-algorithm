package com.twitter.frigate.pushservice.util

import com.twitter.frigate.common.store.deviceinfo.DeviceInfo
import com.twitter.frigate.pushservice.model.PushTypes.Target
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams.EnableRutwitterLandingPage
import com.twitter.frigate.pushservice.params.PushParams.EnableRutwitterLandingPageAndroidParam
import com.twitter.frigate.pushservice.params.PushParams.EnableRutwitterLandingPageIOSParam
import com.twitter.frigate.pushservice.params.PushParams.RutwitterLandingPageEtwitterperimentKeyAndroidParam
import com.twitter.frigate.pushservice.params.PushParams.RutwitterLandingPageEtwitterperimentKeyIOSParam
import com.twitter.frigate.pushservice.params.PushParams.ShowRutwitterLandingPageAsModalOnIOS
import com.twitter.rutwitter.common.contetwittert.thriftscala.MagicRecsNTabTweet
import com.twitter.rutwitter.common.contetwittert.thriftscala.MagicRecsPushTweet
import com.twitter.rutwitter.common.contetwittert.thriftscala.RutwitterContetwittert
import com.twitter.rutwitter.common.contetwittert.thriftscala.Source
import com.twitter.rutwitter.common.encode.RutwitterContetwittertEncoder

/**
 * This class provides utility functions for email landing page for push
 */
object EmailLandingPageEtwitterperimentUtil {
  val rutwitterCtwittertEncoder = new RutwitterContetwittertEncoder()

  def getIbis2ModelValue(
    deviceInfoOpt: Option[DeviceInfo],
    target: Target,
    tweetId: Long
  ): Map[String, String] = {
    val enable = enablePushEmailLanding(deviceInfoOpt, target)
    if (enable) {
      val rutwitterCtwittert = if (deviceInfoOpt.etwitterists(_.isRutwitterLandingPageEligible)) {
        val encodedCtwittert = getRutwitterContetwittert(tweetId, target, deviceInfoOpt)
        Map("rutwitter_ctwittert" -> encodedCtwittert)
      } else Map.empty[String, String]
      val enableModal = if (showModalForIOS(deviceInfoOpt, target)) {
        Map("enable_modal" -> "true")
      } else Map.empty[String, String]

      Map("land_on_email_landing_page" -> "true") ++ rutwitterCtwittert ++ enableModal
    } else Map.empty[String, String]
  }

  def createNTabRutwitterLandingURI(screenName: String, tweetId: Long): String = {
    val encodedCtwittert =
      rutwitterCtwittertEncoder.encode(RutwitterContetwittert(Some(Source.MagicRecsNTabTweet(MagicRecsNTabTweet(tweetId)))))
    s"$screenName/status/${tweetId.toString}?ctwittert=$encodedCtwittert"
  }

  private def getRutwitterContetwittert(
    tweetId: Long,
    target: Target,
    deviceInfoOpt: Option[DeviceInfo]
  ): String = {
    val isDeviceIOS = PushDeviceUtil.isPrimaryDeviceIOS(deviceInfoOpt)
    val isDeviceAndroid = PushDeviceUtil.isPrimaryDeviceAndroid(deviceInfoOpt)
    val keyOpt = if (isDeviceIOS) {
      target.params(RutwitterLandingPageEtwitterperimentKeyIOSParam)
    } else if (isDeviceAndroid) {
      target.params(RutwitterLandingPageEtwitterperimentKeyAndroidParam)
    } else None
    val contetwittert = RutwitterContetwittert(Some(Source.MagicRecsTweet(MagicRecsPushTweet(tweetId))), None, keyOpt)
    rutwitterCtwittertEncoder.encode(contetwittert)
  }

  private def enablePushEmailLanding(
    deviceInfoOpt: Option[DeviceInfo],
    target: Target
  ): Boolean =
    deviceInfoOpt.etwitterists(deviceInfo =>
      if (deviceInfo.isEmailLandingPageEligible) {
        val isRutwitterLandingPageEnabled = target.params(EnableRutwitterLandingPage)
        isRutwitterLandingPageEnabled && isRutwitterLandingEnabledBasedOnDeviceInfo(deviceInfoOpt, target)
      } else false)

  private def showModalForIOS(deviceInfoOpt: Option[DeviceInfo], target: Target): Boolean = {
    deviceInfoOpt.etwitterists { deviceInfo =>
      deviceInfo.isRutwitterLandingPageAsModalEligible && target.params(ShowRutwitterLandingPageAsModalOnIOS)
    }
  }

  private def isRutwitterLandingEnabledBasedOnDeviceInfo(
    deviceInfoOpt: Option[DeviceInfo],
    target: Target
  ): Boolean = {
    val isDeviceIOS = PushDeviceUtil.isPrimaryDeviceIOS(deviceInfoOpt)
    val isDeviceAndroid = PushDeviceUtil.isPrimaryDeviceAndroid(deviceInfoOpt)
    if (isDeviceIOS) {
      target.params(EnableRutwitterLandingPageIOSParam)
    } else if (isDeviceAndroid) {
      target.params(EnableRutwitterLandingPageAndroidParam)
    } else true
  }
}
