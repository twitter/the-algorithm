package com.twitter.home_mixer.marshaller.timelines

import com.twitter.home_mixer.model.request.DeviceContext
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.timelineservice.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceContextMarshaller @Inject() () {

  def apply(deviceContext: DeviceContext, clientContext: ClientContext): t.DeviceContext = {
    t.DeviceContext(
      countryCode = clientContext.countryCode,
      languageCode = clientContext.languageCode,
      clientAppId = clientContext.appId,
      ipAddress = clientContext.ipAddress,
      guestId = clientContext.guestId,
      userAgent = clientContext.userAgent,
      deviceId = clientContext.deviceId,
      isPolling = deviceContext.isPolling,
      requestContext = deviceContext.requestContext,
      referrer = None,
      tfeAuthHeader = None,
      mobileDeviceId = clientContext.mobileDeviceId,
      isSessionStart = None,
      latestControlAvailable = deviceContext.latestControlAvailable,
      guestIdMarketing = clientContext.guestIdMarketing,
      isInternalOrTwoffice = clientContext.isTwoffice,
      guestIdAds = clientContext.guestIdAds,
      isUrtRequest = Some(true)
    )
  }
}
