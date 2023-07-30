package com.X.follow_recommendations.products.home_timeline

import com.X.product_mixer.core.model.common.identifier.ProductIdentifier
import com.X.product_mixer.core.model.marshalling.request.Product

case object HTLProductMixer extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("HomeTimeline")
  override val stringCenterProject: Option[String] = Some("people-discovery")
}
