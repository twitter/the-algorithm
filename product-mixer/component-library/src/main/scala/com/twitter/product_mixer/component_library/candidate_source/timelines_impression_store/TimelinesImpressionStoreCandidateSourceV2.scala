package com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l nes_ mpress on_store

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyFetc rS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.t  l nes. mpress on_store.T et mpress onStoreManhattanV2OnUserCl entColumn
 mport com.tw ter.t  l nes. mpress on.thr ftscala.T et mpress onsEntr es
 mport com.tw ter.t  l nes. mpress on.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l nes mpress onStoreCand dateS ceV2 @ nject() (
  cl ent: T et mpress onStoreManhattanV2OnUserCl entColumn)
    extends StratoKeyFetc rS ce[
      Long,
      t.T et mpress onsEntr es,
      t.T et mpress onsEntry
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    "T  l nes mpress onStore")

  overr de val fetc r: Fetc r[Long, Un , T et mpress onsEntr es] = cl ent.fetc r

  overr de def stratoResultTransfor r(
    stratoResult: t.T et mpress onsEntr es
  ): Seq[t.T et mpress onsEntry] =
    stratoResult.entr es
}
