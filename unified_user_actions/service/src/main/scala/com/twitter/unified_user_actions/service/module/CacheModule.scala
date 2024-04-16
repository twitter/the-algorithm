package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.google.common.cac .Cac Bu lder
 mport com.google. nject.Prov des
 mport com.tw ter.dynmap.DynMap
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.un f ed_user_act ons.enr c r.hcac .LocalCac 
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.ut l.Future
 mport java.ut l.concurrent.T  Un 
 mport javax. nject.S ngleton

object Cac Module extends Tw terModule {
  pr vate f nal val localCac TtlFlagNa  = "local.cac .ttl.seconds"
  pr vate f nal val localCac MaxS zeFlagNa  = "local.cac .max.s ze"

  flag[Long](
    na  = localCac TtlFlagNa ,
    default = 1800L,
     lp = "Local Cac 's TTL  n seconds"
  )

  flag[Long](
    na  = localCac MaxS zeFlagNa ,
    default = 1000L,
     lp = "Local Cac 's max s ze"
  )

  @Prov des
  @S ngleton
  def prov desLocalCac (
    @Flag(localCac TtlFlagNa ) localCac TtlFlag: Long,
    @Flag(localCac MaxS zeFlagNa ) localCac MaxS zeFlag: Long,
    statsRece ver: StatsRece ver
  ): LocalCac [Enr ch ntKey, DynMap] = {
    val underly ng = Cac Bu lder
      .newBu lder()
      .exp reAfterWr e(localCac TtlFlag, T  Un .SECONDS)
      .max mumS ze(localCac MaxS zeFlag)
      .bu ld[Enr ch ntKey, Future[DynMap]]()

    new LocalCac [Enr ch ntKey, DynMap](
      underly ng = underly ng,
      statsRece ver = statsRece ver.scope("enr c rLocalCac "))
  }
}
