package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Top cContext
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Soc alContextMarshaller @ nject() (
  generalContextMarshaller: GeneralContextMarshaller,
  top cContextMarshaller: Top cContextMarshaller) {

  def apply(soc alContext: Soc alContext): urt.Soc alContext =
    soc alContext match {
      case generalContextBanner: GeneralContext =>
        generalContextMarshaller(generalContextBanner)
      case top cContextBanner: Top cContext =>
        top cContextMarshaller(top cContextBanner)
    }
}
