package com.tw ter.v s b l y.eng ne

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.spam.rtf.thr ftscala.{SafetyLevel => Thr ftSafetyLevel}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.V s b l yResultBu lder
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.SafetyLevel.DeprecatedSafetyLevel
 mport com.tw ter.v s b l y.rules.Evaluat onContext
 mport com.tw ter.v s b l y.rules.State._
 mport com.tw ter.v s b l y.rules._
 mport com.tw ter.v s b l y.rules.prov ders.Prov dedEvaluat onContext
 mport com.tw ter.v s b l y.rules.prov ders.Pol cyProv der

class V s b l yRuleEng ne pr vate[V s b l yRuleEng ne] (
  rulePreprocessor: V s b l yRulePreprocessor,
   tr csRecorder: V s b l yResults tr cRecorder,
  enableComposableAct ons: Gate[Un ],
  enableFa lClosed: Gate[Un ],
  pol cyProv derOpt: Opt on[Pol cyProv der] = None)
    extends Dec derableV s b l yRuleEng ne {

  pr vate[v s b l y] def apply(
    evaluat onContext: Prov dedEvaluat onContext,
    v s b l yPol cy: V s b l yPol cy,
    v s b l yResultBu lder: V s b l yResultBu lder,
    enableShortC rcu  ng: Gate[Un ],
    preprocessedRules: Opt on[Seq[Rule]]
  ): St ch[V s b l yResult] = {
    val (resultBu lder, rules) = preprocessedRules match {
      case So (r) =>
        (v s b l yResultBu lder, r)
      case None =>
        rulePreprocessor.evaluate(evaluat onContext, v s b l yPol cy, v s b l yResultBu lder)
    }
    evaluate(evaluat onContext, resultBu lder, rules, enableShortC rcu  ng)
  }

  def apply(
    evaluat onContext: Evaluat onContext,
    safetyLevel: SafetyLevel,
    v s b l yResultBu lder: V s b l yResultBu lder,
    enableShortC rcu  ng: Gate[Un ] = Gate.True,
    preprocessedRules: Opt on[Seq[Rule]] = None
  ): St ch[V s b l yResult] = {
    val v s b l yPol cy = pol cyProv derOpt match {
      case So (pol cyProv der) =>
        pol cyProv der.pol cyForSurface(safetyLevel)
      case None => RuleBase.RuleMap(safetyLevel)
    }
     f (evaluat onContext.params(safetyLevel.enabledParam)) {
      apply(
        Prov dedEvaluat onContext. njectRunt  Rules ntoEvaluat onContext(
          evaluat onContext = evaluat onContext,
          safetyLevel = So (safetyLevel),
          pol cyProv derOpt = pol cyProv derOpt
        ),
        v s b l yPol cy,
        v s b l yResultBu lder,
        enableShortC rcu  ng,
        preprocessedRules
      ).onSuccess { result =>
           tr csRecorder.recordSuccess(safetyLevel, result)
        }
        .onFa lure { _ =>
           tr csRecorder.recordAct on(safetyLevel, "fa lure")
        }
    } else {
       tr csRecorder.recordAct on(safetyLevel, "d sabled")
      val rules: Seq[Rule] = v s b l yPol cy.forContent d(v s b l yResultBu lder.content d)
      St ch.value(
        v s b l yResultBu lder
          .w hRuleResultMap(rules.map(r => r -> RuleResult(Allow, Sk pped)).toMap)
          .w hVerd ct(verd ct = Allow)
          .w hF n s d(f n s d = true)
          .bu ld
      )
    }
  }

  def apply(
    evaluat onContext: Evaluat onContext,
    thr ftSafetyLevel: Thr ftSafetyLevel,
    v s b l yResultBu lder: V s b l yResultBu lder
  ): St ch[V s b l yResult] = {
    val safetyLevel: SafetyLevel = SafetyLevel.fromThr ft(thr ftSafetyLevel)
    safetyLevel match {
      case DeprecatedSafetyLevel =>
         tr csRecorder.recordUnknownSafetyLevel(safetyLevel)
        St ch.value(
          v s b l yResultBu lder
            .w hVerd ct(verd ct = Allow)
            .w hF n s d(f n s d = true)
            .bu ld
        )

      case thr ftSafetyLevel: SafetyLevel =>
        t (
          Prov dedEvaluat onContext. njectRunt  Rules ntoEvaluat onContext(
            evaluat onContext = evaluat onContext,
            safetyLevel = So (safetyLevel),
            pol cyProv derOpt = pol cyProv derOpt
          ),
          thr ftSafetyLevel,
          v s b l yResultBu lder
        )
    }
  }

  pr vate[v s b l y] def evaluateRules(
    evaluat onContext: Prov dedEvaluat onContext,
    resolvedFeatureMap: Map[Feature[_], Any],
    fa ledFeatures: Map[Feature[_], Throwable],
    resultBu lderW houtFa ledFeatures: V s b l yResultBu lder,
    preprocessedRules: Seq[Rule],
    enableShortC rcu  ng: Gate[Un ]
  ): V s b l yResultBu lder = {
    preprocessedRules
      .foldLeft(resultBu lderW houtFa ledFeatures) { (bu lder, rule) =>
        bu lder.ruleResults.get(rule) match {
          case So (RuleResult(_, state))  f state == Evaluated || state == ShortC rcu ed =>
            bu lder

          case _ =>
            val fa ledFeatureDependenc es: Map[Feature[_], Throwable] =
              fa ledFeatures.f lterKeys(key => rule.featureDependenc es.conta ns(key))

            val shortC rcu  =
              bu lder.f n s d && enableShortC rcu  ng() &&
                !(enableComposableAct ons() && bu lder. sVerd ctComposable())

             f (fa ledFeatureDependenc es.nonEmpty && rule.fallbackAct onBu lder. sEmpty) {
               tr csRecorder.recordRuleFa ledFeatures(rule.na , fa ledFeatureDependenc es)
              bu lder.w hRuleResult(
                rule,
                RuleResult(NotEvaluated, FeatureFa led(fa ledFeatureDependenc es)))

            } else  f (shortC rcu ) {

               tr csRecorder.recordRuleEvaluat on(rule.na , NotEvaluated, ShortC rcu ed)
              bu lder.w hRuleResult(rule, RuleResult(bu lder.verd ct, ShortC rcu ed))
            } else {

               f (fa ledFeatureDependenc es.nonEmpty && rule.fallbackAct onBu lder.nonEmpty) {
                 tr csRecorder.recordRuleFallbackAct on(rule.na )
              }


              val ruleResult =
                rule.evaluate(evaluat onContext, resolvedFeatureMap)
               tr csRecorder
                .recordRuleEvaluat on(rule.na , ruleResult.act on, ruleResult.state)
              val nextBu lder = (ruleResult.act on, bu lder.f n s d) match {
                case (NotEvaluated | Allow, _) =>
                  ruleResult.state match {
                    case  ldback =>
                       tr csRecorder.recordRuleHoldBack(rule.na )
                    case RuleFa led(_) =>
                       tr csRecorder.recordRuleFa led(rule.na )
                    case _ =>
                  }
                  bu lder.w hRuleResult(rule, ruleResult)

                case (_, true) =>
                  bu lder
                    .w hRuleResult(rule, ruleResult)
                    .w hSecondaryVerd ct(ruleResult.act on, rule)

                case _ =>
                  bu lder
                    .w hRuleResult(rule, ruleResult)
                    .w hVerd ct(ruleResult.act on, So (rule))
                    .w hF n s d(true)
              }

              nextBu lder
            }
        }
      }.w hResolvedFeatureMap(resolvedFeatureMap)
  }

  pr vate[v s b l y] def evaluateFa lClosed(
    evaluat onContext: Prov dedEvaluat onContext
  ): V s b l yResultBu lder => St ch[V s b l yResultBu lder] = { bu lder =>
    bu lder.fa lClosedExcept on(evaluat onContext) match {
      case So (e: Fa lClosedExcept on)  f enableFa lClosed() =>
         tr csRecorder.recordFa lClosed(e.getRuleNa , e.getState);
        St ch.except on(e)
      case _ => St ch.value(bu lder)
    }
  }

  pr vate[v s b l y] def c ckMarkF n s d(
    bu lder: V s b l yResultBu lder
  ): V s b l yResult = {
    val allRulesEvaluated: Boolean = bu lder.ruleResults.values.forall {
      case RuleResult(_, state) =>
        state == Evaluated || state == D sabled || state == Sk pped
      case _ =>
        false
    }

     f (allRulesEvaluated) {
      bu lder.w hF n s d(true).bu ld
    } else {
      bu lder.bu ld
    }
  }

  pr vate[v s b l y] def evaluate(
    evaluat onContext: Prov dedEvaluat onContext,
    v s b l yResultBu lder: V s b l yResultBu lder,
    preprocessedRules: Seq[Rule],
    enableShortC rcu  ng: Gate[Un ] = Gate.True
  ): St ch[V s b l yResult] = {

    val f nalBu lder =
      FeatureMap.resolve(v s b l yResultBu lder.features, evaluat onContext.statsRece ver).map {
        resolvedFeatureMap =>
          val (fa ledFeatureMap, successfulFeatureMap) = resolvedFeatureMap.constantMap.part  on({
            case (_, _: FeatureFa ledPlaceholderObject) => true
            case _ => false
          })

          val fa ledFeatures: Map[Feature[_], Throwable] =
            fa ledFeatureMap.mapValues({
              case fa lurePlaceholder: FeatureFa ledPlaceholderObject =>
                fa lurePlaceholder.throwable
            })

          val resultBu lderW houtFa ledFeatures =
            v s b l yResultBu lder.w hFeatureMap(ResolvedFeatureMap(successfulFeatureMap))

          evaluateRules(
            evaluat onContext,
            successfulFeatureMap,
            fa ledFeatures,
            resultBu lderW houtFa ledFeatures,
            preprocessedRules,
            enableShortC rcu  ng
          )
      }

    f nalBu lder.flatMap(evaluateFa lClosed(evaluat onContext)).map(c ckMarkF n s d)
  }
}

object V s b l yRuleEng ne {

  def apply(
    rulePreprocessor: Opt on[V s b l yRulePreprocessor] = None,
     tr csRecorder: V s b l yResults tr cRecorder = NullV s b l yResults tr csRecorder,
    enableComposableAct ons: Gate[Un ] = Gate.False,
    enableFa lClosed: Gate[Un ] = Gate.False,
    pol cyProv derOpt: Opt on[Pol cyProv der] = None,
  ): V s b l yRuleEng ne = {
    new V s b l yRuleEng ne(
      rulePreprocessor.getOrElse(V s b l yRulePreprocessor( tr csRecorder)),
       tr csRecorder,
      enableComposableAct ons,
      enableFa lClosed,
      pol cyProv derOpt = pol cyProv derOpt)
  }
}
