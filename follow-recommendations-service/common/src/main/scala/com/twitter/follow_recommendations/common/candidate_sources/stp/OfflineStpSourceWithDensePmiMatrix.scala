package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.google. nject.S ngleton
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.generated.cl ent.hub.Ppm DenseMatr xCand datesCl entColumn
 mport javax. nject. nject

/**
 * Ma n s ce for strong-t e-pred ct on cand dates generated offl ne.
 */
@S ngleton
class Offl neStpS ceW hDensePm Matr x @ nject() (
  stpColumn: Ppm DenseMatr xCand datesCl entColumn)
    extends Offl neStrongT ePred ct onBaseS ce(stpColumn.fetc r) {
  overr de val  dent f er: Cand dateS ce dent f er = Offl neStpS ceW hDensePm Matr x. dent f er
}

object Offl neStpS ceW hDensePm Matr x {
  val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er(Algor hm.StrongT ePred ct onRec.toStr ng)
}
