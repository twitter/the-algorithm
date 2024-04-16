package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Onl neSTPS ceScorer @ nject() (
  onl neSTPS ceW hEPScorer: Onl neSTPS ceW hEPScorer)
    extends Cand dateS ce[
      HasCl entContext w h HasParams w h HasRecentFollo dUser ds,
      Cand dateUser
    ] {

  overr de def apply(
    request: HasCl entContext w h HasParams w h HasRecentFollo dUser ds
  ): St ch[Seq[Cand dateUser]] = {
    onl neSTPS ceW hEPScorer(request)
  }

  overr de val  dent f er: Cand dateS ce dent f er = BaseOnl neSTPS ce. dent f er
}
