package com.tw ter.ho _m xer.marshaller.t  l nes

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.BottomCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.GapCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => t}

object Chronolog calCursorMarshaller {

  def apply(cursor: UrtOrderedCursor): Opt on[t.Chronolog calCursor] = {
    cursor.cursorType match {
      case So (TopCursor) => So (t.Chronolog calCursor(bottom = cursor. d))
      case So (BottomCursor) => So (t.Chronolog calCursor(top = cursor. d))
      case So (GapCursor) =>
        So (t.Chronolog calCursor(top = cursor. d, bottom = cursor.gapBoundary d))
      case _ => None
    }
  }
}
