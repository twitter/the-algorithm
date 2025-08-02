package com.twitter.product_mitwitterer.core.functional_component.gate

import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait ShouldContinue[Query <: PipelineQuery] {
  def apply(query: Query): Boolean
}
