package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object AdsParams {
  object AdsCand dateGenerat onMaxCand datesNumParam
      extends FSBoundedParam[ nt](
        na  = "ads_cand date_generat on_max_cand dates_num",
        default = 400,
        m n = 0,
        max = 2000
      )

  object EnableScoreBoost
      extends FSParam[Boolean](
        na  = "ads_cand date_generat on_enable_score_boost",
        default = false
      )

  object AdsCand dateGenerat onScoreBoostFactor
      extends FSBoundedParam[Double](
        na  = "ads_cand date_generat on_score_boost_factor",
        default = 10000.0,
        m n = 1.0,
        max = 100000.0
      )

  object EnableScr be
      extends FSParam[Boolean](
        na  = "ads_cand date_generat on_enable_scr be",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    AdsCand dateGenerat onMaxCand datesNumParam,
    EnableScoreBoost,
    AdsCand dateGenerat onScoreBoostFactor
  )

  lazy val conf g: BaseConf g = {
    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      AdsCand dateGenerat onMaxCand datesNumParam)

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableScoreBoost,
      EnableScr be
    )

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(AdsCand dateGenerat onScoreBoostFactor)

    BaseConf gBu lder()
      .set( ntOverr des: _*)
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }
}
