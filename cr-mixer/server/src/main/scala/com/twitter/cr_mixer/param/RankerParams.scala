package com.tw ter.cr_m xer.param

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object RankerParams {

  object MaxCand datesToRank
      extends FSBoundedParam[ nt](
        na  = "tw stly_core_max_cand dates_to_rank",
        default = 2000,
        m n = 0,
        max = 9999
      )

  object EnableBlueVer f edTopK
      extends FSParam[Boolean](
        na  = "tw stly_core_blue_ver f ed_top_k",
        default = true
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    MaxCand datesToRank,
    EnableBlueVer f edTopK
  )

  lazy val conf g: BaseConf g = {

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(EnableBlueVer f edTopK)

    val boundedDurat onFSOverr des =
      FeatureSw chOverr deUt l.getBoundedDurat onFSOverr des()

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      MaxCand datesToRank
    )

    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
    )
    val str ngFSOverr des = FeatureSw chOverr deUt l.getStr ngFSOverr des()

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(boundedDurat onFSOverr des: _*)
      .set( ntOverr des: _*)
      .set(enumOverr des: _*)
      .set(str ngFSOverr des: _*)
      .bu ld()
  }
}
