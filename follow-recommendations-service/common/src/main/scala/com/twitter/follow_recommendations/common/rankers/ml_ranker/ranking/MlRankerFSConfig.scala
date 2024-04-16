package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSParam

@S ngleton
class MlRankerFSConf g @ nject() extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(MlRankerParams.Scr beRank ng nfo nMlRanker)
}
