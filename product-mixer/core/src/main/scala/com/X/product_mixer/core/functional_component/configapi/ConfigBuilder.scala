package com.X.product_mixer.core.functional_component.configapi

import com.X.product_mixer.core.functional_component.configapi.registry.GlobalParamRegistry
import com.X.product_mixer.core.product.registry.ProductParamRegistry
import com.X.timelines.configapi.CompositeConfig
import com.X.timelines.configapi.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigBuilder @Inject() (
  productParamRegistry: ProductParamRegistry,
  globalParamRegistry: GlobalParamRegistry) {

  def build(): Config =
    new CompositeConfig(productParamRegistry.build() ++ Seq(globalParamRegistry.build()))
}
