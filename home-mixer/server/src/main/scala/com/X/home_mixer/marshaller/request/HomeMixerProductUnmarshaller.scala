package com.X.home_mixer.marshaller.request

import com.X.home_mixer.model.request.FollowingProduct
import com.X.home_mixer.model.request.ForYouProduct
import com.X.home_mixer.model.request.ListRecommendedUsersProduct
import com.X.home_mixer.model.request.ListTweetsProduct
import com.X.home_mixer.model.request.ScoredTweetsProduct
import com.X.home_mixer.model.request.SubscribedProduct
import com.X.home_mixer.{thriftscala => t}
import com.X.product_mixer.core.model.marshalling.request.Product
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
