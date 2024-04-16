package com.tw ter.tsp.modules

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.top cl st ng.Top cL st ng
 mport com.tw ter.top cl st ng.Top cL st ngBu lder
 mport javax. nject.S ngleton

object Top cL st ngModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desTop cL st ng(statsRece ver: StatsRece ver): Top cL st ng = {
    new Top cL st ngBu lder(statsRece ver.scope(na space = "Top cL st ngBu lder")).bu ld
  }
}
