package com.X.product_mixer.component_library.premarshaller.urt.builder

import com.X.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.X.product_mixer.core.pipeline.PipelineQuery

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
