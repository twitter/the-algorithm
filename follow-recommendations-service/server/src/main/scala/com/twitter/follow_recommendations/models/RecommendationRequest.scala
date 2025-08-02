package com.twitter.follow_recommendations.models

import com.twitter.follow_recommendations.common.models.ClientContetwittertConverter
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.follow_recommendations.logging.{thriftscala => offline}
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert

case class RecommendationRequest(
  clientContetwittert: ClientContetwittert,
  displayLocation: DisplayLocation,
  displayContetwittert: Option[DisplayContetwittert],
  matwitterResults: Option[Int],
  cursor: Option[String],
  etwittercludedIds: Option[Seq[Long]],
  fetchPromotedContent: Option[Boolean],
  debugParams: Option[DebugParams] = None,
  userLocationState: Option[String] = None,
  isSoftUser: Boolean = false) {
  def toOfflineThrift: offline.OfflineRecommendationRequest = offline.OfflineRecommendationRequest(
    ClientContetwittertConverter.toFRSOfflineClientContetwittertThrift(clientContetwittert),
    displayLocation.toOfflineThrift,
    displayContetwittert.map(_.toOfflineThrift),
    matwitterResults,
    cursor,
    etwittercludedIds,
    fetchPromotedContent,
    debugParams.map(DebugParams.toOfflineThrift)
  )
}
