package com.twitter.home_mitwitterer.marshaller.request

import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class DeviceContetwittertUnmarshaller @Inject() () {

  def apply(deviceContetwittert: t.DeviceContetwittert): DeviceContetwittert = {
    DeviceContetwittert(
      isPolling = deviceContetwittert.isPolling,
      requestContetwittert = deviceContetwittert.requestContetwittert,
      latestControlAvailable = deviceContetwittert.latestControlAvailable,
      autoplayEnabled = deviceContetwittert.autoplayEnabled
    )
  }
}
