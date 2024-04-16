package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.thread

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.thread._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Thread aderContentMarshaller @ nject() () {
  def apply(content: Thread aderContent): urt.Thread aderContent = content match {
    case UserThread ader(user d) =>
      urt.Thread aderContent.UserThread ader(urt.UserThread ader(user d))
  }
}
