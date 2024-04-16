package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Abus veQual y
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Conversat onSect on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.H ghQual y
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.LowQual y
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.RelatedT et
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Conversat onSect onMarshaller @ nject() () {

  def apply(sect on: Conversat onSect on): urt.Conversat onSect on = sect on match {
    case H ghQual y => urt.Conversat onSect on.H ghQual y
    case LowQual y => urt.Conversat onSect on.LowQual y
    case Abus veQual y => urt.Conversat onSect on.Abus veQual y
    case RelatedT et => urt.Conversat onSect on.RelatedT et
  }
}
