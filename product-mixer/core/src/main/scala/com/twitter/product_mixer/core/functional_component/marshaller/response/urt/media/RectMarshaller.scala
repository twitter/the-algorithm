package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. d a

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a.Rect
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class RectMarshaller @ nject() () {

  def apply(rect: Rect): urt.Rect = urt.Rect(
    left = rect.left,
    top = rect.top,
    w dth = rect.w dth,
      ght = rect.  ght
  )
}
