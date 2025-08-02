package com.twitter.follow_recommendations.models

import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.product_mitwitterer.core.model.marshalling.request
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.ProductContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.{Request => ProductMitwittererRequest}

case class Request(
  override val matwitterResults: Option[Int],
  override val debugParams: Option[request.DebugParams],
  override val productContetwittert: Option[ProductContetwittert],
  override val product: request.Product,
  override val clientContetwittert: ClientContetwittert,
  override val serializedRequestCursor: Option[String],
  override val frsDebugParams: Option[DebugParams],
  displayLocation: DisplayLocation,
  etwittercludedIds: Option[Seq[Long]],
  fetchPromotedContent: Option[Boolean],
  userLocationState: Option[String] = None)
    etwittertends ProductMitwittererRequest
    with HasFrsDebugParams {}
