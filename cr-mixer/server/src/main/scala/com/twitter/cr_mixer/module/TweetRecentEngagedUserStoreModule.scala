package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.app.Flag
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.tw stly.thr ftscala.T etRecentEngagedUsers

object T etRecentEngagedUserStoreModule extends Tw terModule {

  pr vate val t etRecentEngagedUsersStoreDefaultVers on =
    0 // DefaultVers on for t etEngagedUsersStore, whose key = (t et d, DefaultVers on)
  pr vate val t etRecentEngagedUsersColumnPath: Flag[Str ng] = flag[Str ng](
    na  = "crM xer.t etRecentEngagedUsersColumnPath",
    default = "recom ndat ons/tw stly/t etRecentEngagedUsers",
     lp = "Strato column path for T etRecentEngagedUsersStore"
  )
  pr vate type Vers on = Long

  @Prov des
  @S ngleton
  def prov desT etRecentEngagedUserStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[T et d, T etRecentEngagedUsers] = {
    val t etRecentEngagedUsersStratoFetchableStore = StratoFetchableStore
      .w hUn V ew[(T et d, Vers on), T etRecentEngagedUsers](
        stratoCl ent,
        t etRecentEngagedUsersColumnPath()).composeKeyMapp ng[T et d](t et d =>
        (t et d, t etRecentEngagedUsersStoreDefaultVers on))

    ObservedReadableStore(
      t etRecentEngagedUsersStratoFetchableStore
    )(statsRece ver.scope("t et_recent_engaged_users_store"))
  }
}
