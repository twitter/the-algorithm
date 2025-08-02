package com.twitter.home_mitwitterer.model.request

import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.DebugParams
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import com.twitter.product_mitwitterer.core.model.marshalling.request.ProductContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.Request

case class HomeMitwittererRequest(
  override val clientContetwittert: ClientContetwittert,
  override val product: Product,
  // Product-specific parameters should be placed in the Product Contetwittert
  override val productContetwittert: Option[ProductContetwittert],
  override val serializedRequestCursor: Option[String],
  override val matwitterResults: Option[Int],
  override val debugParams: Option[DebugParams],
  // Parameters that apply to all products can be promoted to the request-level
  homeRequestParam: Boolean)
    etwittertends Request
