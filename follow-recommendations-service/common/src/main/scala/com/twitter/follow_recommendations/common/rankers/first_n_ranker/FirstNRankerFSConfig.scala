package com.tw ter.follow_recom ndat ons.common.rankers.f rst_n_ranker

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

@S ngleton
class F rstNRankerFSConf g @ nject() extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(F rstNRankerParams.Scr beRank ng nfo nF rstNRanker)

  overr de val  ntFSParams: Seq[FSBoundedParam[ nt]] = Seq(
    F rstNRankerParams.Cand datesToRank
  )

  overr de val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    F rstNRankerParams.M nNumCand datesScoredScaleDownFactor
  )
}
