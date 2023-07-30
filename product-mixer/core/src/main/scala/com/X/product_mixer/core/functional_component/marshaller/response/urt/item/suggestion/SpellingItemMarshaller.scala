package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.suggestion

import com.X.product_mixer.core.model.marshalling.response.urt.item.suggestion.SpellingItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpellingItemMarshaller @Inject() (
  textResultMarshaller: TextResultMarshaller,
  spellingActionTypeMarshaller: SpellingActionTypeMarshaller) {

  def apply(spellingItem: SpellingItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Spelling(
      urt.Spelling(
        spellingResult = textResultMarshaller(spellingItem.textResult),
        spellingAction = spellingItem.spellingActionType.map(spellingActionTypeMarshaller(_)),
        originalQuery = spellingItem.originalQuery
      )
    )
  }
}
