package com.twitter.follow_recommendations.common.clients.adserver

import com.twitter.adserver.{thriftscala => t}
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext

case class AdRequest(
  clientContext: ClientContext,
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
      clientId = clientContext.appId.map(_.toInt),
      userIp = clientContext.ipAddress,
      userId64 = clientContext.userId,
      guestId = clientContext.guestId,
      userAgent = clientContext.userAgent,
      referrer = None,
      deviceId = clientContext.deviceId,
      languageCode = clientContext.languageCode,
      countryCode = clientContext.countryCode
    )

    t.AdRequestParams(request, clientInfo)
  }
}

object AdRequest {
  val DefaultNumOrganicItems = 10
}

class MissingAdDisplayLocation(displayLocation: DisplayLocation)
    extends Exception(
      s"Display Location ${displayLocation.toString} has no mapped AdsDisplayLocation set.")
