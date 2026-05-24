package com.twitter.tweet_mixer.model.request

import com.twitter.product_mixer.core.model.common.identifier.ProductIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.Product

/**
 * Identifier names on products can be used to create Feature Switch rules by product,
 * which useful if bucketing occurs in a component shared by multiple products.
 * @see [[Product.identifier]]
 */

case object HomeRecommendedTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("HomeRecommendedTweets")
  override val stringCenterProject: Option[String] = Some("tweet-mixer-home-recommended-tweets")
}

case object NotificationsRecommendedTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("NotificationsRecommendedTweets")
  override val stringCenterProject: Option[String] = Some(
    "tweet-mixer-notifications-recommended-tweets")
}
case object IMVRecommendedTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("IMVRecommendedTweets")
  override val stringCenterProject: Option[String] = Some("tweet-mixer-imv-recommended-tweets")
}

case object IMVRelatedTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("IMVRelatedTweets")
  override val stringCenterProject: Option[String] = Some("tweet-mixer-imv-related-tweets")
}

case object TopicTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("TopicTweets")
  override val stringCenterProject: Option[String] = Some("tweet-mixer-topic-tweets")
}
case object RUXRelatedTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("RUXRelatedTweets")
  override val stringCenterProject: Option[String] = Some("tweet-mixer-rux-related-tweets")
}

case object VideoRecommendedTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("VideoRecommendedTweets")
  override val stringCenterProject: Option[String] = Some("tweet-mixer-video-recommended-tweets")
}

case object LoggedOutVideoRecommendedTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("LoggedOutVideoRecommendedTweets")
  override val stringCenterProject: Option[String] = Some(
    "tweet-mixer-logged-out-video-recommended-tweets")
}

case object DebuggerTweetsProduct extends Product {
  override val identifier: ProductIdentifier = ProductIdentifier("DebuggerTweets")
  override val stringCenterProject: Option[String] = Some("tweet-mixer-debugger-tweets")
}
