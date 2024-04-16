package com.tw ter.v s b l y. nterfaces.t ets.enr ch nts

 mport com.tw ter.featuresw c s.FSRec p ent
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.common.Local zedL m edAct onsS ce
 mport com.tw ter.v s b l y.common.act ons.converter.scala.L m edAct onTypeConverter
 mport com.tw ter.v s b l y.common.act ons.L m edAct onsPol cy
 mport com.tw ter.v s b l y.common.act ons.L m edAct onType
 mport com.tw ter.v s b l y.common.act ons.L m edEngage ntReason
 mport com.tw ter.v s b l y.rules.Act on
 mport com.tw ter.v s b l y.rules.E rgencyDynam c nterst  al
 mport com.tw ter.v s b l y.rules. nterst  alL m edEngage nts
 mport com.tw ter.v s b l y.rules.L m edEngage nts

case class Pol cyFeatureSw chResults(
  l m edAct onTypes: Opt on[Seq[L m edAct onType]],
  copyNa space: Str ng,
  promptType: Str ng,
  learnMoreUrl: Opt on[Str ng])

object L m edAct onsPol cyEnr ch nt {
  object FeatureSw chKeys {
    val L m edAct onTypes = "l m ed_act ons_pol cy_l m ed_act ons"
    val CopyNa space = "l m ed_act ons_pol cy_copy_na space"
    val PromptType = "l m ed_act ons_pol cy_prompt_type"
    val LearnMoreUrl = "l m ed_act ons_pol cy_prompt_learn_more_url"
  }

  val DefaultCopyNa Space = "Default"
  val DefaultPromptType = "bas c"
  val L m edAct onsPol cyEnr ch ntScope = "l m ed_act ons_pol cy_enr ch nt"
  val M ss ngL m edAct onTypesScope = "m ss ng_l m ed_act on_types"
  val ExecutedScope = "executed"

  def apply(
    result: V s b l yResult,
    local zedL m edAct onS ce: Local zedL m edAct onsS ce,
    languageCode: Str ng,
    countryCode: Opt on[Str ng],
    featureSw c s: FeatureSw c s,
    statsRece ver: StatsRece ver
  ): V s b l yResult = {
    val scopedStatsRece ver = statsRece ver.scope(L m edAct onsPol cyEnr ch ntScope)

    val enr chVerd ct_ = enr chVerd ct(
      _: Act on,
      local zedL m edAct onS ce,
      languageCode,
      countryCode,
      featureSw c s,
      scopedStatsRece ver
    )

    result.copy(
      verd ct = enr chVerd ct_(result.verd ct),
      secondaryVerd cts = result.secondaryVerd cts.map(enr chVerd ct_)
    )
  }

  pr vate def enr chVerd ct(
    verd ct: Act on,
    local zedL m edAct onsS ce: Local zedL m edAct onsS ce,
    languageCode: Str ng,
    countryCode: Opt on[Str ng],
    featureSw c s: FeatureSw c s,
    statsRece ver: StatsRece ver
  ): Act on = {
    val l m edAct onsPol cyForReason_ = l m edAct onsPol cyForReason(
      _: L m edEngage ntReason,
      local zedL m edAct onsS ce,
      languageCode,
      countryCode,
      featureSw c s,
      statsRece ver
    )
    val executedCounter = statsRece ver.scope(ExecutedScope)

    verd ct match {
      case le: L m edEngage nts => {
        executedCounter.counter(""). ncr()
        executedCounter.counter(le.na ). ncr()
        le.copy(
          pol cy = l m edAct onsPol cyForReason_(le.getL m edEngage ntReason)
        )
      }
      case  le:  nterst  alL m edEngage nts => {
        executedCounter.counter(""). ncr()
        executedCounter.counter( le.na ). ncr()
         le.copy(
          pol cy = l m edAct onsPol cyForReason_(
             le.getL m edEngage ntReason
          )
        )
      }
      case ed : E rgencyDynam c nterst  al => {
        executedCounter.counter(""). ncr()
        executedCounter.counter(ed .na ). ncr()
        E rgencyDynam c nterst  al(
          copy = ed .copy,
          l nkOpt = ed .l nkOpt,
          local zed ssage = ed .local zed ssage,
          pol cy = l m edAct onsPol cyForReason_(ed .getL m edEngage ntReason)
        )
      }
      case _ => verd ct
    }
  }

  pr vate def l m edAct onsPol cyForReason(
    reason: L m edEngage ntReason,
    local zedL m edAct onsS ce: Local zedL m edAct onsS ce,
    languageCode: Str ng,
    countryCode: Opt on[Str ng],
    featureSw c s: FeatureSw c s,
    statsRece ver: StatsRece ver
  ): Opt on[L m edAct onsPol cy] = {
    val pol cyConf g = getPol cyFeatureSw chResults(featureSw c s, reason)

    pol cyConf g.l m edAct onTypes match {
      case So (l m edAct onTypes)  f l m edAct onTypes.nonEmpty =>
        So (
          L m edAct onsPol cy(
            l m edAct onTypes.map(
              local zedL m edAct onsS ce.fetch(
                _,
                languageCode,
                countryCode,
                pol cyConf g.promptType,
                pol cyConf g.copyNa space,
                pol cyConf g.learnMoreUrl
              )
            )
          )
        )
      case _ => {
        statsRece ver
          .scope(M ss ngL m edAct onTypesScope).counter(reason.toL m edAct onsStr ng). ncr()
        None
      }
    }
  }

  pr vate def getPol cyFeatureSw chResults(
    featureSw c s: FeatureSw c s,
    reason: L m edEngage ntReason
  ): Pol cyFeatureSw chResults = {
    val rec p ent = FSRec p ent().w hCustomF elds(
      ("L m edEngage ntReason", reason.toL m edAct onsStr ng)
    )
    val featureSw c sResults = featureSw c s
      .matchRec p ent(rec p ent)

    val l m edAct onTypes = featureSw c sResults
      .getStr ngArray(FeatureSw chKeys.L m edAct onTypes)
      .map(_.map(L m edAct onTypeConverter.fromStr ng).flatten)

    val copyNa space = featureSw c sResults
      .getStr ng(FeatureSw chKeys.CopyNa space)
      .getOrElse(DefaultCopyNa Space)

    val promptType = featureSw c sResults
      .getStr ng(FeatureSw chKeys.PromptType)
      .getOrElse(DefaultPromptType)

    val learnMoreUrl = featureSw c sResults
      .getStr ng(FeatureSw chKeys.LearnMoreUrl)
      .f lter(_.nonEmpty)

    Pol cyFeatureSw chResults(l m edAct onTypes, copyNa space, promptType, learnMoreUrl)
  }
}
