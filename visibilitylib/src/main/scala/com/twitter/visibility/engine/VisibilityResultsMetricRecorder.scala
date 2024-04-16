package com.tw ter.v s b l y.eng ne

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.stats.Verbos y
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.rules.NotEvaluated
 mport com.tw ter.v s b l y.rules.RuleResult
 mport com.tw ter.v s b l y.rules.State
 mport com.tw ter.v s b l y.rules.State.D sabled
 mport com.tw ter.v s b l y.rules.State.FeatureFa led
 mport com.tw ter.v s b l y.rules.State.M ss ngFeature
 mport com.tw ter.v s b l y.rules.State.RuleFa led
 mport com.tw ter.v s b l y.rules.Act on


case class V s b l yResults tr cRecorder(
  statsRece ver: StatsRece ver,
  captureDebugStats: Gate[Un ]) {

  pr vate val scopedStatsRece ver = new  mo z ngStatsRece ver(
    statsRece ver.scope("v s b l y_rule_eng ne")
  )
  pr vate val act onStats: StatsRece ver = scopedStatsRece ver.scope("by_act on")
  pr vate val featureFa lureRece ver: StatsRece ver =
    scopedStatsRece ver.scope("feature_fa led")
  pr vate val safetyLevelStatsRece ver: StatsRece ver =
    scopedStatsRece ver.scope("from_safety_level")
  pr vate val ruleStatsRece ver: StatsRece ver = scopedStatsRece ver.scope("for_rule")
  pr vate val ruleFa lureRece ver: StatsRece ver =
    scopedStatsRece ver.scope("rule_fa lures")
  pr vate val fa lClosedRece ver: StatsRece ver =
    scopedStatsRece ver.scope("fa l_closed")
  pr vate val ruleStatsBySafetyLevelRece ver: StatsRece ver =
    scopedStatsRece ver.scope("for_rule_by_safety_level")

  def recordSuccess(
    safetyLevel: SafetyLevel,
    result: V s b l yResult
  ): Un  = {
    recordAct on(safetyLevel, result.verd ct.fullNa )

    val  sFeatureFa lure = result.ruleResultMap.values
      .collectF rst {
        case RuleResult(_, FeatureFa led(_)) =>
          ruleFa lureRece ver.counter("feature_fa led"). ncr()
          true
      }.getOrElse(false)

    val  sM ss ngFeature = result.ruleResultMap.values
      .collectF rst {
        case RuleResult(_, M ss ngFeature(_)) =>
          ruleFa lureRece ver.counter("m ss ng_feature"). ncr()
          true
      }.getOrElse(false)

    val  sRuleFa led = result.ruleResultMap.values
      .collectF rst {
        case RuleResult(_, RuleFa led(_)) =>
          ruleFa lureRece ver.counter("rule_fa led"). ncr()
          true
      }.getOrElse(false)

     f ( sFeatureFa lure ||  sM ss ngFeature ||  sRuleFa led) {
      ruleFa lureRece ver.counter(). ncr()
    }

     f (captureDebugStats()) {
      val ruleBySafetyLevelStat =
        ruleStatsBySafetyLevelRece ver.scope(safetyLevel.na )
      result.ruleResultMap.foreach {
        case (rule, ruleResult) => {
          ruleBySafetyLevelStat
            .scope(rule.na )
            .scope("act on")
            .counter(Verbos y.Debug, ruleResult.act on.fullNa ). ncr()
          ruleBySafetyLevelStat
            .scope(rule.na )
            .scope("state")
            .counter(Verbos y.Debug, ruleResult.state.na ). ncr()
        }
      }
    }
  }

  def recordFa ledFeature(
    fa ledFeature: Feature[_],
    except on: Throwable
  ): Un  = {
    featureFa lureRece ver.counter(). ncr()

    val featureStat = featureFa lureRece ver.scope(fa ledFeature.na )
    featureStat.counter(). ncr()
    featureStat.counter(except on.getClass.getNa ). ncr()
  }

  def recordAct on(
    safetyLevel: SafetyLevel,
    act on: Str ng
  ): Un  = {
    safetyLevelStatsRece ver.scope(safetyLevel.na ).counter(act on). ncr()
    act onStats.counter(act on). ncr()
  }

  def recordUnknownSafetyLevel(
    safetyLevel: SafetyLevel
  ): Un  = {
    safetyLevelStatsRece ver
      .scope("unknown_safety_level")
      .counter(safetyLevel.na .toLo rCase). ncr()
  }

  def recordRuleM ss ngFeatures(
    ruleNa : Str ng,
    m ss ngFeatures: Set[Feature[_]]
  ): Un  = {
    val ruleStat = ruleStatsRece ver.scope(ruleNa )
    m ss ngFeatures.foreach { feature d =>
      ruleStat.scope("m ss ng_feature").counter(feature d.na ). ncr()
    }
    ruleStat.scope("act on").counter(NotEvaluated.fullNa ). ncr()
    ruleStat.scope("state").counter(M ss ngFeature(m ss ngFeatures).na ). ncr()
  }

  def recordRuleFa ledFeatures(
    ruleNa : Str ng,
    fa ledFeatures: Map[Feature[_], Throwable]
  ): Un  = {
    val ruleStat = ruleStatsRece ver.scope(ruleNa )

    ruleStat.scope("act on").counter(NotEvaluated.fullNa ). ncr()
    ruleStat.scope("state").counter(FeatureFa led(fa ledFeatures).na ). ncr()
  }

  def recordFa lClosed(rule: Str ng, state: State) {
    fa lClosedRece ver.scope(state.na ).counter(rule). ncr();
  }

  def recordRuleEvaluat on(
    ruleNa : Str ng,
    act on: Act on,
    state: State
  ): Un  = {
    val ruleStat = ruleStatsRece ver.scope(ruleNa )
    ruleStat.scope("act on").counter(act on.fullNa ). ncr()
    ruleStat.scope("state").counter(state.na ). ncr()
  }


  def recordRuleFallbackAct on(
    ruleNa : Str ng
  ): Un  = {
    val ruleStat = ruleStatsRece ver.scope(ruleNa )
    ruleStat.counter("fallback_act on"). ncr()
  }

  def recordRuleHoldBack(
    ruleNa : Str ng
  ): Un  = {
    ruleStatsRece ver.scope(ruleNa ).counter(" ldback"). ncr()
  }

  def recordRuleFa led(
    ruleNa : Str ng
  ): Un  = {
    ruleStatsRece ver.scope(ruleNa ).counter("fa led"). ncr()
  }

  def recordD sabledRule(
    ruleNa : Str ng
  ): Un  = recordRuleEvaluat on(ruleNa , NotEvaluated, D sabled)
}

object NullV s b l yResults tr csRecorder
    extends V s b l yResults tr cRecorder(NullStatsRece ver, Gate.False)
