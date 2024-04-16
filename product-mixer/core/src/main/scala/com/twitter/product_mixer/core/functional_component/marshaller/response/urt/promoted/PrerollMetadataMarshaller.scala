package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Preroll tadata
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Preroll tadataMarshaller @ nject() (
  prerollMarshaller: PrerollMarshaller) {
  def apply(preroll tadata: Preroll tadata): urt.Preroll tadata =
    urt.Preroll tadata(
      preroll = preroll tadata.preroll.map(prerollMarshaller(_)),
      v deoAnalyt csScr bePassthrough = preroll tadata.v deoAnalyt csScr bePassthrough
    )
}
