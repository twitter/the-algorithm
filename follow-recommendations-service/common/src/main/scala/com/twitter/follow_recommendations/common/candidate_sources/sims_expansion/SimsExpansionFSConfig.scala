package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class S msExpans onFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val  ntFSParams: Seq[FSBoundedParam[ nt]] = Seq(
    RecentFollow ngS m larUsersParams.MaxF rstDegreeNodes,
    RecentFollow ngS m larUsersParams.MaxSecondaryDegreeExpans onPerNode,
    RecentFollow ngS m larUsersParams.MaxResults
  )

  overr de val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    DBV2S msExpans onParams.RecentFollow ngS m larUsersDBV2Cal brateD v sor,
    DBV2S msExpans onParams.RecentEngage ntS m larUsersDBV2Cal brateD v sor
  )

  overr de val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    DBV2S msExpans onParams.D sable avyRanker,
    RecentFollow ngS m larUsersParams.T  stamp ntegrated
  )
}
