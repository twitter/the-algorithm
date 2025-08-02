package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class FeedbackInfoMarshaller @Inject() (
  feedbackActionMarshaller: FeedbackActionMarshaller,
  feedbackDisplayContetwittertMarshaller: FeedbackDisplayContetwittertMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller) {

  def apply(feedbackActionInfo: FeedbackActionInfo): urt.FeedbackInfo = urt.FeedbackInfo(
    // Generate key from the hashcode of the marshalled feedback action URT
    feedbackKeys = feedbackActionInfo.feedbackActions
      .map(feedbackActionMarshaller(_)).map(FeedbackActionMarshaller.generateKey),
    feedbackMetadata = feedbackActionInfo.feedbackMetadata,
    displayContetwittert = feedbackActionInfo.displayContetwittert.map(feedbackDisplayContetwittertMarshaller(_)),
    clientEventInfo = feedbackActionInfo.clientEventInfo.map(clientEventInfoMarshaller(_)),
  )
}
