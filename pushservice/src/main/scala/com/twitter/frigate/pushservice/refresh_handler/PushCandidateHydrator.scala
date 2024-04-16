package com.tw ter.fr gate.pushserv ce.refresh_handler

 mport com.tw ter.channels.common.thr ftscala.Ap L st
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.rec_types.RecTypes. s nNetworkT etType
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.TrendT etPushCand date
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.refresh_handler.cross.Cand dateCopyExpans on
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateHydrat onUt l._
 mport com.tw ter.fr gate.pushserv ce.ut l.MrUserStateUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Relat onsh pUt l
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

case class PushCand dateHydrator(
  soc alGraphServ ceProcessStore: ReadableStore[Relat onEdge, Boolean],
  safeUserStore: ReadableStore[Long, User],
  ap L stStore: ReadableStore[Long, Ap L st],
  cand dateCopyCross: Cand dateCopyExpans on
)(
   mpl c  statsRece ver: StatsRece ver,
   mpl c  val   ghtedOpenOrNtabCl ckModelScorer: PushMLModelScorer) {

  lazy val cand dateW hCopyNumStat = statsRece ver.stat("cand date_w h_copy_num")
  lazy val hydratedCand dateStat = statsRece ver.scope("hydrated_cand dates")
  lazy val mrUserStateStat = statsRece ver.scope("mr_user_state")

  lazy val queryStep = statsRece ver.scope("query_step")
  lazy val relat onEdgeW houtDupl cate nQueryStep =
    queryStep.counter("number_of_relat onEdge_w hout_dupl cate_ n_query_step")
  lazy val relat onEdgeW houtDupl cate nQueryStepD str but on =
    queryStep.stat("number_of_relat onEdge_w hout_dupl cate_ n_query_step_d str but on")

  case class Ent  es(
    users: Set[Long] = Set.empty[Long],
    relat onsh pEdges: Set[Relat onEdge] = Set.empty[Relat onEdge]) {
    def  rge(ot rEnt  es: Ent  es): Ent  es = {
      t .copy(
        users = t .users ++ ot rEnt  es.users,
        relat onsh pEdges =
          t .relat onsh pEdges ++ ot rEnt  es.relat onsh pEdges
      )
    }
  }

  case class Ent  esMap(
    userMap: Map[Long, User] = Map.empty[Long, User],
    relat onsh pMap: Map[Relat onEdge, Boolean] = Map.empty[Relat onEdge, Boolean])

  pr vate def updateCand dateAndCrtStats(
    cand date: RawCand date,
    cand dateType: Str ng,
    numEnt  es:  nt = 1
  ): Un  = {
    statsRece ver
      .scope(cand dateType).scope(cand date.commonRecType.na ).stat(
        "totalEnt  esPerCand dateTypePerCrt").add(numEnt  es)
    statsRece ver.scope(cand dateType).stat("totalEnt  esPerCand dateType").add(numEnt  es)
  }

  pr vate def collectEnt  es(
    cand dateDeta lsSeq: Seq[Cand dateDeta ls[RawCand date]]
  ): Ent  es = {
    cand dateDeta lsSeq
      .map { cand dateDeta ls =>
        val pushCand date = cand dateDeta ls.cand date

        val userEnt  es = pushCand date match {
          case t etW hSoc alContext: RawCand date w h T etW hSoc alContextTra s =>
            val author dOpt = getAuthor dFromT etCand date(t etW hSoc alContext)
            val scUser ds = t etW hSoc alContext.soc alContextUser ds.toSet
            updateCand dateAndCrtStats(pushCand date, "t etW hSoc alContext", scUser ds.s ze + 1)
            Ent  es(users = scUser ds ++ author dOpt.toSet)

          case _ => Ent  es()
        }

        val relat onEnt  es = {
           f ( s nNetworkT etType(pushCand date.commonRecType)) {
            Ent  es(
              relat onsh pEdges =
                Relat onsh pUt l.getPreCand dateRelat onsh psFor nNetworkT ets(pushCand date).toSet
            )
          } else Ent  es()
        }

        userEnt  es. rge(relat onEnt  es)
      }
      .foldLeft(Ent  es()) { (e1, e2) => e1. rge(e2) }

  }

  /**
   * T   thod calls G zmoduck and Soc al Graph Serv ce, keep t  results  n Ent  esMap
   * and passed onto t  update cand date phase  n t  hydrat on step
   *
   * @param ent  es conta ns all user ds and relat onEdges for all cand dates
   * @return Ent  esMap conta ns userMap and relat onsh pMap
   */
  pr vate def queryEnt  es(ent  es: Ent  es): Future[Ent  esMap] = {

    relat onEdgeW houtDupl cate nQueryStep. ncr(ent  es.relat onsh pEdges.s ze)
    relat onEdgeW houtDupl cate nQueryStepD str but on.add(ent  es.relat onsh pEdges.s ze)

    val relat onsh pMapFuture = Future
      .collect(soc alGraphServ ceProcessStore.mult Get(ent  es.relat onsh pEdges))
      .map { resultMap =>
        resultMap.collect {
          case (relat onsh pEdge, So (res)) => relat onsh pEdge -> res
          case (relat onsh pEdge, None) => relat onsh pEdge -> false
        }
      }

    val userMapFuture = Future
      .collect(safeUserStore.mult Get(ent  es.users))
      .map { userMap =>
        userMap.collect {
          case (user d, So (user)) =>
            user d -> user
        }
      }

    Future.jo n(userMapFuture, relat onsh pMapFuture).map {
      case (uMap, rMap) => Ent  esMap(userMap = uMap, relat onsh pMap = rMap)
    }
  }

  /**
   * @param cand dateDeta ls: recom ndat on cand dates for a user
   * @return sequence of cand dates tagged w h push and ntab copy  d
   */
  pr vate def expandCand datesW hCopy(
    cand dateDeta ls: Seq[Cand dateDeta ls[RawCand date]]
  ): Future[Seq[(Cand dateDeta ls[RawCand date], Copy ds)]] = {
    cand dateCopyCross.expandCand datesW hCopy d(cand dateDeta ls)
  }

  def updateCand dates(
    cand dateDeta lsW hCop es: Seq[(Cand dateDeta ls[RawCand date], Copy ds)],
    ent  esMaps: Ent  esMap
  ): Seq[Cand dateDeta ls[PushCand date]] = {
    cand dateDeta lsW hCop es.map {
      case (cand dateDeta l, copy ds) =>
        val pushCand date = cand dateDeta l.cand date
        val userMap = ent  esMaps.userMap
        val relat onsh pMap = ent  esMaps.relat onsh pMap

        val hydratedCand date = pushCand date match {

          case f1T etCand date: F1F rstDegree =>
            getHydratedCand dateForF1F rstDegreeT et(
              f1T etCand date,
              userMap,
              relat onsh pMap,
              copy ds)

          case t etRet et: T etRet etCand date =>
            getHydratedCand dateForT etRet et(t etRet et, userMap, copy ds)

          case t etFavor e: T etFavor eCand date =>
            getHydratedCand dateForT etFavor e(t etFavor e, userMap, copy ds)

          case tr pT etCand date: OutOfNetworkT etCand date w h Tr pCand date =>
            getHydratedCand dateForTr pT etCand date(tr pT etCand date, userMap, copy ds)

          case outOfNetworkT etCand date: OutOfNetworkT etCand date w h Top cCand date =>
            getHydratedCand dateForOutOfNetworkT etCand date(
              outOfNetworkT etCand date,
              userMap,
              copy ds)

          case top cProofT etCand date: Top cProofT etCand date =>
            getHydratedTop cProofT etCand date(top cProofT etCand date, userMap, copy ds)

          case subscr bedSearchT etCand date: Subscr bedSearchT etCand date =>
            getHydratedSubscr bedSearchT etCand date(
              subscr bedSearchT etCand date,
              userMap,
              copy ds)

          case l stRecom ndat on: L stPushCand date =>
            getHydratedL stCand date(ap L stStore, l stRecom ndat on, copy ds)

          case d scoverTw terCand date: D scoverTw terCand date =>
            getHydratedCand dateForD scoverTw terCand date(d scoverTw terCand date, copy ds)

          case topT et mpress onsCand date: TopT et mpress onsCand date =>
            getHydratedCand dateForTopT et mpress onsCand date(
              topT et mpress onsCand date,
              copy ds)

          case trendT etCand date: TrendT etCand date =>
            new TrendT etPushCand date(
              trendT etCand date,
              trendT etCand date.author d.flatMap(userMap.get),
              copy ds)

          case unknownCand date =>
            throw new  llegalArgu ntExcept on(
              s" ncorrect cand date for hydrat on: ${unknownCand date.commonRecType}")
        }

        Cand dateDeta ls(
          hydratedCand date,
          s ce = cand dateDeta l.s ce
        )
    }
  }

  def apply(
    cand dateDeta ls: Seq[Cand dateDeta ls[RawCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
    val  sLoggedOutRequest =
      cand dateDeta ls. adOpt on.ex sts(_.cand date.target. sLoggedOutUser)
     f (! sLoggedOutRequest) {
      cand dateDeta ls. adOpt on.map { cd =>
        MrUserStateUt l.updateMrUserStateStats(cd.cand date.target)(mrUserStateStat)
      }
    }

    expandCand datesW hCopy(cand dateDeta ls).flatMap { cand dateDeta lsW hCopy =>
      cand dateW hCopyNumStat.add(cand dateDeta lsW hCopy.s ze)
      val ent  es = collectEnt  es(cand dateDeta lsW hCopy.map(_._1))
      queryEnt  es(ent  es).flatMap { ent  esMap =>
        val updatedCand dates = updateCand dates(cand dateDeta lsW hCopy, ent  esMap)
        updatedCand dates.foreach { cand =>
          hydratedCand dateStat.counter(cand.cand date.commonRecType.na ). ncr()
        }
        Future.value(updatedCand dates)
      }
    }
  }
}
