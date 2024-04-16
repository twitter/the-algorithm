package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.forward_p vot

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.color.RosettaColorMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.BadgeMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageVar antMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot.ForwardP vot
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ForwardP votMarshaller @ nject() (
  urlMarshaller: UrlMarshaller,
  r chTextMarshaller: R chTextMarshaller,
  forwardP votD splayTypeMarshaller: ForwardP votD splayTypeMarshaller,
  soft ntervent onD splayTypeMarshaller: Soft ntervent onD splayTypeMarshaller,
   mageVar antMarshaller:  mageVar antMarshaller,
  badgeMarshaller: BadgeMarshaller,
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(forwardP vot: ForwardP vot): urt.ForwardP vot = urt.ForwardP vot(
    text = r chTextMarshaller(forwardP vot.text),
    land ngUrl = urlMarshaller(forwardP vot.land ngUrl),
    d splayType = forwardP votD splayTypeMarshaller(forwardP vot.d splayType),
     con mageVar ant = forwardP vot. con mageVar ant.map( mageVar antMarshaller(_)),
    stateBadge = forwardP vot.stateBadge.map(badgeMarshaller(_)),
    subtext = forwardP vot.subtext.map(r chTextMarshaller(_)),
    backgroundColorNa  = forwardP vot.backgroundColorNa .map(rosettaColorMarshaller(_)),
    engage ntNudge = forwardP vot.engage ntNudge,
    soft ntervent onD splayType =
      forwardP vot.soft ntervent onD splayType.map(soft ntervent onD splayTypeMarshaller(_)),
  )
}
