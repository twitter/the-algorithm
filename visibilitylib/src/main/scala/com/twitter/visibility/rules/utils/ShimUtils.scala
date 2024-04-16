package com.tw ter.v s b l y.rules.ut ls

 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.features.FeatureMap
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.rules.F ltered
 mport com.tw ter.v s b l y.rules.Rule
 mport com.tw ter.v s b l y.rules.RuleBase
 mport com.tw ter.v s b l y.rules.RuleBase.RuleMap
 mport com.tw ter.v s b l y.rules.prov ders.Prov dedEvaluat onContext
 mport com.tw ter.v s b l y.rules.prov ders.Pol cyProv der

object Sh mUt ls {

  def preF lterFeatureMap(
    featureMap: FeatureMap,
    safetyLevel: SafetyLevel,
    content d: Content d,
    evaluat onContext: Prov dedEvaluat onContext,
    pol cyProv derOpt: Opt on[Pol cyProv der] = None,
  ): FeatureMap = {
    val safetyLevelRules: Seq[Rule] = pol cyProv derOpt match {
      case So (pol cyProv der) =>
        pol cyProv der
          .pol cyForSurface(safetyLevel)
          .forContent d(content d)
      case _ => RuleMap(safetyLevel).forContent d(content d)
    }

    val afterD sabledRules =
      safetyLevelRules.f lter(evaluat onContext.ruleEnabled nContext)

    val afterM ss ngFeatureRules =
      afterD sabledRules.f lter(rule => {
        val m ss ngFeatures: Set[Feature[_]] = rule.featureDependenc es.collect {
          case feature: Feature[_]  f !featureMap.conta ns(feature) => feature
        }
         f (m ss ngFeatures. sEmpty) {
          true
        } else {
          false
        }
      })

    val afterPreF lterRules = afterM ss ngFeatureRules.f lter(rule => {
      rule.preF lter(evaluat onContext, featureMap.constantMap, null) match {
        case F ltered =>
          false
        case _ =>
          true
      }
    })

    val f lteredFeatureMap =
      RuleBase.removeUnusedFeaturesFromFeatureMap(featureMap, afterPreF lterRules)

    f lteredFeatureMap
  }
}
