package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.fr gate.thr ftscala.Not f cat onD splayLocat on

object Cand date2Fr gateNot f cat on {

  def getFr gateNot f cat on(
    cand date: PushCand date
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Fr gateNot f cat on = {
    cand date match {

      case top cT etCand date: PushCand date w h BaseTop cT etCand date =>
        PushAdaptorUt l.getFr gateNot f cat onForT et(
          crt = top cT etCand date.commonRecType,
          t et d = top cT etCand date.t et d,
          scAct ons = N l,
          author dOpt = top cT etCand date.author d,
          pushCopy d = top cT etCand date.pushCopy d,
          ntabCopy d = top cT etCand date.ntabCopy d,
          s mcluster d = None,
          semant cCoreEnt y ds = top cT etCand date.semant cCoreEnt y d.map(L st(_)),
          cand dateContent = top cT etCand date.content,
          trend d = None
        )

      case trendT etCand date: PushCand date w h TrendT etCand date =>
        PushAdaptorUt l.getFr gateNot f cat onForT et(
          trendT etCand date.commonRecType,
          trendT etCand date.t et d,
          N l,
          trendT etCand date.author d,
          trendT etCand date.pushCopy d,
          trendT etCand date.ntabCopy d,
          None,
          None,
          trendT etCand date.content,
          So (trendT etCand date.trend d)
        )

      case tr pT etCand date: PushCand date w h OutOfNetworkT etCand date w h Tr pCand date =>
        PushAdaptorUt l.getFr gateNot f cat onForT et(
          crt = tr pT etCand date.commonRecType,
          t et d = tr pT etCand date.t et d,
          scAct ons = N l,
          author dOpt = tr pT etCand date.author d,
          pushCopy d = tr pT etCand date.pushCopy d,
          ntabCopy d = tr pT etCand date.ntabCopy d,
          s mcluster d = None,
          semant cCoreEnt y ds = None,
          cand dateContent = tr pT etCand date.content,
          trend d = None,
          t etTr pDoma n = tr pT etCand date.tr pDoma n
        )

      case outOfNetworkT etCand date: PushCand date w h OutOfNetworkT etCand date =>
        PushAdaptorUt l.getFr gateNot f cat onForT et(
          crt = outOfNetworkT etCand date.commonRecType,
          t et d = outOfNetworkT etCand date.t et d,
          scAct ons = N l,
          author dOpt = outOfNetworkT etCand date.author d,
          pushCopy d = outOfNetworkT etCand date.pushCopy d,
          ntabCopy d = outOfNetworkT etCand date.ntabCopy d,
          s mcluster d = None,
          semant cCoreEnt y ds = None,
          cand dateContent = outOfNetworkT etCand date.content,
          trend d = None
        )

      case userCand date: PushCand date w h UserCand date w h Soc alContextAct ons =>
        PushAdaptorUt l.getFr gateNot f cat onForUser(
          userCand date.commonRecType,
          userCand date.user d,
          userCand date.soc alContextAct ons,
          userCand date.pushCopy d,
          userCand date.ntabCopy d
        )

      case userCand date: PushCand date w h UserCand date =>
        PushAdaptorUt l.getFr gateNot f cat onForUser(
          userCand date.commonRecType,
          userCand date.user d,
          N l,
          userCand date.pushCopy d,
          userCand date.ntabCopy d
        )

      case t etCand date: PushCand date w h T etCand date w h T etDeta ls w h Soc alContextAct ons =>
        PushAdaptorUt l.getFr gateNot f cat onForT etW hSoc alContextAct ons(
          t etCand date.commonRecType,
          t etCand date.t et d,
          t etCand date.soc alContextAct ons,
          t etCand date.author d,
          t etCand date.pushCopy d,
          t etCand date.ntabCopy d,
          cand dateContent = t etCand date.content,
          semant cCoreEnt y ds = None,
          trend d = None
        )
      case pushCand date: PushCand date =>
        Fr gateNot f cat on(
          commonRecom ndat onType = pushCand date.commonRecType,
          not f cat onD splayLocat on = Not f cat onD splayLocat on.PushToMob leDev ce,
          pushCopy d = pushCand date.pushCopy d,
          ntabCopy d = pushCand date.ntabCopy d
        )

      case _ =>
        statsRece ver
          .scope(s"${cand date.commonRecType}").counter("fr gate_not f cat on_error"). ncr()
        throw new  llegalStateExcept on(" ncorrect cand date type w n create Fr gateNot f cat on")
    }
  }
}
