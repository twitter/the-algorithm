package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextEnt y
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class R chTextEnt yMarshaller @ nject() (
  referenceObjectMarshaller: ReferenceObjectMarshaller,
  r chTextFormatMarshaller: R chTextFormatMarshaller) {

  def apply(ent y: R chTextEnt y): urt.R chTextEnt y = urt.R chTextEnt y(
    from ndex = ent y.from ndex,
    to ndex = ent y.to ndex,
    ref = ent y.ref.map(referenceObjectMarshaller(_)),
    format = ent y.format.map(r chTextFormatMarshaller(_))
  )
}
