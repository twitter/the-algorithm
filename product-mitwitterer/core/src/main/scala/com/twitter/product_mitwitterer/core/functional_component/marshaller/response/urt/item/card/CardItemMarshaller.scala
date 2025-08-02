package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.card

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.card.CardItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CardItemMarshaller @Inject() (
  cardDisplayTypeMarshaller: CardDisplayTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(cardItem: CardItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Card(
      urt.Card(
        cardUrl = cardItem.cardUrl,
        tetwittert = cardItem.tetwittert,
        subtetwittert = cardItem.subtetwittert,
        url = cardItem.url.map(urlMarshaller(_)),
        cardDisplayType = cardItem.displayType.map(cardDisplayTypeMarshaller(_))
      )
    )
  }
}
