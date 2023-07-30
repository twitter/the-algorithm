package com.X.timelines.prediction.common.aggregates.real_time

import com.X.summingbird_internal.runner.storm.GenericRunner

object TypeSafeRunner {
  def main(args: Array[String]): Unit = GenericRunner(args, TimelinesRealTimeAggregatesJob(_))
}
