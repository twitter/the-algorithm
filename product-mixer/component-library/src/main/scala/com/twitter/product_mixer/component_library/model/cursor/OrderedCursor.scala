package com.tw ter.product_m xer.component_l brary.model.cursor

 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.{
  CursorType => UrtCursorType
}

/**
 * Cursor model that may be used w n cursor ng over an ordered cand date s ce.
 *
 * @param  n  alSort ndex See [[UrtP pel neCursor]]
 * @param  d represents t   D of t  ele nt, typ cally t  top ele nt for a top cursor or t 
 *           bottom ele nt for a bottom cursor,  n an ordered cand date l st
 * @param gapBoundary d represents t   D of t  gap boundary ele nt, wh ch  n gap cursors  s t 
 *                      oppos e bound of t  gap to be f lled w h t  cursor
 */
case class UrtOrderedCursor(
  overr de val  n  alSort ndex: Long,
   d: Opt on[Long],
  cursorType: Opt on[UrtCursorType],
  gapBoundary d: Opt on[Long] = None)
    extends UrtP pel neCursor

case class OrderedCursor(
   d: Opt on[Long],
  cursorType: Opt on[CursorType],
  gapBoundary d: Opt on[Long] = None)
    extends P pel neCursor
