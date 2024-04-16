package com.tw ter.v s b l y. nterfaces.t ets.enr ch nts

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.results.r chtext.Publ c nterestReasonToPla nText
 mport com.tw ter.v s b l y.rules.Act on
 mport com.tw ter.v s b l y.rules.Compl anceT etNot cePreEnr ch nt
 mport com.tw ter.v s b l y.rules.Publ c nterest
 mport com.tw ter.v s b l y.rules.Reason

object Compl anceT etNot ceEnr ch nt {
  val Compl anceT etNot ceEnr ch ntScope = "compl ance_t et_not ce_enr ch nt"
  val Compl anceT etNot cePreEnr ch ntAct onScope =
    "compl ance_t et_not ce_pre_enr ch nt_act on"

  val engl shLanguageTag = "en"

  def apply(result: V s b l yResult, statsRece ver: StatsRece ver): V s b l yResult = {
    val scopedStatsRece ver = statsRece ver.scope(Compl anceT etNot ceEnr ch ntScope)

    val enr c dVerd ct = enr chVerd ct(result.verd ct, scopedStatsRece ver)
    result.copy(verd ct = enr c dVerd ct)
  }

  pr vate def enr chVerd ct(
    verd ct: Act on,
    statsRece ver: StatsRece ver
  ): Act on = {
    val preEnr ch ntAct onScope =
      statsRece ver.scope(Compl anceT etNot cePreEnr ch ntAct onScope)

    verd ct match {
      case compl anceT etNot cePreEnr ch ntVerd ct: Compl anceT etNot cePreEnr ch nt =>
        preEnr ch ntAct onScope.counter(""). ncr()

        val verd ctW hDeta lsAndUrl = compl anceT etNot cePreEnr ch ntVerd ct.reason match {
          case Reason.Unspec f ed =>
            preEnr ch ntAct onScope.counter("reason_unspec f ed"). ncr()
            compl anceT etNot cePreEnr ch ntVerd ct

          case reason =>
            preEnr ch ntAct onScope.counter("reason_spec f ed"). ncr()
            val safetyResultReason = Publ c nterest.ReasonToSafetyResultReason(reason)
            val (deta ls, url) =
              Publ c nterestReasonToPla nText(safetyResultReason, engl shLanguageTag)
            compl anceT etNot cePreEnr ch ntVerd ct.copy(
              deta ls = So (deta ls),
              extendedDeta lsUrl = So (url))
        }
        verd ctW hDeta lsAndUrl.toCompl anceT etNot ce()

      case _ => verd ct
    }
  }
}
