package com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.LiveEventDetails
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiveEventDetailsMarshaller @Inject() () {

  def apply(liveEventDetails: LiveEventDetails): urt.LiveEventDetails = urt.LiveEventDetails(
    eventId = liveEventDetails.eventId
  )
}
