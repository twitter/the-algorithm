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
 * Updates an [[OrderedCursor]]  n t  Next pos  on
 *
 * @param  dSelector Spec f es t  entry from wh ch to der ve t  ` d` f eld
 * @param  ncludeOperat on Spec f es w t r to  nclude t  bu lder operat on  n t  response
 * @param ser al zer Converts t  cursor to an encoded str ng
 */
case class OrderedNextCursorUpdater[Query <: P pel neQuery w h HasP pel neCursor[OrderedCursor]](
   dSelector: Part alFunct on[Sl ce em, Long],
  overr de val  ncludeOperat on: Should nclude[Query] = Always nclude,
  ser al zer: P pel neCursorSer al zer[OrderedCursor] = CursorSer al zer)
    extends Sl ceCursorUpdaterFromUnderly ngBu lder[Query] {
  overr de val cursorType: CursorType = NextCursor

  overr de val underly ng: OrderedNextCursorBu lder[Query] =
    OrderedNextCursorBu lder( dSelector,  ncludeOperat on, ser al zer)
}
