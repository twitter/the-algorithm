package com.twitter.product_mixer.core.functional_component.marshaller.request

import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.{thriftscala => t}

object ClientContextMarshaller {

  def apply(clientContext: ClientContext): t.ClientContext = {
    t.ClientContext(
      userId = clientContext.userId,
      guestId = clientContext.guestId,
      appId = clientContext.appId,
      ipAddress = clientContext.ipAddress,
      userAgent = clientContext.userAgent,
      countryCode = clientContext.countryCode,
      languageCode = clientContext.languageCode,
      isTwoffice = clientContext.isTwoffice,
      userRoles = clientContext.userRoles,
      deviceId = clientContext.deviceId,
      mobileDeviceId = clientContext.mobileDeviceId,
      mobileDeviceAdId = clientContext.mobileDeviceAdId,
      limitAdTracking = clientContext.limitAdTracking,
      guestIdAds = clientContext.guestIdAds,
      guestIdMarketing = clientContext.guestIdMarketing
    )
  }
}
