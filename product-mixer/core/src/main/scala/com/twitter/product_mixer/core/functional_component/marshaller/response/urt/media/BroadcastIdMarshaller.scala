package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. d a

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a.Broadcast d
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Broadcast dMarshaller @ nject() () {

  def apply(broadcast d: Broadcast d): urt.Broadcast d = urt.Broadcast d(
     d = broadcast d. d
  )
}
