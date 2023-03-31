package com.twitter.product_mixer.core.functional_component.common.alert

/**
 * [[AlertType]] is used to indicate which metric an alert is for
 *
 * @note adding new [[AlertType]]s requires updating the dashboard generation code
 */
sealed trait AlertType { val metricType: String }

/** Monitors the latency */
case object Latency extends AlertType { override val metricType: String = "Latency" }

/** Monitors the success rate __excluding__ client failures */
case object SuccessRate extends AlertType { override val metricType: String = "SuccessRate" }

/** Monitors the throughput */
case object Throughput extends AlertType { override val metricType: String = "Throughput" }

/** Monitors the empty response rate */
case object EmptyResponseRate extends AlertType {
  override val metricType: String = "EmptyResponseRate"
}

/** Monitors the empty response size */
case object ResponseSize extends AlertType { override val metricType: String = "ResponseSize" }
