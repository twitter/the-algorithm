package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.forward_p vot

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.Commun yNotes
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.ForwardP votD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.L veEvent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.Soft ntervent on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ForwardP votD splayTypeMarshaller @ nject() () {

  def apply(forwardP votD splayType: ForwardP votD splayType): urt.ForwardP votD splayType =
    forwardP votD splayType match {
      case L veEvent => urt.ForwardP votD splayType.L veEvent
      case Soft ntervent on => urt.ForwardP votD splayType.Soft ntervent on
      case Commun yNotes => urt.ForwardP votD splayType.Commun yNotes
    }
}
