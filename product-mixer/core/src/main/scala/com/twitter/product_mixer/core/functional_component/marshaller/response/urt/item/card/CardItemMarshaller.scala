package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.card

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.card.CardItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardItemMarshaller @Inject() (
  cardDisplayTypeMarshaller: CardDisplayTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(cardItem: CardItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Card(
      urt.Card(
        cardUrl = cardItem.cardUrl,
        text = cardItem.text,
        subtext = cardItem.subtext,
        url = cardItem.url.map(urlMarshaller(_)),
        cardDisplayType = cardItem.displayType.map(cardDisplayTypeMarshaller(_))
      )
    )
  }
}
