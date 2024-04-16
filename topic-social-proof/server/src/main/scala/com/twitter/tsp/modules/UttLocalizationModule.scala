package com.tw ter.tsp.modules

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.top cl st ng.Top cL st ng
 mport com.tw ter.top cl st ng.cl ents.utt.UttCl ent
 mport com.tw ter.top cl st ng.utt.UttLocal zat on
 mport com.tw ter.top cl st ng.utt.UttLocal zat on mpl
 mport javax. nject.S ngleton

object UttLocal zat onModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desUttLocal zat on(
    top cL st ng: Top cL st ng,
    uttCl ent: UttCl ent,
    statsRece ver: StatsRece ver
  ): UttLocal zat on = {
    new UttLocal zat on mpl(
      top cL st ng,
      uttCl ent,
      statsRece ver
    )
  }
}
