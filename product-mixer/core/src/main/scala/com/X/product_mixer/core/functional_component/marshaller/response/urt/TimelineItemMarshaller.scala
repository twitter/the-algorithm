package com.X.product_mixer.core.functional_component.marshaller.response.urt

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.FeedbackInfoMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineItemMarshaller @Inject() (
  timelineItemContentMarshaller: TimelineItemContentMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller,
  feedbackInfoMarshaller: FeedbackInfoMarshaller) {

  def apply(item: TimelineItem): urt.TimelineItem = urt.TimelineItem(
    content = timelineItemContentMarshaller(item),
    clientEventInfo = item.clientEventInfo.map(clientEventInfoMarshaller(_)),
    feedbackInfo = item.feedbackActionInfo.map(feedbackInfoMarshaller(_)),
    prompt = None
  )
}
