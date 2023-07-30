package com.X.timelines.data_processing.ml_util.aggregation_framework.heron

import com.X.finagle.stats.StatsReceiver
import com.X.ml.api.DataRecord
import com.X.ml.api.Feature
import com.X.summingbird._
import com.X.summingbird.storm.Storm
import com.X.timelines.data_processing.ml_util.aggregation_framework.AggregateSource
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
