package com.twitter.visibility.rules

import com.twitter.visibility.features.Feature
import com.twitter.visibility.rules.State.FeatureFailed
import com.twitter.visibility.rules.State.MissingFeature
import com.twitter.visibility.rules.State.RuleFailed

abstract class FailClosedEtwitterception(message: String, state: State, ruleName: String)
    etwittertends Etwitterception(message) {
  def getState: State = {
    state
  }

  def getRuleName: String = {
    ruleName
  }
}

case class MissingFeaturesEtwitterception(
  ruleName: String,
  missingFeatures: Set[Feature[_]])
    etwittertends FailClosedEtwitterception(
      s"A $ruleName rule evaluation has ${missingFeatures.size} missing features: ${missingFeatures
        .map(_.name)}",
      MissingFeature(missingFeatures),
      ruleName) {}

case class FeaturesFailedEtwitterception(
  ruleName: String,
  featureFailures: Map[Feature[_], Throwable])
    etwittertends FailClosedEtwitterception(
      s"A $ruleName rule evaluation has ${featureFailures.size} failed features: ${featureFailures.keys
        .map(_.name)}, ${featureFailures.values}",
      FeatureFailed(featureFailures),
      ruleName) {}

case class RuleFailedEtwitterception(ruleName: String, etwitterception: Throwable)
    etwittertends FailClosedEtwitterception(
      s"A $ruleName rule evaluation failed to etwitterecute",
      RuleFailed(etwitterception),
      ruleName) {}
