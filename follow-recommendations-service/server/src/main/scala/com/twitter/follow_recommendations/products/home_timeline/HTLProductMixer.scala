package com.ExTwitter.follow_recommendations.products.home_timeline

import com.ExTwitter.product_mixer.core.model.common.identifier.ProductIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.Product

case object HTLProductMixer extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("HomeTimeline")
  override val stringCenterProject: Option[String] = Some("people-discovery")
}
