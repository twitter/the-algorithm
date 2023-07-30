package com.X.home_mixer.product

import com.X.inject.XModule
import com.X.product_mixer.core.product.registry.ProductPipelineRegistryConfig

object HomeMixerProductModule extends XModule {

  override def configure(): Unit = {
    bind[ProductPipelineRegistryConfig].to[HomeProductPipelineRegistryConfig]
  }
}
