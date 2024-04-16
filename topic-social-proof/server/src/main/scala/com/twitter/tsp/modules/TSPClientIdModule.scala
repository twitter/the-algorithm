package com.tw ter.tsp.modules

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.S ngleton

object TSPCl ent dModule extends Tw terModule {
  pr vate val cl ent dFlag = flag("thr ft.cl ent d", "top c-soc al-proof.prod", "Thr ft cl ent  d")

  @Prov des
  @S ngleton
  def prov desCl ent d: Cl ent d = Cl ent d(cl ent dFlag())
}
