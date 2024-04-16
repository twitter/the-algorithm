package com.tw ter.product_m xer.component_l brary.cand date_s ce.cr_m xer

 mport com.tw ter.cr_m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Returns out-of-network T et recom ndat ons by us ng user recom ndat ons
 * from FollowRecom ndat onServ ce as an  nput seed-set to Earlyb rd
 */
@S ngleton
class CrM xerFrsBasedT etRecom ndat onsCand dateS ce @ nject() (
  crM xerCl ent: t.CrM xer. thodPerEndpo nt)
    extends Cand dateS ce[t.FrsT etRequest, t.FrsT et] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("CrM xerFrsBasedT etRecom ndat ons")

  overr de def apply(request: t.FrsT etRequest): St ch[Seq[t.FrsT et]] = St ch
    .callFuture(crM xerCl ent.getFrsBasedT etRecom ndat ons(request))
    .map(_.t ets)
}
