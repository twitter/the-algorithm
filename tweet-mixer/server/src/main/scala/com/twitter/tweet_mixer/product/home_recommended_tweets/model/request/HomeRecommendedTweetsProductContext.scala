package com.twitter.tweet_mixer.product.home_recommended_tweets.model.request

import com.twitter.product_mixer.core.model.marshalling.request.ProductContext

case class HomeRecommendedTweetsProductContext(
  excludedIds: Set[Long],
  getRandomTweets: Boolean,
  predictionRequestId: Option[Long])
    extends ProductContext
