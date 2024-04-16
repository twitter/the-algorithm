package com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PopGeoS ceFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val  ntFSParams: Seq[FSBoundedParam[ nt] w h FSNa ] = Seq(
    PopGeoS ceParams.PopGeoS ceGeoHashMaxPrec s on,
    PopGeoS ceParams.PopGeoS ceMaxResultsPerPrec s on,
    PopGeoS ceParams.PopGeoS ceGeoHashM nPrec s on,
  )
  overr de val booleanFSParams: Seq[FSParam[Boolean] w h FSNa ] = Seq(
    PopGeoS ceParams.PopGeoS ceReturnFromAllPrec s ons,
  )
}
