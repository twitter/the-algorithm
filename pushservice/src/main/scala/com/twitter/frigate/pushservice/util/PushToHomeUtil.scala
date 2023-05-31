package com.twitter.frigate.pushservice.util

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.store.deviceinfo.DeviceInfo
import com.twitter.frigate.pushservice.model.PushTypes.Target
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
object PushToHomeUtil {
  def getIbis2ModelValue(
    deviceInfoOpt: Option[DeviceInfo],
    target: Target,
    stats: StatsReceiver
  ): Option[Map[String, String]] = {
    deviceInfoOpt.flatMap { deviceInfo =>
      val isAndroidEnabled = deviceInfo.isLandOnHomeAndroid && target.params(
        PushFeatureSwitchParams.EnableTweetPushToHomeAndroid)
      val isIOSEnabled = deviceInfo.isLandOnHomeiOS && target.params(
        PushFeatureSwitchParams.EnableTweetPushToHomeiOS)
      if (isAndroidEnabled || isIOSEnabled) {
        stats.counter("enable_push_to_home").incr()
        Some(Map("is_land_on_home" -> "true"))
      } else None
    }
  }
}
