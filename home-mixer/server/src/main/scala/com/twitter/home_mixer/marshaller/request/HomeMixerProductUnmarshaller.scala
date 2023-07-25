package com.twitter.home_mixer.marshaller.request

import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.model.request.ListRecommendedUsersProduct
import com.twitter.home_mixer.model.request.ListTweetsProduct
import com.twitter.home_mixer.model.request.ScoredTweetsProduct
import com.twitter.home_mixer.model.request.SubscribedProduct
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.model.marshalling.request.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMixerProductUnmarshaller @Inject() () {

  def apply(product: t.Product): Product = product match {
    case t.Product.Following => FollowingProduct
    case t.Product.ForYou => ForYouProduct
    case t.Product.ListManagement =>
      throw new UnsupportedOperationException(s"This product is no longer used")
    case t.Product.ScoredTweets => ScoredTweetsProduct
    case t.Product.ListTweets => ListTweetsProduct
    case t.Product.ListRecommendedUsers => ListRecommendedUsersProduct
    case t.Product.Subscribed => SubscribedProduct
    case t.Product.EnumUnknownProduct(value) =>
      throw new UnsupportedOperationException(s"Unknown product: $value")
  }
}
