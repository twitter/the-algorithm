package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.card

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.card.Card em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Card emMarshaller @ nject() (
  cardD splayTypeMarshaller: CardD splayTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(card em: Card em): urt.T  l ne emContent = {
    urt.T  l ne emContent.Card(
      urt.Card(
        cardUrl = card em.cardUrl,
        text = card em.text,
        subtext = card em.subtext,
        url = card em.url.map(urlMarshaller(_)),
        cardD splayType = card em.d splayType.map(cardD splayTypeMarshaller(_))
      )
    )
  }
}
