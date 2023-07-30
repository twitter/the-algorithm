package com.X.timelines.data_processing.ml_util.aggregation_framework

trait AggregationConfig {
  def aggregatesToCompute: Set[TypedAggregateGroup[_]]
}
