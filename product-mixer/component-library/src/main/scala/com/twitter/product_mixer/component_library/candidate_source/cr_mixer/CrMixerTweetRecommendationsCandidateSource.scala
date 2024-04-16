package com.tw ter.product_m xer.component_l brary.cand date_s ce.cr_m xer

 mport com.tw ter.cr_m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CrM xerT etRecom ndat onsCand dateS ce @ nject() (
  crM xerCl ent: t.CrM xer. thodPerEndpo nt)
    extends Cand dateS ce[t.CrM xerT etRequest, t.T etRecom ndat on] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("CrM xerT etRecom ndat ons")

  overr de def apply(request: t.CrM xerT etRequest): St ch[Seq[t.T etRecom ndat on]] = St ch
    .callFuture(crM xerCl ent.getT etRecom ndat ons(request))
    .map(_.t ets)
}
