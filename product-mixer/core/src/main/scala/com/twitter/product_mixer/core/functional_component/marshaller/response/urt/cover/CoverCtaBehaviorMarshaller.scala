package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverCtaBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverBehav orD sm ss
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverBehav orNav gate
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller

@S ngleton
class CoverCtaBehav orMarshaller @ nject() (
  r chTextMarshaller: R chTextMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(coverCtaBehav or: CoverCtaBehav or): urt.CoverCtaBehav or =
    coverCtaBehav or match {
      case d sm ss: CoverBehav orD sm ss =>
        urt.CoverCtaBehav or.D sm ss(
          urt.CoverBehav orD sm ss(d sm ss.feedback ssage.map(r chTextMarshaller(_))))
      case nav: CoverBehav orNav gate =>
        urt.CoverCtaBehav or.Nav gate(urt.CoverBehav orNav gate(urlMarshaller(nav.url)))
    }
}
