package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object T etBasedUserT etGraphParams {

  object M nCoOccurrenceParam
      extends FSBoundedParam[ nt](
        na  = "t et_based_user_t et_graph_m n_co_occurrence",
        default = 3,
        m n = 0,
        max = 500
      )

  object T etBasedM nScoreParam
      extends FSBoundedParam[Double](
        na  = "t et_based_user_t et_graph_t et_based_m n_score",
        default = 0.5,
        m n = 0.0,
        max = 10.0
      )

  object Consu rsBasedM nScoreParam
      extends FSBoundedParam[Double](
        na  = "t et_based_user_t et_graph_consu rs_based_m n_score",
        default = 4.0,
        m n = 0.0,
        max = 10.0
      )
  object MaxConsu rSeedsNumParam
      extends FSBoundedParam[ nt](
        na  = "t et_based_user_t et_graph_max_user_seeds_num",
        default = 100,
        m n = 0,
        max = 300
      )

  object EnableCoverageExpans onOldT etParam
      extends FSParam[Boolean](
        na  = "t et_based_user_t et_graph_enable_coverage_expans on_old_t et",
        default = false
      )

  object EnableCoverageExpans onAllT etParam
      extends FSParam[Boolean](
        na  = "t et_based_user_t et_graph_enable_coverage_expans on_all_t et",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableCoverageExpans onAllT etParam,
    EnableCoverageExpans onOldT etParam,
    M nCoOccurrenceParam,
    MaxConsu rSeedsNumParam,
    T etBasedM nScoreParam,
    Consu rsBasedM nScoreParam
  )

  lazy val conf g: BaseConf g = {

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableCoverageExpans onAllT etParam,
      EnableCoverageExpans onOldT etParam
    )

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      M nCoOccurrenceParam,
      MaxConsu rSeedsNumParam
    )

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(
        T etBasedM nScoreParam,
        Consu rsBasedM nScoreParam)

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set( ntOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }

}
