package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * Consu rsBasedUserT etGraph Params, t re are mult ple ways (e.g. FRS, RealGraphOon) to generate consu rsSeedSet for Consu rsBasedUserT etGraph
 * for now   allow flex b l y  n tun ng UTG params for d fferent consu rsSeedSet generat on algo by g v ng t  param na  {consu rSeedSetAlgo}{ParamNa }
 */

object Consu rsBasedUserT etGraphParams {

  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "consu rs_based_user_t et_graph_enable_s ce",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceParam,
  )

  lazy val conf g: BaseConf g = {

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des()

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des()

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam
    )

    BaseConf gBu lder()
      .set( ntOverr des: _*)
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }
}
