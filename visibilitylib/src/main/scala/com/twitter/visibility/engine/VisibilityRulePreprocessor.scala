package com.tw ter.v s b l y.eng ne

 mport com.tw ter.abdec der.NullABDec der
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport com.tw ter.v s b l y.bu lder.V s b l yResultBu lder
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.rules.Rule.D sabledRuleResult
 mport com.tw ter.v s b l y.rules.Rule.EvaluatedRuleResult
 mport com.tw ter.v s b l y.rules.State._
 mport com.tw ter.v s b l y.rules._
 mport com.tw ter.v s b l y.rules.prov ders.Prov dedEvaluat onContext
 mport com.tw ter.v s b l y.rules.prov ders.Pol cyProv der

class V s b l yRulePreprocessor pr vate (
   tr csRecorder: V s b l yResults tr cRecorder,
  pol cyProv derOpt: Opt on[Pol cyProv der] = None) {

  pr vate[eng ne] def f lterEvaluableRules(
    evaluat onContext: Prov dedEvaluat onContext,
    resultBu lder: V s b l yResultBu lder,
    rules: Seq[Rule]
  ): (V s b l yResultBu lder, Seq[Rule]) = {
    val (bu lder, ruleL st) = rules.foldLeft((resultBu lder, Seq.empty[Rule])) {
      case ((bu lder, nextPassRules), rule) =>
         f (evaluat onContext.ruleEnabled nContext(rule)) {
          val m ss ngFeatures: Set[Feature[_]] = rule.featureDependenc es.collect {
            case feature: Feature[_]  f !bu lder.featureMap.conta ns(feature) => feature
          }

           f (m ss ngFeatures. sEmpty) {
            (bu lder, nextPassRules :+ rule)
          } else {
             tr csRecorder.recordRuleM ss ngFeatures(rule.na , m ss ngFeatures)
            (
              bu lder.w hRuleResult(
                rule,
                RuleResult(NotEvaluated, M ss ngFeature(m ss ngFeatures))
              ),
              nextPassRules
            )
          }
        } else {
          (bu lder.w hRuleResult(rule, D sabledRuleResult), nextPassRules)
        }
    }
    (bu lder, ruleL st)
  }

  pr vate[v s b l y] def preF lterRules(
    evaluat onContext: Prov dedEvaluat onContext,
    resolvedFeatureMap: Map[Feature[_], Any],
    resultBu lder: V s b l yResultBu lder,
    rules: Seq[Rule]
  ): (V s b l yResultBu lder, Seq[Rule]) = {
    val  sResolvedFeatureMap = resultBu lder.featureMap. s nstanceOf[ResolvedFeatureMap]
    val (bu lder, ruleL st) = rules.foldLeft((resultBu lder, Seq.empty[Rule])) {
      case ((bu lder, nextPassRules), rule) =>
        rule.preF lter(evaluat onContext, resolvedFeatureMap, NullABDec der) match {
          case NeedsFullEvaluat on =>
            (bu lder, nextPassRules :+ rule)
          case NotF ltered =>
            (bu lder, nextPassRules :+ rule)
          case F ltered  f  sResolvedFeatureMap =>
            (bu lder, nextPassRules :+ rule)
          case F ltered =>
            (bu lder.w hRuleResult(rule, EvaluatedRuleResult), nextPassRules)
        }
    }
    (bu lder, ruleL st)
  }

  pr vate[v s b l y] def evaluate(
    evaluat onContext: Prov dedEvaluat onContext,
    safetyLevel: SafetyLevel,
    resultBu lder: V s b l yResultBu lder
  ): (V s b l yResultBu lder, Seq[Rule]) = {
    val v s b l yPol cy = pol cyProv derOpt match {
      case So (pol cyProv der) =>
        pol cyProv der.pol cyForSurface(safetyLevel)
      case None => RuleBase.RuleMap(safetyLevel)
    }

     f (evaluat onContext.params(safetyLevel.enabledParam)) {
      evaluate(evaluat onContext, v s b l yPol cy, resultBu lder)
    } else {
       tr csRecorder.recordAct on(safetyLevel, "d sabled")

      val rules: Seq[Rule] = v s b l yPol cy.forContent d(resultBu lder.content d)
      val sk ppedResultBu lder = resultBu lder
        .w hRuleResultMap(rules.map(r => r -> RuleResult(Allow, Sk pped)).toMap)
        .w hVerd ct(verd ct = Allow)
        .w hF n s d(f n s d = true)

      (sk ppedResultBu lder, rules)
    }
  }

  pr vate[v s b l y] def evaluate(
    evaluat onContext: Prov dedEvaluat onContext,
    v s b l yPol cy: V s b l yPol cy,
    resultBu lder: V s b l yResultBu lder,
  ): (V s b l yResultBu lder, Seq[Rule]) = {

    val rules: Seq[Rule] = v s b l yPol cy.forContent d(resultBu lder.content d)

    val (secondPassBu lder, secondPassRules) =
      f lterEvaluableRules(evaluat onContext, resultBu lder, rules)

    val secondPassFeatureMap = secondPassBu lder.featureMap

    val secondPassConstantFeatures: Set[Feature[_]] = RuleBase
      .getFeaturesForRules(secondPassRules)
      .f lter(secondPassFeatureMap.conta nsConstant(_))

    val secondPassFeatureValues: Set[(Feature[_], Any)] = secondPassConstantFeatures.map {
      feature =>
        Try(secondPassFeatureMap.getConstant(feature)) match {
          case Return(value) => (feature, value)
          case Throw(ex) =>
             tr csRecorder.recordFa ledFeature(feature, ex)
            (feature, FeatureFa ledPlaceholderObject(ex))
        }
    }

    val resolvedFeatureMap: Map[Feature[_], Any] =
      secondPassFeatureValues.f lterNot {
        case (_, value) => value. s nstanceOf[FeatureFa ledPlaceholderObject]
      }.toMap

    val (preF lteredResultBu lder, preF lteredRules) = preF lterRules(
      evaluat onContext,
      resolvedFeatureMap,
      secondPassBu lder,
      secondPassRules
    )

    val preF lteredFeatureMap =
      RuleBase.removeUnusedFeaturesFromFeatureMap(
        preF lteredResultBu lder.featureMap,
        preF lteredRules)

    (preF lteredResultBu lder.w hFeatureMap(preF lteredFeatureMap), preF lteredRules)
  }
}

object V s b l yRulePreprocessor {
  def apply(
     tr csRecorder: V s b l yResults tr cRecorder,
    pol cyProv derOpt: Opt on[Pol cyProv der] = None
  ): V s b l yRulePreprocessor = {
    new V s b l yRulePreprocessor( tr csRecorder, pol cyProv derOpt)
  }
}
