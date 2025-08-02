package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class StaticTimelineScribeConfigBuilder(
  timelineScribeConfig: TimelineScribeConfig)
    etwittertends TimelineScribeConfigBuilder[PipelineQuery] {

  def build(
    query: PipelineQuery,
    entries: Seq[TimelineEntry]
  ): Option[TimelineScribeConfig] = Some(timelineScribeConfig)
}
