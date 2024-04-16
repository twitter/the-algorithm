package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. d a

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a. d a
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  d aMarshaller @ nject() (
   d aEnt yMarshaller:  d aEnt yMarshaller,
   d aKeyMarshaller:  d aKeyMarshaller,
  rectMarshaller: RectMarshaller,
  aspectRat oMarshaller: AspectRat oMarshaller) {

  def apply( d a:  d a): urt. d a = urt. d a(
     d aEnt y =  d a. d aEnt y.map( d aEnt yMarshaller(_)),
     d aKey =  d a. d aKey.map( d aKeyMarshaller(_)),
     magePoss bleCropp ng =  d a. magePoss bleCropp ng.map { rects =>
      rects.map(rectMarshaller(_))
    },
    aspectRat o =  d a.aspectRat o.map(aspectRat oMarshaller(_))
  )
}
