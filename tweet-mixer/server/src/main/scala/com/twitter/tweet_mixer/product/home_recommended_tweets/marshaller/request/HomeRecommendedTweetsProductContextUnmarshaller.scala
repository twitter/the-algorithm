package com.twitter.tweet_mixer.product.home_recommended_tweets.marshaller.request

import com.twitter.tweet_mixer.product.home_recommended_tweets.model.request.HomeRecommendedTweetsProductContext
import com.twitter.tweet_mixer.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRecommendedTweetsProductContextUnmarshaller @Inject() () {

  def apply(
    productContext: t.HomeRecommendedTweetsProductContext
  ): HomeRecommendedTweetsProductContext =
    HomeRecommendedTweetsProductContext(
      excludedIds = productContext.excludedTweetIds
        .map(_.toSet)
        .getOrElse(Set.empty),
      getRandomTweets = productContext.getRandomTweets.getOrElse(false),
      predictionRequestId = productContext.predictionRequestId
    )
}
