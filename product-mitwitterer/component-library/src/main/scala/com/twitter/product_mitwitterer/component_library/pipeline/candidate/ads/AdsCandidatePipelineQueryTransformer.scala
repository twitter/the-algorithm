package com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.product_mitwitterer.component_library.model.query.ads.AdsQuery
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads.AdsCandidatePipelineQueryTransformer.buildAdRequestParams
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

/**
 * Transform a PipelineQuery with AdsQuery into an AdsRequestParams
 *
 * @param adsDisplayLocationBuilder Builder that determines the display location for the ads
 * @param estimatedNumOrganicItems  Estimate for the number of organic items that will be served
 *                                  alongside inorganic items such as ads. 
 */
case class AdsCandidatePipelineQueryTransformer[Query <: PipelineQuery with AdsQuery](
  adsDisplayLocationBuilder: AdsDisplayLocationBuilder[Query],
  estimatedNumOrganicItems: EstimateNumOrganicItems[Query],
  urtRequest: Option[Boolean],
) etwittertends CandidatePipelineQueryTransformer[Query, ads.AdRequestParams] {

  override def transform(query: Query): ads.AdRequestParams =
    buildAdRequestParams(
      query = query,
      adsDisplayLocation = adsDisplayLocationBuilder(query),
      organicItemIds = None,
      numOrganicItems = Some(estimatedNumOrganicItems(query)),
      urtRequest = urtRequest
    )
}

object AdsCandidatePipelineQueryTransformer {

  def buildAdRequestParams(
    query: PipelineQuery with AdsQuery,
    adsDisplayLocation: ads.DisplayLocation,
    organicItemIds: Option[Seq[Long]],
    numOrganicItems: Option[Short],
    urtRequest: Option[Boolean],
  ): ads.AdRequestParams = {
    val searchRequestContetwittert = query.searchRequestContetwittert
    val queryString = query.searchRequestContetwittert.flatMap(_.queryString)

    val adRequest = ads.AdRequest(
      queryString = queryString, 
      displayLocation = adsDisplayLocation,
      searchRequestContetwittert = searchRequestContetwittert,
      organicItemIds = organicItemIds,
      numOrganicItems = numOrganicItems,
      profileUserId = query.userProfileViewedUserId,
      isDebug = Some(false),
      isTest = Some(false),
      requestTriggerType = query.requestTriggerType,
      disableNsfwAvoidance = query.disableNsfwAvoidance,
      timelineRequestParams = query.timelineRequestParams,
    )

    val contetwittert = query.clientContetwittert

    val clientInfo = ads.ClientInfo(
      clientId = contetwittert.appId.map(_.toInt),
      userId64 = contetwittert.userId,
      userIp = contetwittert.ipAddress,
      guestId = contetwittert.guestIdAds,
      userAgent = contetwittert.userAgent,
      deviceId = contetwittert.deviceId,
      languageCode = contetwittert.languageCode,
      countryCode = contetwittert.countryCode,
      mobileDeviceId = contetwittert.mobileDeviceId,
      mobileDeviceAdId = contetwittert.mobileDeviceAdId,
      limitAdTracking = contetwittert.limitAdTracking,
      autoplayEnabled = query.autoplayEnabled,
      urtRequest = urtRequest,
      dspClientContetwittert = query.dspClientContetwittert
    )

    ads.AdRequestParams(adRequest, clientInfo)
  }
}
