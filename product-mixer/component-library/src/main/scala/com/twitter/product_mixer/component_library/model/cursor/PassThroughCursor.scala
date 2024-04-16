package com.tw ter.product_m xer.component_l brary.model.cursor

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.CursorType
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.{
  CursorType => UrtCursorType
}

case object Prev ousCursorFeature
    extends Feature[P pel neQuery w h HasP pel neCursor[UrtPassThroughCursor], Str ng]

case object NextCursorFeature
    extends Feature[P pel neQuery w h HasP pel neCursor[UrtPassThroughCursor], Str ng]

/**
 * Cursor model that may be used w n   want to pass through t  cursor value from and back to
 * a downstream as- s.
 *
 * @param  n  alSort ndex See [[UrtP pel neCursor]]
 * @param cursorValue t  pass through cursor
 */
case class UrtPassThroughCursor(
  overr de val  n  alSort ndex: Long,
  cursorValue: Str ng,
  cursorType: Opt on[UrtCursorType] = None)
    extends UrtP pel neCursor

case class PassThroughCursor(
  cursorValue: Str ng,
  cursorType: Opt on[CursorType] = None)
    extends P pel neCursor
