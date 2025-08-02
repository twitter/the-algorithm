package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ConversationDetails
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ConversationDetailsMarshaller @Inject() (sectionMarshaller: ConversationSectionMarshaller) {

  def apply(conversationDetails: ConversationDetails): urt.ConversationDetails =
    urt.ConversationDetails(
      conversationSection = conversationDetails.conversationSection.map(sectionMarshaller(_))
    )
}
