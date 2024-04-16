package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.color.ColorPaletteMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageVar ant
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  mageVar antMarshaller @ nject() (
  colorPaletteMarshaller: ColorPaletteMarshaller) {

  def apply( mageVar ant:  mageVar ant): urt. mageVar ant = urt. mageVar ant(
    url =  mageVar ant.url,
    w dth =  mageVar ant.w dth,
      ght =  mageVar ant.  ght,
    palette =  mageVar ant.palette.map { paletteL st => paletteL st.map(colorPaletteMarshaller(_)) }
  )
}
