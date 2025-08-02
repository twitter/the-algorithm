package com.twitter.product_mitwitterer.core.functional_component.marshaller.request

import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ClientContetwittertUnmarshaller @Inject() () {

  def apply(clientContetwittert: t.ClientContetwittert): ClientContetwittert = {
    ClientContetwittert(
      userId = clientContetwittert.userId,
      guestId = clientContetwittert.guestId,
      guestIdAds = clientContetwittert.guestIdAds,
      guestIdMarketing = clientContetwittert.guestIdMarketing,
      appId = clientContetwittert.appId,
      ipAddress = clientContetwittert.ipAddress,
      userAgent = clientContetwittert.userAgent,
      countryCode = clientContetwittert.countryCode,
      languageCode = clientContetwittert.languageCode,
      isTwoffice = clientContetwittert.isTwoffice,
      userRoles = clientContetwittert.userRoles.map(_.toSet),
      deviceId = clientContetwittert.deviceId,
      mobileDeviceId = clientContetwittert.mobileDeviceId,
      mobileDeviceAdId = clientContetwittert.mobileDeviceAdId,
      limitAdTracking = clientContetwittert.limitAdTracking
    )
  }
}
