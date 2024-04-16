package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.tombstone

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.D sconnectedRepl esAncestor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.D sconnectedRepl esDescendant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone. nl ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.NonCompl ant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.TombstoneD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.T etUnava lable
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class TombstoneD splayTypeMarshaller @ nject() () {

  def apply(tombstoneD splayType: TombstoneD splayType): urt.TombstoneD splayType =
    tombstoneD splayType match {
      case T etUnava lable => urt.TombstoneD splayType.T etUnava lable
      case D sconnectedRepl esAncestor => urt.TombstoneD splayType.D sconnectedRepl esAncestor
      case D sconnectedRepl esDescendant => urt.TombstoneD splayType.D sconnectedRepl esDescendant
      case  nl ne => urt.TombstoneD splayType. nl ne
      case NonCompl ant => urt.TombstoneD splayType.NonCompl ant
    }
}
