package com.twitter.tweet_mixer.product

import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.core.product.registry.ProductPipelineRegistryConfig

object TweetMixerProductModule extends TwitterModule {

  override def configure(): Unit = {
    bind[ProductPipelineRegistryConfig].to[TweetMixerProductPipelineRegistryConfig]
  }
}
