package com.tw ter.product_m xer.component_l brary.cand date_s ce.ads

 mport com.tw ter.adserver.thr ftscala.Ad mpress on
 mport com.tw ter.adserver.thr ftscala.AdRequestParams
 mport com.tw ter.adserver.thr ftscala.NewAdServer
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AdsProdThr ftCand dateS ce @ nject() (
  adServerCl ent: NewAdServer. thodPerEndpo nt)
    extends Cand dateS ce[AdRequestParams, Ad mpress on] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("AdsProdThr ft")

  overr de def apply(request: AdRequestParams): St ch[Seq[Ad mpress on]] =
    St ch.callFuture(adServerCl ent.makeAdRequest(request)).map(_. mpress ons)
}
