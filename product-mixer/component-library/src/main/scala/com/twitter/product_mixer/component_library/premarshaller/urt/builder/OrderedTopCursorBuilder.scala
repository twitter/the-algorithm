package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.OrderedTopCursorBu lder.TopCursorOffset
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case object OrderedTopCursorBu lder {
  // Ensure that t  next  n  al sort  ndex  s at least 10000 entr es away from top cursor's
  // current sort  ndex. T   s to ensure that t  contents of t  next page can be populated
  // w hout be ng ass gned sort  nd ces wh ch confl ct w h that of t  current page. T  assu s
  // that each page w ll have fe r than 10000 entr es.
  val TopCursorOffset = 10000L
}

/**
 * Bu lds [[UrtOrderedCursor]]  n t  Top pos  on
 *
 * @param  dSelector Spec f es t  entry from wh ch to der ve t  ` d` f eld
 * @param ser al zer Converts t  cursor to an encoded str ng
 */
case class OrderedTopCursorBu lder(
   dSelector: Part alFunct on[Un versalNoun[_], Long],
  ser al zer: P pel neCursorSer al zer[UrtOrderedCursor] = UrtCursorSer al zer)
    extends UrtCursorBu lder[
      P pel neQuery w h HasP pel neCursor[UrtOrderedCursor]
    ] {
  overr de val cursorType: CursorType = TopCursor

  overr de def cursorValue(
    query: P pel neQuery w h HasP pel neCursor[UrtOrderedCursor],
    t  l neEntr es: Seq[T  l neEntry]
  ): Str ng = {
    val top d = t  l neEntr es.collectF rst( dSelector)

    val  d = top d.orElse(query.p pel neCursor.flatMap(_. d))

    val cursor = UrtOrderedCursor(
       n  alSort ndex = cursorSort ndex(query, t  l neEntr es) + TopCursorOffset,
       d =  d,
      cursorType = So (cursorType)
    )

    ser al zer.ser al zeCursor(cursor)
  }
}
