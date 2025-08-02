package com.twitter.product_mitwitterer.component_library.premarshaller.urp.builder

import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.PageBody
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.PageHeader
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.PageNavBar
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class StaticTimelineScribeConfigBuilder(
  timelineScribeConfig: TimelineScribeConfig)
    etwittertends TimelineScribeConfigBuilder[PipelineQuery] {

  override def build(
    query: PipelineQuery,
    pageBody: PageBody,
    pageHeader: Option[PageHeader],
    pageNavBar: Option[PageNavBar]
  ): Option[TimelineScribeConfig] = Some(timelineScribeConfig)
}
