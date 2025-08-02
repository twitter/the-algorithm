package com.twitter.follow_recommendations.common.clients.adserver

import com.twitter.adserver.{thriftscala => t}
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert

case class AdRequest(
  clientContetwittert: ClientContetwittert,
  displayLocation: DisplayLocation,
  isTest: Option[Boolean],
  profileUserId: Option[Long]) {
  def toThrift: t.AdRequestParams = {

    val request = t.AdRequest(
      displayLocation = displayLocation.toAdDisplayLocation.getOrElse(
        throw new MissingAdDisplayLocation(displayLocation)),
      isTest = isTest,
      countImpressionsOnCallback = Some(true),
      numOrganicItems = Some(AdRequest.DefaultNumOrganicItems.toShort),
      profileUserId = profileUserId
    )

    val clientInfo = t.ClientInfo(
      clientId = clientContetwittert.appId.map(_.toInt),
      userIp = clientContetwittert.ipAddress,
      userId64 = clientContetwittert.userId,
      guestId = clientContetwittert.guestId,
      userAgent = clientContetwittert.userAgent,
      referrer = None,
      deviceId = clientContetwittert.deviceId,
      languageCode = clientContetwittert.languageCode,
      countryCode = clientContetwittert.countryCode
    )

    t.AdRequestParams(request, clientInfo)
  }
}

object AdRequest {
  val DefaultNumOrganicItems = 10
}

class MissingAdDisplayLocation(displayLocation: DisplayLocation)
    etwittertends Etwitterception(
      s"Display Location ${displayLocation.toString} has no mapped AdsDisplayLocation set.")
