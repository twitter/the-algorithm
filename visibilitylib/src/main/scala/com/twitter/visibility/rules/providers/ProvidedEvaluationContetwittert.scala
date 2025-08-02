package com.twitter.visibility.rules.providers

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.timelines.configapi.Params
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.rules.EvaluationContetwittert
import com.twitter.visibility.rules.VisibilityPolicy

sealed abstract class ProvidedEvaluationContetwittert(
  visibilityPolicy: VisibilityPolicy,
  params: Params,
  statsReceiver: StatsReceiver)
    etwittertends EvaluationContetwittert(
      visibilityPolicy = visibilityPolicy,
      params = params,
      statsReceiver = statsReceiver)

object ProvidedEvaluationContetwittert {

  def injectRuntimeRulesIntoEvaluationContetwittert(
    evaluationContetwittert: EvaluationContetwittert,
    safetyLevel: Option[SafetyLevel] = None,
    policyProviderOpt: Option[PolicyProvider] = None
  ): ProvidedEvaluationContetwittert = {
    (policyProviderOpt, safetyLevel) match {
      case (Some(policyProvider), Some(safetyLevel)) =>
        new InjectedEvaluationContetwittert(
          evaluationContetwittert = evaluationContetwittert,
          safetyLevel = safetyLevel,
          policyProvider = policyProvider)
      case (_, _) => new StaticEvaluationContetwittert(evaluationContetwittert)
    }
  }
}

private class StaticEvaluationContetwittert(
  evaluationContetwittert: EvaluationContetwittert)
    etwittertends ProvidedEvaluationContetwittert(
      visibilityPolicy = evaluationContetwittert.visibilityPolicy,
      params = evaluationContetwittert.params,
      statsReceiver = evaluationContetwittert.statsReceiver)

private class InjectedEvaluationContetwittert(
  evaluationContetwittert: EvaluationContetwittert,
  safetyLevel: SafetyLevel,
  policyProvider: PolicyProvider)
    etwittertends ProvidedEvaluationContetwittert(
      visibilityPolicy = policyProvider.policyForSurface(safetyLevel),
      params = evaluationContetwittert.params,
      statsReceiver = evaluationContetwittert.statsReceiver)
