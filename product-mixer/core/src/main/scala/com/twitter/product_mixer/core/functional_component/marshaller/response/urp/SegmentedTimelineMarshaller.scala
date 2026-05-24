package com.twitter.product_mixer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.TimelineScribeConfigMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urp.SegmentedTimeline
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SegmentedTimelineMarshaller @Inject() (
  timelineKeyMarshaller: TimelineKeyMarshaller,
  timelineScribeConfigMarshaller: TimelineScribeConfigMarshaller) {

  def apply(segmentedTimeline: SegmentedTimeline): urp.SegmentedTimeline = urp.SegmentedTimeline(
    id = segmentedTimeline.id,
    labelText = segmentedTimeline.labelText,
    timeline = timelineKeyMarshaller(segmentedTimeline.timeline),
    scribeConfig = segmentedTimeline.scribeConfig.map(timelineScribeConfigMarshaller(_)),
    refreshIntervalSec = segmentedTimeline.refreshIntervalSec
  )
}
