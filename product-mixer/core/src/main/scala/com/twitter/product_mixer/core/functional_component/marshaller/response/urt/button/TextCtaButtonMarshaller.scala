package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.button

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.TextCtaButton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class TextCtaButtonMarshaller @ nject() (
  urlMarshaller: UrlMarshaller) {

  def apply(textCtaButton: TextCtaButton): urt.TextCtaButton =
    urt.TextCtaButton(
      buttonText = textCtaButton.buttonText,
      url = urlMarshaller(textCtaButton.url)
    )
}
