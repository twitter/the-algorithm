package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TimelineScribeConfigMarshaller @Inject() () {

  def apply(timelineScribeConfig: TimelineScribeConfig): urt.TimelineScribeConfig =
    urt.TimelineScribeConfig(
      page = timelineScribeConfig.page,
      section = timelineScribeConfig.section,
      entityToken = timelineScribeConfig.entityToken
    )
}
