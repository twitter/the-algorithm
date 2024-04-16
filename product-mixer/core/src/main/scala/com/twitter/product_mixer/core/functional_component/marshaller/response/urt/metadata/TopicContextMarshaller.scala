package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Bas cTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Top cContext
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cContextMarshaller @ nject() () {

  def apply(top cContext: Top cContext): urt.Soc alContext = {
    urt.Soc alContext.Top cContext(
      urt.Top cContext(
        top c d = top cContext.top c d,
        funct onal yType = Top cContextFunct onal yTypeMarshaller(
          top cContext.funct onal yType.getOrElse(Bas cTop cContextFunct onal yType))
      )
    )
  }
}
