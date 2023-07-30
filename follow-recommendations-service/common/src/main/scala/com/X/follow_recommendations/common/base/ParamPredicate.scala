package com.X.follow_recommendations.common.base

import com.X.follow_recommendations.common.models.FilterReason.ParamReason
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams
import com.X.timelines.configapi.Param

case class ParamPredicate[Request <: HasParams](param: Param[Boolean]) extends Predicate[Request] {

  def apply(request: Request): Stitch[PredicateResult] = {
    if (request.params(param)) {
      Stitch.value(PredicateResult.Valid)
    } else {
      Stitch.value(PredicateResult.Invalid(Set(ParamReason(param.statName))))
    }
  }
}
