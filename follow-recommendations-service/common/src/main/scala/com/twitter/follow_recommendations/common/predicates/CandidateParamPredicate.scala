package com.twitter.follow_recommendations.common.predicates

import com.twitter.follow_recommendations.common.base.Predicate
import com.twitter.follow_recommendations.common.base.PredicateResult
import com.twitter.follow_recommendations.common.models.FilterReason
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import com.twitter.timelines.configapi.Param

class CandidateParamPredicate[A <: HasParams](
  param: Param[Boolean],
  reason: FilterReason)
    extends Predicate[A] {
  override def apply(candidate: A): Stitch[PredicateResult] = {
    if (candidate.params(param)) {
      Stitch.value(PredicateResult.Valid)
    } else {
      Stitch.value(PredicateResult.Invalid(Set(reason)))
    }
  }
}
