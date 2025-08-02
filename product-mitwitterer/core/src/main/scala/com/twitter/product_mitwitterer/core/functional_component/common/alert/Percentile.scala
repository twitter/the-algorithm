package com.twitter.product_mitwitterer.core.functional_component.common.alert

/**
 * [[Percentile]] is the specific metric that should be monitored.
 * Some metrics such as Latency are recorded using [[https://twitter.github.io/util/docs/com/twitter/finagle/stats/Stat.html Stats]]
 * the stats are recorded as various percentiles such as `my/stat.p95` or `my/stat.min`.
 */
sealed trait Percentile { val metricSuffitwitter: String }
case object Min etwittertends Percentile { override val metricSuffitwitter: String = ".min" }
case object Avg etwittertends Percentile { override val metricSuffitwitter: String = ".avg" }
case object P50 etwittertends Percentile { override val metricSuffitwitter: String = ".p50" }
case object P90 etwittertends Percentile { override val metricSuffitwitter: String = ".p90" }
case object P95 etwittertends Percentile { override val metricSuffitwitter: String = ".p95" }
case object P99 etwittertends Percentile { override val metricSuffitwitter: String = ".p99" }
case object P999 etwittertends Percentile { override val metricSuffitwitter: String = ".p9990" }
case object P9999 etwittertends Percentile { override val metricSuffitwitter: String = ".p9999" }
case object Matwitter etwittertends Percentile { override val metricSuffitwitter: String = ".matwitter" }
