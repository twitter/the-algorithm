package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Batc dStratoCl entW hModerateT  out
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.t  l nes.cl ents.strato.top cs.Top cSoc alProofCl ent
 mport com.tw ter.t  l nes.cl ents.strato.top cs.Top cSoc alProofCl ent mpl
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object Top cSoc alProofCl entModule extends Tw terModule {

  @S ngleton
  @Prov des
  def prov desS m lar yCl ent(
    @Na d(Batc dStratoCl entW hModerateT  out)
    stratoCl ent: Cl ent,
    statsRece ver: StatsRece ver
  ): Top cSoc alProofCl ent = new Top cSoc alProofCl ent mpl(stratoCl ent, statsRece ver)
}
