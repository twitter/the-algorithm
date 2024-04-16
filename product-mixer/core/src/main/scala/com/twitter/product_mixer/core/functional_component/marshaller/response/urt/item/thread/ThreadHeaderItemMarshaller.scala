package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.thread

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.thread.Thread ader em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Thread ader emMarshaller @ nject() (
  thread aderContentMarshaller: Thread aderContentMarshaller) {

  def apply(thread ader em: Thread ader em): urt.T  l ne emContent.Thread ader =
    urt.T  l ne emContent.Thread ader(
      urt.Thread ader em(
        content = thread aderContentMarshaller(thread ader em.content)
      )
    )
}
