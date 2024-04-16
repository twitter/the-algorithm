package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.label

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.label.Label em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Label emMarshaller @ nject() (
  d splayTypeMarshaller: LabelD splayTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(label em: Label em): urt.T  l ne emContent = {
    urt.T  l ne emContent.Label(
      urt.Label(
        text = label em.text,
        subtext = label em.subtext,
        d sclosure nd cator = label em.d sclosure nd cator,
        url = label em.url.map(urlMarshaller(_)),
        d splayType = label em.d splayType.map(d splayTypeMarshaller(_))
      )
    )
  }
}
