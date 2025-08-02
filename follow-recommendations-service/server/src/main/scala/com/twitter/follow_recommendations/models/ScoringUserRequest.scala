package com.twitter.follow_recommendations.models

import com.twitter.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.twitter.follow_recommendations.common.models._
import com.twitter.follow_recommendations.logging.{thriftscala => offline}
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.timelines.configapi.HasParams
import com.twitter.timelines.configapi.Params

case class ScoringUserRequest(
  override val clientContetwittert: ClientContetwittert,
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
    etwittertends HasClientContetwittert
    with HasDisplayLocation
    with HasParams
    with HasDebugOptions
    with HasPreFetchedFeature
    with HasSimilarToContetwittert {
  def toOfflineThrift: offline.OfflineScoringUserRequest = offline.OfflineScoringUserRequest(
    ClientContetwittertConverter.toFRSOfflineClientContetwittertThrift(clientContetwittert),
    displayLocation.toOfflineThrift,
    candidates.map(_.toOfflineUserThrift)
  )
  def toRecommendationRequest: RecommendationRequest = RecommendationRequest(
    clientContetwittert = clientContetwittert,
    displayLocation = displayLocation,
    displayContetwittert = None,
    matwitterResults = None,
    cursor = None,
    etwittercludedIds = None,
    fetchPromotedContent = None,
    debugParams = debugParams,
    isSoftUser = isSoftUser
  )
}
