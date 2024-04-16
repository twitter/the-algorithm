package com.tw ter.product_m xer.core.p pel ne

/**
 * P pel neCursor represents any product-spec f c cursor model. Typ cally t  P pel neCursor w ll be
 * a de-ser al zed base 64 thr ft struct from  n  al request.
 */
tra  P pel neCursor

/**
 * HasP pel neCursor  nd cates that a [[P pel neQuery]] has a cursor
 */
tra  HasP pel neCursor[+Cursor <: P pel neCursor] {
  def p pel neCursor: Opt on[Cursor]

  /**
   *  f t  cursor  s not present, t  typ cally  ans that   are on t  f rst page
   */
  def  sF rstPage: Boolean = p pel neCursor. sEmpty
}

/**
 * UrtP pel neCursor represents a URT product-spec f c cursor model. Typ cally t  UrtP pel neCursor
 * w ll be a de-ser al zed base 64 thr ft struct from  n  al request.
 */
tra  UrtP pel neCursor extends P pel neCursor {

  /** See [[UrtCursorBu lder]] for background on bu ld ng  n  alSort ndex */
  def  n  alSort ndex: Long
}

object UrtP pel neCursor {
  def getCursor n  alSort ndex(query: P pel neQuery w h HasP pel neCursor[_]): Opt on[Long] = {
    query.p pel neCursor match {
      case So (cursor: UrtP pel neCursor) => So (cursor. n  alSort ndex)
      case _ => None
    }
  }
}
