package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageTextAct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  ssageTextAct onMarshaller @ nject() (
   ssageAct onMarshaller:  ssageAct onMarshaller) {

  def apply( ssageTextAct on:  ssageTextAct on): urt. ssageTextAct on =
    urt. ssageTextAct on(
      text =  ssageTextAct on.text,
      act on =  ssageAct onMarshaller( ssageTextAct on.act on)
    )
}
