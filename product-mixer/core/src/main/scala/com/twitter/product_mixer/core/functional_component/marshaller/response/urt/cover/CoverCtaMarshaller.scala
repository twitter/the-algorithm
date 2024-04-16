package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.button.ButtonStyleMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. con.Hor zon conMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.CallbackMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverCta
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CoverCtaMarshaller @ nject() (
  coverCtaBehav orMarshaller: CoverCtaBehav orMarshaller,
  callbackMarshaller: CallbackMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller,
  hor zon conMarshaller: Hor zon conMarshaller,
  buttonStyleMarshaller: ButtonStyleMarshaller) {

  def apply(coverCta: CoverCta): urt.CoverCta = urt.CoverCta(
    text = coverCta.text,
    ctaBehav or = coverCtaBehav orMarshaller(coverCta.ctaBehav or),
    callbacks = coverCta.callbacks.map(_.map(callbackMarshaller(_))),
    cl entEvent nfo = coverCta.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
     con = coverCta. con.map(hor zon conMarshaller(_)),
    buttonStyle = coverCta.buttonStyle.map(buttonStyleMarshaller(_))
  )
}
