package com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo

 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport javax. nject. nject

@S ngleton
class PopGeohashS ce @ nject() (
  popGeoS ce: PopGeoS ce,
  statsRece ver: StatsRece ver)
    extends BasePopGeohashS ce(
      popGeoS ce = popGeoS ce,
      statsRece ver = statsRece ver.scope("PopGeohashS ce"),
    ) {
  overr de def cand dateS ceEnabled(target: Target): Boolean = true
  overr de val  dent f er: Cand dateS ce dent f er = PopGeohashS ce. dent f er
  overr de def m nGeohashLength(target: Target):  nt = {
    target.params(PopGeoS ceParams.PopGeoS ceGeoHashM nPrec s on)
  }
  overr de def maxResults(target: Target):  nt = {
    target.params(PopGeoS ceParams.PopGeoS ceMaxResultsPerPrec s on)
  }
  overr de def maxGeohashLength(target: Target):  nt = {
    target.params(PopGeoS ceParams.PopGeoS ceGeoHashMaxPrec s on)
  }
  overr de def returnResultFromAllPrec s on(target: Target): Boolean = {
    target.params(PopGeoS ceParams.PopGeoS ceReturnFromAllPrec s ons)
  }
}

object PopGeohashS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.PopGeohash.toStr ng)
}
