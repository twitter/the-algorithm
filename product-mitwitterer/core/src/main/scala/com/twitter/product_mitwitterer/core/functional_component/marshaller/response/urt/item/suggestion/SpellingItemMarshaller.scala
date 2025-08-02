package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.suggestion

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.suggestion.SpellingItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SpellingItemMarshaller @Inject() (
  tetwittertResultMarshaller: TetwittertResultMarshaller,
  spellingActionTypeMarshaller: SpellingActionTypeMarshaller) {

  def apply(spellingItem: SpellingItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Spelling(
      urt.Spelling(
        spellingResult = tetwittertResultMarshaller(spellingItem.tetwittertResult),
        spellingAction = spellingItem.spellingActionType.map(spellingActionTypeMarshaller(_)),
        originalQuery = spellingItem.originalQuery
      )
    )
  }
}
