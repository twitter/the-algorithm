package com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.AbusiveQuality
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ConversationSection
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.HighQuality
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.LowQuality
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.RelatedTweet
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationSectionMarshaller @Inject() () {

  def apply(section: ConversationSection): urt.ConversationSection = section match {
    case HighQuality => urt.ConversationSection.HighQuality
    case LowQuality => urt.ConversationSection.LowQuality
    case AbusiveQuality => urt.ConversationSection.AbusiveQuality
    case RelatedTweet => urt.ConversationSection.RelatedTweet
  }
}
