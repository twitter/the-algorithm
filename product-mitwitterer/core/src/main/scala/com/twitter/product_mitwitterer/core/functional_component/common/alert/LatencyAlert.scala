package com.twitter.product_mitwitterer.core.functional_component.common.alert

import com.twitter.product_mitwitterer.core.functional_component.common.alert.predicate.TriggerIfLatencyAbove

/**
 * [[GenericClientLatencyAlert]] triggers when the Latency for the component this is used with
 * rises above the [[TriggerIfLatencyAbove]] threshold for the configured amount of time
 */
case class LatencyAlert(
  override val notificationGroup: NotificationGroup,
  percentile: Percentile,
  override val warnPredicate: TriggerIfLatencyAbove,
  override val criticalPredicate: TriggerIfLatencyAbove,
  override val runbookLink: Option[String] = None)
    etwittertends Alert
    with IsObservableFromStrato {
  override val alertType: AlertType = Latency

  override val metricSuffitwitter: Option[String] = Some(percentile.metricSuffitwitter)
}
