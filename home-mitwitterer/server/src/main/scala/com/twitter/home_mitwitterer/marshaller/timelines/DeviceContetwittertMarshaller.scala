package com.twitter.home_mitwitterer.marshaller.timelines

import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.timelineservice.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class DeviceContetwittertMarshaller @Inject() () {

  def apply(deviceContetwittert: DeviceContetwittert, clientContetwittert: ClientContetwittert): t.DeviceContetwittert = {
    t.DeviceContetwittert(
      countryCode = clientContetwittert.countryCode,
      languageCode = clientContetwittert.languageCode,
      clientAppId = clientContetwittert.appId,
      ipAddress = clientContetwittert.ipAddress,
      guestId = clientContetwittert.guestId,
      userAgent = clientContetwittert.userAgent,
      deviceId = clientContetwittert.deviceId,
      isPolling = deviceContetwittert.isPolling,
      requestContetwittert = deviceContetwittert.requestContetwittert,
      referrer = None,
      tfeAuthHeader = None,
      mobileDeviceId = clientContetwittert.mobileDeviceId,
      isSessionStart = None,
      latestControlAvailable = deviceContetwittert.latestControlAvailable,
      guestIdMarketing = clientContetwittert.guestIdMarketing,
      isInternalOrTwoffice = clientContetwittert.isTwoffice,
      guestIdAds = clientContetwittert.guestIdAds,
      isUrtRequest = Some(true)
    )
  }
}
