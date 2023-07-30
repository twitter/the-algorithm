package com.X.follow_recommendations.flows.ads
import com.X.follow_recommendations.common.clients.adserver.AdRequest
import com.X.follow_recommendations.common.models.DisplayLocation
import com.X.follow_recommendations.common.models.HasDisplayLocation
import com.X.follow_recommendations.common.models.HasExcludedUserIds
import com.X.product_mixer.core.model.marshalling.request.ClientContext
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.timelines.configapi.HasParams
import com.X.timelines.configapi.Params

case class PromotedAccountsFlowRequest(
  override val clientContext: ClientContext,
  override val params: Params,
  displayLocation: DisplayLocation,
  profileId: Option[Long],
  // note we also add userId and profileId to excludeUserIds
  excludeIds: Seq[Long])
    extends HasParams
    with HasClientContext
    with HasExcludedUserIds
    with HasDisplayLocation {
  def toAdsRequest(fetchProductionPromotedAccounts: Boolean): AdRequest = {
    AdRequest(
      clientContext = clientContext,
      displayLocation = displayLocation,
      isTest = Some(!fetchProductionPromotedAccounts),
      profileUserId = profileId
    )
  }
  override val excludedUserIds: Seq[Long] = {
    excludeIds ++ clientContext.userId.toSeq ++ profileId.toSeq
  }
}
