package com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PopGeoQual yFollowS ceFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val  ntFSParams: Seq[FSBoundedParam[ nt] w h FSNa ] = Seq(
    PopGeoQual yFollowS ceParams.PopGeoS ceGeoHashMaxPrec s on,
    PopGeoQual yFollowS ceParams.PopGeoS ceGeoHashM nPrec s on,
    PopGeoQual yFollowS ceParams.PopGeoS ceMaxResultsPerPrec s on
  )
  overr de val doubleFSParams: Seq[FSBoundedParam[Double] w h FSNa ] = Seq(
    PopGeoQual yFollowS ceParams.Cand dateS ce  ght
  )
  overr de val booleanFSParams: Seq[FSParam[Boolean] w h FSNa ] = Seq(
    PopGeoQual yFollowS ceParams.Cand dateS ceEnabled,
    PopGeoQual yFollowS ceParams.PopGeoS ceReturnFromAllPrec s ons
  )
}
