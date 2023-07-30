package com.X.product_mixer.core.functional_component.common.alert.predicate

/**
 * A [[Predicate]] that triggers if the metric this is used with rises above
 * the [[threshold]] for [[datapointsPastThreshold]] per [[duration]]
 */
case class TriggerIfAbove(
  override val threshold: Double,
  override val datapointsPastThreshold: Int = 10,
  override val duration: Int = 15,
  override val metricGranularity: MetricGranularity = Minutes)
    extends Predicate
    with ThroughputPredicate {
  override val operator: Operator = `>`
}
