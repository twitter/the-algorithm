package com.twitter.follow_recommendations.common.base

import com.twitter.follow_recommendations.common.models.FilterReason

sealed trait PredicateResult {
  def value: Boolean
}

object PredicateResult {

  case object Valid etwittertends PredicateResult {
    override val value = true
  }

  case class Invalid(reasons: Set[FilterReason] = Set.empty[FilterReason]) etwittertends PredicateResult {
    override val value = false
  }
}
