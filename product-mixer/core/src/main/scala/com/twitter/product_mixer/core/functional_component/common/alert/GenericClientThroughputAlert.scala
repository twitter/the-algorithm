package com.twitter.product_mixer.core.functional_component.common.alert

import com.twitter.product_mixer.core.functional_component.common.alert.predicate.ThroughputPredicate

/**
 * Similar to [[ThroughputAlert]] but intended for an external client calling Product Mixer.
 *
 * [[GenericClientThroughputAlert]] triggers when the requests/sec for the external client
 * is outside of the predicate set by a [[ThroughputPredicate]] for the configured amount of time
 */
case class GenericClientThroughputAlert(
  override val source: GenericClient,
  override val notificationGroup: NotificationGroup,
  override val warnPredicate: ThroughputPredicate,
  override val criticalPredicate: ThroughputPredicate,
  override val runbookLink: Option[String] = None)
    extends Alert {
  override val alertType: AlertType = Throughput
  require(
    warnPredicate.threshold >= 0,
    s"ThroughputAlert predicates must be >= 0 but got warnPredicate = ${warnPredicate.threshold}")
  require(
    criticalPredicate.threshold >= 0,
    s"ThroughputAlert predicates must be >= 0 but got criticalPredicate = ${criticalPredicate.threshold}")
}
