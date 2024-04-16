package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT et
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT ets
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pDoma n
 mport javax. nject.Na d

object Tr pCand dateStoreModule extends Tw terModule {
  pr vate val stratoColumn = "trends/tr p/tr pT etsDataflowProd"

  @Prov des
  @Na d(ModuleNa s.Tr pCand dateStore)
  def prov desS mClustersTr pCand dateStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent
  ): ReadableStore[Tr pDoma n, Seq[Tr pT et]] = {
    val tr pCand dateStratoFetchableStore =
      StratoFetchableStore
        .w hUn V ew[Tr pDoma n, Tr pT ets](stratoCl ent, stratoColumn)
        .mapValues(_.t ets)

    ObservedReadableStore(
      tr pCand dateStratoFetchableStore
    )(statsRece ver.scope("s mclusters_tr p_cand date_store"))
  }
}
