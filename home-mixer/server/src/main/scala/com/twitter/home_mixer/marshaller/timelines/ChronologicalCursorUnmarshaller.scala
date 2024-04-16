package com.tw ter.ho _m xer.marshaller.t  l nes

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.BottomCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.GapCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => t}

object Chronolog calCursorUnmarshaller {

  def apply(requestCursor: t.RequestCursor): Opt on[UrtOrderedCursor] = {
    requestCursor match {
      case t.RequestCursor.Chronolog calCursor(cursor) =>
        (cursor.top, cursor.bottom) match {
          case (So (top), None) =>
            So (UrtOrderedCursor(top, cursor.top, So (BottomCursor)))
          case (None, So (bottom)) =>
            So (UrtOrderedCursor(bottom, cursor.bottom, So (TopCursor)))
          case (So (top), So (bottom)) =>
            So (UrtOrderedCursor(top, cursor.top, So (GapCursor), cursor.bottom))
          case _ => None
        }
      case _ => None
    }
  }
}
