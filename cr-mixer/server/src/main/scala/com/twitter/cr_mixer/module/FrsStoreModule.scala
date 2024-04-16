package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.s ce_s gnal.FrsStore
 mport com.tw ter.cr_m xer.s ce_s gnal.FrsStore.FrsQueryResult
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter.storehaus.ReadableStore
 mport javax. nject.Na d

object FrsStoreModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.FrsStore)
  def prov desFrsStore(
    frsCl ent: FollowRecom ndat onsThr ftServ ce. thodPerEndpo nt,
    statsRece ver: StatsRece ver,
    dec der: CrM xerDec der
  ): ReadableStore[FrsStore.Query, Seq[FrsQueryResult]] = {
    ObservedReadableStore(FrsStore(frsCl ent, statsRece ver, dec der))(
      statsRece ver.scope("follow_recom ndat ons_store"))
  }
}
