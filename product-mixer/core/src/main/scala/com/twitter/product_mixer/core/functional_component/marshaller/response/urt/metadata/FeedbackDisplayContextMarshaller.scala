package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackD splayContext
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class FeedbackD splayContextMarshaller @ nject() () {

  def apply(d splayContext: FeedbackD splayContext): urt.FeedbackD splayContext =
    urt.FeedbackD splayContext(
      reason = d splayContext.reason
    )
}
