package com.tw ter.fr gate.pushserv ce.take.cand date_val dator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.logger.MRLogger
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.TakeCommonPred cates
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter. rm .pred cate.ConcurrentPred cate
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter. rm .pred cate.Sequent alPred cate
 mport com.tw ter.ut l.Future

tra  Cand dateVal dator extends TakeCommonPred cates {

  overr de  mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver

  protected val log = MRLogger("Cand dateVal dator")

  pr vate lazy val sk pF ltersCounter = statsRece ver.counter("enable_sk p_f lters")
  pr vate lazy val ema lUserSk pF ltersCounter =
    statsRece ver.counter("ema l_user_enable_sk p_f lters")
  pr vate lazy val enablePred catesCounter = statsRece ver.counter("enable_pred cates")

  protected def enabledPred cates[C <: PushCand date](
    cand date: C,
    pred cates: L st[Na dPred cate[C]]
  ): L st[Na dPred cate[C]] = {
    val target = cand date.target
    val sk pF lters: Boolean =
      target.pushContext.flatMap(_.sk pF lters).getOrElse(false) || target.params(
        PushFeatureSw chParams.Sk pPostRank ngF lters)

     f (sk pF lters) {
      sk pF ltersCounter. ncr()
       f (target. sEma lUser) ema lUserSk pF ltersCounter. ncr()

      val pred catesToEnable = target.pushContext.flatMap(_.pred catesToEnable).getOrElse(N l)
       f (pred catesToEnable.nonEmpty) enablePred catesCounter. ncr()

      //  f   sk p pred cates on pushContext, only enable t  expl c ly spec f ed pred cates
      pred cates.f lter(pred catesToEnable.conta ns)
    } else pred cates
  }

  protected def executeSequent alPred cates[C <: PushCand date](
    cand date: C,
    pred cates: L st[Na dPred cate[C]]
  ): Future[Opt on[Pred cate[C]]] = {
    val pred catesEnabled = enabledPred cates(cand date, pred cates)
    val sequent alPred cate = new Sequent alPred cate(pred catesEnabled)

    sequent alPred cate.track(Seq(cand date)).map(_. ad)
  }

  protected def executeConcurrentPred cates[C <: PushCand date](
    cand date: C,
    pred cates: L st[Na dPred cate[C]]
  ): Future[L st[Pred cate[C]]] = {
    val pred catesEnabled = enabledPred cates(cand date, pred cates)
    val concurrentPred cate: ConcurrentPred cate[C] = new ConcurrentPred cate[C](pred catesEnabled)
    concurrentPred cate.track(Seq(cand date)).map(_. ad)
  }

  protected val cand datePred catesMap: Map[CommonRecom ndat onType, L st[
    Na dPred cate[_ <: PushCand date]
  ]]

  protected def getCRTPred cates[C <: PushCand date](
    CRT: CommonRecom ndat onType
  ): L st[Na dPred cate[C]] = {
    cand datePred catesMap.get(CRT) match {
      case So (pred cates) =>
        pred cates.as nstanceOf[L st[Na dPred cate[C]]]
      case _ =>
        throw new  llegalStateExcept on(
          s"Unknown CommonRecom ndat onType for Pred cates: ${CRT.na }")
    }
  }

  def val dateCand date[C <: PushCand date](cand date: C): Future[Opt on[Pred cate[C]]]
}
