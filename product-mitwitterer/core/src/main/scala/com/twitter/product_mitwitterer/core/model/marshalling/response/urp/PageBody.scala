package com.twitter.product_mitwitterer.core.model.marshalling.response.urp

sealed trait PageBody

case class TimelineKeyPageBody(timeline: TimelineKey) etwittertends PageBody

case class SegmentedTimelinesPageBody(
  initialTimeline: SegmentedTimeline,
  timelines: Seq[SegmentedTimeline])
    etwittertends PageBody
