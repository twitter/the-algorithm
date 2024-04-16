package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.base.Cand dateResult
 mport com.tw ter.fr gate.common.base. nval d
 mport com.tw ter.fr gate.common.base.OK
 mport com.tw ter.fr gate.common.base.Result
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.refresh_handler.ResultW hDebug nfo
 mport com.tw ter.fr gate.pushserv ce.take.cand date_val dator.SendHandlerPostCand dateVal dator
 mport com.tw ter.fr gate.pushserv ce.take.cand date_val dator.SendHandlerPreCand dateVal dator
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushStatus
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter.ut l.Future

class SendHandlerPred cateUt l()(globalStats: StatsRece ver) {
   mpl c  val statsRece ver: StatsRece ver =
    globalStats.scope("SendHandler")
  pr vate val val dateStats: StatsRece ver = statsRece ver.scope("val date")

  pr vate def updateF lteredStatusExptStats(cand date: PushCand date, predNa : Str ng): Un  = {

    val recTypeStat = globalStats.scope(
      cand date.commonRecType.toStr ng
    )

    recTypeStat.counter(PushStatus.F ltered.toStr ng). ncr()
    recTypeStat
      .scope(PushStatus.F ltered.toStr ng)
      .counter(predNa )
      . ncr()
  }

  /**
   * Pars ng t  cand dateVal dtor result  nto des red format for preVal dat on before ml f lter ng
   * @param hydratedCand dates
   * @param cand dateVal dator
   * @return
   */
  def preVal dat onForCand date(
    hydratedCand dates: Seq[Cand dateDeta ls[PushCand date]],
    cand dateVal dator: SendHandlerPreCand dateVal dator
  ): Future[
    (Seq[Cand dateDeta ls[PushCand date]], Seq[Cand dateResult[PushCand date, Result]])
  ] = {
    val predResultFuture =
      Future.collect(
        hydratedCand dates.map(hydratedCand date =>
          cand dateVal dator.val dateCand date(hydratedCand date.cand date))
      )

    predResultFuture.map { results =>
      results
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
                (goodCand dates :+ cand dateDeta ls, f lteredCand dates)
              case So (pred: Na dPred cate[_]) =>
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
    }
  }

  /**
   * Pars ng t  cand dateVal dtor result  nto des red format for postVal dat on  nclud ng and after ml f lter ng
   * @param cand date
   * @param cand dateVal dator
   * @return
   */
  def postVal dat onForCand date(
    cand date: PushCand date,
    cand dateVal dator: SendHandlerPostCand dateVal dator
  ): Future[ResultW hDebug nfo] = {
    val predResultFuture =
      cand dateVal dator.val dateCand date(cand date)

    predResultFuture.map {
      case (So (pred: Na dPred cate[_])) =>
        val dateStats.counter("f ltered_by_na d_general_pred cate"). ncr()
        updateF lteredStatusExptStats(cand date, pred.na )
        ResultW hDebug nfo(
           nval d(So (pred.na )),
          N l
        )

      case So (_) =>
        val dateStats.counter("f ltered_by_unna d_general_pred cate"). ncr()
        updateF lteredStatusExptStats(cand date, predNa  = "unk")
        ResultW hDebug nfo(
           nval d(So ("unna d_cand date_pred cate")),
          N l
        )

      case _ =>
        val dateStats.counter("accepted_push_ok"). ncr()
        ResultW hDebug nfo(
          OK,
          N l
        )
    }
  }
}
