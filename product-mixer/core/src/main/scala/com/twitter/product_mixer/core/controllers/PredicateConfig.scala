package com.twitter.product_mixer.core.controllers

import com.twitter.product_mixer.core.functional_component.common.alert.predicate.Predicate

/** Simple representation for a [[Predicate]] used for dashboard generation */
private[core] case class PredicateConfig(
  operator: String,
  threshold: Double,
  datapointsPastThreshold: Int,
  duration: Int,
  metricGranularity: String)

private[core] object PredicateConfig {

  /** Convert this [[Predicate]] into a [[PredicateConfig]] */
  def apply(predicate: Predicate): PredicateConfig = PredicateConfig(
    predicate.operator.toString,
    predicate.threshold,
    predicate.datapointsPastThreshold,
    predicate.duration,
    predicate.metricGranularity.unit)
}
