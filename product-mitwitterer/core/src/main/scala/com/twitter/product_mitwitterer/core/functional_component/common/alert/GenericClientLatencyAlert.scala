package com.twitter.product_mitwitterer.core.functional_component.common.alert

import com.twitter.product_mitwitterer.core.functional_component.common.alert.predicate.TriggerIfLatencyAbove

/**
 * Similar to [[LatencyAlert]] but intended for use with an etwitterternal client calling Product Mitwitterer.
 *
 * [[GenericClientLatencyAlert]] triggers when the Latency for the specified client
 * rises above the [[TriggerIfLatencyAbove]] threshold for the configured amount of time.
 */
case class GenericClientLatencyAlert(
  override val source: GenericClient,
  override val notificationGroup: NotificationGroup,
  override val warnPredicate: TriggerIfLatencyAbove,
  override val criticalPredicate: TriggerIfLatencyAbove,
  override val runbookLink: Option[String] = None)
    etwittertends Alert {
  override val alertType: AlertType = Latency
}
