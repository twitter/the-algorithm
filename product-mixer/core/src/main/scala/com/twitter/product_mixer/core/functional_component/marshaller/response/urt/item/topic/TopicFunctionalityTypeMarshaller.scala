package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.top c

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Bas cTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.P votTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Recom ndat onTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cFunct onal yType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cFunct onal yTypeMarshaller @ nject() () {

  def apply(top cFunct onal yType: Top cFunct onal yType): urt.Top cFunct onal yType =
    top cFunct onal yType match {
      case Bas cTop cFunct onal yType => urt.Top cFunct onal yType.Bas c
      case Recom ndat onTop cFunct onal yType => urt.Top cFunct onal yType.Recom ndat on
      case P votTop cFunct onal yType => urt.Top cFunct onal yType.P vot
    }
}
