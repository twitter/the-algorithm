package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.tombstone

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t et.T et emMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.Tombstone em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Tombstone emMarshaller @ nject() (
  d splayTypeMarshaller: TombstoneD splayTypeMarshaller,
  tombstone nfoMarshaller: Tombstone nfoMarshaller,
  t et emMarshaller: T et emMarshaller) {

  def apply(tombstone em: Tombstone em): urt.T  l ne emContent =
    urt.T  l ne emContent.Tombstone(
      urt.Tombstone(
        d splayType = d splayTypeMarshaller(tombstone em.tombstoneD splayType),
        tombstone nfo = tombstone em.tombstone nfo.map(tombstone nfoMarshaller(_)),
        t et = tombstone em.t et.map(t et emMarshaller(_).t et)
      )
    )
}
