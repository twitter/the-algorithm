package com.twitter.timelines.prediction.common.aggregates.real_time

import com.twitter.summingbird_internal.runner.storm.GenericRunner

object TypeSafeRunner {
  def main(args: Array[String]): Unit = GenericRunner(args, TimelinesRealTimeAggregatesJob(_))
}
