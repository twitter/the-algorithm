package com.twitter.product_mitwitterer.core.functional_component.configapi

import com.twitter.product_mitwitterer.core.functional_component.configapi.registry.GlobalParamRegistry
import com.twitter.product_mitwitterer.core.product.registry.ProductParamRegistry
import com.twitter.timelines.configapi.CompositeConfig
import com.twitter.timelines.configapi.Config
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ConfigBuilder @Inject() (
  productParamRegistry: ProductParamRegistry,
  globalParamRegistry: GlobalParamRegistry) {

  def build(): Config =
    new CompositeConfig(productParamRegistry.build() ++ Seq(globalParamRegistry.build()))
}
