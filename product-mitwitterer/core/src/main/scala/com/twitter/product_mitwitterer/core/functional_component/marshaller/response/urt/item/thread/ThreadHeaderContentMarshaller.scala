package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.thread

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.thread._
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ThreadHeaderContentMarshaller @Inject() () {
  def apply(content: ThreadHeaderContent): urt.ThreadHeaderContent = content match {
    case UserThreadHeader(userId) =>
      urt.ThreadHeaderContent.UserThreadHeader(urt.UserThreadHeader(userId))
  }
}
