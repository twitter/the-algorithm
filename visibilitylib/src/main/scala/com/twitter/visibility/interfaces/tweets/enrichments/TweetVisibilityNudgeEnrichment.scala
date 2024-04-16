package com.tw ter.v s b l y. nterfaces.t ets.enr ch nts

 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.bu lder.t ets.T etV s b l yNudgeS ceWrapper
 mport com.tw ter.v s b l y.common.act ons.T etV s b l yNudgeReason.Semant cCoreM s nformat onLabelReason
 mport com.tw ter.v s b l y.rules.Act on
 mport com.tw ter.v s b l y.rules.Local zedNudge
 mport com.tw ter.v s b l y.rules.Soft ntervent on
 mport com.tw ter.v s b l y.rules.T etV s b l yNudge

object T etV s b l yNudgeEnr ch nt {

  def apply(
    result: V s b l yResult,
    t etV s b l yNudgeS ceWrapper: T etV s b l yNudgeS ceWrapper,
    languageCode: Str ng,
    countryCode: Opt on[Str ng]
  ): V s b l yResult = {

    val soft ntervent on = extractSoft ntervent on(result.verd ct, result.secondaryVerd cts)

    val enr c dPr maryVerd ct = enr chAct on(
      result.verd ct,
      t etV s b l yNudgeS ceWrapper,
      soft ntervent on,
      languageCode,
      countryCode)

    val enr c dSecondaryVerd cts: Seq[Act on] =
      result.secondaryVerd cts.map(sv =>
        enr chAct on(
          sv,
          t etV s b l yNudgeS ceWrapper,
          soft ntervent on,
          languageCode,
          countryCode))

    result.copy(verd ct = enr c dPr maryVerd ct, secondaryVerd cts = enr c dSecondaryVerd cts)
  }

  pr vate def extractSoft ntervent on(
    pr mary: Act on,
    secondar es: Seq[Act on]
  ): Opt on[Soft ntervent on] = {
    pr mary match {
      case s : Soft ntervent on => So (s )
      case _ =>
        secondar es.collectF rst {
          case sv: Soft ntervent on => sv
        }
    }
  }

  pr vate def enr chAct on(
    act on: Act on,
    t etV s b l yNudgeS ceWrapper: T etV s b l yNudgeS ceWrapper,
    soft ntervent on: Opt on[Soft ntervent on],
    languageCode: Str ng,
    countryCode: Opt on[Str ng]
  ): Act on = {
    act on match {
      case T etV s b l yNudge(reason, None) =>
        val local zedNudge =
          t etV s b l yNudgeS ceWrapper.getLocal zedNudge(reason, languageCode, countryCode)
         f (reason == Semant cCoreM s nformat onLabelReason)
          T etV s b l yNudge(
            reason,
            enr chLocal zedM s nfoNudge(local zedNudge, soft ntervent on))
        else
          T etV s b l yNudge(reason, local zedNudge)
      case _ => act on
    }
  }

  pr vate def enr chLocal zedM s nfoNudge(
    local zedNudge: Opt on[Local zedNudge],
    soft ntervent on: Opt on[Soft ntervent on]
  ): Opt on[Local zedNudge] = {
    soft ntervent on match {
      case So (s ) => {
        val enr c dLocal zedNudge = local zedNudge.map { ln =>
          val enr c dLocal zedNudgeAct ons = ln.local zedNudgeAct ons.map { na =>
            val enr c dPayload = na.nudgeAct onPayload.map { payload =>
              payload.copy(ctaUrl = s .deta lsUrl,  ad ng = s .warn ng)
            }
            na.copy(nudgeAct onPayload = enr c dPayload)
          }
          ln.copy(local zedNudgeAct ons = enr c dLocal zedNudgeAct ons)
        }
        enr c dLocal zedNudge
      }
      case None => local zedNudge
    }
  }

}
