package com.twitter.tweet_mixer.marshaller.request

import com.twitter.product_mixer.core.model.marshalling.request.Product
import com.twitter.tweet_mixer.model.request.DebuggerTweetsProduct
import com.twitter.tweet_mixer.model.request.HomeRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.IMVRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.IMVRelatedTweetsProduct
import com.twitter.tweet_mixer.model.request.LoggedOutVideoRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.NotificationsRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.RUXRelatedTweetsProduct
import com.twitter.tweet_mixer.model.request.TopicTweetsProduct
import com.twitter.tweet_mixer.model.request.VideoRecommendedTweetsProduct
import com.twitter.tweet_mixer.{thriftscala => t}

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetMixerProductUnmarshaller @Inject() () {

  def apply(product: t.Product): Product = product match {
    case t.Product.HomeRecommendedTweets => HomeRecommendedTweetsProduct
    case t.Product.NotificationsRecommendedTweets => NotificationsRecommendedTweetsProduct
    case t.Product.ImvRecommendedTweets => IMVRecommendedTweetsProduct
    case t.Product.ImvRelatedTweets => IMVRelatedTweetsProduct
    case t.Product.RuxRelatedTweets => RUXRelatedTweetsProduct
    case t.Product.VideoRecommendedTweets => VideoRecommendedTweetsProduct
    case t.Product.TopicTweets => TopicTweetsProduct
    case t.Product.LoggedOutVideoRecommendedTweets => LoggedOutVideoRecommendedTweetsProduct
    case t.Product.DebuggerTweets => DebuggerTweetsProduct
    case t.Product.EnumUnknownProduct(value) =>
      throw new UnsupportedOperationException(s"Unknown product: $value")
  }
}
