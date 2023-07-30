package com.X.frigate.pushservice.util

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.deviceinfo.DeviceInfo
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
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
