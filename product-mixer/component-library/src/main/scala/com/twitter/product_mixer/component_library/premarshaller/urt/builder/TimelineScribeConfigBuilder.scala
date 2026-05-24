package com.twitter.product_mixer.component_library.premarshaller.urt.builder

import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mixer.core.pipeline.PipelineQuery

/**
 * Trait for our builder which given a query and entries will return an `Option[TimelineScribeConfig]`
 *
 * @tparam Query
 */
trait TimelineScribeConfigBuilder[-Query <: PipelineQuery] {

  def build(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Option[TimelineScribeConfig]
}
