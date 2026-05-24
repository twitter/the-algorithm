package com.twitter.product_mixer.component_library.premarshaller.urt.builder

import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class StaticTimelineScribeConfigBuilder(
  timelineScribeConfig: TimelineScribeConfig)
    extends TimelineScribeConfigBuilder[PipelineQuery] {

  def build(
    query: PipelineQuery,
    entries: Seq[TimelineEntry]
  ): Option[TimelineScribeConfig] = Some(timelineScribeConfig)
}
