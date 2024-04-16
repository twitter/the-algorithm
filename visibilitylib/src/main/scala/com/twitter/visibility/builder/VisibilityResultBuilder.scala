package com.tw ter.v s b l y.bu lder

 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.rules.Act on
 mport com.tw ter.v s b l y.rules.Allow
 mport com.tw ter.v s b l y.rules.Evaluat onContext
 mport com.tw ter.v s b l y.rules.Fa lClosedExcept on
 mport com.tw ter.v s b l y.rules.FeaturesFa ledExcept on
 mport com.tw ter.v s b l y.rules.M ss ngFeaturesExcept on
 mport com.tw ter.v s b l y.rules.Rule
 mport com.tw ter.v s b l y.rules.RuleFa ledExcept on
 mport com.tw ter.v s b l y.rules.RuleResult
 mport com.tw ter.v s b l y.rules.State.FeatureFa led
 mport com.tw ter.v s b l y.rules.State.M ss ngFeature
 mport com.tw ter.v s b l y.rules.State.RuleFa led

class V s b l yResultBu lder(
  val content d: Content d,
  val featureMap: FeatureMap = FeatureMap.empty,
  pr vate var ruleResultMap: Map[Rule, RuleResult] = Map.empty) {
  pr vate var mapBu lder = Map.newBu lder[Rule, RuleResult]
  mapBu lder ++= ruleResultMap
  var verd ct: Act on = Allow
  var f n s d: Boolean = false
  var features: FeatureMap = featureMap
  var act ngRule: Opt on[Rule] = None
  var secondaryVerd cts: Seq[Act on] = Seq()
  var secondaryAct ngRules: Seq[Rule] = Seq()
  var resolvedFeatureMap: Map[Feature[_], Any] = Map.empty

  def ruleResults: Map[Rule, RuleResult] = mapBu lder.result()

  def w hFeatureMap(featureMap: FeatureMap): V s b l yResultBu lder = {
    t .features = featureMap
    t 
  }

  def w hRuleResultMap(ruleResultMap: Map[Rule, RuleResult]): V s b l yResultBu lder = {
    t .ruleResultMap = ruleResultMap
    mapBu lder = Map.newBu lder[Rule, RuleResult]
    mapBu lder ++= ruleResultMap
    t 
  }

  def w hRuleResult(rule: Rule, result: RuleResult): V s b l yResultBu lder = {
    mapBu lder += ((rule, result))
    t 
  }

  def w hVerd ct(verd ct: Act on, ruleOpt: Opt on[Rule] = None): V s b l yResultBu lder = {
    t .verd ct = verd ct
    t .act ngRule = ruleOpt
    t 
  }

  def w hSecondaryVerd ct(verd ct: Act on, rule: Rule): V s b l yResultBu lder = {
    t .secondaryVerd cts = t .secondaryVerd cts :+ verd ct
    t .secondaryAct ngRules = t .secondaryAct ngRules :+ rule
    t 
  }

  def w hF n s d(f n s d: Boolean): V s b l yResultBu lder = {
    t .f n s d = f n s d
    t 
  }

  def w hResolvedFeatureMap(
    resolvedFeatureMap: Map[Feature[_], Any]
  ): V s b l yResultBu lder = {
    t .resolvedFeatureMap = resolvedFeatureMap
    t 
  }

  def  sVerd ctComposable(): Boolean = t .verd ct. sComposable

  def fa lClosedExcept on(evaluat onContext: Evaluat onContext): Opt on[Fa lClosedExcept on] = {
    mapBu lder
      .result().collect {
        case (r: Rule, RuleResult(_, M ss ngFeature(mf)))
             f r.shouldFa lClosed(evaluat onContext.params) =>
          So (M ss ngFeaturesExcept on(r.na , mf))
        case (r: Rule, RuleResult(_, FeatureFa led(ff)))
             f r.shouldFa lClosed(evaluat onContext.params) =>
          So (FeaturesFa ledExcept on(r.na , ff))
        case (r: Rule, RuleResult(_, RuleFa led(t)))
             f r.shouldFa lClosed(evaluat onContext.params) =>
          So (RuleFa ledExcept on(r.na , t))
      }.toL st.foldLeft(None: Opt on[Fa lClosedExcept on]) { (acc, arg) =>
        (acc, arg) match {
          case (None, So (_)) => arg
          case (So (FeaturesFa ledExcept on(_, _)), So (M ss ngFeaturesExcept on(_, _))) => arg
          case (So (RuleFa ledExcept on(_, _)), So (M ss ngFeaturesExcept on(_, _))) => arg
          case (So (RuleFa ledExcept on(_, _)), So (FeaturesFa ledExcept on(_, _))) => arg
          case _ => acc
        }
      }
  }

  def bu ld: V s b l yResult = {
    V s b l yResult(
      content d = content d,
      featureMap = features,
      ruleResultMap = mapBu lder.result(),
      verd ct = verd ct,
      f n s d = f n s d,
      act ngRule = act ngRule,
      secondaryAct ngRules = secondaryAct ngRules,
      secondaryVerd cts = secondaryVerd cts,
      resolvedFeatureMap = resolvedFeatureMap
    )
  }
}
