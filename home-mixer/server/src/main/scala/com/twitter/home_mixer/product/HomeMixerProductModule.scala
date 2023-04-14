try {
package com.twitter.home_mixer.product

import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.core.product.registry.ProductPipelineRegistryConfig

object HomeMixerProductModule extends TwitterModule {

  override def configure(): Unit = {
    bind[ProductPipelineRegistryConfig].to[HomeProductPipelineRegistryConfig]
  }
}

} catch {
  case e: Exception =>
}
