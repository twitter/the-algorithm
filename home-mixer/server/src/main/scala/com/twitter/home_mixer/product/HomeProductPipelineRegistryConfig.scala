package com.twitter.home_mixer.product

import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.model.request.ListRecommendedUsersProduct
import com.twitter.home_mixer.model.request.ListTweetsProduct
import com.twitter.home_mixer.model.request.ScoredTweetsProduct
import com.twitter.home_mixer.model.request.SubscribedProduct
import com.twitter.home_mixer.product.following.FollowingProductPipelineConfig
import com.twitter.home_mixer.product.for_you.ForYouProductPipelineConfig
import com.twitter.home_mixer.product.list_recommended_users.ListRecommendedUsersProductPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.ScoredTweetsProductPipelineConfig
import com.twitter.home_mixer.product.list_tweets.ListTweetsProductPipelineConfig
import com.twitter.home_mixer.product.subscribed.SubscribedProductPipelineConfig
import com.twitter.inject.Injector
import com.twitter.product_mixer.core.product.guice.ProductScope
import com.twitter.product_mixer.core.product.registry.ProductPipelineRegistryConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeProductPipelineRegistryConfig @Inject() (
  injector: Injector,
  productScope: ProductScope)
    extends ProductPipelineRegistryConfig {

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
