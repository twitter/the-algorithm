package com.twitter.product_mixer.core.model.marshalling.response.urp

import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig

case class SegmentedTimeline(
  id: String,
  labelText: String,
  timeline: TimelineKey,
  scribeConfig: Option[TimelineScribeConfig] = None,
  refreshIntervalSec: Option[Long] = None)
