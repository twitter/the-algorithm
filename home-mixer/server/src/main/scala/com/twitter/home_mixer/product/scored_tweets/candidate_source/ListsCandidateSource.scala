package com.tw ter.ho _m xer.product.scored_t ets.cand date_s ce

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce
 mport com.tw ter.t  l neserv ce.{thr ftscala => tls}

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class L stsCand dateS ce @ nject() (t  l neServ ce: T  l neServ ce)
    extends Cand dateS ce[Seq[tls.T  l neQuery], tls.T et] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("L sts")

  overr de def apply(requests: Seq[tls.T  l neQuery]): St ch[Seq[tls.T et]] = {
    val t  l nes = St ch.traverse(requests) { request => t  l neServ ce.getT  l ne(request) }

    t  l nes.map {
      _.flatMap {
        _.entr es.collect { case tls.T  l neEntry.T et(t et) => t et }
      }
    }
  }
}
