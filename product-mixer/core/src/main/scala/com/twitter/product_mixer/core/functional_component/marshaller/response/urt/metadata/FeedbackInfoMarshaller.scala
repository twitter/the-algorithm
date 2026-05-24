package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedbackInfoMarshaller @Inject() (
  feedbackActionMarshaller: FeedbackActionMarshaller,
  feedbackDisplayContextMarshaller: FeedbackDisplayContextMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller) {

  def apply(feedbackActionInfo: FeedbackActionInfo): urt.FeedbackInfo = urt.FeedbackInfo(
    // Generate key from the hashcode of the marshalled feedback action URT
    feedbackKeys = feedbackActionInfo.feedbackActions
      .map(feedbackActionMarshaller(_)).map(FeedbackActionMarshaller.generateKey),
    feedbackMetadata = feedbackActionInfo.feedbackMetadata,
    displayContext = feedbackActionInfo.displayContext.map(feedbackDisplayContextMarshaller(_)),
    clientEventInfo = feedbackActionInfo.clientEventInfo.map(clientEventInfoMarshaller(_)),
  )
}
