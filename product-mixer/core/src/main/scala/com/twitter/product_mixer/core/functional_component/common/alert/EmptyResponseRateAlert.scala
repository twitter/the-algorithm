package com.twitter.product_mixer.core.functional_component.common.alert

import com.twitter.product_mixer.core.functional_component.common.alert.predicate.TriggerIfAbove

/**
 * [[EmptyResponseRateAlert]] triggers when the percentage of requests with empty responses (defined
 * as the number of items returned excluding cursors) rises above the [[TriggerIfAbove]] threshold
 * for a configured amount of time.
 *
 * @note EmptyResponseRate thresholds must be between 0 and 100%
 */
case class EmptyResponseRateAlert(
  override val notificationGroup: NotificationGroup,
  override val warnPredicate: TriggerIfAbove,
  override val criticalPredicate: TriggerIfAbove,
  override val runbookLink: Option[String] = None)
    extends Alert {
  override val alertType: AlertType = EmptyResponseRate
  require(
    warnPredicate.threshold > 0 && warnPredicate.threshold <= 100,
    s"EmptyResponseRateAlert predicates must be between 0 and 100 but got warnPredicate = ${warnPredicate.threshold}"
  )
  require(
    criticalPredicate.threshold > 0 && criticalPredicate.threshold <= 100,
    s"EmptyResponseRateAlert predicates must be between 0 and 100 but got criticalPredicate = ${criticalPredicate.threshold}"
  )
}
