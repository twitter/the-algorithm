package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t le

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.CallToAct onT leContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.StandardT leContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.T leContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T leContentMarshaller @ nject() (
  standardT leContentMarshaller: StandardT leContentMarshaller,
  callToAct onT leContentMarshaller: CallToAct onT leContentMarshaller) {

  def apply(t leContent: T leContent): urt.T leContent = t leContent match {
    case t leCont: StandardT leContent =>
      urt.T leContent.Standard(standardT leContentMarshaller(t leCont))
    case t leCont: CallToAct onT leContent =>
      urt.T leContent.CallToAct on(callToAct onT leContentMarshaller(t leCont))
  }
}
