package com.twitter.tweet_mixer.product.home_recommended_tweets.model.response

import com.twitter.tweet_mixer.model.response.TweetMixerProductResponse
import com.twitter.product_mixer.core.model.marshalling.HasLength

case class HomeRecommendedTweetsProductResponse(
  results: Seq[HomeRecommendedTweetsResult])
    extends TweetMixerProductResponse
    with HasLength {
  override def length: Int = results.length
}
