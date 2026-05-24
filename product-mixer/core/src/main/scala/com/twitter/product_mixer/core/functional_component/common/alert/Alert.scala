package com.twitter.product_mixer.core.functional_component.common.alert

import com.twitter.product_mixer.core.functional_component.common.alert.predicate.Predicate

/**
 * [[Alert]]s will trigger notifications to their [[NotificationGroup]]
 * when the [[Predicate]]s are triggered.
 */
trait Alert {

  /** A group of alert levels and where the alerts for those levels should be sent */
  val notificationGroup: NotificationGroup

  /** Predicate indicating that the component is in a degraded state */
  val warnPredicate: Predicate

  /** Predicate indicating that the component is not functioning correctly */
  val criticalPredicate: Predicate

  /** An optional link to the runbook detailing how to respond to this alert */
  val runbookLink: Option[String]

  /** Indicates which metrics this [[Alert]] is for */
  val alertType: AlertType

  /** Where the metrics are from, @see [[Source]] */
  val source: Source = Server()

  /** A suffix to add to the end of the metric, this is often a [[Percentile]] */
  val metricSuffix: Option[String] = None
}
