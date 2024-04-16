package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object T etBasedUserAdGraphParams {

  object M nCoOccurrenceParam
      extends FSBoundedParam[ nt](
        na  = "t et_based_user_ad_graph_m n_co_occurrence",
        default = 1,
        m n = 0,
        max = 500
      )

  object Consu rsBasedM nScoreParam
      extends FSBoundedParam[Double](
        na  = "t et_based_user_ad_graph_consu rs_based_m n_score",
        default = 0.0,
        m n = 0.0,
        max = 10.0
      )

  object MaxConsu rSeedsNumParam
      extends FSBoundedParam[ nt](
        na  = "t et_based_user_ad_graph_max_user_seeds_num",
        default = 100,
        m n = 0,
        max = 300
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    M nCoOccurrenceParam,
    MaxConsu rSeedsNumParam,
    Consu rsBasedM nScoreParam
  )

  lazy val conf g: BaseConf g = {

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      M nCoOccurrenceParam,
      MaxConsu rSeedsNumParam
    )

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(Consu rsBasedM nScoreParam)

    BaseConf gBu lder()
      .set( ntOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }

}
