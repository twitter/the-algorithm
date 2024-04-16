package com.tw ter.follow_recom ndat ons.common.rankers.fat gue_ranker

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  mpress onBasedFat gueRankerFSConf g @ nject() extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq( mpress onBasedFat gueRankerParams.Scr beRank ng nfo nFat gueRanker)
}
