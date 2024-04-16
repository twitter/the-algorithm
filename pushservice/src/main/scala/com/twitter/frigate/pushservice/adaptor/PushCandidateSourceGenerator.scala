package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.content_m xer.thr ftscala.ContentM xerRequest
 mport com.tw ter.content_m xer.thr ftscala.ContentM xerResponse
 mport com.tw ter.explore_ranker.thr ftscala.ExploreRankerRequest
 mport com.tw ter.explore_ranker.thr ftscala.ExploreRankerResponse
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.cand date._
 mport com.tw ter.fr gate.common.store.RecentT etsQuery
 mport com.tw ter.fr gate.common.store. nterests. nterestsLookupRequestW hContext
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.store._
 mport com.tw ter.geoduck.common.thr ftscala.Locat on
 mport com.tw ter.geoduck.serv ce.thr ftscala.Locat onResponse
 mport com.tw ter. rm .pop_geo.thr ftscala.PopT ets nPlace
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter. rm .store.t etyp e.UserT et
 mport com.tw ter. nterests.thr ftscala.User nterests
 mport com.tw ter. nterests_d scovery.thr ftscala.NonPersonal zedRecom ndedL sts
 mport com.tw ter. nterests_d scovery.thr ftscala.Recom ndedL stsRequest
 mport com.tw ter. nterests_d scovery.thr ftscala.Recom ndedL stsResponse
 mport com.tw ter.recom ndat on. nterests.d scovery.core.model. nterestDoma n
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pDoma n
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT ets
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofRequest
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofResponse

/**
 * PushCand dateS ceGenerator generates cand date s ce l st for a g ven Target user
 */
class PushCand dateS ceGenerator(
  earlyb rdCand dates: Cand dateS ce[Earlyb rdCand dateS ce.Query, Earlyb rdCand date],
  userT etEnt yGraphCand dates: Cand dateS ce[UserT etEnt yGraphCand dates.Target, Cand date],
  cac dT etyP eStoreV2: ReadableStore[Long, T etyP eResult],
  safeCac dT etyP eStoreV2: ReadableStore[Long, T etyP eResult],
  userT etT etyP eStore: ReadableStore[UserT et, T etyP eResult],
  safeUserT etT etyP eStore: ReadableStore[UserT et, T etyP eResult],
  cac dT etyP eStoreV2NoVF: ReadableStore[Long, T etyP eResult],
  edgeStore: ReadableStore[Relat onEdge, Boolean],
   nterestsLookupStore: ReadableStore[ nterestsLookupRequestW hContext, User nterests],
  uttEnt yHydrat onStore: UttEnt yHydrat onStore,
  geoDuckV2Store: ReadableStore[Long, Locat onResponse],
  topT etsByGeoStore: ReadableStore[ nterestDoma n[Str ng], Map[Str ng, L st[(Long, Double)]]],
  topT etsByGeoV2Vers onedStore: ReadableStore[Str ng, PopT ets nPlace],
  t et mpress onsStore: T et mpress onsStore,
  recom ndedTrendsCand dateS ce: Recom ndedTrendsCand dateS ce,
  recentT etsByAuthorStore: ReadableStore[RecentT etsQuery, Seq[Seq[Long]]],
  top cSoc alProofServ ceStore: ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse],
  crM xerStore: CrM xerT etStore,
  contentM xerStore: ReadableStore[ContentM xerRequest, ContentM xerResponse],
  exploreRankerStore: ReadableStore[ExploreRankerRequest, ExploreRankerResponse],
  softUserLocat onStore: ReadableStore[Long, Locat on],
  tr pT etCand dateStore: ReadableStore[Tr pDoma n, Tr pT ets],
  l stRecsStore: ReadableStore[Str ng, NonPersonal zedRecom ndedL sts],
   dsStore: ReadableStore[Recom ndedL stsRequest, Recom ndedL stsResponse]
)(
   mpl c  val globalStats: StatsRece ver) {

  pr vate val earlyB rdF rstDegreeCand dateAdaptor = EarlyB rdF rstDegreeCand dateAdaptor(
    earlyb rdCand dates,
    cac dT etyP eStoreV2,
    cac dT etyP eStoreV2NoVF,
    userT etT etyP eStore,
    PushFeatureSw chParams.NumberOfMaxEarlyb rd nNetworkCand datesParam,
    globalStats
  )

  pr vate val frsT etCand dateAdaptor = FRST etCand dateAdaptor(
    crM xerStore,
    cac dT etyP eStoreV2,
    cac dT etyP eStoreV2NoVF,
    userT etT etyP eStore,
    uttEnt yHydrat onStore,
    top cSoc alProofServ ceStore,
    globalStats
  )

  pr vate val contentRecom nderM xerAdaptor = ContentRecom nderM xerAdaptor(
    crM xerStore,
    safeCac dT etyP eStoreV2,
    edgeStore,
    top cSoc alProofServ ceStore,
    uttEnt yHydrat onStore,
    globalStats
  )

  pr vate val tr pGeoCand datesAdaptor = Tr pGeoCand datesAdaptor(
    tr pT etCand dateStore,
    contentM xerStore,
    safeCac dT etyP eStoreV2,
    cac dT etyP eStoreV2NoVF,
    globalStats
  )

  val s ces: Seq[
    Cand dateS ce[Target, RawCand date] w h Cand dateS ceEl g ble[
      Target,
      RawCand date
    ]
  ] = {
    Seq(
      earlyB rdF rstDegreeCand dateAdaptor,
      Gener cCand dateAdaptor(
        userT etEnt yGraphCand dates,
        cac dT etyP eStoreV2,
        cac dT etyP eStoreV2NoVF,
        globalStats.scope("UserT etEnt yGraphCand dates")
      ),
      new Onboard ngPushCand dateAdaptor(globalStats),
      TopT etsByGeoAdaptor(
        geoDuckV2Store,
        softUserLocat onStore,
        topT etsByGeoStore,
        topT etsByGeoV2Vers onedStore,
        cac dT etyP eStoreV2,
        cac dT etyP eStoreV2NoVF,
        globalStats
      ),
      frsT etCand dateAdaptor,
      TopT et mpress onsCand dateAdaptor(
        recentT etsByAuthorStore,
        cac dT etyP eStoreV2,
        cac dT etyP eStoreV2NoVF,
        t et mpress onsStore,
        globalStats
      ),
      TrendsCand datesAdaptor(
        softUserLocat onStore,
        recom ndedTrendsCand dateS ce,
        safeCac dT etyP eStoreV2,
        cac dT etyP eStoreV2NoVF,
        safeUserT etT etyP eStore,
        globalStats
      ),
      contentRecom nderM xerAdaptor,
      tr pGeoCand datesAdaptor,
      H ghQual yT etsAdaptor(
        tr pT etCand dateStore,
         nterestsLookupStore,
        cac dT etyP eStoreV2,
        cac dT etyP eStoreV2NoVF,
        globalStats
      ),
      ExploreV deoT etCand dateAdaptor(
        exploreRankerStore,
        cac dT etyP eStoreV2,
        globalStats
      ),
      L stsToRecom ndCand dateAdaptor(
        l stRecsStore,
        geoDuckV2Store,
         dsStore,
        globalStats
      )
    )
  }
}
