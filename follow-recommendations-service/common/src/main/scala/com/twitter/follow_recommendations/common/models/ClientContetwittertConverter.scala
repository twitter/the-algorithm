package com.twitter.follow_recommendations.common.models

import com.twitter.follow_recommendations.logging.{thriftscala => offline}
import com.twitter.follow_recommendations.{thriftscala => frs}
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert

object ClientContetwittertConverter {
  def toFRSOfflineClientContetwittertThrift(
    productMitwittererClientContetwittert: ClientContetwittert
  ): offline.OfflineClientContetwittert =
    offline.OfflineClientContetwittert(
      productMitwittererClientContetwittert.userId,
      productMitwittererClientContetwittert.guestId,
      productMitwittererClientContetwittert.appId,
      productMitwittererClientContetwittert.countryCode,
      productMitwittererClientContetwittert.languageCode,
      productMitwittererClientContetwittert.guestIdAds,
      productMitwittererClientContetwittert.guestIdMarketing
    )

  def fromThrift(clientContetwittert: frs.ClientContetwittert): ClientContetwittert = ClientContetwittert(
    userId = clientContetwittert.userId,
    guestId = clientContetwittert.guestId,
    appId = clientContetwittert.appId,
    ipAddress = clientContetwittert.ipAddress,
    userAgent = clientContetwittert.userAgent,
    countryCode = clientContetwittert.countryCode,
    languageCode = clientContetwittert.languageCode,
    isTwoffice = clientContetwittert.isTwoffice,
    userRoles = clientContetwittert.userRoles.map(_.toSet),
    deviceId = clientContetwittert.deviceId,
    guestIdAds = clientContetwittert.guestIdAds,
    guestIdMarketing = clientContetwittert.guestIdMarketing,
    mobileDeviceId = None,
    mobileDeviceAdId = None,
    limitAdTracking = None
  )

  def toThrift(clientContetwittert: ClientContetwittert): frs.ClientContetwittert = frs.ClientContetwittert(
    userId = clientContetwittert.userId,
    guestId = clientContetwittert.guestIdAds,
    appId = clientContetwittert.appId,
    ipAddress = clientContetwittert.ipAddress,
    userAgent = clientContetwittert.userAgent,
    countryCode = clientContetwittert.countryCode,
    languageCode = clientContetwittert.languageCode,
    isTwoffice = clientContetwittert.isTwoffice,
    userRoles = clientContetwittert.userRoles,
    deviceId = clientContetwittert.deviceId,
    guestIdAds = clientContetwittert.guestIdAds,
    guestIdMarketing = clientContetwittert.guestIdMarketing
  )
}
