package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Bas cTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.RecW hEducat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Recom ndat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Top cContextFunct onal yType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

object Top cContextFunct onal yTypeMarshaller {

  def apply(
    top cContextFunct onal yType: Top cContextFunct onal yType
  ): urt.Top cContextFunct onal yType = top cContextFunct onal yType match {
    case Bas cTop cContextFunct onal yType => urt.Top cContextFunct onal yType.Bas c
    case Recom ndat onTop cContextFunct onal yType =>
      urt.Top cContextFunct onal yType.Recom ndat on
    case RecW hEducat onTop cContextFunct onal yType =>
      urt.Top cContextFunct onal yType.RecW hEducat on
  }
}
