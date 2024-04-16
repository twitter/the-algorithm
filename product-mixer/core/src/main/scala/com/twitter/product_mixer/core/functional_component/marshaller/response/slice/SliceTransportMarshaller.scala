package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.sl ce

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.common. dent f er.TransportMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce
 mport com.tw ter.strato.graphql.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Sl ceTransportMarshaller @ nject() (sl ce emMarshaller: Sl ce emMarshaller)
    extends TransportMarshaller[Sl ce, t.Sl ceResult] {

  overr de val  dent f er: TransportMarshaller dent f er = TransportMarshaller dent f er("Sl ce")

  overr de def apply(sl ce: Sl ce): t.Sl ceResult = {
    t.Sl ceResult.Sl ce(
      t.Sl ce(
         ems = sl ce. ems.map(sl ce emMarshaller(_)),
        sl ce nfo = t.Sl ce nfo(
          prev ousCursor = sl ce.sl ce nfo.prev ousCursor,
          nextCursor = sl ce.sl ce nfo.nextCursor
        )
      ))
  }
}
