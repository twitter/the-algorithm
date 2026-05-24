package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.card

import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.card._
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class CardDisplayTypeMarshaller @Inject() () {

  def apply(cardDisplayType: CardDisplayType): urt.CardDisplayType = cardDisplayType match {
    case HeroDisplayType => urt.CardDisplayType.Hero
    case CellDisplayType => urt.CardDisplayType.Cell
    case TweetCardDisplayType => urt.CardDisplayType.TweetCard
  }
}
