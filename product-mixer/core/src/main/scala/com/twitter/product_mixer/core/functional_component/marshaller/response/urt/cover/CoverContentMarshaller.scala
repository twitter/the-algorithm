package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.FullCoverContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.HalfCoverContent
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CoverContentMarshaller @ nject() (
  fullCoverContentMarshaller: FullCoverContentMarshaller,
  halfCoverContentMarshaller: HalfCoverContentMarshaller) {

  def apply(coverContent: CoverContent): urt.Cover = coverContent match {
    case fullCover: FullCoverContent => fullCoverContentMarshaller(fullCover)
    case halfCover: HalfCoverContent => halfCoverContentMarshaller(halfCover)
  }
}
