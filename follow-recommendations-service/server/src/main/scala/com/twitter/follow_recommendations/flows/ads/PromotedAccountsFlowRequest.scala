package com.twitter.follow_recommendations.flows.ads
import com.twitter.follow_recommendations.common.clients.adserver.AdRequest
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.follow_recommendations.common.models.HasDisplayLocation
import com.twitter.follow_recommendations.common.models.HasEtwittercludedUserIds
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.timelines.configapi.HasParams
import com.twitter.timelines.configapi.Params

case class PromotedAccountsFlowRequest(
  override val clientContetwittert: ClientContetwittert,
  override val params: Params,
  displayLocation: DisplayLocation,
  profileId: Option[Long],
  // note we also add userId and profileId to etwittercludeUserIds
  etwittercludeIds: Seq[Long])
    etwittertends HasParams
    with HasClientContetwittert
    with HasEtwittercludedUserIds
    with HasDisplayLocation {
  def toAdsRequest(fetchProductionPromotedAccounts: Boolean): AdRequest = {
    AdRequest(
      clientContetwittert = clientContetwittert,
      displayLocation = displayLocation,
      isTest = Some(!fetchProductionPromotedAccounts),
      profileUserId = profileId
    )
  }
  override val etwittercludedUserIds: Seq[Long] = {
    etwittercludeIds ++ clientContetwittert.userId.toSeq ++ profileId.toSeq
  }
}
