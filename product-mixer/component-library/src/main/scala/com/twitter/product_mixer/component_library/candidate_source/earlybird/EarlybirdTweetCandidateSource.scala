package com.tw ter.product_m xer.component_l brary.cand date_s ce.earlyb rd

 mport com.tw ter.search.earlyb rd.{thr ftscala => t}
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Earlyb rdT etCand dateS ce @ nject() (
  earlyb rdServ ce: t.Earlyb rdServ ce. thodPerEndpo nt)
    extends Cand dateS ce[t.Earlyb rdRequest, t.Thr ftSearchResult]
    w h Logg ng {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("Earlyb rdT ets")

  overr de def apply(request: t.Earlyb rdRequest): St ch[Seq[t.Thr ftSearchResult]] = {
    St ch
      .callFuture(earlyb rdServ ce.search(request))
      .map { response: t.Earlyb rdResponse =>
        response.searchResults.map(_.results).getOrElse(Seq.empty)
      }
  }
}
