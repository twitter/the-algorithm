package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cl ents.d sm ss_store.D sm ssStore
 mport com.tw ter.follow_recom ndat ons.common.cl ents.geoduck.UserLocat onFetc r
 mport com.tw ter.follow_recom ndat ons.common.cl ents. mpress on_store.Wtf mpress onStore
 mport com.tw ter.follow_recom ndat ons.common.cl ents. nterests_serv ce. nterestServ ceCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.user_state.UserStateCl ent
 mport com.tw ter.follow_recom ndat ons.common.pred cates.d sm ss.D sm ssedCand datePred cateParams
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls._
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlRequestBu lderParams.D sm ssed dScanBudget
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlRequestBu lderParams.Top c dFetchBudget
 mport com.tw ter.follow_recom ndat ons.flows.post_nux_ml.PostNuxMlRequestBu lderParams.WTF mpress onsScanBudget
 mport com.tw ter.follow_recom ndat ons.products.common.ProductRequest
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PostNuxMlRequestBu lder @ nject() (
  soc alGraph: Soc alGraphCl ent,
  wtf mpress onStore: Wtf mpress onStore,
  d sm ssStore: D sm ssStore,
  userLocat onFetc r: UserLocat onFetc r,
   nterestServ ceCl ent:  nterestServ ceCl ent,
  userStateCl ent: UserStateCl ent,
  statsRece ver: StatsRece ver)
    extends Logg ng {

  val stats: StatsRece ver = statsRece ver.scope("post_nux_ml_request_bu lder")
  val  nval dRelat onsh pUsersStats: StatsRece ver = stats.scope(" nval dRelat onsh pUser ds")
  pr vate val  nval dRelat onsh pUsersMaxS zeCounter =
     nval dRelat onsh pUsersStats.counter("maxS ze")
  pr vate val  nval dRelat onsh pUsersNotMaxS zeCounter =
     nval dRelat onsh pUsersStats.counter("notMaxS ze")

  def bu ld(
    req: ProductRequest,
    prev ouslyRecom ndedUser ds: Opt on[Set[Long]] = None,
    prev ouslyFollo dUser ds: Opt on[Set[Long]] = None
  ): St ch[PostNuxMlRequest] = {
    val dl = req.recom ndat onRequest.d splayLocat on
    val resultsSt ch = St ch.collect(
      req.recom ndat onRequest.cl entContext.user d
        .map { user d =>
          val lookBackDurat on = req.params(D sm ssedCand datePred cateParams.LookBackDurat on)
          val negat veStartTs = -(T  .now - lookBackDurat on). nM ll s
          val recentFollo dUser dsSt ch =
            rescueW hStats(
              soc alGraph.getRecentFollo dUser ds(user d),
              stats,
              "recentFollo dUser ds")
          val  nval dRelat onsh pUser dsSt ch =
             f (req.params(PostNuxMlParams.Enable nval dRelat onsh pPred cate)) {
              rescueW hStats(
                soc alGraph
                  .get nval dRelat onsh pUser ds(user d)
                  .onSuccess( ds =>
                     f ( ds.s ze >= Soc alGraphCl ent.MaxNum nval dRelat onsh p) {
                       nval dRelat onsh pUsersMaxS zeCounter. ncr()
                    } else {
                       nval dRelat onsh pUsersNotMaxS zeCounter. ncr()
                    }),
                stats,
                " nval dRelat onsh pUser ds"
              )
            } else {
              St ch.value(Seq.empty)
            }
          // recentFollo dByUser ds are only used  n exper  nt cand date s ces
          val recentFollo dByUser dsSt ch =  f (req.params(PostNuxMlParams.GetFollo rsFromSgs)) {
            rescueW hStats(
              soc alGraph.getRecentFollo dByUser dsFromCac dColumn(user d),
              stats,
              "recentFollo dByUser ds")
          } else St ch.value(Seq.empty)
          val wtf mpress onsSt ch =
            rescueW hStatsW h n(
              wtf mpress onStore.get(user d, dl),
              stats,
              "wtf mpress ons",
              req.params(WTF mpress onsScanBudget))
          val d sm ssedUser dsSt ch =
            rescueW hStatsW h n(
              d sm ssStore.get(user d, negat veStartTs, None),
              stats,
              "d sm ssedUser ds",
              req.params(D sm ssed dScanBudget))
          val locat onSt ch =
            rescueOpt onalW hStats(
              userLocat onFetc r.getGeohashAndCountryCode(
                So (user d),
                req.recom ndat onRequest.cl entContext. pAddress),
              stats,
              "userLocat on"
            )
          val top c dsSt ch =
            rescueW hStatsW h n(
               nterestServ ceCl ent.fetchUtt nterest ds(user d),
              stats,
              "top c ds",
              req.params(Top c dFetchBudget))
          val userStateSt ch =
            rescueOpt onalW hStats(userStateCl ent.getUserState(user d), stats, "userState")
          St ch.jo n(
            recentFollo dUser dsSt ch,
             nval dRelat onsh pUser dsSt ch,
            recentFollo dByUser dsSt ch,
            d sm ssedUser dsSt ch,
            wtf mpress onsSt ch,
            locat onSt ch,
            top c dsSt ch,
            userStateSt ch
          )
        })

    resultsSt ch.map {
      case So (
            (
              recentFollo dUser ds,
               nval dRelat onsh pUser ds,
              recentFollo dByUser ds,
              d sm ssedUser ds,
              wtf mpress ons,
              locat on nfo,
              top c ds,
              userState)) =>
        PostNuxMlRequest(
          params = req.params,
          cl entContext = req.recom ndat onRequest.cl entContext,
          s m larToUser ds = N l,
           nputExcludeUser ds = req.recom ndat onRequest.excluded ds.getOrElse(N l),
          recentFollo dUser ds = So (recentFollo dUser ds),
           nval dRelat onsh pUser ds = So ( nval dRelat onsh pUser ds.toSet),
          recentFollo dByUser ds = So (recentFollo dByUser ds),
          d sm ssedUser ds = So (d sm ssedUser ds),
          d splayLocat on = dl,
          maxResults = req.recom ndat onRequest.maxResults,
          debugOpt ons = req.recom ndat onRequest.debugParams.flatMap(_.debugOpt ons),
          wtf mpress ons = So (wtf mpress ons),
          geohashAndCountryCode = locat on nfo,
          utt nterest ds = So (top c ds),
           nputPrev ouslyRecom ndedUser ds = prev ouslyRecom ndedUser ds,
           nputPrev ouslyFollo dUser ds = prev ouslyFollo dUser ds,
           sSoftUser = req.recom ndat onRequest. sSoftUser,
          userState = userState
        )
      case _ =>
        PostNuxMlRequest(
          params = req.params,
          cl entContext = req.recom ndat onRequest.cl entContext,
          s m larToUser ds = N l,
           nputExcludeUser ds = req.recom ndat onRequest.excluded ds.getOrElse(N l),
          recentFollo dUser ds = None,
           nval dRelat onsh pUser ds = None,
          recentFollo dByUser ds = None,
          d sm ssedUser ds = None,
          d splayLocat on = dl,
          maxResults = req.recom ndat onRequest.maxResults,
          debugOpt ons = req.recom ndat onRequest.debugParams.flatMap(_.debugOpt ons),
          wtf mpress ons = None,
          geohashAndCountryCode = None,
           nputPrev ouslyRecom ndedUser ds = prev ouslyRecom ndedUser ds,
           nputPrev ouslyFollo dUser ds = prev ouslyFollo dUser ds,
           sSoftUser = req.recom ndat onRequest. sSoftUser,
          userState = None
        )
    }
  }
}
