package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object ProducerBasedUserAdGraphParams {

  object M nCoOccurrenceParam
      extends FSBoundedParam[ nt](
        na  = "producer_based_user_ad_graph_m n_co_occurrence",
        default = 2,
        m n = 0,
        max = 500
      )

  object M nScoreParam
      extends FSBoundedParam[Double](
        na  = "producer_based_user_ad_graph_m n_score",
        default = 3.0,
        m n = 0.0,
        max = 10.0
      )

  object MaxNumFollo rsParam
      extends FSBoundedParam[ nt](
        na  = "producer_based_user_ad_graph_max_num_follo rs",
        default = 500,
        m n = 100,
        max = 1000
      )

  val AllParams: Seq[Param[_] w h FSNa ] =
    Seq(M nCoOccurrenceParam, MaxNumFollo rsParam, M nScoreParam)

  lazy val conf g: BaseConf g = {

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      M nCoOccurrenceParam,
      MaxNumFollo rsParam,
    )

    val doubleOverr des = FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(M nScoreParam)

    BaseConf gBu lder()
      .set( ntOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }
}
