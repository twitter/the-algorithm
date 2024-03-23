package com.ExTwitter.home_mixer.product

import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.product_mixer.core.product.registry.ProductPipelineRegistryConfig

object HomeMixerProductModule extends ExTwitterModule {

  override def configure(): Unit = {
    bind[ProductPipelineRegistryConfig].to[HomeProductPipelineRegistryConfig]
  }
}
