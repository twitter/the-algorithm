package com.twitter.home_mitwitterer.product

import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.home_mitwitterer.model.request.ForYouProduct
import com.twitter.home_mitwitterer.model.request.ListRecommendedUsersProduct
import com.twitter.home_mitwitterer.model.request.ListTweetsProduct
import com.twitter.home_mitwitterer.model.request.ScoredTweetsProduct
import com.twitter.home_mitwitterer.model.request.SubscribedProduct
import com.twitter.home_mitwitterer.product.following.FollowingProductPipelineConfig
import com.twitter.home_mitwitterer.product.for_you.ForYouProductPipelineConfig
import com.twitter.home_mitwitterer.product.list_recommended_users.ListRecommendedUsersProductPipelineConfig
import com.twitter.home_mitwitterer.product.scored_tweets.ScoredTweetsProductPipelineConfig
import com.twitter.home_mitwitterer.product.list_tweets.ListTweetsProductPipelineConfig
import com.twitter.home_mitwitterer.product.subscribed.SubscribedProductPipelineConfig
import com.twitter.inject.Injector
import com.twitter.product_mitwitterer.core.product.guice.ProductScope
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistryConfig
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeProductPipelineRegistryConfig @Inject() (
  injector: Injector,
  productScope: ProductScope)
    etwittertends ProductPipelineRegistryConfig {

  private val followingProductPipelineConfig = productScope.let(FollowingProduct) {
    injector.instance[FollowingProductPipelineConfig]
  }

  private val forYouProductPipelineConfig = productScope.let(ForYouProduct) {
    injector.instance[ForYouProductPipelineConfig]
  }

  private val scoredTweetsProductPipelineConfig = productScope.let(ScoredTweetsProduct) {
    injector.instance[ScoredTweetsProductPipelineConfig]
  }

  private val listTweetsProductPipelineConfig = productScope.let(ListTweetsProduct) {
    injector.instance[ListTweetsProductPipelineConfig]
  }

  private val listRecommendedUsersProductPipelineConfig =
    productScope.let(ListRecommendedUsersProduct) {
      injector.instance[ListRecommendedUsersProductPipelineConfig]
    }

  private val subscribedProductPipelineConfig = productScope.let(SubscribedProduct) {
    injector.instance[SubscribedProductPipelineConfig]
  }

  override val productPipelineConfigs = Seq(
    followingProductPipelineConfig,
    forYouProductPipelineConfig,
    scoredTweetsProductPipelineConfig,
    listTweetsProductPipelineConfig,
    listRecommendedUsersProductPipelineConfig,
    subscribedProductPipelineConfig,
  )
}
