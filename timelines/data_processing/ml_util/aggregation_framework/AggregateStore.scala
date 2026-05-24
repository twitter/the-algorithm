package com.twitter.timelines.data_processing.ml_util.aggregation_framework

trait AggregateStore extends Serializable {
  def name: String
}
