package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.color.RosettaColorMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Badge
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class BadgeMarshaller @ nject() (
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(badge: Badge): urt.Badge = urt.Badge(
    text = badge.text,
    textColorNa  = badge.textColorNa .map(rosettaColorMarshaller(_)),
    backgroundColorNa  = badge.backgroundColorNa .map(rosettaColorMarshaller(_))
  )
}
