package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Batc dStratoCl entW hModerateT  out
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.t  l nes.cl ents.strato.tw stly.S mClustersRecentEngage ntS m lar yCl ent
 mport com.tw ter.t  l nes.cl ents.strato.tw stly.S mClustersRecentEngage ntS m lar yCl ent mpl
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object S mClustersRecentEngage ntsCl entModule extends Tw terModule {
  @S ngleton
  @Prov des
  def prov desS m lar yCl ent(
    @Na d(Batc dStratoCl entW hModerateT  out)
    stratoCl ent: Cl ent,
    statsRece ver: StatsRece ver
  ): S mClustersRecentEngage ntS m lar yCl ent = {
    new S mClustersRecentEngage ntS m lar yCl ent mpl(stratoCl ent, statsRece ver)
  }
}
