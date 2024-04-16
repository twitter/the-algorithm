package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.P nEntryT  l ne nstruct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class P nEntry nstruct onMarshaller @ nject() (
  t  l neEntryMarshaller: T  l neEntryMarshaller) {

  def apply( nstruct on: P nEntryT  l ne nstruct on): urt.P nEntry = {
    urt.P nEntry(entry = t  l neEntryMarshaller( nstruct on.entry))
  }
}
