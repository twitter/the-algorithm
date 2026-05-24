package com.twitter.product_mixer.core.functional_component.marshaller.request

import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientContextUnmarshaller @Inject() () {

  def apply(clientContext: t.ClientContext): ClientContext = {
    ClientContext(
      userId = clientContext.userId,
      guestId = clientContext.guestId,
      guestIdAds = clientContext.guestIdAds,
      guestIdMarketing = clientContext.guestIdMarketing,
      appId = clientContext.appId,
      ipAddress = clientContext.ipAddress,
      userAgent = clientContext.userAgent,
      countryCode = clientContext.countryCode,
      languageCode = clientContext.languageCode,
      isTwoffice = clientContext.isTwoffice,
      userRoles = clientContext.userRoles.map(_.toSet),
      deviceId = clientContext.deviceId,
      mobileDeviceId = clientContext.mobileDeviceId,
      mobileDeviceAdId = clientContext.mobileDeviceAdId,
      limitAdTracking = clientContext.limitAdTracking
    )
  }
}
