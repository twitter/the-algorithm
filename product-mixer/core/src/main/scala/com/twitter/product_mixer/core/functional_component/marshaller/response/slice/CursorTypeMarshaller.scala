package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.sl ce

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.product_m xer.component_l brary.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.NextCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Prev ousCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.GapCursor

@S ngleton
class CursorTypeMarshaller @ nject() () {

  def apply(cursorType: CursorType): t.CursorType = cursorType match {
    case NextCursor => t.CursorType.Next
    case Prev ousCursor => t.CursorType.Prev ous
    case GapCursor => t.CursorType.Gap
  }

  def unmarshall(cursorType: t.CursorType): CursorType = cursorType match {
    case t.CursorType.Next => NextCursor
    case t.CursorType.Prev ous => Prev ousCursor
    case t.CursorType.Gap => GapCursor
    case t.CursorType.EnumUnknownCursorType( d) =>
      throw new UnsupportedOperat onExcept on(
        s"Attempted to unmarshall unrecogn zed cursor type: $ d")
  }

}
