package com.X.follow_recommendations.common.predicates

import com.X.follow_recommendations.common.base.Predicate
import com.X.follow_recommendations.common.base.PredicateResult
import com.X.follow_recommendations.common.models.FilterReason
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams
import com.X.timelines.configapi.Param

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
