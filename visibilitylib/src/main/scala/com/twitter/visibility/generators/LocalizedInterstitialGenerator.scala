package com.tw ter.v s b l y.generators

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.common.act ons.Local zed ssage
 mport com.tw ter.v s b l y.common.act ons. ssageL nk
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.results.r chtext.Publ c nterestReasonToR chText
 mport com.tw ter.v s b l y.results.translat on.LearnMoreL nk
 mport com.tw ter.v s b l y.results.translat on.Res ce
 mport com.tw ter.v s b l y.results.translat on.SafetyResultReasonToRes ce
 mport com.tw ter.v s b l y.results.translat on.Translator
 mport com.tw ter.v s b l y.rules.E rgencyDynam c nterst  al
 mport com.tw ter.v s b l y.rules. nterst  al
 mport com.tw ter.v s b l y.rules. nterst  alL m edEngage nts
 mport com.tw ter.v s b l y.rules.Publ c nterest
 mport com.tw ter.v s b l y.rules.Reason
 mport com.tw ter.v s b l y.rules.T et nterst  al

object Local zed nterst  alGenerator {
  def apply(
    v s b l yDec der: Dec der,
    baseStatsRece ver: StatsRece ver,
  ): Local zed nterst  alGenerator = {
    new Local zed nterst  alGenerator(v s b l yDec der, baseStatsRece ver)
  }
}

class Local zed nterst  alGenerator pr vate (
  val v s b l yDec der: Dec der,
  val baseStatsRece ver: StatsRece ver) {

  pr vate val v s b l yDec derGates = V s b l yDec derGates(v s b l yDec der)
  pr vate val local zat onStatsRece ver = baseStatsRece ver.scope(" nterst  al_local zat on")
  pr vate val publ c nterest nterst  alStats =
    local zat onStatsRece ver.scope("publ c_ nterest_copy")
  pr vate val e rgencyDynam c nterst  alStats =
    local zat onStatsRece ver.scope("e rgency_dynam c_copy")
  pr vate val regular nterst  alStats = local zat onStatsRece ver.scope(" nterst  al_copy")

  def apply(v s b l yResult: V s b l yResult, languageTag: Str ng): V s b l yResult = {
     f (!v s b l yDec derGates.enableLocal zed nterst  alGenerator()) {
      return v s b l yResult
    }

    v s b l yResult.verd ct match {
      case  p :  nterst  alL m edEngage nts  f Publ c nterest.Reasons.conta ns( p .reason) =>
        v s b l yResult.copy(
          verd ct =  p .copy(
            local zed ssage = So (local zePubl c nterestCopy nResult( p , languageTag))
          ))
      case ed : E rgencyDynam c nterst  al =>
        v s b l yResult.copy(
          verd ct = E rgencyDynam c nterst  al(
            ed .copy,
            ed .l nkOpt,
            So (local zeE rgencyDynam cCopy nResult(ed , languageTag))
          ))
      case  nterst  al:  nterst  al =>
        v s b l yResult.copy(
          verd ct =  nterst  al.copy(
            local zed ssage = local ze nterst  alCopy nResult( nterst  al, languageTag)
          ))
      case t et nterst  al: T et nterst  al  f t et nterst  al. nterst  al. sDef ned =>
        t et nterst  al. nterst  al.get match {
          case  p :  nterst  alL m edEngage nts  f Publ c nterest.Reasons.conta ns( p .reason) =>
            v s b l yResult.copy(
              verd ct = t et nterst  al.copy(
                 nterst  al = So (
                   p .copy(
                    local zed ssage = So (local zePubl c nterestCopy nResult( p , languageTag))
                  ))
              ))
          case ed : E rgencyDynam c nterst  al =>
            v s b l yResult.copy(
              verd ct = t et nterst  al.copy(
                 nterst  al = So (
                  E rgencyDynam c nterst  al(
                    ed .copy,
                    ed .l nkOpt,
                    So (local zeE rgencyDynam cCopy nResult(ed , languageTag))
                  ))
              ))
          case  nterst  al:  nterst  al =>
            v s b l yResult.copy(
              verd ct = t et nterst  al.copy(
                 nterst  al = So (
                   nterst  al.copy(
                    local zed ssage = local ze nterst  alCopy nResult( nterst  al, languageTag)
                  ))
              ))
          case _ => v s b l yResult
        }
      case _ => v s b l yResult
    }
  }

  pr vate def local zeE rgencyDynam cCopy nResult(
    ed : E rgencyDynam c nterst  al,
    languageTag: Str ng
  ): Local zed ssage = {
    val text = ed .l nkOpt
      .map(_ => s"${ed .copy} {${Res ce.LearnMorePlaceholder}}")
      .getOrElse(ed .copy)

    val  ssageL nks = ed .l nkOpt
      .map { l nk =>
        val learnMoreText = Translator.translate(LearnMoreL nk, languageTag)
        Seq( ssageL nk(Res ce.LearnMorePlaceholder, learnMoreText, l nk))
      }.getOrElse(Seq.empty)

    e rgencyDynam c nterst  alStats.counter("local zed"). ncr()
    Local zed ssage(text, languageTag,  ssageL nks)
  }

  pr vate def local zePubl c nterestCopy nResult(
     p :  nterst  alL m edEngage nts,
    languageTag: Str ng
  ): Local zed ssage = {
    val safetyResultReason = Publ c nterest.ReasonToSafetyResultReason( p .reason)
    val text = Translator.translate(
      SafetyResultReasonToRes ce.res ce(safetyResultReason),
      languageTag,
    )

    val learnMoreL nk = Publ c nterestReasonToR chText.toLearnMoreL nk(safetyResultReason)
    val learnMoreText = Translator.translate(LearnMoreL nk, languageTag)
    val  ssageL nks = Seq( ssageL nk(Res ce.LearnMorePlaceholder, learnMoreText, learnMoreL nk))

    publ c nterest nterst  alStats.counter("local zed"). ncr()
    Local zed ssage(text, languageTag,  ssageL nks)
  }

  pr vate def local ze nterst  alCopy nResult(
     nterst  al:  nterst  al,
    languageTag: Str ng
  ): Opt on[Local zed ssage] = {
    val local zed ssageOpt = Reason
      .to nterst  alReason( nterst  al.reason)
      .flatMap( nterst  alReasonToLocal zed ssage(_, languageTag))

     f (local zed ssageOpt. sDef ned) {
      regular nterst  alStats.counter("local zed"). ncr()
      local zed ssageOpt
    } else {
      regular nterst  alStats.counter("empty"). ncr()
      None
    }
  }
}
