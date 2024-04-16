package com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PPM LocaleFollowS ceFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] = Seq(
    PPM LocaleFollowS ceParams.Cand dateS ceEnabled,
  )

  overr de val str ngSeqFSParams: Seq[Param[Seq[Str ng]] w h FSNa ] = Seq(
    PPM LocaleFollowS ceParams.LocaleToExcludeFromRecom ndat on,
  )

  overr de val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    PPM LocaleFollowS ceParams.Cand dateS ce  ght,
  )
}
