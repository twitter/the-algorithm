package com.tw ter.v s b l y.bu lder

 mport com.tw ter.spam.rtf.thr ftscala.SafetyResult
 mport com.tw ter.v s b l y.common.act ons.converter.scala.DropReasonConverter
 mport com.tw ter.v s b l y.rules.ComposableAct ons._
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.rules._
 mport com.tw ter.v s b l y.{thr ftscala => t}

case class V s b l yResult(
  content d: Content d,
  featureMap: FeatureMap = FeatureMap.empty,
  ruleResultMap: Map[Rule, RuleResult] = Map.empty,
  verd ct: Act on = Allow,
  f n s d: Boolean = false,
  act ngRule: Opt on[Rule] = None,
  secondaryAct ngRules: Seq[Rule] = Seq(),
  secondaryVerd cts: Seq[Act on] = Seq(),
  resolvedFeatureMap: Map[Feature[_], Any] = Map.empty) {

  def getSafetyResult: SafetyResult =
    verd ct match {
      case  nterst  alL m edEngage nts(reason: Reason, _, _, _)
           f Publ c nterest.Reasons
            .conta ns(reason) =>
        SafetyResult(
          So (Publ c nterest.ReasonToSafetyResultReason(reason)),
          verd ct.toAct onThr ft()
        )
      case ComposableAct onsW h nterst  alL m edEngage nts(t et nterst  al)
           f Publ c nterest.Reasons.conta ns(t et nterst  al.reason) =>
        SafetyResult(
          So (Publ c nterest.ReasonToSafetyResultReason(t et nterst  al.reason)),
          verd ct.toAct onThr ft()
        )
      case FreedomOfSpeechNotReachReason(appealableReason) =>
        SafetyResult(
          So (FreedomOfSpeechNotReach.reasonToSafetyResultReason(appealableReason)),
          verd ct.toAct onThr ft()
        )
      case _ => SafetyResult(None, verd ct.toAct onThr ft())
    }

  def getUserV s b l yResult: Opt on[t.UserV s b l yResult] =
    (verd ct match {
      case Drop(reason, _) =>
        So (
          t.UserAct on.Drop(t.Drop(Reason.toDropReason(reason).map(DropReasonConverter.toThr ft))))
      case _ => None
    }).map(userAct on => t.UserV s b l yResult(So (userAct on)))
}

object V s b l yResult {
  class Bu lder {
    var featureMap: FeatureMap = FeatureMap.empty
    var ruleResultMap: Map[Rule, RuleResult] = Map.empty
    var verd ct: Act on = Allow
    var f n s d: Boolean = false
    var act ngRule: Opt on[Rule] = None
    var secondaryAct ngRules: Seq[Rule] = Seq()
    var secondaryVerd cts: Seq[Act on] = Seq()
    var resolvedFeatureMap: Map[Feature[_], Any] = Map.empty

    def w hFeatureMap(featureMapBld: FeatureMap) = {
      featureMap = featureMapBld
      t 
    }

    def w hRuleResultMap(ruleResultMapBld: Map[Rule, RuleResult]) = {
      ruleResultMap = ruleResultMapBld
      t 
    }

    def w hVerd ct(verd ctBld: Act on) = {
      verd ct = verd ctBld
      t 
    }

    def w hF n s d(f n s dBld: Boolean) = {
      f n s d = f n s dBld
      t 
    }

    def w hAct ngRule(act ngRuleBld: Opt on[Rule]) = {
      act ngRule = act ngRuleBld
      t 
    }

    def w hSecondaryAct ngRules(secondaryAct ngRulesBld: Seq[Rule]) = {
      secondaryAct ngRules = secondaryAct ngRulesBld
      t 
    }

    def w hSecondaryVerd cts(secondaryVerd ctsBld: Seq[Act on]) = {
      secondaryVerd cts = secondaryVerd ctsBld
      t 
    }

    def bu ld(content d: Content d) = V s b l yResult(
      content d,
      featureMap,
      ruleResultMap,
      verd ct,
      f n s d,
      act ngRule,
      secondaryAct ngRules,
      secondaryVerd cts,
      resolvedFeatureMap)
  }
}
