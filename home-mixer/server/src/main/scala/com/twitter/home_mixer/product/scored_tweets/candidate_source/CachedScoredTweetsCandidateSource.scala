package com.tw ter.ho _m xer.product.scored_t ets.cand date_s ce

 mport com.tw ter.ho _m xer.ut l.Cac dScoredT ets lper
 mport com.tw ter.ho _m xer.{thr ftscala => hmt}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cac dScoredT etsCand dateS ce @ nject() ()
    extends Cand dateS ce[P pel neQuery, hmt.ScoredT et] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("Cac dScoredT ets")

  overr de def apply(request: P pel neQuery): St ch[Seq[hmt.ScoredT et]] = {
    St ch.value(
      request.features.map(Cac dScoredT ets lper.unseenCac dScoredT ets).getOrElse(Seq.empty))
  }
}
