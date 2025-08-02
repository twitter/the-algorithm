package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackDisplayContetwittert
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class FeedbackDisplayContetwittertMarshaller @Inject() () {

  def apply(displayContetwittert: FeedbackDisplayContetwittert): urt.FeedbackDisplayContetwittert =
    urt.FeedbackDisplayContetwittert(
      reason = displayContetwittert.reason
    )
}
