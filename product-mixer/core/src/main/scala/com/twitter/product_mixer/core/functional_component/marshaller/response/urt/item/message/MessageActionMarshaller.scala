package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.CallbackMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageAct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  ssageAct onMarshaller @ nject() (
  callbackMarshaller: CallbackMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller) {

  def apply( ssageAct on:  ssageAct on): urt. ssageAct on = {

    urt. ssageAct on(
      d sm ssOnCl ck =  ssageAct on.d sm ssOnCl ck,
      url =  ssageAct on.url,
      cl entEvent nfo =  ssageAct on.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
      onCl ckCallbacks =
         ssageAct on.onCl ckCallbacks.map(callbackL st => callbackL st.map(callbackMarshaller(_)))
    )
  }
}
