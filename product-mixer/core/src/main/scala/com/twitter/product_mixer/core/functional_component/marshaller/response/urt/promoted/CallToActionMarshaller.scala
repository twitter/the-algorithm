package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.CallToAct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject.S ngleton

@S ngleton
class CallToAct onMarshaller {
  def apply(callToAct on: CallToAct on): urt.CallToAct on = {
    urt.CallToAct on(
      callToAct onType = callToAct on.callToAct onType,
      url = callToAct on.url
    )
  }
}
