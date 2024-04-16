package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Gr dCarousel tadata
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Gr dCarousel tadataMarshaller @ nject() () {

  def apply(gr dCarousel tadata: Gr dCarousel tadata): urt.Gr dCarousel tadata =
    urt.Gr dCarousel tadata(numRows = gr dCarousel tadata.numRows)
}
