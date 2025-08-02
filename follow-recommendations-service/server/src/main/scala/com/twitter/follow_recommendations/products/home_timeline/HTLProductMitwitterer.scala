package com.twitter.follow_recommendations.products.home_timeline

import com.twitter.product_mitwitterer.core.model.common.identifier.ProductIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product

case object HTLProductMitwitterer etwittertends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("HomeTimeline")
  override val stringCenterProject: Option[String] = Some("people-discovery")
}
