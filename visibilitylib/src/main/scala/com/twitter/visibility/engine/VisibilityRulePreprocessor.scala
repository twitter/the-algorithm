package com.twitter.visibility.engine

import com.twitter.abdecider.NullABDecider
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try
import com.twitter.visibility.builder.VisibilityResultBuilder
import com.twitter.visibility.features._
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.rules.Rule.DisabledRuleResult
import com.twitter.visibility.rules.Rule.EvaluatedRuleResult
import com.twitter.visibility.rules.State._
import com.twitter.visibility.rules._
import com.twitter.visibility.rules.providers.ProvidedEvaluationContetwittert
import com.twitter.visibility.rules.providers.PolicyProvider

class VisibilityRulePreprocessor private (
  metricsRecorder: VisibilityResultsMetricRecorder,
  policyProviderOpt: Option[PolicyProvider] = None) {

  private[engine] def filterEvaluableRules(
    evaluationContetwittert: ProvidedEvaluationContetwittert,
    resultBuilder: VisibilityResultBuilder,
    rules: Seq[Rule]
  ): (VisibilityResultBuilder, Seq[Rule]) = {
    val (builder, ruleList) = rules.foldLeft((resultBuilder, Seq.empty[Rule])) {
      case ((builder, netwittertPassRules), rule) =>
        if (evaluationContetwittert.ruleEnabledInContetwittert(rule)) {
          val missingFeatures: Set[Feature[_]] = rule.featureDependencies.collect {
            case feature: Feature[_] if !builder.featureMap.contains(feature) => feature
          }

          if (missingFeatures.isEmpty) {
            (builder, netwittertPassRules :+ rule)
          } else {
            metricsRecorder.recordRuleMissingFeatures(rule.name, missingFeatures)
            (
              builder.withRuleResult(
                rule,
                RuleResult(NotEvaluated, MissingFeature(missingFeatures))
              ),
              netwittertPassRules
            )
          }
        } else {
          (builder.withRuleResult(rule, DisabledRuleResult), netwittertPassRules)
        }
    }
    (builder, ruleList)
  }

  private[visibility] def preFilterRules(
    evaluationContetwittert: ProvidedEvaluationContetwittert,
    resolvedFeatureMap: Map[Feature[_], Any],
    resultBuilder: VisibilityResultBuilder,
    rules: Seq[Rule]
  ): (VisibilityResultBuilder, Seq[Rule]) = {
    val isResolvedFeatureMap = resultBuilder.featureMap.isInstanceOf[ResolvedFeatureMap]
    val (builder, ruleList) = rules.foldLeft((resultBuilder, Seq.empty[Rule])) {
      case ((builder, netwittertPassRules), rule) =>
        rule.preFilter(evaluationContetwittert, resolvedFeatureMap, NullABDecider) match {
          case NeedsFullEvaluation =>
            (builder, netwittertPassRules :+ rule)
          case NotFiltered =>
            (builder, netwittertPassRules :+ rule)
          case Filtered if isResolvedFeatureMap =>
            (builder, netwittertPassRules :+ rule)
          case Filtered =>
            (builder.withRuleResult(rule, EvaluatedRuleResult), netwittertPassRules)
        }
    }
    (builder, ruleList)
  }

  private[visibility] def evaluate(
    evaluationContetwittert: ProvidedEvaluationContetwittert,
    safetyLevel: SafetyLevel,
    resultBuilder: VisibilityResultBuilder
  ): (VisibilityResultBuilder, Seq[Rule]) = {
    val visibilityPolicy = policyProviderOpt match {
      case Some(policyProvider) =>
        policyProvider.policyForSurface(safetyLevel)
      case None => RuleBase.RuleMap(safetyLevel)
    }

    if (evaluationContetwittert.params(safetyLevel.enabledParam)) {
      evaluate(evaluationContetwittert, visibilityPolicy, resultBuilder)
    } else {
      metricsRecorder.recordAction(safetyLevel, "disabled")

      val rules: Seq[Rule] = visibilityPolicy.forContentId(resultBuilder.contentId)
      val skippedResultBuilder = resultBuilder
        .withRuleResultMap(rules.map(r => r -> RuleResult(Allow, Skipped)).toMap)
        .withVerdict(verdict = Allow)
        .withFinished(finished = true)

      (skippedResultBuilder, rules)
    }
  }

  private[visibility] def evaluate(
    evaluationContetwittert: ProvidedEvaluationContetwittert,
    visibilityPolicy: VisibilityPolicy,
    resultBuilder: VisibilityResultBuilder,
  ): (VisibilityResultBuilder, Seq[Rule]) = {

    val rules: Seq[Rule] = visibilityPolicy.forContentId(resultBuilder.contentId)

    val (secondPassBuilder, secondPassRules) =
      filterEvaluableRules(evaluationContetwittert, resultBuilder, rules)

    val secondPassFeatureMap = secondPassBuilder.featureMap

    val secondPassConstantFeatures: Set[Feature[_]] = RuleBase
      .getFeaturesForRules(secondPassRules)
      .filter(secondPassFeatureMap.containsConstant(_))

    val secondPassFeatureValues: Set[(Feature[_], Any)] = secondPassConstantFeatures.map {
      feature =>
        Try(secondPassFeatureMap.getConstant(feature)) match {
          case Return(value) => (feature, value)
          case Throw(etwitter) =>
            metricsRecorder.recordFailedFeature(feature, etwitter)
            (feature, FeatureFailedPlaceholderObject(etwitter))
        }
    }

    val resolvedFeatureMap: Map[Feature[_], Any] =
      secondPassFeatureValues.filterNot {
        case (_, value) => value.isInstanceOf[FeatureFailedPlaceholderObject]
      }.toMap

    val (preFilteredResultBuilder, preFilteredRules) = preFilterRules(
      evaluationContetwittert,
      resolvedFeatureMap,
      secondPassBuilder,
      secondPassRules
    )

    val preFilteredFeatureMap =
      RuleBase.removeUnusedFeaturesFromFeatureMap(
        preFilteredResultBuilder.featureMap,
        preFilteredRules)

    (preFilteredResultBuilder.withFeatureMap(preFilteredFeatureMap), preFilteredRules)
  }
}

object VisibilityRulePreprocessor {
  def apply(
    metricsRecorder: VisibilityResultsMetricRecorder,
    policyProviderOpt: Option[PolicyProvider] = None
  ): VisibilityRulePreprocessor = {
    new VisibilityRulePreprocessor(metricsRecorder, policyProviderOpt)
  }
}
