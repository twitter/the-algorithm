package com.tw ter.cr_m xer.module
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter.users gnalserv ce.thr ftscala.BatchS gnalRequest
 mport com.tw ter.users gnalserv ce.thr ftscala.BatchS gnalResponse
 mport javax. nject.Na d

object UserS gnalServ ceColumnModule extends Tw terModule {
  pr vate val UssColumnPath = "recom ndat ons/user-s gnal-serv ce/s gnals"

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.UssStratoColumn)
  def prov desUserS gnalServ ceStore(
    statsRece ver: StatsRece ver,
    stratoCl ent: StratoCl ent,
  ): ReadableStore[BatchS gnalRequest, BatchS gnalResponse] = {
    ObservedReadableStore(
      StratoFetchableStore
        .w hUn V ew[BatchS gnalRequest, BatchS gnalResponse](stratoCl ent, UssColumnPath))(
      statsRece ver.scope("user_s gnal_serv ce_store"))
  }
}
