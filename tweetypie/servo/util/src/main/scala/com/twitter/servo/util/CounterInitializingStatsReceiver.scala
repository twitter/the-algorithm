package com.twitter.servo.util

import com.twitter.finagle.stats.{Counter, MetricBuilder, StatsReceiver, StatsReceiverProxy}

/**
 * A StatsReceiver that initializes counters to zero.
 * Provides a simple wrapper that wraps a StatsReceiver where when using counters,
 * have them auto initialize to 0.
 * Until a counter performs its first incr() its returned as "undefined",
 * which means if an alert is set on that counter
 * it will result in an error.
 * Another advantage is to remove the need to manually initialize counters in order
 * to overcome aforementioned problem.
 * @param self - underlying StatsReceiver
 */
class CounterInitializingStatsReceiver(protected val self: StatsReceiver)
    extends StatsReceiverProxy {

  override def counter(metricBuilder: MetricBuilder): Counter = {
    val counter = self.counter(metricBuilder)
    counter.incr(0)
    counter
  }
}
