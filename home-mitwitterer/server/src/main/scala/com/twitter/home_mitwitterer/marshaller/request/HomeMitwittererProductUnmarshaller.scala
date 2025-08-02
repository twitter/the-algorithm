package com.twitter.home_mitwitterer.marshaller.request

import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.home_mitwitterer.model.request.ForYouProduct
import com.twitter.home_mitwitterer.model.request.ListRecommendedUsersProduct
import com.twitter.home_mitwitterer.model.request.ListTweetsProduct
import com.twitter.home_mitwitterer.model.request.ScoredTweetsProduct
import com.twitter.home_mitwitterer.model.request.SubscribedProduct
import com.twitter.home_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeMitwittererProductUnmarshaller @Inject() () {

  def apply(product: t.Product): Product = product match {
    case t.Product.Following => FollowingProduct
    case t.Product.ForYou => ForYouProduct
    case t.Product.ListManagement =>
      throw new UnsupportedOperationEtwitterception(s"This product is no longer used")
    case t.Product.ScoredTweets => ScoredTweetsProduct
    case t.Product.ListTweets => ListTweetsProduct
    case t.Product.ListRecommendedUsers => ListRecommendedUsersProduct
    case t.Product.Subscribed => SubscribedProduct
    case t.Product.EnumUnknownProduct(value) =>
      throw new UnsupportedOperationEtwitterception(s"Unknown product: $value")
  }
}
