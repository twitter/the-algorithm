package com.tw ter.product_m xer.component_l brary.decorator.sl ce.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.{
  NextCursor => CursorCand dateNextCursor
}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.{
  Prev ousCursor => CursorCand datePrev ousCursor
}
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Cursor em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.NextCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Prev ousCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.sl ce.bu lder.Cand dateSl ce emBu lder

case class CursorCand dateSl ce emBu lder()
    extends Cand dateSl ce emBu lder[P pel neQuery, CursorCand date, Cursor em] {

  overr de def apply(
    query: P pel neQuery,
    cand date: CursorCand date,
    featureMap: FeatureMap
  ): Cursor em =
    cand date.cursorType match {
      case CursorCand dateNextCursor => Cursor em(cand date.value, NextCursor)
      case CursorCand datePrev ousCursor => Cursor em(cand date.value, Prev ousCursor)
    }
}
