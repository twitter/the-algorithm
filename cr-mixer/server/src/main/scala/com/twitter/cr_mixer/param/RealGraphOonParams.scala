package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object RealGraphOonParams {
  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "s gnal_realgraphoon_enable_s ce",
        default = false
      )

  object EnableS ceGraphParam
      extends FSParam[Boolean](
        na  = "graph_realgraphoon_enable_s ce",
        default = false
      )

  object MaxConsu rSeedsNumParam
      extends FSBoundedParam[ nt](
        na  = "graph_realgraphoon_max_user_seeds_num",
        default = 200,
        m n = 0,
        max = 1000
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceParam,
    EnableS ceGraphParam,
    MaxConsu rSeedsNumParam
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam,
      EnableS ceGraphParam
    )

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(MaxConsu rSeedsNumParam)

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set( ntOverr des: _*)
      .bu ld()
  }
}
