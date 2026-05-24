package com.twitter.home_mixer.model.request

import com.twitter.product_mixer.core.model.common.identifier.ProductIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.Product

/**
 * Identifier names on products can be used to create Feature Switch rules by product,
 * which useful if bucketing occurs in a component shared by multiple products.
 * @see [[Product.identifier]]
 */

case object FollowingProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("Following")
  override val stringCenterProject: Option[String] = Some("timelinemixer")
}

case object ForYouProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("ForYou")
  override val stringCenterProject: Option[String] = Some("timelinemixer")
}

case object ScoredTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("ScoredTweets")
  override val stringCenterProject: Option[String] = Some("timelinemixer")
}

case object ScoredVideoTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("ScoredVideoTweets")
  override val stringCenterProject: Option[String] = Some("timelinemixer")
}

case object SubscribedProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("Subscribed")
  override val stringCenterProject: Option[String] = Some("timelinemixer")
}

case object HeavyRankerScoresProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("HeavyRankerScores")
  override val stringCenterProject: Option[String] = Some("timelinemixer")
}
