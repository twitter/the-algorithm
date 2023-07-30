package com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ConversationDetails
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationDetailsMarshaller @Inject() (sectionMarshaller: ConversationSectionMarshaller) {

  def apply(conversationDetails: ConversationDetails): urt.ConversationDetails =
    urt.ConversationDetails(
      conversationSection = conversationDetails.conversationSection.map(sectionMarshaller(_))
    )
}
