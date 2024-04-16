package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. d a

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a.AspectRat o
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class AspectRat oMarshaller @ nject() () {

  def apply(aspectRat o: AspectRat o): urt.AspectRat o = urt.AspectRat o(
    nu rator = aspectRat o.nu rator,
    denom nator = aspectRat o.denom nator
  )
}
