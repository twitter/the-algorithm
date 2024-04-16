package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.MarkEntr esUnread nstruct on
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class MarkEntr esUnread nstruct onMarshaller @ nject() () {

  def apply( nstruct on: MarkEntr esUnread nstruct on): urt.MarkEntr esUnread =
    urt.MarkEntr esUnread(entry ds =  nstruct on.entry ds)
}
