package com.twitter.follow_recommendations.products.home_timeline

import com.twitter.product_mixer.core.model.common.identifier.ProductIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.Product

case object HTLProductMixer extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("HomeTimeline")
  override val stringCenterProject: Option[String] = Some("people-discovery")
}
