package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.D sm ss nfo
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class D sm ss nfoMarshaller @ nject() (callbackMarshaller: CallbackMarshaller) {

  def apply(d sm ss nfo: D sm ss nfo): urt.D sm ss nfo =
    urt.D sm ss nfo(d sm ss nfo.callbacks.map(_.map(callbackMarshaller(_))))
}
