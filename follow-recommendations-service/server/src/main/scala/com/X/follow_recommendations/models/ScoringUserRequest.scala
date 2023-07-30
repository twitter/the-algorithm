package com.X.follow_recommendations.models

import com.X.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.X.follow_recommendations.common.models._
import com.X.follow_recommendations.logging.{thriftscala => offline}
import com.X.product_mixer.core.model.marshalling.request.ClientContext
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.timelines.configapi.HasParams
import com.X.timelines.configapi.Params

case class ScoringUserRequest(
  override val clientContext: ClientContext,
  override val displayLocation: DisplayLocation,
  override val params: Params,
  override val debugOptions: Option[DebugOptions] = None,
  override val recentFollowedUserIds: Option[Seq[Long]],
  override val recentFollowedByUserIds: Option[Seq[Long]],
  override val wtfImpressions: Option[Seq[WtfImpression]],
  override val similarToUserIds: Seq[Long],
  candidates: Seq[CandidateUser],
  debugParams: Option[DebugParams] = None,
  isSoftUser: Boolean = false)
    extends HasClientContext
    with HasDisplayLocation
    with HasParams
    with HasDebugOptions
    with HasPreFetchedFeature
    with HasSimilarToContext {
  def toOfflineThrift: offline.OfflineScoringUserRequest = offline.OfflineScoringUserRequest(
    ClientContextConverter.toFRSOfflineClientContextThrift(clientContext),
    displayLocation.toOfflineThrift,
    candidates.map(_.toOfflineUserThrift)
  )
  def toRecommendationRequest: RecommendationRequest = RecommendationRequest(
    clientContext = clientContext,
    displayLocation = displayLocation,
    displayContext = None,
    maxResults = None,
    cursor = None,
    excludedIds = None,
    fetchPromotedContent = None,
    debugParams = debugParams,
    isSoftUser = isSoftUser
  )
}
