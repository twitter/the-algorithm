package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtPlaceholderCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.PlaceholderTopCursorBu lder.DefaultPlaceholderCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor

object PlaceholderTopCursorBu lder {
  val DefaultPlaceholderCursor = UrtPlaceholderCursor()
}

/**
 * Top cursor bu lder that can be used w n t  Product does not support pag ng up. T  URT spec
 * requ res that both bottom and top cursors always be present on each page. T refore,  f t 
 * product does not support pag ng up, t n   can use a cursor value that  s not deser al zable.
 * T  way  f t  cl ent subm s a TopCursor, t  backend w ll treat t  t  request as  f no
 * cursor was subm ted.
 */
case class PlaceholderTopCursorBu lder(
  ser al zer: P pel neCursorSer al zer[UrtP pel neCursor] = UrtCursorSer al zer)
    extends UrtCursorBu lder[P pel neQuery w h HasP pel neCursor[UrtP pel neCursor]] {
  overr de val cursorType: CursorType = TopCursor

  overr de def cursorValue(
    query: P pel neQuery w h HasP pel neCursor[UrtP pel neCursor],
    t  l neEntr es: Seq[T  l neEntry]
  ): Str ng = ser al zer.ser al zeCursor(DefaultPlaceholderCursor)
}
