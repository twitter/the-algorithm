package com.twitter.product_mixer.core.functional_component.common.alert

/**
 * [[Percentile]] is the specific metric that should be monitored.
 * Some metrics such as Latency are recorded using [[https://twitter.github.io/util/docs/com/twitter/finagle/stats/Stat.html Stats]]
 * the stats are recorded as various percentiles such as `my/stat.p95` or `my/stat.min`.
 */
sealed trait Percentile { val metricSuffix: String }
case object Min extends Percentile { override val metricSuffix: String = ".min" }
case object Avg extends Percentile { override val metricSuffix: String = ".avg" }
case object P50 extends Percentile { override val metricSuffix: String = ".p50" }
case object P90 extends Percentile { override val metricSuffix: String = ".p90" }
case object P95 extends Percentile { override val metricSuffix: String = ".p95" }
case object P99 extends Percentile { override val metricSuffix: String = ".p99" }
case object P999 extends Percentile { override val metricSuffix: String = ".p9990" }
case object P9999 extends Percentile { override val metricSuffix: String = ".p9999" }
case object Max extends Percentile { override val metricSuffix: String = ".max" }
