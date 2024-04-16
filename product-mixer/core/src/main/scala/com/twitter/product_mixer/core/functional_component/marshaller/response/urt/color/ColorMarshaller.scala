package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.color

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.Color
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject.S ngleton

@S ngleton
class ColorMarshaller {

  def apply(color: Color): urt.Color = urt.Color(
    red = color.red,
    green = color.green,
    blue = color.blue,
    opac y = color.opac y
  )
}
