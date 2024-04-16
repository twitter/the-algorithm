package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t le

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.button.CtaButtonMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.CallToAct onT leContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CallToAct onT leContentMarshaller @ nject() (
  ctaButtonMarshaller: CtaButtonMarshaller,
  r chTextMarshaller: R chTextMarshaller) {

  def apply(callToAct onT leContent: CallToAct onT leContent): urt.T leContentCallToAct on =
    urt.T leContentCallToAct on(
      text = callToAct onT leContent.text,
      r chText = callToAct onT leContent.r chText.map(r chTextMarshaller(_)),
      ctaButton = callToAct onT leContent.ctaButton.map(ctaButtonMarshaller(_))
    )
}
