package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object RecentRet etsParams {

  // S ce params
  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "tw stly_recentret ets_enable_s ce",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(EnableS ceParam)

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam
    )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .bu ld()
  }
}
