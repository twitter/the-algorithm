package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleFooter
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class ModuleFooterMarshaller @ nject() (urlMarshaller: UrlMarshaller) {

  def apply(footer: ModuleFooter): urt.ModuleFooter = urt.ModuleFooter(
    text = footer.text,
    land ngUrl = footer.land ngUrl.map(urlMarshaller(_))
  )
}
