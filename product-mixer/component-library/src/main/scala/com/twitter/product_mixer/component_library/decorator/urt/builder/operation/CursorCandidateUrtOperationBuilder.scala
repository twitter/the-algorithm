package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.operat on

 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorD splayTreat nt
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class CursorCand dateUrtOperat onBu lder[-Query <: P pel neQuery](
  cursorType: CursorType,
  d splayTreat nt: Opt on[CursorD splayTreat nt] = None,
   dToReplace: Opt on[Long] = None)
    extends Cand dateUrtEntryBu lder[Query, CursorCand date, CursorOperat on] {

  overr de def apply(
    query: Query,
    cursorCand date: CursorCand date,
    cand dateFeatures: FeatureMap
  ): CursorOperat on = CursorOperat on(
     d = cursorCand date. d,
    sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
    value = cursorCand date.value,
    cursorType = cursorType,
    d splayTreat nt = d splayTreat nt,
     dToReplace =  dToReplace
  )
}
