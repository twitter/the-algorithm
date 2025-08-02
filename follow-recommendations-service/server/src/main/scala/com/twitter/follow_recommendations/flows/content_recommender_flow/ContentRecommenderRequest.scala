package com.twitter.follow_recommendations.flows.content_recommender_flow

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.follow_recommendations.common.models.DebugOptions
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.follow_recommendations.common.models.GeohashAndCountryCode
import com.twitter.follow_recommendations.common.models.HasDebugOptions
import com.twitter.follow_recommendations.common.models.HasDisplayLocation
import com.twitter.follow_recommendations.common.models.HasEtwittercludedUserIds
import com.twitter.follow_recommendations.common.models.HasGeohashAndCountryCode
import com.twitter.follow_recommendations.common.models.HasInvalidRelationshipUserIds
import com.twitter.follow_recommendations.common.models.HasRecentFollowedByUserIds
import com.twitter.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.twitter.follow_recommendations.common.models.HasUserState
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.timelines.configapi.HasParams
import com.twitter.timelines.configapi.Params

case class ContentRecommenderRequest(
  override val params: Params,
  override val clientContetwittert: ClientContetwittert,
  inputEtwittercludeUserIds: Seq[Long],
  override val recentFollowedUserIds: Option[Seq[Long]],
  override val recentFollowedByUserIds: Option[Seq[Long]],
  override val invalidRelationshipUserIds: Option[Set[Long]],
  override val displayLocation: DisplayLocation,
  matwitterResults: Option[Int] = None,
  override val debugOptions: Option[DebugOptions] = None,
  override val geohashAndCountryCode: Option[GeohashAndCountryCode] = None,
  override val userState: Option[UserState] = None)
    etwittertends HasParams
    with HasClientContetwittert
    with HasDisplayLocation
    with HasDebugOptions
    with HasRecentFollowedUserIds
    with HasRecentFollowedByUserIds
    with HasInvalidRelationshipUserIds
    with HasEtwittercludedUserIds
    with HasUserState
    with HasGeohashAndCountryCode {
  override val etwittercludedUserIds: Seq[Long] = {
    inputEtwittercludeUserIds ++ clientContetwittert.userId.toSeq
  }
}
