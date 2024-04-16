package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Pla n
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextFormat
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Strong
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class R chTextFormatMarshaller @ nject() () {

  def apply(format: R chTextFormat): urt.R chTextFormat = format match {
    case Pla n => urt.R chTextFormat.Pla n
    case Strong => urt.R chTextFormat.Strong
  }
}
