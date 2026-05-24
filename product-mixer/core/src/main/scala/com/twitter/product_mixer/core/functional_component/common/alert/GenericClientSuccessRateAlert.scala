package com.twitter.product_mixer.core.functional_component.common.alert

import com.twitter.product_mixer.core.functional_component.common.alert.predicate.TriggerIfBelow

/**
 * Similar to [[SuccessRateAlert]] but intended for use with an external client calling Product Mixer
 *
 * [[GenericClientSuccessRateAlert]] triggers when the Success Rate for the external client
 * drops below the [[TriggerIfBelow]] threshold for the configured amount of time
 *
 * @note SuccessRate thresholds must be between 0 and 100%
 */
case class GenericClientSuccessRateAlert(
  override val source: GenericClient,
  override val notificationGroup: NotificationGroup,
  override val warnPredicate: TriggerIfBelow,
  override val criticalPredicate: TriggerIfBelow,
  override val runbookLink: Option[String] = None)
    extends Alert {
  override val alertType: AlertType = SuccessRate
  require(
    warnPredicate.threshold > 0 && warnPredicate.threshold <= 100,
    s"SuccessRateAlert predicates must be between 0 and 100 but got warnPredicate = ${warnPredicate.threshold}"
  )
  require(
    criticalPredicate.threshold > 0 && criticalPredicate.threshold <= 100,
    s"SuccessRateAlert predicates must be between 0 and 100 but got criticalPredicate = ${criticalPredicate.threshold}"
  )
}
