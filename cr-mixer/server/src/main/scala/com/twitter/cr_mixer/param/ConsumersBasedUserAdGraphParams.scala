package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Consu rsBasedUserAdGraphParams {

  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "consu rs_based_user_ad_graph_enable_s ce",
        default = false
      )

  // UTG-Lookal ke
  object M nCoOccurrenceParam
      extends FSBoundedParam[ nt](
        na  = "consu rs_based_user_ad_graph_m n_co_occurrence",
        default = 2,
        m n = 0,
        max = 500
      )

  object M nScoreParam
      extends FSBoundedParam[Double](
        na  = "consu rs_based_user_ad_graph_m n_score",
        default = 0.0,
        m n = 0.0,
        max = 10.0
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceParam,
    M nCoOccurrenceParam,
    M nScoreParam
  )

  lazy val conf g: BaseConf g = {

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(M nCoOccurrenceParam)
    val doubleOverr des = FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(M nScoreParam)
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(EnableS ceParam)

    BaseConf gBu lder()
      .set( ntOverr des: _*)
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }
}
