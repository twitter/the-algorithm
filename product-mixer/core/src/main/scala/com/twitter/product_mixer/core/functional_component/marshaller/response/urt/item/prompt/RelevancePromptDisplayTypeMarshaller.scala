package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.prompt

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt._
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Singleton

@Singleton
class RelevancePromptDisplayTypeMarshaller {

  def apply(
    relevancePromptDisplayType: RelevancePromptDisplayType
  ): urt.RelevancePromptDisplayType = relevancePromptDisplayType match {
    case Normal => urt.RelevancePromptDisplayType.Normal
    case Compact => urt.RelevancePromptDisplayType.Compact
    case Large => urt.RelevancePromptDisplayType.Large
    case ThumbsUpAndDown => urt.RelevancePromptDisplayType.ThumbsUpAndDown
  }
}
