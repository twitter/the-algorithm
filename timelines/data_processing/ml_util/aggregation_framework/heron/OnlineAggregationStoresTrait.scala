package com.twitter.timelines.data_processing.ml_util.aggregation_framework.heron

trait OnlineAggregationStoresTrait {
  def ProductionStore: RealTimeAggregateStore
  def StagingStore: RealTimeAggregateStore
}
