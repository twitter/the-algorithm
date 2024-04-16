package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.vert cal_gr d_ em

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d emTop cT le
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Vert calGr d emTop cT leMarshaller @ nject() (
  styleMarshaller: Vert calGr d emT leStyleMarshaller,
  funct onal yTypeMarshaller: Vert calGr d emTop cFunct onal yTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(vert calGr d emTop cT le: Vert calGr d emTop cT le): urt.Vert calGr d emContent =
    urt.Vert calGr d emContent.Top cT le(
      urt.Vert calGr d emTop cT le(
        top c d = vert calGr d emTop cT le. d.toStr ng,
        style = vert calGr d emTop cT le.style
          .map(styleMarshaller(_)).getOrElse(urt.Vert calGr d emT leStyle.S ngleStateDefault),
        funct onal yType = vert calGr d emTop cT le.funct onal yType
          .map(funct onal yTypeMarshaller(_)).getOrElse(
            urt.Vert calGr d emTop cFunct onal yType.P vot),
        url = vert calGr d emTop cT le.url.map(urlMarshaller(_))
      )
    )

}
