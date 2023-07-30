package com.X.product_mixer.core.functional_component.gate

import com.X.product_mixer.core.pipeline.PipelineQuery

trait ShouldContinue[Query <: PipelineQuery] {
  def apply(query: Query): Boolean
}
