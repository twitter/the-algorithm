package com.twitter.product_mixer.core.functional_component.common.alert.predicate

/**
 * Specifies the metric granularity
 *
 * @see [[https://docbird.twitter.biz/mon/reference.html#predicate DURATION]]
 */
sealed trait MetricGranularity { val unit: String }

/**
 * Use minutely metrics and have alert durations in terms of minutes
 *
 * i.e. for a [[Predicate]] if [[Predicate.datapointsPastThreshold]] = 5 and [[Predicate.duration]] = 10
 * then the alert will trigger if there are at least 5 '''minutely''' metric points that are past the threshold
 * in any 10 '''minute''' period
 */
case object Minutes extends MetricGranularity { override val unit: String = "m" }

/**
 * Use hourly metrics and have alert durations in terms of hours
 *
 * i.e. for a [[Predicate]] if [[Predicate.datapointsPastThreshold]] = 5 and [[Predicate.duration]] = 10
 * then the alert will trigger if there are at least 5 '''hourly''' metric points that are past the threshold
 * in any 10 '''hour''' period
 */
case object Hours extends MetricGranularity { override val unit: String = "h" }

/**
 * Use daily metrics and have alert durations in terms of days
 *
 * i.e. for a [[Predicate]] if [[Predicate.datapointsPastThreshold]] = 5 and [[Predicate.duration]] = 10
 * then the alert will trigger if there are at least 5 '''daily''' metric points that are past the threshold
 * in any 10 '''day''' period
 */
case object Days extends MetricGranularity { override val unit: String = "d" }
