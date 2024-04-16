package com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RealGraphOonFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] =
    Seq(
      RealGraphOonParams. ncludeRealGraphOonCand dates,
      RealGraphOonParams.TryToReadRealGraphOonCand dates,
      RealGraphOonParams.UseV2
    )
  overr de val doubleFSParams: Seq[FSBoundedParam[Double]] =
    Seq(
      RealGraphOonParams.ScoreThreshold
    )
  overr de val  ntFSParams: Seq[FSBoundedParam[ nt]] =
    Seq(
      RealGraphOonParams.RealGraphOonResultCountThreshold,
      RealGraphOonParams.MaxResults,
    )
}
