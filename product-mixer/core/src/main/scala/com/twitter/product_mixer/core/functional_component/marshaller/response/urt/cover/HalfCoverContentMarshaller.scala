package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.CallbackMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.D sm ss nfoMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.HalfCoverContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class HalfCoverContentMarshaller @ nject() (
  halfCoverD splayTypeMarshaller: HalfCoverD splayTypeMarshaller,
  coverCtaMarshaller: CoverCtaMarshaller,
  r chTextMarshaller: R chTextMarshaller,
  cover mageMarshaller: Cover mageMarshaller,
  d sm ss nfoMarshaller: D sm ss nfoMarshaller,
  callbackMarshaller: CallbackMarshaller) {

  def apply(halfCover: HalfCoverContent): urt.Cover =
    urt.Cover.HalfCover(
      urt.HalfCover(
        d splayType = halfCoverD splayTypeMarshaller(halfCover.d splayType),
        pr maryText = r chTextMarshaller(halfCover.pr maryText),
        pr maryCoverCta = coverCtaMarshaller(halfCover.pr maryCoverCta),
        secondaryCoverCta = halfCover.secondaryCoverCta.map(coverCtaMarshaller(_)),
        secondaryText = halfCover.secondaryText.map(r chTextMarshaller(_)),
         mpress onCallbacks = halfCover. mpress onCallbacks.map(_.map(callbackMarshaller(_))),
        d sm ss ble = halfCover.d sm ss ble,
        cover mage = halfCover.cover mage.map(cover mageMarshaller(_)),
        d sm ss nfo = halfCover.d sm ss nfo.map(d sm ss nfoMarshaller(_))
      ))
}
