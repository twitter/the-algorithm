package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.operat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CursorOperat onMarshaller @ nject() (
  cursorTypeMarshaller: CursorTypeMarshaller,
  cursorD splayTreat ntMarshaller: CursorD splayTreat ntMarshaller) {

  def apply(cursorOperat on: CursorOperat on): urt.T  l neOperat on.Cursor =
    urt.T  l neOperat on.Cursor(
      urt.T  l neCursor(
        value = cursorOperat on.value,
        cursorType = cursorTypeMarshaller(cursorOperat on.cursorType),
        d splayTreat nt = cursorOperat on.d splayTreat nt.map(cursorD splayTreat ntMarshaller(_))
      )
    )
}
