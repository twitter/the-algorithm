package com.twitter.product_mixer.core.functional_component.common.alert

import com.twitter.product_mixer.core.functional_component.common.alert.predicate.ThroughputPredicate

/**
 * [[ThroughputAlert]] triggers when the requests/sec for the component this is used
 * with is outside of the predicate set by a [[ThroughputPredicate]] for
 * the configured amount of time
 */
case class ThroughputAlert(
  override val notificationGroup: NotificationGroup,
  override val warnPredicate: ThroughputPredicate,
  override val criticalPredicate: ThroughputPredicate,
  override val runbookLink: Option[String] = None)
    extends Alert
    with IsObservableFromStrato {
  override val alertType: AlertType = Throughput
  require(
    warnPredicate.threshold >= 0,
    s"ThroughputAlert predicates must be >= 0 but got warnPredicate = ${warnPredicate.threshold}")
  require(
    criticalPredicate.threshold >= 0,
    s"ThroughputAlert predicates must be >= 0 but got criticalPredicate = ${criticalPredicate.threshold}")
}
