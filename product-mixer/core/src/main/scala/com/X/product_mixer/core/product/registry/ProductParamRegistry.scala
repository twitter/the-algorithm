package com.X.product_mixer.core.product.registry

import com.X.finagle.stats.StatsReceiver
import com.X.servo.decider.DeciderGateBuilder
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductParamRegistry @Inject() (
  productPipelineRegistryConfig: ProductPipelineRegistryConfig,
  deciderGateBuilder: DeciderGateBuilder,
  statsReceiver: StatsReceiver) {

  def build(): Seq[Config] = {
    val productConfigs = productPipelineRegistryConfig.productPipelineConfigs.map {
      productPipelineConfig =>
        BaseConfigBuilder(
          productPipelineConfig.paramConfig.build(deciderGateBuilder, statsReceiver))
          .build(productPipelineConfig.paramConfig.getClass.getSimpleName)
    }

    productConfigs
  }
}
