package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.app.Flag
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq

object RealGraphOonStoreModule extends Tw terModule {

  pr vate val userRealGraphOonColumnPath: Flag[Str ng] = flag[Str ng](
    na  = "crM xer.userRealGraphOonColumnPath",
    default = "recom ndat ons/tw stly/userRealgraphOon",
     lp = "Strato column path for user real graph OON Store"
  )

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.RealGraphOonStore)
  def prov desRealGraphOonStore(
    stratoCl ent: StratoCl ent,
    statsRece ver: StatsRece ver
  ): ReadableStore[User d, Cand dateSeq] = {
    val realGraphOonStratoFetchableStore = StratoFetchableStore
      .w hUn V ew[User d, Cand dateSeq](stratoCl ent, userRealGraphOonColumnPath())

    ObservedReadableStore(
      realGraphOonStratoFetchableStore
    )(statsRece ver.scope("user_real_graph_oon_store"))
  }
}
