package com.ExTwitter.follow_recommendations.common.predicates

import com.ExTwitter.follow_recommendations.common.base.Predicate
import com.ExTwitter.follow_recommendations.common.base.PredicateResult
import com.ExTwitter.follow_recommendations.common.models.FilterReason
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams
import com.ExTwitter.timelines.configapi.Param

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
