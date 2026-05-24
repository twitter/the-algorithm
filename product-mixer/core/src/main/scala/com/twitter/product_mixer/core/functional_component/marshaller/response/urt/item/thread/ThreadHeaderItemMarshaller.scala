package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.thread

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.thread.ThreadHeaderItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreadHeaderItemMarshaller @Inject() (
  threadHeaderContentMarshaller: ThreadHeaderContentMarshaller) {

  def apply(threadHeaderItem: ThreadHeaderItem): urt.TimelineItemContent.ThreadHeader =
    urt.TimelineItemContent.ThreadHeader(
      urt.ThreadHeaderItem(
        content = threadHeaderContentMarshaller(threadHeaderItem.content)
      )
    )
}
