package com.X.follow_recommendations.models

import com.X.follow_recommendations.common.models.ClientContextConverter
import com.X.follow_recommendations.common.models.DisplayLocation
import com.X.follow_recommendations.logging.{thriftscala => offline}
import com.X.product_mixer.core.model.marshalling.request.ClientContext

case class RecommendationRequest(
  clientContext: ClientContext,
  displayLocation: DisplayLocation,
  displayContext: Option[DisplayContext],
  maxResults: Option[Int],
  cursor: Option[String],
  excludedIds: Option[Seq[Long]],
  fetchPromotedContent: Option[Boolean],
  debugParams: Option[DebugParams] = None,
  userLocationState: Option[String] = None,
  isSoftUser: Boolean = false) {
  def toOfflineThrift: offline.OfflineRecommendationRequest = offline.OfflineRecommendationRequest(
    ClientContextConverter.toFRSOfflineClientContextThrift(clientContext),
    displayLocation.toOfflineThrift,
    displayContext.map(_.toOfflineThrift),
    maxResults,
    cursor,
    excludedIds,
    fetchPromotedContent,
    debugParams.map(DebugParams.toOfflineThrift)
  )
}
