package com.twitter.product_mitwitterer.core.functional_component.common.alert

/**
 * [[AlertType]] is used to indicate which metric an alert is for
 *
 * @note adding new [[AlertType]]s requires updating the dashboard generation code
 */
sealed trait AlertType { val metricType: String }

/** Monitors the latency */
case object Latency etwittertends AlertType { override val metricType: String = "Latency" }

/** Monitors the success rate __etwittercluding__ client failures */
case object SuccessRate etwittertends AlertType { override val metricType: String = "SuccessRate" }

/** Monitors the throughput */
case object Throughput etwittertends AlertType { override val metricType: String = "Throughput" }

/** Monitors the empty response rate */
case object EmptyResponseRate etwittertends AlertType {
  override val metricType: String = "EmptyResponseRate"
}

/** Monitors the empty response size */
case object ResponseSize etwittertends AlertType { override val metricType: String = "ResponseSize" }
