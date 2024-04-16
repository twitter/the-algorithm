package com.tw ter.product_m xer.component_l brary.model.cursor

 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor

/**
 * Cursor model that may be used w n   just need a placeholder but no real cursor value. S nce URT
 * requ res that top and bottom cursors are always present, placeholders are often used w n up
 * scroll ng (PTR)  s not supported on a t  l ne. Wh le placeholder cursors generally should not be
 * subm ted back by t  cl ent, t y so t  s are l ke  n t  case of cl ent-s de background
 * auto-refresh.  f subm ted, t  backend w ll treat any request w h a placeholder cursor l ke no
 * cursor was subm ted, wh ch w ll behave t  sa  way as an  n  al page load.
 */
case class UrtPlaceholderCursor() extends UrtP pel neCursor {
  // T  value  s unused,  n that    s not ser al zed  nto t  f nal cursor value
  overr de def  n  alSort ndex: Long = throw new UnsupportedOperat onExcept on(
    " n  alSort ndex  s not def ned for placeholder cursors")
}
