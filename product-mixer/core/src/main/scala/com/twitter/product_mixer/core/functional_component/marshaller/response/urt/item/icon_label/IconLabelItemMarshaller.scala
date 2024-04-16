package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. con_label

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. con.Hor zon conMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. con_label. conLabel em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  conLabel emMarshaller @ nject() (
  r chTextMarshaller: R chTextMarshaller,
  hor zon conMarshaller: Hor zon conMarshaller) {

  def apply( conLabel em:  conLabel em): urt.T  l ne emContent =
    urt.T  l ne emContent. conLabel(
      urt. conLabel(
        text = r chTextMarshaller( conLabel em.text),
         con =  conLabel em. con.map(hor zon conMarshaller(_))
      )
    )
}
