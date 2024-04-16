package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Preroll
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PrerollMarshaller @ nject() (
  dynam cPrerollTypeMarshaller: Dynam cPrerollTypeMarshaller,
   d a nfoMarshaller:  d a nfoMarshaller) {

  def apply(preroll: Preroll): urt.Preroll =
    urt.Preroll(
      preroll d = preroll.preroll d,
      dynam cPrerollType = preroll.dynam cPrerollType.map(dynam cPrerollTypeMarshaller(_)),
       d a nfo = preroll. d a nfo.map( d a nfoMarshaller(_))
    )
}
