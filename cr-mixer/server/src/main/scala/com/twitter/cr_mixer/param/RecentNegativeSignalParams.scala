package com.tw ter.cr_m xer.param

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object RecentNegat veS gnalParams {
  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "tw stly_recentnegat ves gnals_enable_s ce",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceParam
  )

  lazy val conf g: BaseConf g = {
    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
    )

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam
    )

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des()

    BaseConf gBu lder()
      .set(booleanOverr des: _*).set(doubleOverr des: _*).set(enumOverr des: _*).bu ld()
  }
}
