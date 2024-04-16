package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.button

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.CtaButton
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button. conCtaButton
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.TextCtaButton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CtaButtonMarshaller @ nject() (
   conCtaButtonMarshaller:  conCtaButtonMarshaller,
  textCtaButtonMarshaller: TextCtaButtonMarshaller) {

  def apply(ctaButton: CtaButton): urt.CtaButton = ctaButton match {
    case button: TextCtaButton => urt.CtaButton.Text(textCtaButtonMarshaller(button))
    case button:  conCtaButton => urt.CtaButton. con( conCtaButtonMarshaller(button))
  }
}
