package com.X.product_mixer.core.functional_component.marshaller.response.urt

import com.X.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineScribeConfigMarshaller @Inject() () {

  def apply(timelineScribeConfig: TimelineScribeConfig): urt.TimelineScribeConfig =
    urt.TimelineScribeConfig(
      page = timelineScribeConfig.page,
      section = timelineScribeConfig.section,
      entityToken = timelineScribeConfig.entityToken
    )
}
