package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.app.Flag
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter. rm .stp.thr ftscala.STPResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport javax. nject.Na d

object StrongT ePred ct onStoreModule extends Tw terModule {

  pr vate val strongT ePred ct onColumnPath: Flag[Str ng] = flag[Str ng](
    na  = "crM xer.strongT ePred ct onColumnPath",
    default = "onboard ng/userrecs/strong_t e_pred ct on_b g",
     lp = "Strato column path for StrongT ePred ct onStore"
  )

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.StpStore)
  def prov desStrongT ePred ct onStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[User d, STPResult] = {
    val strongT ePred ct onStratoFetchableStore = StratoFetchableStore
      .w hUn V ew[User d, STPResult](stratoCl ent, strongT ePred ct onColumnPath())

    ObservedReadableStore(
      strongT ePred ct onStratoFetchableStore
    )(statsRece ver.scope("strong_t e_pred ct on_b g_store"))
  }
}
