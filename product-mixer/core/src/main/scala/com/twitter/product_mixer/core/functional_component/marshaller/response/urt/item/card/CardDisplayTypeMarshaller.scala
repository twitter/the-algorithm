package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.card

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.card._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class CardD splayTypeMarshaller @ nject() () {

  def apply(cardD splayType: CardD splayType): urt.CardD splayType = cardD splayType match {
    case  roD splayType => urt.CardD splayType. ro
    case CellD splayType => urt.CardD splayType.Cell
    case T etCardD splayType => urt.CardD splayType.T etCard
  }
}
