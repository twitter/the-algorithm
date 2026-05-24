package com.twitter.product_mixer.core.functional_component.common.alert.predicate

import com.twitter.util.Duration

/**
 * A [[Predicate]] that triggers if the metric this is used with rises above the
 * [[latencyThreshold]] for [[datapointsPastThreshold]] per [[duration]]
 *
 * @note [[latencyThreshold]] must be > 0
 */
case class TriggerIfLatencyAbove(
  latencyThreshold: Duration,
  override val datapointsPastThreshold: Int = 10,
  override val duration: Int = 15,
  override val metricGranularity: MetricGranularity = Minutes)
    extends Predicate {
  override val threshold: Double = latencyThreshold.inMillis
  override val operator: Operator = `>`
  require(
    latencyThreshold > Duration.Zero,
    s"TriggerIfLatencyAbove thresholds must be greater than 0 but got $latencyThreshold")
}
