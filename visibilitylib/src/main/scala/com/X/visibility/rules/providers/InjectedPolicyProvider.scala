package com.X.visibility.rules.providers

import com.X.visibility.configapi.configs.VisibilityDeciderGates
import com.X.visibility.models.SafetyLevel
import com.X.visibility.rules.MixedVisibilityPolicy
import com.X.visibility.rules.RuleBase
import com.X.visibility.rules.generators.TweetRuleGenerator

class InjectedPolicyProvider(
  visibilityDeciderGates: VisibilityDeciderGates,
  tweetRuleGenerator: TweetRuleGenerator)
    extends PolicyProvider {

  private[rules] val policiesForSurface: Map[SafetyLevel, MixedVisibilityPolicy] =
    RuleBase.RuleMap.map {
      case (safetyLevel, policy) =>
        (
          safetyLevel,
          MixedVisibilityPolicy(
            originalPolicy = policy,
            additionalTweetRules = tweetRuleGenerator.rulesForSurface(safetyLevel)))
    }

  override def policyForSurface(safetyLevel: SafetyLevel): MixedVisibilityPolicy = {
    policiesForSurface(safetyLevel)
  }
}
