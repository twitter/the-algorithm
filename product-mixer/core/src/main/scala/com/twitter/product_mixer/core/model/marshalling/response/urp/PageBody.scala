package com.twitter.product_mixer.core.model.marshalling.response.urp

sealed trait PageBody

case class TimelineKeyPageBody(timeline: TimelineKey) extends PageBody

case class SegmentedTimelinesPageBody(
  initialTimeline: SegmentedTimeline,
  timelines: Seq[SegmentedTimeline])
    extends PageBody
