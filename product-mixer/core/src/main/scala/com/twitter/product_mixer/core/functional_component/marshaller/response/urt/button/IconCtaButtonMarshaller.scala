package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.button

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. con.Hor zon conMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button. conCtaButton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  conCtaButtonMarshaller @ nject() (
  hor zon conMarshaller: Hor zon conMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply( conCtaButton:  conCtaButton): urt. conCtaButton =
    urt. conCtaButton(
      button con = hor zon conMarshaller( conCtaButton.button con),
      access b l yLabel =  conCtaButton.access b l yLabel,
      url = urlMarshaller( conCtaButton.url)
    )
}
