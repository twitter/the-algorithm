package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.LiveEventDetails
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class LiveEventDetailsMarshaller @Inject() () {

  def apply(liveEventDetails: LiveEventDetails): urt.LiveEventDetails = urt.LiveEventDetails(
    eventId = liveEventDetails.eventId
  )
}
