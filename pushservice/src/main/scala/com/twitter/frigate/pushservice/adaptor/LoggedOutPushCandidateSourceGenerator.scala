package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.geoduck.serv ce.thr ftscala.Locat onResponse
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pDoma n
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT ets
 mport com.tw ter.content_m xer.thr ftscala.ContentM xerRequest
 mport com.tw ter.content_m xer.thr ftscala.ContentM xerResponse
 mport com.tw ter.geoduck.common.thr ftscala.Locat on
 mport com.tw ter. rm .pop_geo.thr ftscala.PopT ets nPlace
 mport com.tw ter.recom ndat on. nterests.d scovery.core.model. nterestDoma n

class LoggedOutPushCand dateS ceGenerator(
  tr pT etCand dateStore: ReadableStore[Tr pDoma n, Tr pT ets],
  geoDuckV2Store: ReadableStore[Long, Locat onResponse],
  safeCac dT etyP eStoreV2: ReadableStore[Long, T etyP eResult],
  cac dT etyP eStoreV2NoVF: ReadableStore[Long, T etyP eResult],
  cac dT etyP eStoreV2: ReadableStore[Long, T etyP eResult],
  contentM xerStore: ReadableStore[ContentM xerRequest, ContentM xerResponse],
  softUserLocat onStore: ReadableStore[Long, Locat on],
  topT etsByGeoStore: ReadableStore[ nterestDoma n[Str ng], Map[Str ng, L st[(Long, Double)]]],
  topT etsByGeoV2Vers onedStore: ReadableStore[Str ng, PopT ets nPlace],
)(
   mpl c  val globalStats: StatsRece ver) {
  val s ces: Seq[Cand dateS ce[Target, RawCand date] w h Cand dateS ceEl g ble[
    Target,
    RawCand date
  ]] = {
    Seq(
      Tr pGeoCand datesAdaptor(
        tr pT etCand dateStore,
        contentM xerStore,
        safeCac dT etyP eStoreV2,
        cac dT etyP eStoreV2NoVF,
        globalStats
      ),
      TopT etsByGeoAdaptor(
        geoDuckV2Store,
        softUserLocat onStore,
        topT etsByGeoStore,
        topT etsByGeoV2Vers onedStore,
        cac dT etyP eStoreV2,
        cac dT etyP eStoreV2NoVF,
        globalStats
      )
    )
  }
}
