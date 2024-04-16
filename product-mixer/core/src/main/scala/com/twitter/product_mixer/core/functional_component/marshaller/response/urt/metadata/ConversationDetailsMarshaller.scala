package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Conversat onDeta ls
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Conversat onDeta lsMarshaller @ nject() (sect onMarshaller: Conversat onSect onMarshaller) {

  def apply(conversat onDeta ls: Conversat onDeta ls): urt.Conversat onDeta ls =
    urt.Conversat onDeta ls(
      conversat onSect on = conversat onDeta ls.conversat onSect on.map(sect onMarshaller(_))
    )
}
