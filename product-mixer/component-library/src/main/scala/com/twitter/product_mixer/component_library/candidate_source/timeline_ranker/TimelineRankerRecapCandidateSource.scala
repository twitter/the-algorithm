package com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l ne_ranker

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l neranker.{thr ftscala => t}

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l neRankerRecapCand dateS ce @ nject() (
  t  l neRankerCl ent: t.T  l neRanker. thodPerEndpo nt)
    extends Cand dateS ce[t.RecapQuery, t.Cand dateT et] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("T  l neRankerRecap")

  overr de def apply(
    request: t.RecapQuery
  ): St ch[Seq[t.Cand dateT et]] = {
    St ch
      .callFuture(t  l neRankerCl ent.getRecapCand datesFromAuthors(Seq(request)))
      .map { response: Seq[t.GetCand dateT etsResponse] =>
        response. adOpt on.flatMap(_.cand dates).getOrElse(Seq.empty).f lter(_.t et.nonEmpty)
      }
  }
}
