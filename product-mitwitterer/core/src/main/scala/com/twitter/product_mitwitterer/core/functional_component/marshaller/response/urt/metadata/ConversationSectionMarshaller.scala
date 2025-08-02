package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.AbusiveQuality
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ConversationSection
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.HighQuality
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.LowQuality
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RelatedTweet
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ConversationSectionMarshaller @Inject() () {

  def apply(section: ConversationSection): urt.ConversationSection = section match {
    case HighQuality => urt.ConversationSection.HighQuality
    case LowQuality => urt.ConversationSection.LowQuality
    case AbusiveQuality => urt.ConversationSection.AbusiveQuality
    case RelatedTweet => urt.ConversationSection.RelatedTweet
  }
}
