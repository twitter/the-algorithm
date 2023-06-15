package com.twitter.timelines.data_processing.ml_util.aggregation_framework.heron

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.Feature
import com.twitter.summingbird._
import com.twitter.summingbird.storm.Storm
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateSource
import java.lang.{Long => JLong}

/**
 * Use this trait to implement online summingbird producer that subscribes to
 * spouts and generates a data record.
 */
trait StormAggregateSource extends AggregateSource {
  def name: String

  def timestampFeature: Feature[JLong]

  /**
   * Constructs the storm Producer with the implemented topology at runtime.
   */
  def build(
    statsReceiver: StatsReceiver,
    jobConfig: RealTimeAggregatesJobConfig
  ): Producer[Storm, DataRecord]
}
