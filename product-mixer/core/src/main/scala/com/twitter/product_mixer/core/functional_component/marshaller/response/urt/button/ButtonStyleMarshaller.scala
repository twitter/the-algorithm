package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.button

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.ButtonStyle
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.Default
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.Pr mary
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.Secondary
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.Text
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.Destruct ve
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.Neutral
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.Destruct veSecondary
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.Destruct veText
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ButtonStyleMarshaller @ nject() () {
  def apply(buttonStyle: ButtonStyle): urt.ButtonStyle =
    buttonStyle match {
      case Default => urt.ButtonStyle.Default
      case Pr mary => urt.ButtonStyle.Pr mary
      case Secondary => urt.ButtonStyle.Secondary
      case Text => urt.ButtonStyle.Text
      case Destruct ve => urt.ButtonStyle.Destruct ve
      case Neutral => urt.ButtonStyle.Neutral
      case Destruct veSecondary => urt.ButtonStyle.Destruct veSecondary
      case Destruct veText => urt.ButtonStyle.Destruct veText
    }
}
