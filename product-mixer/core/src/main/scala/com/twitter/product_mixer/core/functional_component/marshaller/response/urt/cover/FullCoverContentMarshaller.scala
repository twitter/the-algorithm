package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.CallbackMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.D sm ss nfoMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageD splayTypeMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageVar antMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.FullCoverContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class FullCoverContentMarshaller @ nject() (
  fullCoverD splayTypeMarshaller: FullCoverD splayTypeMarshaller,
  coverCtaMarshaller: CoverCtaMarshaller,
  r chTextMarshaller: R chTextMarshaller,
   mageVar antMarshaller:  mageVar antMarshaller,
  d sm ss nfoMarshaller: D sm ss nfoMarshaller,
   mageD splayTypeMarshaller:  mageD splayTypeMarshaller,
  callbackMarshaller: CallbackMarshaller) {

  def apply(fullCover: FullCoverContent): urt.Cover =
    urt.Cover.FullCover(
      urt.FullCover(
        d splayType = fullCoverD splayTypeMarshaller(fullCover.d splayType),
        pr maryText = r chTextMarshaller(fullCover.pr maryText),
        pr maryCoverCta = coverCtaMarshaller(fullCover.pr maryCoverCta),
        secondaryCoverCta = fullCover.secondaryCoverCta.map(coverCtaMarshaller(_)),
        secondaryText = fullCover.secondaryText.map(r chTextMarshaller(_)),
         mage = fullCover. mageVar ant.map( mageVar antMarshaller(_)),
        deta ls = fullCover.deta ls.map(r chTextMarshaller(_)),
        d sm ss nfo = fullCover.d sm ss nfo.map(d sm ss nfoMarshaller(_)),
         mageD splayType = fullCover. mageD splayType.map( mageD splayTypeMarshaller(_)),
         mpress onCallbacks = fullCover. mpress onCallbacks.map(_.map(callbackMarshaller(_)))
      ))
}
