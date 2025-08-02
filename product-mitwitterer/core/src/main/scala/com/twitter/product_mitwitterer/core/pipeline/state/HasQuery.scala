package com.twitter.product_mitwitterer.core.pipeline.state

import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait HasQuery[Query <: PipelineQuery, T] {
  def query: Query
  def updateQuery(query: Query): T
}
