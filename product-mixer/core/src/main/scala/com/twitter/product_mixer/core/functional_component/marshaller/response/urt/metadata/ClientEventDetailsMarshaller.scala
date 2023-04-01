package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventDetails
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientEventDetailsMarshaller @Inject() (
  conversationDetailsMarshaller: ConversationDetailsMarshaller,
  timelinesDetailsMarshaller: TimelinesDetailsMarshaller,
  articleDetailsMarshaller: ArticleDetailsMarshaller,
  liveEventDetailsMarshaller: LiveEventDetailsMarshaller,
  commerceDetailsMarshaller: CommerceDetailsMarshaller) {

  def apply(clientEventDetails: ClientEventDetails): urt.ClientEventDetails = {
    urt.ClientEventDetails(
      conversationDetails =
        clientEventDetails.conversationDetails.map(conversationDetailsMarshaller(_)),
      timelinesDetails = clientEventDetails.timelinesDetails.map(timelinesDetailsMarshaller(_)),
      articleDetails = clientEventDetails.articleDetails.map(articleDetailsMarshaller(_)),
      liveEventDetails = clientEventDetails.liveEventDetails.map(liveEventDetailsMarshaller(_)),
      commerceDetails = clientEventDetails.commerceDetails.map(commerceDetailsMarshaller(_))
    )
  }
}
