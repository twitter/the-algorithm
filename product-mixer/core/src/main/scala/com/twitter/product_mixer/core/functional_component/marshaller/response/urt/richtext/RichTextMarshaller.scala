package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class R chTextMarshaller @ nject() (
  r chTextEnt yMarshaller: R chTextEnt yMarshaller,
  r chTextAl gn ntMarshaller: R chTextAl gn ntMarshaller) {

  def apply(r chText: R chText): urt.R chText = urt.R chText(
    text = r chText.text,
    ent  es = r chText.ent  es.map(r chTextEnt yMarshaller(_)),
    rtl = r chText.rtl,
    al gn nt = r chText.al gn nt.map(r chTextAl gn ntMarshaller(_))
  )
}
