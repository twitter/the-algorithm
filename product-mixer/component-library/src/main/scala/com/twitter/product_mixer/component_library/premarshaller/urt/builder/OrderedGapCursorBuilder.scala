package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.GapCursor
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Bu lds [[UrtOrderedCursor]]  n t  Bottom pos  on as a Gap cursor.
 *
 * @param  dSelector Spec f es t  entry from wh ch to der ve t  ` d` f eld
 * @param  ncludeOperat on Log c to determ ne w t r or not to bu ld t  gap cursor, wh ch should
 *                         always be t   nverse of t  log c used to dec de w t r or not to bu ld
 *                         t  bottom cursor v a [[OrderedBottomCursorBu lder]], s nce e  r t 
 *                         gap or t  bottom cursor must always be returned.
 * @param ser al zer Converts t  cursor to an encoded str ng
 */
case class OrderedGapCursorBu lder[
  -Query <: P pel neQuery w h HasP pel neCursor[UrtOrderedCursor]
](
   dSelector: Part alFunct on[T  l neEntry, Long],
  overr de val  ncludeOperat on:  nclude nstruct on[Query],
  ser al zer: P pel neCursorSer al zer[UrtOrderedCursor] = UrtCursorSer al zer)
    extends UrtCursorBu lder[Query] {
  overr de val cursorType: CursorType = GapCursor

  overr de def cursorValue(
    query: Query,
    t  l neEntr es: Seq[T  l neEntry]
  ): Str ng = {
    // To determ ne t  gap boundary, use any ex st ng cursor gap boundary  d ( .e.  f subm ted
    // from a prev ous gap cursor, else use t  ex st ng cursor  d ( .e. from a prev ous top cursor)
    val gapBoundary d = query.p pel neCursor.flatMap(_.gapBoundary d).orElse {
      query.p pel neCursor.flatMap(_. d)
    }

    val bottom d = t  l neEntr es.reverse erator.collectF rst( dSelector)

    val  d = bottom d.orElse(gapBoundary d)

    val cursor = UrtOrderedCursor(
       n  alSort ndex = nextBottom n  alSort ndex(query, t  l neEntr es),
       d =  d,
      cursorType = So (cursorType),
      gapBoundary d = gapBoundary d
    )

    ser al zer.ser al zeCursor(cursor)
  }
}
