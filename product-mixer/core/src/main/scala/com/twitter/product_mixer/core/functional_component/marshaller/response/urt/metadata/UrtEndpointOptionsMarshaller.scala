package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.UrtEndpo ntOpt ons
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UrtEndpo ntOpt onsMarshaller @ nject() () {

  def apply(urtEndpo ntOpt ons: UrtEndpo ntOpt ons): urt.UrtEndpo ntOpt ons =
    urt.UrtEndpo ntOpt ons(
      requestParams = urtEndpo ntOpt ons.requestParams,
      t le = urtEndpo ntOpt ons.t le,
      cac  d = urtEndpo ntOpt ons.cac  d,
      subt le = urtEndpo ntOpt ons.subt le
    )
}
