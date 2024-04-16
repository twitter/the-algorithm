package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.operat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorD splayTreat nt
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CursorD splayTreat ntMarshaller @ nject() () {

  def apply(treat nt: CursorD splayTreat nt): urt.CursorD splayTreat nt =
    urt.CursorD splayTreat nt(
      act onText = treat nt.act onText,
      labelText = treat nt.labelText
    )
}
