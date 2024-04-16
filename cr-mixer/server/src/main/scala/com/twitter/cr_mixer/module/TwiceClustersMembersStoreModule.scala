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
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.s mclusters_v2.thr ftscala.OrderedClustersAnd mbers
 mport javax. nject.Na d

object Tw ceClusters mbersStoreModule extends Tw terModule {

  pr vate val tw ceClusters mbersColumnPath: Flag[Str ng] = flag[Str ng](
    na  = "crM xer.tw ceClusters mbersColumnPath",
    default =
      "recom ndat ons/s mclusters_v2/embedd ngs/Tw ceClusters mbersLargestD mApeS m lar y",
     lp = "Strato column path for T etRecentEngagedUsersStore"
  )

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Tw ceClusters mbersStore)
  def prov desT etRecentEngagedUserStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[User d, OrderedClustersAnd mbers] = {
    val tw ceClusters mbersStratoFetchableStore = StratoFetchableStore
      .w hUn V ew[User d, OrderedClustersAnd mbers](
        stratoCl ent,
        tw ceClusters mbersColumnPath())

    ObservedReadableStore(
      tw ceClusters mbersStratoFetchableStore
    )(statsRece ver.scope("tw ce_clusters_ mbers_largestD mApe_s m lar y_store"))
  }
}
