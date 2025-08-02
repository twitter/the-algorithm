package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.SegmentedTimelinesPageBody
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SegmentedTimelinesMarshaller @Inject() (
  segmentedTimelineMarshaller: SegmentedTimelineMarshaller) {

  def apply(segmentedTimelinesPageBody: SegmentedTimelinesPageBody): urp.SegmentedTimelines =
    urp.SegmentedTimelines(
      initialTimeline = segmentedTimelineMarshaller(segmentedTimelinesPageBody.initialTimeline),
      timelines = segmentedTimelinesPageBody.timelines.map(segmentedTimelineMarshaller(_))
    )
}
