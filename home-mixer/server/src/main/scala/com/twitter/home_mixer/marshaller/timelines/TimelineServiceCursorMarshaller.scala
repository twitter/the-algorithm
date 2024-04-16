package com.tw ter.ho _m xer.marshaller.t  l nes

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.BottomCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.GapCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.t  l neserv ce.{thr ftscala => t}

object T  l neServ ceCursorMarshaller {

  def apply(cursor: UrtOrderedCursor): Opt on[t.Cursor2] = {
    val  d = cursor. d.map(_.toStr ng)
    val gapBoundary d = cursor.gapBoundary d.map(_.toStr ng)
    cursor.cursorType match {
      case So (TopCursor) => So (t.Cursor2(bottom =  d))
      case So (BottomCursor) => So (t.Cursor2(top =  d))
      case So (GapCursor) => So (t.Cursor2(top =  d, bottom = gapBoundary d))
      case _ => None
    }
  }
}
