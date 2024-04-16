package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CallbackMarshaller @ nject() () {

  def apply(callback: Callback): urt.Callback = urt.Callback(
    endpo nt = callback.endpo nt
  )
}
