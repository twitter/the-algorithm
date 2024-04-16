package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddEntr esT  l ne nstruct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AddEntr es nstruct onMarshaller @ nject() (
  t  l neEntryMarshaller: T  l neEntryMarshaller) {

  def apply( nstruct on: AddEntr esT  l ne nstruct on): urt.AddEntr es = urt.AddEntr es(
    entr es =  nstruct on.entr es.map(t  l neEntryMarshaller(_))
  )
}
