package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Onl neSTPS ceFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[FSParam[Boolean] w h FSNa ] = Seq(
    Onl neSTPS ceParams.D sable avyRanker,
    Onl neSTPS ceParams.UseDBv2Scorer,
  )
}
