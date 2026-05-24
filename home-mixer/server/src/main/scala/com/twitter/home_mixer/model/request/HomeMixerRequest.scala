package com.twitter.home_mixer.model.request

import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.model.marshalling.request.DebugParams
import com.twitter.product_mixer.core.model.marshalling.request.Product
import com.twitter.product_mixer.core.model.marshalling.request.ProductContext
import com.twitter.product_mixer.core.model.marshalling.request.Request

case class HomeMixerRequest(
  override val clientContext: ClientContext,
  override val product: Product,
  // Product-specific parameters should be placed in the Product Context
  override val productContext: Option[ProductContext],
  override val serializedRequestCursor: Option[String],
  override val maxResults: Option[Int],
  override val debugParams: Option[DebugParams],
  // Parameters that apply to all products can be promoted to the request-level
  homeRequestParam: Boolean)
    extends Request
