package com.ExTwitter.follow_recommendations.flows.ads
import com.ExTwitter.follow_recommendations.common.clients.adserver.AdRequest
import com.ExTwitter.follow_recommendations.common.models.DisplayLocation
import com.ExTwitter.follow_recommendations.common.models.HasDisplayLocation
import com.ExTwitter.follow_recommendations.common.models.HasExcludedUserIds
import com.ExTwitter.product_mixer.core.model.marshalling.request.ClientContext
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.timelines.configapi.HasParams
import com.ExTwitter.timelines.configapi.Params

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
