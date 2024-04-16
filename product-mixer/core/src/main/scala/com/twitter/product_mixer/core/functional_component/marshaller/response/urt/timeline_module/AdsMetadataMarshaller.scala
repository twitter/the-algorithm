package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Ads tadata
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ads tadataMarshaller @ nject() () {

  def apply(ads tadata: Ads tadata): urt.Ads tadata =
    urt.Ads tadata(carousel d = ads tadata.carousel d)
}
