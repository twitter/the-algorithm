package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Feedback nfoMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l ne emMarshaller @ nject() (
  t  l ne emContentMarshaller: T  l ne emContentMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller,
  feedback nfoMarshaller: Feedback nfoMarshaller) {

  def apply( em: T  l ne em): urt.T  l ne em = urt.T  l ne em(
    content = t  l ne emContentMarshaller( em),
    cl entEvent nfo =  em.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
    feedback nfo =  em.feedbackAct on nfo.map(feedback nfoMarshaller(_)),
    prompt = None
  )
}
