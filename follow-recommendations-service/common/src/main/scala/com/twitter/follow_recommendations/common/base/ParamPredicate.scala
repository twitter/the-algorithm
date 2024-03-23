package com.ExTwitter.follow_recommendations.common.base

import com.ExTwitter.follow_recommendations.common.models.FilterReason.ParamReason
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams
import com.ExTwitter.timelines.configapi.Param

case class ParamPredicate[Request <: HasParams](param: Param[Boolean]) extends Predicate[Request] {

  def apply(request: Request): Stitch[PredicateResult] = {
    if (request.params(param)) {
      Stitch.value(PredicateResult.Valid)
    } else {
      Stitch.value(PredicateResult.Invalid(Set(ParamReason(param.statName))))
    }
  }
}
