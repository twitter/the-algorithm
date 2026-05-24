package com.twitter.follow_recommendations.common.models

import com.twitter.product_mixer.core.model.common.identifier.ProductIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.{Product => ProductMixerProduct}

object Product {
  case object MagicRecs extends ProductMixerProduct {
    override val identifier: ProductIdentifier = ProductIdentifier("MagicRecs")
    override val stringCenterProject: Option[String] = Some("people-discovery")
  }

  case object PlaceholderProductMixerProduct extends ProductMixerProduct {
    override val identifier: ProductIdentifier = ProductIdentifier("PlaceholderProductMixerProduct")
  }
}
