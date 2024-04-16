package com.tw ter.cr_m xer.s ce_s gnal

 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derConstants
 mport com.tw ter.cr_m xer.s ce_s gnal.FrsStore.Query
 mport com.tw ter.cr_m xer.s ce_s gnal.FrsStore.FrsQueryResult
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Cl entContext
 mport com.tw ter.follow_recom ndat ons.thr ftscala.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Recom ndat on
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Recom ndat onRequest
 mport com.tw ter.storehaus.ReadableStore
 mport javax. nject.S ngleton
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.ut l.Future

@S ngleton
case class FrsStore(
  frsCl ent: FollowRecom ndat onsThr ftServ ce. thodPerEndpo nt,
  statsRece ver: StatsRece ver,
  dec der: CrM xerDec der)
    extends ReadableStore[Query, Seq[FrsQueryResult]] {

  overr de def get(
    query: Query
  ): Future[Opt on[Seq[FrsQueryResult]]] = {
     f (dec der. sAva lable(Dec derConstants.enableFRSTraff cDec derKey)) {
      val recom ndat onRequest =
        bu ldFollowRecom ndat onRequest(query)

      frsCl ent
        .getRecom ndat ons(recom ndat onRequest).map { recom ndat onResponse =>
          So (recom ndat onResponse.recom ndat ons.collect {
            case recom ndat on: Recom ndat on.User =>
              FrsQueryResult(
                recom ndat on.user.user d,
                recom ndat on.user.scor ngDeta ls
                  .flatMap(_.score).getOrElse(0.0),
                recom ndat on.user.scor ngDeta ls
                  .flatMap(_.cand dateS ceDeta ls.flatMap(_.pr maryS ce)),
                recom ndat on.user.scor ngDeta ls
                  .flatMap(_.cand dateS ceDeta ls.flatMap(_.cand dateS ceScores)).map(_.toMap)
              )
          })
        }
    } else {
      Future.None
    }
  }

  pr vate def bu ldFollowRecom ndat onRequest(
    query: Query
  ): Recom ndat onRequest = {
    Recom ndat onRequest(
      cl entContext = Cl entContext(
        user d = So (query.user d),
        countryCode = query.countryCodeOpt,
        languageCode = query.languageCodeOpt),
      d splayLocat on = query.d splayLocat on,
      maxResults = So (query.maxConsu rSeedsNum),
      excluded ds = So (query.excludedUser ds)
    )
  }
}

object FrsStore {
  case class Query(
    user d: User d,
    maxConsu rSeedsNum:  nt,
    d splayLocat on: D splayLocat on = D splayLocat on.ContentRecom nder,
    excludedUser ds: Seq[User d] = Seq.empty,
    languageCodeOpt: Opt on[Str ng] = None,
    countryCodeOpt: Opt on[Str ng] = None)

  case class FrsQueryResult(
    user d: User d,
    score: Double,
    pr maryS ce: Opt on[ nt],
    s ceW hScores: Opt on[Map[Str ng, Double]])
}
