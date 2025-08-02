package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.thread

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.thread.ThreadHeaderItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

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
