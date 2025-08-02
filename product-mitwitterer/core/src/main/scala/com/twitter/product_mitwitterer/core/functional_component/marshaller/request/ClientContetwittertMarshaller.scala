package com.twitter.product_mitwitterer.core.functional_component.marshaller.request

import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.{thriftscala => t}

object ClientContetwittertMarshaller {

  def apply(clientContetwittert: ClientContetwittert): t.ClientContetwittert = {
    t.ClientContetwittert(
      userId = clientContetwittert.userId,
      guestId = clientContetwittert.guestId,
      appId = clientContetwittert.appId,
      ipAddress = clientContetwittert.ipAddress,
      userAgent = clientContetwittert.userAgent,
      countryCode = clientContetwittert.countryCode,
      languageCode = clientContetwittert.languageCode,
      isTwoffice = clientContetwittert.isTwoffice,
      userRoles = clientContetwittert.userRoles,
      deviceId = clientContetwittert.deviceId,
      mobileDeviceId = clientContetwittert.mobileDeviceId,
      mobileDeviceAdId = clientContetwittert.mobileDeviceAdId,
      limitAdTracking = clientContetwittert.limitAdTracking,
      guestIdAds = clientContetwittert.guestIdAds,
      guestIdMarketing = clientContetwittert.guestIdMarketing
    )
  }
}
