package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.suggestion

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.suggestion._
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Singleton

@Singleton
class SpellingActionTypeMarshaller {

  def apply(spellingActionType: SpellingActionType): urt.SpellingActionType =
    spellingActionType match {
      case ReplaceSpellingActionType => urt.SpellingActionType.Replace
      case ExpandSpellingActionType => urt.SpellingActionType.Expand
      case SuggestSpellingActionType => urt.SpellingActionType.Suggest
    }
}
