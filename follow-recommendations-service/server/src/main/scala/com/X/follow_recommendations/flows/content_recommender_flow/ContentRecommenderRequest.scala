package com.X.follow_recommendations.flows.content_recommender_flow

import com.X.core_workflows.user_model.thriftscala.UserState
import com.X.follow_recommendations.common.models.DebugOptions
import com.X.follow_recommendations.common.models.DisplayLocation
import com.X.follow_recommendations.common.models.GeohashAndCountryCode
import com.X.follow_recommendations.common.models.HasDebugOptions
import com.X.follow_recommendations.common.models.HasDisplayLocation
import com.X.follow_recommendations.common.models.HasExcludedUserIds
import com.X.follow_recommendations.common.models.HasGeohashAndCountryCode
import com.X.follow_recommendations.common.models.HasInvalidRelationshipUserIds
import com.X.follow_recommendations.common.models.HasRecentFollowedByUserIds
import com.X.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.X.follow_recommendations.common.models.HasUserState
import com.X.product_mixer.core.model.marshalling.request.ClientContext
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.timelines.configapi.HasParams
import com.X.timelines.configapi.Params

case class ContentRecommenderRequest(
  override val params: Params,
  override val clientContext: ClientContext,
  inputExcludeUserIds: Seq[Long],
  override val recentFollowedUserIds: Option[Seq[Long]],
  override val recentFollowedByUserIds: Option[Seq[Long]],
  override val invalidRelationshipUserIds: Option[Set[Long]],
  override val displayLocation: DisplayLocation,
  maxResults: Option[Int] = None,
  override val debugOptions: Option[DebugOptions] = None,
  override val geohashAndCountryCode: Option[GeohashAndCountryCode] = None,
  override val userState: Option[UserState] = None)
    extends HasParams
    with HasClientContext
    with HasDisplayLocation
    with HasDebugOptions
    with HasRecentFollowedUserIds
    with HasRecentFollowedByUserIds
    with HasInvalidRelationshipUserIds
    with HasExcludedUserIds
    with HasUserState
    with HasGeohashAndCountryCode {
  override val excludedUserIds: Seq[Long] = {
    inputExcludeUserIds ++ clientContext.userId.toSeq
  }
}
