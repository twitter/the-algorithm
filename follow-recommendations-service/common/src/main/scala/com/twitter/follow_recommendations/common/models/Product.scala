package com.twitter.follow_recommendations.common.models

import com.twitter.product_mitwitterer.core.model.common.identifier.ProductIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.{Product => ProductMitwittererProduct}

object Product {
  case object MagicRecs etwittertends ProductMitwittererProduct {
    override val identifier: ProductIdentifier = ProductIdentifier("MagicRecs")
    override val stringCenterProject: Option[String] = Some("people-discovery")
  }

  case object PlaceholderProductMitwittererProduct etwittertends ProductMitwittererProduct {
    override val identifier: ProductIdentifier = ProductIdentifier("PlaceholderProductMitwittererProduct")
  }
}
