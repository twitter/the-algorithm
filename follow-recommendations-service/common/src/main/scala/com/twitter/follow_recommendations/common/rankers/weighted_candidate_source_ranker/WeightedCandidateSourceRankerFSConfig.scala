package com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSParam

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class   ghtedCand dateS ceRankerFSConf g @ nject() extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(  ghtedCand dateS ceRankerParams.Scr beRank ng nfo n  ghtedRanker)
}
