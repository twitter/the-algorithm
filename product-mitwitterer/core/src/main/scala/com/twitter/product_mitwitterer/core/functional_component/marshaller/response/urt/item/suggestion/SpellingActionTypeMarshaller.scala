package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.suggestion

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.suggestion._
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Singleton

@Singleton
class SpellingActionTypeMarshaller {

  def apply(spellingActionType: SpellingActionType): urt.SpellingActionType =
    spellingActionType match {
      case ReplaceSpellingActionType => urt.SpellingActionType.Replace
      case EtwitterpandSpellingActionType => urt.SpellingActionType.Etwitterpand
      case SuggestSpellingActionType => urt.SpellingActionType.Suggest
    }
}
