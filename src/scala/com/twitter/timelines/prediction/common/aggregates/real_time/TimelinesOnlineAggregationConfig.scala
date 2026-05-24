package com.twitter.timelines.prediction.common.aggregates.real_time

import com.twitter.conversions.DurationOps._
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.heron.{
  OnlineAggregationStoresTrait,
  RealTimeAggregateStore
}

object TimelinesOnlineAggregationConfig
    extends TimelinesOnlineAggregationDefinitionsTrait
    with OnlineAggregationStoresTrait {

  import TimelinesOnlineAggregationSources._

  override lazy val ProductionStore = RealTimeAggregateStore(
    memcacheDataSet = "timelines_real_time_aggregates",
    isProd = true,
    cacheTTL = 5.days
  )

  override lazy val StagingStore = RealTimeAggregateStore(
    memcacheDataSet = "twemcache_timelines_real_time_aggregates",
    isProd = false,
    cacheTTL = 5.days
  )

  override lazy val inputSource = timelinesOnlineAggregateSource

  /**
   * AggregateToCompute: This defines the complete set of aggregates to be
   *    computed by the aggregation job and to be stored in memcache.
   */
  override lazy val AggregatesToCompute = ProdAggregates ++ StagingAggregates
}
