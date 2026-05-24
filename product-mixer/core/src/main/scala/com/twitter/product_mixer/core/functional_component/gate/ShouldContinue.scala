package com.twitter.product_mixer.core.functional_component.gate

import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait ShouldContinue[Query <: PipelineQuery] {
  def apply(query: Query): Boolean
}
