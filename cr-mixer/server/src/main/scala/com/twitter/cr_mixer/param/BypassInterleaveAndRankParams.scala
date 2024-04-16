package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Bypass nterleaveAndRankParams {
  object EnableTwh nCollabF lterBypassParam
      extends FSParam[Boolean](
        na  = "bypass_ nterleave_and_rank_twh n_collab_f lter",
        default = false
      )

  object EnableTwoTo rBypassParam
      extends FSParam[Boolean](
        na  = "bypass_ nterleave_and_rank_two_to r",
        default = false
      )

  object EnableConsu rBasedTwh nBypassParam
      extends FSParam[Boolean](
        na  = "bypass_ nterleave_and_rank_consu r_based_twh n",
        default = false
      )

  object EnableConsu rBasedWalsBypassParam
      extends FSParam[Boolean](
        na  = "bypass_ nterleave_and_rank_consu r_based_wals",
        default = false
      )

  object Twh nCollabF lterBypassPercentageParam
      extends FSBoundedParam[Double](
        na  = "bypass_ nterleave_and_rank_twh n_collab_f lter_percentage",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  object TwoTo rBypassPercentageParam
      extends FSBoundedParam[Double](
        na  = "bypass_ nterleave_and_rank_two_to r_percentage",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  object Consu rBasedTwh nBypassPercentageParam
      extends FSBoundedParam[Double](
        na  = "bypass_ nterleave_and_rank_consu r_based_twh n_percentage",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  object Consu rBasedWalsBypassPercentageParam
      extends FSBoundedParam[Double](
        na  = "bypass_ nterleave_and_rank_consu r_based_wals_percentage",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableTwh nCollabF lterBypassParam,
    EnableTwoTo rBypassParam,
    EnableConsu rBasedTwh nBypassParam,
    EnableConsu rBasedWalsBypassParam,
    Twh nCollabF lterBypassPercentageParam,
    TwoTo rBypassPercentageParam,
    Consu rBasedTwh nBypassPercentageParam,
    Consu rBasedWalsBypassPercentageParam,
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableTwh nCollabF lterBypassParam,
      EnableTwoTo rBypassParam,
      EnableConsu rBasedTwh nBypassParam,
      EnableConsu rBasedWalsBypassParam,
    )

    val doubleOverr des = FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(
      Twh nCollabF lterBypassPercentageParam,
      TwoTo rBypassPercentageParam,
      Consu rBasedTwh nBypassPercentageParam,
      Consu rBasedWalsBypassPercentageParam,
    )
    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }
}
