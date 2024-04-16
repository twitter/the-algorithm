package com.tw ter.follow_recom ndat ons.common.rankers. nterleave_ranker

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSParam

@S ngleton
class  nterleaveRankerFSConf g @ nject() extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq( nterleaveRankerParams.Scr beRank ng nfo n nterleaveRanker)
}
