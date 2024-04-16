package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.forward_p vot

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.GetT Latest
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.Govern ntRequested
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.M slead ng
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.Soft ntervent onD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.Stay nfor d
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Soft ntervent onD splayTypeMarshaller @ nject() () {

  def apply(
    soft ntervent onD splayType: Soft ntervent onD splayType
  ): urt.Soft ntervent onD splayType =
    soft ntervent onD splayType match {
      case GetT Latest => urt.Soft ntervent onD splayType.GetT Latest
      case Stay nfor d => urt.Soft ntervent onD splayType.Stay nfor d
      case M slead ng => urt.Soft ntervent onD splayType.M slead ng
      case Govern ntRequested => urt.Soft ntervent onD splayType.Govern ntRequested
    }
}
