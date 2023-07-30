package com.X.visibility.rules

import com.X.finagle.stats.StatsReceiver
import com.X.servo.util.Gate
import com.X.timelines.configapi.HasParams
import com.X.timelines.configapi.Params
import com.X.visibility.configapi.VisibilityParams
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.UnitOfDiversion
import com.X.visibility.models.ViewerContext

case class EvaluationContext(
  visibilityPolicy: VisibilityPolicy,
  params: Params,
  statsReceiver: StatsReceiver)
    extends HasParams {

  def ruleEnabledInContext(rule: Rule): Boolean = {
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

object EvaluationContext {

  def apply(
    safetyLevel: SafetyLevel,
    params: Params,
    statsReceiver: StatsReceiver
  ): EvaluationContext = {
    val visibilityPolicy = RuleBase.RuleMap(safetyLevel)
    new EvaluationContext(visibilityPolicy, params, statsReceiver)
  }

  case class Builder(
    statsReceiver: StatsReceiver,
    visibilityParams: VisibilityParams,
    viewerContext: ViewerContext,
    unitsOfDiversion: Seq[UnitOfDiversion] = Seq.empty,
    memoizeParams: Gate[Unit] = Gate.False,
  ) {

    private[this] val emptyContentToUoDCounter =
      statsReceiver.counter("empty_content_id_to_unit_of_diversion")

    def build(safetyLevel: SafetyLevel): EvaluationContext = {
      val policy = RuleBase.RuleMap(safetyLevel)
      val params = if (memoizeParams()) {
        visibilityParams.memoized(viewerContext, safetyLevel, unitsOfDiversion)
      } else {
        visibilityParams(viewerContext, safetyLevel, unitsOfDiversion)
      }
      new EvaluationContext(policy, params, statsReceiver)
    }

    def withUnitOfDiversion(unitOfDiversion: UnitOfDiversion*): Builder =
      this.copy(unitsOfDiversion = unitOfDiversion)

    def withMemoizedParams(memoizeParams: Gate[Unit]) = this.copy(memoizeParams = memoizeParams)
  }

}
