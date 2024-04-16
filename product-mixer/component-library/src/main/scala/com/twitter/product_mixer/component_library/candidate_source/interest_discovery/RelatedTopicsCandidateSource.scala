package com.tw ter.product_m xer.component_l brary.cand date_s ce. nterest_d scovery

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter. nject.Logg ng
 mport com.tw ter. nterests_d scovery.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch

/**
 * Generate a l st of related top cs results from  DS getRelatedTop cs (thr ft) endpo nt.
 * Returns related top cs, g ven a top c, w reas [[Recom ndedTop csCand dateS ce]] returns
 * recom nded top cs, g ven a user.
 */
@S ngleton
class RelatedTop csCand dateS ce @ nject() (
   nterestD scoveryServ ce: t. nterestsD scoveryServ ce. thodPerEndpo nt)
    extends Cand dateS ce[t.RelatedTop csRequest, t.RelatedTop c]
    w h Logg ng {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er(na  = "RelatedTop cs")

  overr de def apply(
    request: t.RelatedTop csRequest
  ): St ch[Seq[t.RelatedTop c]] = {
    St ch
      .callFuture( nterestD scoveryServ ce.getRelatedTop cs(request))
      .map { response: t.RelatedTop csResponse =>
        response.top cs
      }
  }
}
