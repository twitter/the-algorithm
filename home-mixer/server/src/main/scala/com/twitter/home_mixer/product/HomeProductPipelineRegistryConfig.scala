package com.twitter.home_mixer.product

import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.model.request.HeavyRankerScoresProduct
import com.twitter.home_mixer.model.request.ScoredTweetsProduct
import com.twitter.home_mixer.model.request.ScoredVideoTweetsProduct
import com.twitter.home_mixer.model.request.SubscribedProduct
import com.twitter.home_mixer.product.following.FollowingProductPipelineConfig
import com.twitter.home_mixer.product.for_you.ForYouProductPipelineConfig
import com.twitter.home_mixer.product.heavy_ranker_scores.HeavyRankerScoresProductPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.ScoredTweetsProductPipelineConfig
import com.twitter.home_mixer.product.scored_video_tweets.ScoredVideoTweetsProductPipelineConfig
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

  private val scoredVideoTweetsProductPipelineConfig = productScope.let(ScoredVideoTweetsProduct) {
    injector.instance[ScoredVideoTweetsProductPipelineConfig]
  }

  private val subscribedProductPipelineConfig = productScope.let(SubscribedProduct) {
    injector.instance[SubscribedProductPipelineConfig]
  }

  private val heavyRankerScoresProductPipelineConfig = productScope.let(HeavyRankerScoresProduct) {
    injector.instance[HeavyRankerScoresProductPipelineConfig]
  }

  override val productPipelineConfigs = Seq(
    followingProductPipelineConfig,
    forYouProductPipelineConfig,
    scoredTweetsProductPipelineConfig,
    scoredVideoTweetsProductPipelineConfig,
    subscribedProductPipelineConfig,
    heavyRankerScoresProductPipelineConfig
  )
}
