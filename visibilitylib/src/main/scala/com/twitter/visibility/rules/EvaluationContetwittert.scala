package com.twitter.visibility.rules

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.util.Gate
import com.twitter.timelines.configapi.HasParams
import com.twitter.timelines.configapi.Params
import com.twitter.visibility.configapi.VisibilityParams
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.UnitOfDiversion
import com.twitter.visibility.models.ViewerContetwittert

case class EvaluationContetwittert(
  visibilityPolicy: VisibilityPolicy,
  params: Params,
  statsReceiver: StatsReceiver)
    etwittertends HasParams {

  def ruleEnabledInContetwittert(rule: Rule): Boolean = {
    visibilityPolicy.policyRuleParams
      .get(rule)
      .filter(_.ruleParams.nonEmpty)
      .map(policyRuleParams => {
        (policyRuleParams.force || rule.enabled.forall(params(_))) &&
          policyRuleParams.ruleParams.forall(params(_))
      })
      .getOrElse(rule.isEnabled(params))
  }
}

object EvaluationContetwittert {

  def apply(
    safetyLevel: SafetyLevel,
    params: Params,
    statsReceiver: StatsReceiver
  ): EvaluationContetwittert = {
    val visibilityPolicy = RuleBase.RuleMap(safetyLevel)
    new EvaluationContetwittert(visibilityPolicy, params, statsReceiver)
  }

  case class Builder(
    statsReceiver: StatsReceiver,
    visibilityParams: VisibilityParams,
    viewerContetwittert: ViewerContetwittert,
    unitsOfDiversion: Seq[UnitOfDiversion] = Seq.empty,
    memoizeParams: Gate[Unit] = Gate.False,
  ) {

    private[this] val emptyContentToUoDCounter =
      statsReceiver.counter("empty_content_id_to_unit_of_diversion")

    def build(safetyLevel: SafetyLevel): EvaluationContetwittert = {
      val policy = RuleBase.RuleMap(safetyLevel)
      val params = if (memoizeParams()) {
        visibilityParams.memoized(viewerContetwittert, safetyLevel, unitsOfDiversion)
      } else {
        visibilityParams(viewerContetwittert, safetyLevel, unitsOfDiversion)
      }
      new EvaluationContetwittert(policy, params, statsReceiver)
    }

    def withUnitOfDiversion(unitOfDiversion: UnitOfDiversion*): Builder =
      this.copy(unitsOfDiversion = unitOfDiversion)

    def withMemoizedParams(memoizeParams: Gate[Unit]) = this.copy(memoizeParams = memoizeParams)
  }

}
