package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.operat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.Cursor em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cursor emMarshaller @ nject() (
  cursorTypeMarshaller: CursorTypeMarshaller,
  cursorD splayTreat ntMarshaller: CursorD splayTreat ntMarshaller) {

  def apply(cursor em: Cursor em): urt.T  l ne emContent.T  l neCursor =
    urt.T  l ne emContent.T  l neCursor(
      urt.T  l neCursor(
        value = cursor em.value,
        cursorType = cursorTypeMarshaller(cursor em.cursorType),
        d splayTreat nt = cursor em.d splayTreat nt.map(cursorD splayTreat ntMarshaller(_))
      )
    )
}
