package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * Consu rsBasedUserV deoGraph Params: t re are mult ple ways (e.g. FRS, RealGraph n) to generate consu rsSeedSet for Consu rsBasedUserT etGraph
 * for now   allow flex b l y  n tun ng UVG params for d fferent consu rsSeedSet generat on algo by g v ng t  param na  {consu rSeedSetAlgo}{ParamNa }
 */

object Consu rsBasedUserV deoGraphParams {

  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "consu rs_based_user_v deo_graph_enable_s ce",
        default = false
      )

  // UTG-RealGraph N
  object RealGraph nM nCoOccurrenceParam
      extends FSBoundedParam[ nt](
        na  = "consu rs_based_user_v deo_graph_real_graph_ n_m n_co_occurrence",
        default = 3,
        m n = 0,
        max = 500
      )

  object RealGraph nM nScoreParam
      extends FSBoundedParam[Double](
        na  = "consu rs_based_user_v deo_graph_real_graph_ n_m n_score",
        default = 2.0,
        m n = 0.0,
        max = 10.0
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceParam,
    RealGraph nM nCoOccurrenceParam,
    RealGraph nM nScoreParam
  )

  lazy val conf g: BaseConf g = {

    val  ntOverr des =
      FeatureSw chOverr deUt l.getBounded ntFSOverr des(RealGraph nM nCoOccurrenceParam)

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(RealGraph nM nScoreParam)

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
