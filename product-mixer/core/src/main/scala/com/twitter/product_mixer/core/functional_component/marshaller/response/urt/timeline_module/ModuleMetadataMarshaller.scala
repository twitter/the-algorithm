package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module tadata
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Module tadataMarshaller @ nject() (
  ads tadataMarshaller: Ads tadataMarshaller,
  moduleConversat on tadataMarshaller: ModuleConversat on tadataMarshaller,
  gr dCarousel tadataMarshaller: Gr dCarousel tadataMarshaller) {

  def apply(module tadata: Module tadata): urt.Module tadata = urt.Module tadata(
    ads tadata = module tadata.ads tadata.map(ads tadataMarshaller(_)),
    conversat on tadata =
      module tadata.conversat on tadata.map(moduleConversat on tadataMarshaller(_)),
    gr dCarousel tadata =
      module tadata.gr dCarousel tadata.map(gr dCarousel tadataMarshaller(_))
  )
}
