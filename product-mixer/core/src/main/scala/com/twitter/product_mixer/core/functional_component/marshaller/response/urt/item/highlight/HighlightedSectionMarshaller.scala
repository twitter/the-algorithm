package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.h ghl ght

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.h ghl ght.H ghl ghtedSect on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class H ghl ghtedSect onMarshaller @ nject() () {

  def apply(h ghl ghtedSect on: H ghl ghtedSect on): urt.H ghl ghtedSect on =
    urt.H ghl ghtedSect on(
      start ndex = h ghl ghtedSect on.start ndex,
      end ndex = h ghl ghtedSect on.end ndex
    )
}
