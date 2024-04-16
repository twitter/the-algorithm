package com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.OrderedCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.CursorSer al zer
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.NextCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce em
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Bu lds [[OrderedCursor]]  n t  Next pos  on
 *
 * @param  dSelector Spec f es t  entry from wh ch to der ve t  ` d` f eld
 * @param  ncludeOperat on Spec f es w t r to  nclude t  bu lder operat on  n t  response
 * @param ser al zer Converts t  cursor to an encoded str ng
 */
case class OrderedNextCursorBu lder[Query <: P pel neQuery w h HasP pel neCursor[OrderedCursor]](
   dSelector: Part alFunct on[Sl ce em, Long],
  overr de val  ncludeOperat on: Should nclude[Query] = Always nclude,
  ser al zer: P pel neCursorSer al zer[OrderedCursor] = CursorSer al zer)
    extends Sl ceCursorBu lder[Query] {
  overr de val cursorType: CursorType = NextCursor

  overr de def cursorValue(
    query: Query,
    entr es: Seq[Sl ce em]
  ): Str ng = {
    val bottom d = entr es.reverse erator.collectF rst( dSelector)

    val  d = bottom d.orElse(query.p pel neCursor.flatMap(_. d))

    val cursor = OrderedCursor( d =  d, cursorType = So (cursorType))

    ser al zer.ser al zeCursor(cursor)
  }
}
