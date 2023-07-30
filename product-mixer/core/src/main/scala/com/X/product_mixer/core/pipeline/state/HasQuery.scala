package com.X.product_mixer.core.pipeline.state

import com.X.product_mixer.core.pipeline.PipelineQuery

trait HasQuery[Query <: PipelineQuery, T] {
  def query: Query
  def updateQuery(query: Query): T
}
