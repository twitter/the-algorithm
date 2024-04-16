package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.model.L stRecom ndat onPushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter. rm .pred cate.soc algraph.Edge
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter. rm .pred cate.soc algraph.Soc alGraphPred cate
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

object L stPred cates {

  def l stNa Ex stsPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[L stRecom ndat onPushCand date] = {
    Pred cate
      .fromAsync { cand date: L stRecom ndat onPushCand date =>
        cand date.l stNa .map(_. sDef ned)
      }
      .w hStats(stats)
      .w hNa ("l st_na _ex sts")
  }

  def l stAuthorEx stsPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[L stRecom ndat onPushCand date] = {
    Pred cate
      .fromAsync { cand date: L stRecom ndat onPushCand date =>
        cand date.l stOwner d.map(_. sDef ned)
      }
      .w hStats(stats)
      .w hNa ("l st_owner_ex sts")
  }

  def l stAuthorAcceptableToTargetUser(
    edgeStore: ReadableStore[Relat onEdge, Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[L stRecom ndat onPushCand date] = {
    val na  = "l st_author_acceptable_to_target_user"
    val sgsPred cate = Soc alGraphPred cate
      .anyRelat onEx sts(
        edgeStore,
        Set(
          Relat onsh pType.Block ng,
          Relat onsh pType.BlockedBy,
          Relat onsh pType.Mut ng
        )
      )
      .w hStats(statsRece ver.scope("l st_sgs_any_relat on_ex sts"))
      .w hNa ("l st_sgs_any_relat on_ex sts")

    Pred cate
      .fromAsync { cand date: L stRecom ndat onPushCand date =>
        cand date.l stOwner d.flatMap {
          case So (owner d) =>
            sgsPred cate.apply(Seq(Edge(cand date.target.target d, owner d))).map(_. ad)
          case _ => Future.True
        }
      }
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  /**
   * C cks  f t  l st  s acceptable to Target user =>
   *    -  s Target not follow ng t  l st
   *    -  s Target not muted t  l st
   */
  def l stAcceptablePred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[L stRecom ndat onPushCand date] = {
    val na  = "l st_acceptable_to_target_user"
    Pred cate
      .fromAsync { cand date: L stRecom ndat onPushCand date =>
        cand date.ap L st.map {
          case So (ap L st) =>
            !(ap L st.follow ng.conta ns(true) || ap L st.mut ng.conta ns(true))
          case _ => false
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def l stSubscr berCountPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[L stRecom ndat onPushCand date] = {
    val na  = "l st_subscr be_count"
    Pred cate
      .fromAsync { cand date: L stRecom ndat onPushCand date =>
        cand date.ap L st.map { ap L stOpt =>
          ap L stOpt.ex sts { ap L st =>
            ap L st.subscr berCount >= cand date.target.params(
              PushFeatureSw chParams.L stRecom ndat onsSubscr berCount)
          }
        }
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }
}
