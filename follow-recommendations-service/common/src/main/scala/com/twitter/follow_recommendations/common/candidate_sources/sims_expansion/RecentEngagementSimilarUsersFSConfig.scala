package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSParam

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RecentEngage ntS m larUsersFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    RecentEngage ntS m larUsersParams.F rstDegreeSortEnabled
  )
}
