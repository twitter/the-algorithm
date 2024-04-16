package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t et

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.h ghl ght.H ghl ghtedSect onMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T etH ghl ghts
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etH ghl ghtsMarshaller @ nject() (
  h ghl ghtedSect onMarshaller: H ghl ghtedSect onMarshaller) {

  def apply(t etH ghl ghts: T etH ghl ghts): urt.T etH ghl ghts =
    urt.T etH ghl ghts(
      textH ghl ghts = t etH ghl ghts.textH ghl ghts
        .map(_.map(h ghl ghtedSect onMarshaller(_))),
      cardT leH ghl ghts = t etH ghl ghts.cardT leH ghl ghts
        .map(_.map(h ghl ghtedSect onMarshaller(_))),
      cardDescr pt onH ghl ghts = t etH ghl ghts.cardDescr pt onH ghl ghts
        .map(_.map(h ghl ghtedSect onMarshaller(_)))
    )
}
