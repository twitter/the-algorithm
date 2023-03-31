package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.TimelinesDetails
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelinesDetailsMarshaller @Inject() () {

  def apply(timelinesDetails: TimelinesDetails): urt.TimelinesDetails = urt.TimelinesDetails(
    injectionType = timelinesDetails.injectionType,
    controllerData = timelinesDetails.controllerData,
    sourceData = timelinesDetails.sourceData
  )
}
