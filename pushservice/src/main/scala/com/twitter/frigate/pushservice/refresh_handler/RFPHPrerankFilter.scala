package com.tw ter.fr gate.pushserv ce.refresh_handler

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.pred cate.PreRank ngPred cates
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Sequent alPred cate
 mport com.tw ter.ut l._

class RFPHPrerankF lter(
)(
  globalStats: StatsRece ver) {
  def f lter(
    target: Target,
    hydratedCand dates: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[
    (Seq[Cand dateDeta ls[PushCand date]], Seq[Cand dateResult[PushCand date, Result]])
  ] = {
    lazy val f lterStats: StatsRece ver = globalStats.scope("RefreshForPushHandler/f lter")
    lazy val okF lterCounter: Counter = f lterStats.counter("ok")
    lazy val  nval dF lterCounter: Counter = f lterStats.counter(" nval d")
    lazy val  nval dF lterStat: StatsRece ver = f lterStats.scope(" nval d")
    lazy val  nval dF lterReasonStat: StatsRece ver =  nval dF lterStat.scope("reason")
    val allCand datesF lteredPreRank = f lterStats.counter("all_cand dates_f ltered")

    lazy val preRank ngPred cates = PreRank ngPred cates(
      f lterStats.scope("pred cates")
    )

    lazy val preRank ngPred cateCha n =
      new Sequent alPred cate[PushCand date](preRank ngPred cates)

    val pred cateCha n =  f (target.pushContext.ex sts(_.pred catesToEnable.ex sts(_.nonEmpty))) {
      val pred catesToEnable = target.pushContext.flatMap(_.pred catesToEnable).getOrElse(N l)
      new Sequent alPred cate[PushCand date](preRank ngPred cates.f lter { pred =>
        pred catesToEnable.conta ns(pred.na )
      })
    } else preRank ngPred cateCha n

    pred cateCha n
      .track(hydratedCand dates.map(_.cand date))
      .map { results =>
        val resultForPreRankF lter ng = results
          .z p(hydratedCand dates)
          .foldLeft(
            (
              Seq.empty[Cand dateDeta ls[PushCand date]],
              Seq.empty[Cand dateResult[PushCand date, Result]]
            )
          ) {
            case ((goodCand dates, f lteredCand dates), (result, cand dateDeta ls)) =>
              result match {
                case None =>
                  okF lterCounter. ncr()
                  (goodCand dates :+ cand dateDeta ls, f lteredCand dates)

                case So (pred: Na dPred cate[_]) =>
                   nval dF lterCounter. ncr()
                   nval dF lterReasonStat.counter(pred.na ). ncr()
                   nval dF lterReasonStat
                    .scope(cand dateDeta ls.cand date.commonRecType.toStr ng).counter(
                      pred.na ). ncr()

                  val r =  nval d(So (pred.na ))
                  (
                    goodCand dates,
                    f lteredCand dates :+ Cand dateResult[PushCand date, Result](
                      cand dateDeta ls.cand date,
                      cand dateDeta ls.s ce,
                      r
                    )
                  )
                case So (_) =>
                   nval dF lterCounter. ncr()
                   nval dF lterReasonStat.counter("unknown"). ncr()
                   nval dF lterReasonStat
                    .scope(cand dateDeta ls.cand date.commonRecType.toStr ng).counter(
                      "unknown"). ncr()

                  val r =  nval d(So ("F ltered by un-na d pred cate"))
                  (
                    goodCand dates,
                    f lteredCand dates :+ Cand dateResult[PushCand date, Result](
                      cand dateDeta ls.cand date,
                      cand dateDeta ls.s ce,
                      r
                    )
                  )
              }
          }

        resultForPreRankF lter ng match {
          case (val dCand dates, _)  f val dCand dates. sEmpty && hydratedCand dates.nonEmpty =>
            allCand datesF lteredPreRank. ncr()
          case _ => ()
        }

        resultForPreRankF lter ng
      }
  }
}
