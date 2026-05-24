package com.twitter.product_mixer.core.product.registry

import com.twitter.product_mixer.core.model.marshalling.request.Request
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.product.ProductPipelineConfig

trait ProductPipelineRegistryConfig {
  def productPipelineConfigs: Seq[ProductPipelineConfig[_ <: Request, _ <: PipelineQuery, _]]
}
