package com.tw ter.ho _m xer.funct onal_component.cand date_s ce

 mport com.google. nject.na .Na d
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.StaleT etsCac 
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class StaleT etsCac Cand dateS ce @ nject() (
  @Na d(StaleT etsCac ) staleT etsCac :  mcac dCl ent)
    extends Cand dateS ce[Seq[Long], Long] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("StaleT etsCac ")

  pr vate val StaleT etsCac KeyPref x = "v1_"

  overr de def apply(request: Seq[Long]): St ch[Seq[Long]] = {
    val keys = request.map(StaleT etsCac KeyPref x + _)

    St ch.callFuture(staleT etsCac .get(keys).map { t ets =>
      t ets.map {
        case (k, _) => k.replaceF rst(StaleT etsCac KeyPref x, "").toLong
      }.toSeq
    })
  }
}
