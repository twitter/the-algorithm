package com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cl ents.geoduck.UserLocat onFetc r
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.user_state.UserStateCl ent
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls.rescueOpt onalW hStats
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls.rescueW hStats
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls.rescueW hStatsW h n
 mport com.tw ter.follow_recom ndat ons.products.common.ProductRequest
 mport com.tw ter.st ch.St ch

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ContentRecom nderRequestBu lder @ nject() (
  soc alGraph: Soc alGraphCl ent,
  userLocat onFetc r: UserLocat onFetc r,
  userStateCl ent: UserStateCl ent,
  statsRece ver: StatsRece ver) {

  val stats: StatsRece ver = statsRece ver.scope("content_recom nder_request_bu lder")
  val  nval dRelat onsh pUsersStats: StatsRece ver = stats.scope(" nval dRelat onsh pUser ds")
  pr vate val  nval dRelat onsh pUsersMaxS zeCounter =
     nval dRelat onsh pUsersStats.counter("maxS ze")
  pr vate val  nval dRelat onsh pUsersNotMaxS zeCounter =
     nval dRelat onsh pUsersStats.counter("notMaxS ze")

  def bu ld(req: ProductRequest): St ch[ContentRecom nderRequest] = {
    val userStateSt ch = St ch
      .collect(req.recom ndat onRequest.cl entContext.user d.map(user d =>
        userStateCl ent.getUserState(user d))).map(_.flatten)
    val recentFollo dUser dsSt ch =
      St ch
        .collect(req.recom ndat onRequest.cl entContext.user d.map { user d =>
          rescueW hStatsW h n(
            soc alGraph.getRecentFollo dUser ds(user d),
            stats,
            "recentFollo dUser ds",
            req
              .params(
                ContentRecom nderParams.RecentFollow ngPred cateBudget nM ll second).m ll second
          )
        })
    val recentFollo dByUser dsSt ch =
       f (req.params(ContentRecom nderParams.GetFollo rsFromSgs)) {
        St ch
          .collect(
            req.recom ndat onRequest.cl entContext.user d.map(user d =>
              rescueW hStatsW h n(
                soc alGraph.getRecentFollo dByUser dsFromCac dColumn(user d),
                stats,
                "recentFollo dByUser ds",
                req
                  .params(ContentRecom nderParams.RecentFollow ngPred cateBudget nM ll second)
                  .m ll second
              )))
      } else St ch.None
    val  nval dRelat onsh pUser dsSt ch: St ch[Opt on[Seq[Long]]] =
       f (req.params(ContentRecom nderParams.Enable nval dRelat onsh pPred cate)) {
        St ch
          .collect(
            req.recom ndat onRequest.cl entContext.user d.map { user d =>
              rescueW hStats(
                soc alGraph
                  .get nval dRelat onsh pUser dsFromCac dColumn(user d)
                  .onSuccess( ds =>
                     f ( ds.s ze >= Soc alGraphCl ent.MaxNum nval dRelat onsh p) {
                       nval dRelat onsh pUsersMaxS zeCounter. ncr()
                    } else {
                       nval dRelat onsh pUsersNotMaxS zeCounter. ncr()
                    }),
                stats,
                " nval dRelat onsh pUser ds"
              )
            }
          )
      } else {
        St ch.None
      }
    val locat onSt ch =
      rescueOpt onalW hStats(
        userLocat onFetc r.getGeohashAndCountryCode(
          req.recom ndat onRequest.cl entContext.user d,
          req.recom ndat onRequest.cl entContext. pAddress
        ),
        stats,
        "userLocat on"
      )
    St ch
      .jo n(
        recentFollo dUser dsSt ch,
        recentFollo dByUser dsSt ch,
         nval dRelat onsh pUser dsSt ch,
        locat onSt ch,
        userStateSt ch)
      .map {
        case (
              recentFollo dUser ds,
              recentFollo dByUser ds,
               nval dRelat onsh pUser ds,
              locat on,
              userState) =>
          ContentRecom nderRequest(
            req.params,
            req.recom ndat onRequest.cl entContext,
            req.recom ndat onRequest.excluded ds.getOrElse(N l),
            recentFollo dUser ds,
            recentFollo dByUser ds,
             nval dRelat onsh pUser ds.map(_.toSet),
            req.recom ndat onRequest.d splayLocat on,
            req.recom ndat onRequest.maxResults,
            req.recom ndat onRequest.debugParams.flatMap(_.debugOpt ons),
            locat on,
            userState
          )
      }
  }
}
