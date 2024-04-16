package com.tw ter.follow_recom ndat ons.common.cand date_s ces.crowd_search_accounts

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CrowdSearchAccountsFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] = Seq(
    CrowdSearchAccountsParams.Cand dateS ceEnabled,
  )
  overr de val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    CrowdSearchAccountsParams.Cand dateS ce  ght,
  )
}
