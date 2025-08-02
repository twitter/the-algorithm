package com.twitter.product_mitwitterer.core.product.registry

import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineConfig

trait ProductPipelineRegistryConfig {
  def productPipelineConfigs: Seq[ProductPipelineConfig[_ <: Request, _ <: PipelineQuery, _]]
}
