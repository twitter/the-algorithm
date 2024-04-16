package com.tw ter.ho _m xer.marshaller.t  l nes

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Bas cTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.RecW hEducat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Recom ndat onTop cContextFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Top cContextFunct onal yType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

object Top cContextFunct onal yTypeUnmarshaller {

  def apply(
    top cContextFunct onal yType: urt.Top cContextFunct onal yType
  ): Top cContextFunct onal yType = top cContextFunct onal yType match {
    case urt.Top cContextFunct onal yType.Bas c => Bas cTop cContextFunct onal yType
    case urt.Top cContextFunct onal yType.Recom ndat on =>
      Recom ndat onTop cContextFunct onal yType
    case urt.Top cContextFunct onal yType.RecW hEducat on =>
      RecW hEducat onTop cContextFunct onal yType
    case urt.Top cContextFunct onal yType.EnumUnknownTop cContextFunct onal yType(f eld) =>
      throw new UnsupportedOperat onExcept on(s"Unknown top c context funct onal y type: $f eld")
  }
}
