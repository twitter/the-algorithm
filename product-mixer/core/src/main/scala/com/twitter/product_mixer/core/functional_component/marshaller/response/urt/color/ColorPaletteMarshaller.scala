package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.color

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.ColorPalette
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ColorPaletteMarshaller @ nject() (
  colorMarshaller: ColorMarshaller) {

  def apply(colorPalette: ColorPalette): urt.ColorPalette em = urt.ColorPalette em(
    rgb = colorMarshaller(colorPalette.rgb),
    percentage = colorPalette.percentage
  )
}
