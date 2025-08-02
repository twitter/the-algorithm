package com.twitter.home_mitwitterer.product

import com.twitter.inject.TwitterModule
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistryConfig

object HomeMitwittererProductModule etwittertends TwitterModule {

  override def configure(): Unit = {
    bind[ProductPipelineRegistryConfig].to[HomeProductPipelineRegistryConfig]
  }
}
