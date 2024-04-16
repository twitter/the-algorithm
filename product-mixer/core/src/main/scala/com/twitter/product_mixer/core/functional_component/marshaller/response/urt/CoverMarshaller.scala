package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover.CoverContentMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Cover
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.FullCover
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.HalfCover
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CoverMarshaller @ nject() (
  coverContentMarshaller: CoverContentMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller) {

  def apply(cover: Cover): urt.ShowCover = cover match {
    case halfCover: HalfCover =>
      urt.ShowCover(
        cover = coverContentMarshaller(halfCover.content),
        cl entEvent nfo = cover.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)))
    case fullCover: FullCover =>
      urt.ShowCover(
        cover = coverContentMarshaller(fullCover.content),
        cl entEvent nfo = cover.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)))
  }
}

class UnsupportedT  l neCoverExcept on(cover: Cover)
    extends UnsupportedOperat onExcept on(
      "Unsupported t  l ne cover " + TransportMarshaller.getS mpleNa (cover.getClass))
