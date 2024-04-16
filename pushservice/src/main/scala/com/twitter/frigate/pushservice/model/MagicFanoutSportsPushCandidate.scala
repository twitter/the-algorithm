package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.esc rb rd. tadata.thr ftscala.Ent y gadata
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.BaseGa Score
 mport com.tw ter.fr gate.common.base.Mag cFanoutSportsEventCand date
 mport com.tw ter.fr gate.common.base.Mag cFanoutSportsScore nformat on
 mport com.tw ter.fr gate.common.base.Team nfo
 mport com.tw ter.fr gate.common.store. nterests. nterestsLookupRequestW hContext
 mport com.tw ter.fr gate.mag c_events.thr ftscala.FanoutEvent
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.model. b s.Mag cFanoutSportsEvent b s2Hydrator
 mport com.tw ter.fr gate.pushserv ce.model.ntab.Mag cFanoutSportsEventNTabRequestHydrator
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.mag c_fanout.Mag cFanoutPred catesForCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.mag c_fanout.Mag cFanoutTarget ngPred cateWrappersForCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue.Mag cFanoutNtabCaretFat guePred cate
 mport com.tw ter.fr gate.pushserv ce.store.EventRequest
 mport com.tw ter.fr gate.pushserv ce.store.UttEnt yHydrat onStore
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.Bas cSendHandlerPred cates
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .store.semant c_core.Semant cEnt yForQuery
 mport com.tw ter. nterests.thr ftscala.User nterests
 mport com.tw ter.l vev deo.t  l ne.doma n.v2.Event
 mport com.tw ter.l vev deo.t  l ne.doma n.v2.Hydrat onOpt ons
 mport com.tw ter.l vev deo.t  l ne.doma n.v2.LookupContext
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusters nferredEnt  es
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

class Mag cFanoutSportsPushCand date(
  cand date: RawCand date
    w h Mag cFanoutSportsEventCand date
    w h Mag cFanoutSportsScore nformat on,
  copy ds: Copy ds,
  overr de val fanoutEvent: Opt on[FanoutEvent],
  overr de val semant cEnt yResults: Map[Semant cEnt yForQuery, Opt on[Ent y gadata]],
  s mClusterToEnt  es: Map[ nt, Opt on[S mClusters nferredEnt  es]],
  lexServ ceStore: ReadableStore[EventRequest, Event],
   nterestsLookupStore: ReadableStore[ nterestsLookupRequestW hContext, User nterests],
  uttEnt yHydrat onStore: UttEnt yHydrat onStore
)(
   mpl c  statsScoped: StatsRece ver,
  pushModelScorer: PushMLModelScorer)
    extends Mag cFanoutEventPushCand date(
      cand date,
      copy ds,
      fanoutEvent,
      semant cEnt yResults,
      s mClusterToEnt  es,
      lexServ ceStore,
       nterestsLookupStore,
      uttEnt yHydrat onStore)(statsScoped, pushModelScorer)
    w h Mag cFanoutSportsEventCand date
    w h Mag cFanoutSportsScore nformat on
    w h Mag cFanoutSportsEventNTabRequestHydrator
    w h Mag cFanoutSportsEvent b s2Hydrator {

  overr de val  sScoreUpdate: Boolean = cand date. sScoreUpdate
  overr de val ga Scores: Future[Opt on[BaseGa Score]] = cand date.ga Scores
  overr de val ho Team nfo: Future[Opt on[Team nfo]] = cand date.ho Team nfo
  overr de val awayTeam nfo: Future[Opt on[Team nfo]] = cand date.awayTeam nfo

  overr de lazy val stats: StatsRece ver = statsScoped.scope("Mag cFanoutSportsPushCand date")
  overr de val statsRece ver: StatsRece ver = statsScoped.scope("Mag cFanoutSportsPushCand date")

  overr de lazy val eventRequestFut: Future[Opt on[EventRequest]] = {
    Future.jo n(target. nferredUserDev ceLanguage, target.accountCountryCode).map {
      case ( nferredUserDev ceLanguage, accountCountryCode) =>
        So (
          EventRequest(
            event d,
            lookupContext = LookupContext(
              hydrat onOpt ons = Hydrat onOpt ons(
                 ncludeSquare mage = true,
                 ncludePr mary mage = true
              ),
              language =  nferredUserDev ceLanguage,
              countryCode = accountCountryCode
            )
          ))
    }
  }
}

case class Mag cFanoutSportsEventCand datePred cates(conf g: Conf g)
    extends Bas cSendHandlerPred cates[Mag cFanoutSportsPushCand date] {

   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )

  overr de val preCand dateSpec f cPred cates: L st[
    Na dPred cate[Mag cFanoutSportsPushCand date]
  ] =
    L st(
      Pred catesForCand date.paramPred cate(PushFeatureSw chParams.EnableScoreFanoutNot f cat on)
    )

  overr de val postCand dateSpec f cPred cates: L st[
    Na dPred cate[Mag cFanoutSportsPushCand date]
  ] =
    L st(
      Pred catesForCand date. sDev ceEl g bleForNewsOrSports,
      Mag cFanoutPred catesForCand date. nferredUserDev ceLanguagePred cate,
      Mag cFanoutPred catesForCand date.h ghPr or yEventExceptedPred cate(
        Mag cFanoutTarget ngPred cateWrappersForCand date
          .mag cFanoutTarget ngPred cate(statsRece ver, conf g)
      )(conf g),
      Pred catesForCand date.secondaryDormantAccountPred cate(
        statsRece ver
      ),
      Mag cFanoutPred catesForCand date.h ghPr or yEventExceptedPred cate(
        Mag cFanoutNtabCaretFat guePred cate()
      )(conf g),
    )
}
