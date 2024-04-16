package com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RepeatedProf leV s sFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] =
    Seq(
      RepeatedProf leV s sParams. ncludeCand dates,
      RepeatedProf leV s sParams.UseOnl neDataset,
    )
  overr de val  ntFSParams: Seq[FSBoundedParam[ nt]] =
    Seq(
      RepeatedProf leV s sParams.Recom ndat onThreshold,
      RepeatedProf leV s sParams.Bucket ngThreshold,
    )
}
