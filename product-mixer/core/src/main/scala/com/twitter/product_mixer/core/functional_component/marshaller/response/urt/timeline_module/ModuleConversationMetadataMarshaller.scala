package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Soc alContextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleConversat on tadata
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ModuleConversat on tadataMarshaller @ nject() (
  soc alContextMarshaller: Soc alContextMarshaller) {

  def apply(
    moduleConversat on tadata: ModuleConversat on tadata
  ): urt.ModuleConversat on tadata = urt.ModuleConversat on tadata(
    allT et ds = moduleConversat on tadata.allT et ds,
    soc alContext = moduleConversat on tadata.soc alContext.map(soc alContextMarshaller(_)),
    enableDedupl cat on = moduleConversat on tadata.enableDedupl cat on
  )
}
