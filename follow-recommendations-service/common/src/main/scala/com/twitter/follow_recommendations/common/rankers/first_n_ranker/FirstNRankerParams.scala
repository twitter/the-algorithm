package com.tw ter.follow_recom ndat ons.common.rankers.f rst_n_ranker

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

object F rstNRankerParams {
  case object Cand datesToRank
      extends FSBoundedParam[ nt](
        F rstNRankerFeatureSw chKeys.Cand datePoolS ze,
        default = 100,
        m n = 50,
        max = 600)

  case object GroupDupl cateCand dates extends Param[Boolean](true)
  case object Scr beRank ng nfo nF rstNRanker
      extends FSParam[Boolean](F rstNRankerFeatureSw chKeys.Scr beRank ng nfo, true)

  // t  m n mum of cand dates to score  n each request.
  object M nNumCand datesScoredScaleDownFactor
      extends FSBoundedParam[Double](
        na  = F rstNRankerFeatureSw chKeys.M nNumCand datesScoredScaleDownFactor,
        default = 0.3,
        m n = 0.1,
        max = 1.0)
}
