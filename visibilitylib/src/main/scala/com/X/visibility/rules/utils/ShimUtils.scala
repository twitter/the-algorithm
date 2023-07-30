package com.X.visibility.rules.utils

import com.X.visibility.features.Feature
import com.X.visibility.features.FeatureMap
import com.X.visibility.models.ContentId
import com.X.visibility.models.SafetyLevel
import com.X.visibility.rules.Filtered
import com.X.visibility.rules.Rule
import com.X.visibility.rules.RuleBase
import com.X.visibility.rules.RuleBase.RuleMap
import com.X.visibility.rules.providers.ProvidedEvaluationContext
import com.X.visibility.rules.providers.PolicyProvider

object ShimUtils {

  def preFilterFeatureMap(
    featureMap: FeatureMap,
    safetyLevel: SafetyLevel,
    contentId: ContentId,
    evaluationContext: ProvidedEvaluationContext,
    policyProviderOpt: Option[PolicyProvider] = None,
  ): FeatureMap = {
    val safetyLevelRules: Seq[Rule] = policyProviderOpt match {
      case Some(policyProvider) =>
        policyProvider
          .policyForSurface(safetyLevel)
          .forContentId(contentId)
      case _ => RuleMap(safetyLevel).forContentId(contentId)
    }

    val afterDisabledRules =
      safetyLevelRules.filter(evaluationContext.ruleEnabledInContext)

    val afterMissingFeatureRules =
      afterDisabledRules.filter(rule => {
        val missingFeatures: Set[Feature[_]] = rule.featureDependencies.collect {
          case feature: Feature[_] if !featureMap.contains(feature) => feature
        }
        if (missingFeatures.isEmpty) {
          true
        } else {
          false
        }
      })

    val afterPreFilterRules = afterMissingFeatureRules.filter(rule => {
      rule.preFilter(evaluationContext, featureMap.constantMap, null) match {
        case Filtered =>
          false
        case _ =>
          true
      }
    })

    val filteredFeatureMap =
      RuleBase.removeUnusedFeaturesFromFeatureMap(featureMap, afterPreFilterRules)

    filteredFeatureMap
  }
}
