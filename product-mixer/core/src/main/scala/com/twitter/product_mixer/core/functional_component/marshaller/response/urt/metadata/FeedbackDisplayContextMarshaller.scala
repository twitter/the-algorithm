package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackDisplayContext
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedbackDisplayContextMarshaller @Inject() () {

  def apply(displayContext: FeedbackDisplayContext): urt.FeedbackDisplayContext =
    urt.FeedbackDisplayContext(
      reason = displayContext.reason
    )
}
