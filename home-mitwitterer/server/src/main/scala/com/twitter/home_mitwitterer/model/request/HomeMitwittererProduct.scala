package com.twitter.home_mitwitterer.model.request

import com.twitter.product_mitwitterer.core.model.common.identifier.ProductIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product

/**
 * Identifier names on products can be used to create Feature Switch rules by product,
 * which useful if bucketing occurs in a component shared by multiple products.
 * @see [[Product.identifier]]
 */

case object FollowingProduct etwittertends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("Following")
  override val stringCenterProject: Option[String] = Some("timelinemitwitterer")
}

case object ForYouProduct etwittertends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("ForYou")
  override val stringCenterProject: Option[String] = Some("timelinemitwitterer")
}

case object ScoredTweetsProduct etwittertends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("ScoredTweets")
  override val stringCenterProject: Option[String] = Some("timelinemitwitterer")
}

case object ListTweetsProduct etwittertends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("ListTweets")
  override val stringCenterProject: Option[String] = Some("timelinemitwitterer")
}

case object ListRecommendedUsersProduct etwittertends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("ListRecommendedUsers")
  override val stringCenterProject: Option[String] = Some("timelinemitwitterer")
}

case object SubscribedProduct etwittertends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("Subscribed")
  override val stringCenterProject: Option[String] = Some("timelinemitwitterer")
}
