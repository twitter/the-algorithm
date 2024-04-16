package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType.Exper  ntalNudge
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType.Semant cCoreM s nformat on
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType.UnsafeUrl
 mport com.tw ter.v s b l y.common.Local zedNudgeS ce
 mport com.tw ter.v s b l y.common.act ons.T etV s b l yNudgeReason
 mport com.tw ter.v s b l y.common.act ons.T etV s b l yNudgeReason.Exper  ntalNudgeSafetyLabelReason
 mport com.tw ter.v s b l y.common.act ons.T etV s b l yNudgeReason.Semant cCoreM s nformat onLabelReason
 mport com.tw ter.v s b l y.common.act ons.T etV s b l yNudgeReason.UnsafeURLLabelReason
 mport com.tw ter.v s b l y.rules.Local zedNudge

class T etV s b l yNudgeS ceWrapper(local zedNudgeS ce: Local zedNudgeS ce) {

  def getLocal zedNudge(
    reason: T etV s b l yNudgeReason,
    languageCode: Str ng,
    countryCode: Opt on[Str ng]
  ): Opt on[Local zedNudge] =
    reason match {
      case Exper  ntalNudgeSafetyLabelReason =>
        fetchNudge(Exper  ntalNudge, languageCode, countryCode)
      case Semant cCoreM s nformat onLabelReason =>
        fetchNudge(Semant cCoreM s nformat on, languageCode, countryCode)
      case UnsafeURLLabelReason =>
        fetchNudge(UnsafeUrl, languageCode, countryCode)
    }

  pr vate def fetchNudge(
    safetyLabel: SafetyLabelType,
    languageCode: Str ng,
    countryCode: Opt on[Str ng]
  ): Opt on[Local zedNudge] = {
    local zedNudgeS ce
      .fetch(safetyLabel, languageCode, countryCode)
      .map(Local zedNudge.fromStratoThr ft)
  }
}
