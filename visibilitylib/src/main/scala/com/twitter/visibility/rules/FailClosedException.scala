package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.rules.State.FeatureFa led
 mport com.tw ter.v s b l y.rules.State.M ss ngFeature
 mport com.tw ter.v s b l y.rules.State.RuleFa led

abstract class Fa lClosedExcept on( ssage: Str ng, state: State, ruleNa : Str ng)
    extends Except on( ssage) {
  def getState: State = {
    state
  }

  def getRuleNa : Str ng = {
    ruleNa 
  }
}

case class M ss ngFeaturesExcept on(
  ruleNa : Str ng,
  m ss ngFeatures: Set[Feature[_]])
    extends Fa lClosedExcept on(
      s"A $ruleNa  rule evaluat on has ${m ss ngFeatures.s ze} m ss ng features: ${m ss ngFeatures
        .map(_.na )}",
      M ss ngFeature(m ss ngFeatures),
      ruleNa ) {}

case class FeaturesFa ledExcept on(
  ruleNa : Str ng,
  featureFa lures: Map[Feature[_], Throwable])
    extends Fa lClosedExcept on(
      s"A $ruleNa  rule evaluat on has ${featureFa lures.s ze} fa led features: ${featureFa lures.keys
        .map(_.na )}, ${featureFa lures.values}",
      FeatureFa led(featureFa lures),
      ruleNa ) {}

case class RuleFa ledExcept on(ruleNa : Str ng, except on: Throwable)
    extends Fa lClosedExcept on(
      s"A $ruleNa  rule evaluat on fa led to execute",
      RuleFa led(except on),
      ruleNa ) {}
