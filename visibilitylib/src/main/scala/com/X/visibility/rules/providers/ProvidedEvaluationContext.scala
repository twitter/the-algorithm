package com.X.visibility.rules.providers

import com.X.finagle.stats.StatsReceiver
import com.X.timelines.configapi.Params
import com.X.visibility.models.SafetyLevel
import com.X.visibility.rules.EvaluationContext
import com.X.visibility.rules.VisibilityPolicy

sealed abstract class ProvidedEvaluationContext(
  visibilityPolicy: VisibilityPolicy,
  params: Params,
  statsReceiver: StatsReceiver)
    extends EvaluationContext(
      visibilityPolicy = visibilityPolicy,
      params = params,
      statsReceiver = statsReceiver)

object ProvidedEvaluationContext {

  def injectRuntimeRulesIntoEvaluationContext(
    evaluationContext: EvaluationContext,
    safetyLevel: Option[SafetyLevel] = None,
    policyProviderOpt: Option[PolicyProvider] = None
  ): ProvidedEvaluationContext = {
    (policyProviderOpt, safetyLevel) match {
      case (Some(policyProvider), Some(safetyLevel)) =>
        new InjectedEvaluationContext(
          evaluationContext = evaluationContext,
          safetyLevel = safetyLevel,
          policyProvider = policyProvider)
      case (_, _) => new StaticEvaluationContext(evaluationContext)
    }
  }
}

private class StaticEvaluationContext(
  evaluationContext: EvaluationContext)
    extends ProvidedEvaluationContext(
      visibilityPolicy = evaluationContext.visibilityPolicy,
      params = evaluationContext.params,
      statsReceiver = evaluationContext.statsReceiver)

private class InjectedEvaluationContext(
  evaluationContext: EvaluationContext,
  safetyLevel: SafetyLevel,
  policyProvider: PolicyProvider)
    extends ProvidedEvaluationContext(
      visibilityPolicy = policyProvider.policyForSurface(safetyLevel),
      params = evaluationContext.params,
      statsReceiver = evaluationContext.statsReceiver)
