package com.twitter.product_mixer.core.functional_component.common.alert

import com.twitter.product_mixer.core.functional_component.common.alert.predicate.ThroughputPredicate

/**
 * [[ResponseSizeAlert]] triggers when the specified percentile of requests with empty responses (defined
 * as the number of items returned excluding cursors) is beyond the [[ThroughputPredicate]] threshold
 * for a configured amount of time.
 */
case class ResponseSizeAlert(
  override val notificationGroup: NotificationGroup,
  percentile: Percentile,
  override val warnPredicate: ThroughputPredicate,
  override val criticalPredicate: ThroughputPredicate,
  override val runbookLink: Option[String] = None)
    extends Alert {
  override val metricSuffix: Option[String] = Some(percentile.metricSuffix)
  override val alertType: AlertType = ResponseSize
  require(
    warnPredicate.threshold >= 0,
    s"ResponseSizeAlert predicates must be >= 0 but got warnPredicate = ${warnPredicate.threshold}"
  )
  require(
    criticalPredicate.threshold >= 0,
    s"ResponseSizeAlert predicates must be >= 0 but got criticalPredicate = ${criticalPredicate.threshold}"
  )
}
