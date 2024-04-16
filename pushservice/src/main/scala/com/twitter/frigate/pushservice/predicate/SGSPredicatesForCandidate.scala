package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Soc alGraphServ ceRelat onsh pMap
 mport com.tw ter.fr gate.common.base.T etAuthor
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter. rm .pred cate.soc algraph.Edge
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.ut l.Future

/**
 * Refactor SGS pred cates so that pred cates can use relat onsh pMap   generate  n hydrate step
 */
object SGSPred catesForCand date {

  case class Relat onsh pMapEdge(edge: Edge, relat onsh pMap: Map[Relat onEdge, Boolean])

  pr vate def relat onsh pMapEdgeFromCand date(
    cand date: PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap
  ): Opt on[Relat onsh pMapEdge] = {
    cand date.author d map { author d =>
      Relat onsh pMapEdge(Edge(cand date.target.target d, author d), cand date.relat onsh pMap)
    }
  }

  def authorBe ngFollo d(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap] = {
    val na  = "author_not_be ng_follo d"
    val stats = statsRece ver.scope(na )
    val softUserCounter = stats.counter("soft_user")

    val sgsAuthorBe ngFollo dPred cate = Pred cate
      .from { relat onsh pMapEdge: Relat onsh pMapEdge =>
        anyRelat onEx st(relat onsh pMapEdge, Set(Relat onsh pType.Follow ng))
      }

    Pred cate
      .fromAsync {
        cand date: PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap =>
          val target = cand date.target
          target.targetUser.flatMap {
            case So (g zmoduckUser)  f g zmoduckUser.userType == UserType.Soft =>
              softUserCounter. ncr()
              target.seedsW h  ght.map { follo dUsersW h  ghtOpt =>
                cand date.author d match {
                  case So (author d) =>
                    val follo dUsers = follo dUsersW h  ghtOpt.getOrElse(Map.empty).keys
                    follo dUsers.toSet.conta ns(author d)

                  case None => false
                }
              }

            case _ =>
              sgsAuthorBe ngFollo dPred cate
                .opt onalOn(relat onsh pMapEdgeFromCand date, m ss ngResult = false)
                .apply(Seq(cand date))
                .map(_. ad)
          }
      }.w hStats(stats)
      .w hNa (na )
  }

  def authorNotBe ngDev ceFollo d(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap] = {
    val na  = "author_be ng_dev ce_follo d"
    Pred cate
      .from { relat onsh pMapEdge: Relat onsh pMapEdge =>
        {
          anyRelat onEx st(relat onsh pMapEdge, Set(Relat onsh pType.Dev ceFollow ng))
        }
      }
      .opt onalOn(relat onsh pMapEdgeFromCand date, m ss ngResult = false)
      .fl p
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def recom ndedT etAuthorAcceptableToTargetUser(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap] = {
    val na  = "recom nded_t et_author_not_acceptable_to_target_user"
    Pred cate
      .from { relat onsh pMapEdge: Relat onsh pMapEdge =>
        {
          anyRelat onEx st(
            relat onsh pMapEdge,
            Set(
              Relat onsh pType.Block ng,
              Relat onsh pType.BlockedBy,
              Relat onsh pType.H deRecom ndat ons,
              Relat onsh pType.Mut ng
            ))
        }
      }
      .fl p
      .opt onalOn(relat onsh pMapEdgeFromCand date, m ss ngResult = false)
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def authorNotBe ngFollo d(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap] = {
    Pred cate
      .from { relat onsh pMapEdge: Relat onsh pMapEdge =>
        {
          anyRelat onEx st(relat onsh pMapEdge, Set(Relat onsh pType.Follow ng))
        }
      }
      .opt onalOn(relat onsh pMapEdgeFromCand date, m ss ngResult = false)
      .fl p
      .w hStats(statsRece ver.scope("pred cate_author_not_be ng_follo d_pre_rank ng"))
      .w hNa ("author_not_be ng_follo d")
  }

  def d sable nNetworkT etPred cate(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap] = {
    val na  = "enable_ n_network_t et"
    Pred cate
      .fromAsync {
        cand date: PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap =>
           f (cand date.target.params(PushParams.D sable nNetworkT etCand datesParam)) {
            authorNotBe ngFollo d
              .apply(Seq(cand date))
              .map(_. ad)
          } else Future.True
      }.w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def d sableOutNetworkT etPred cate(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap] = {
    val na  = "enable_out_network_t et"
    Pred cate
      .fromAsync {
        cand date: PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap =>
           f (cand date.target.params(PushFeatureSw chParams.D sableOutNetworkT etCand datesFS)) {
            authorBe ngFollo d
              .apply(Seq(cand date))
              .map(_. ad)
          } else Future.True
      }.w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  /**
   * Returns true  f t  prov ded relat onsh pEdge ex sts among
   * @param cand date cand date
   * @param relat onsh ps rela onsh ps
   * @return Boolean result
   */
  pr vate def anyRelat onEx st(
    relat onsh pMapEdge: Relat onsh pMapEdge,
    relat onsh ps: Set[Relat onsh pType]
  ): Boolean = {
    val resultSeq = relat onsh ps.map { relat onsh p =>
      relat onsh pMapEdge.relat onsh pMap.getOrElse(
        Relat onEdge(relat onsh pMapEdge.edge, relat onsh p),
        false)
    }.toSeq
    resultSeq.conta ns(true)
  }
}
