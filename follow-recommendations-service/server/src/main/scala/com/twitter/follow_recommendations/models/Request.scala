package com.twitter.follow_recommendations.models

import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.product_mixer.core.model.marshalling.request
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.model.marshalling.request.ProductContext
import com.twitter.product_mixer.core.model.marshalling.request.{Request => ProductMixerRequest}

case class Request(
  override val maxResults: Option[Int],
  override val debugParams: Option[request.DebugParams],
  override val productContext: Option[ProductContext],
  override val product: request.Product,
  override val clientContext: ClientContext,
  override val serializedRequestCursor: Option[String],
  override val frsDebugParams: Option[DebugParams],
  displayLocation: DisplayLocation,
  excludedIds: Option[Seq[Long]],
  fetchPromotedContent: Option[Boolean],
  userLocationState: Option[String] = None)
    extends ProductMixerRequest
    with HasFrsDebugParams {}
