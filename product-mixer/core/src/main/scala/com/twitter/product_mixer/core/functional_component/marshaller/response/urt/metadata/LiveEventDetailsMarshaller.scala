package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.L veEventDeta ls
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class L veEventDeta lsMarshaller @ nject() () {

  def apply(l veEventDeta ls: L veEventDeta ls): urt.L veEventDeta ls = urt.L veEventDeta ls(
    event d = l veEventDeta ls.event d
  )
}
