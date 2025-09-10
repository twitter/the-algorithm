package com.twitter.tweet_mixer.product.home_recommended_tweets.marshaller.response

import com.twitter.tweet_mixer.marshaller.response.common.TweetResultMarshaller
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.response.HomeRecommendedTweetsProductResponse
import com.twitter.tweet_mixer.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRecommendedTweetsProductResponseMarshaller @Inject() (
  tweetResultMarshaller: TweetResultMarshaller) {

  def apply(
    homeRecommendedTweetsProductResponse: HomeRecommendedTweetsProductResponse
  ): t.TweetMixerRecommendationResponse =
    t.TweetMixerRecommendationResponse.HomeRecommendedTweetsProductResponse(
      t.HomeRecommendedTweetsProductResponse(results =
        homeRecommendedTweetsProductResponse.results.map { result =>
          t.HomeRecommendedTweetsResult(
            tweetResult = tweetResultMarshaller(result.tweetResult)
          )
        })
    )
}
