package com.twitter.visibility.rules.providers

import com.twitter.visibility.configapi.configs.VisibilityDeciderGates
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.rules.MitwitteredVisibilityPolicy
import com.twitter.visibility.rules.RuleBase
import com.twitter.visibility.rules.generators.TweetRuleGenerator

class InjectedPolicyProvider(
  visibilityDeciderGates: VisibilityDeciderGates,
  tweetRuleGenerator: TweetRuleGenerator)
    etwittertends PolicyProvider {

  private[rules] val policiesForSurface: Map[SafetyLevel, MitwitteredVisibilityPolicy] =
    RuleBase.RuleMap.map {
      case (safetyLevel, policy) =>
        (
          safetyLevel,
          MitwitteredVisibilityPolicy(
            originalPolicy = policy,
            additionalTweetRules = tweetRuleGenerator.rulesForSurface(safetyLevel)))
    }

  override def policyForSurface(safetyLevel: SafetyLevel): MitwitteredVisibilityPolicy = {
    policiesForSurface(safetyLevel)
  }
}
