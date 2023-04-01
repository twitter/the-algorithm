package com.twitter.product_mixer.component_library.premarshaller.urp.builder

import com.twitter.product_mixer.core.model.marshalling.response.urp.PageBody
import com.twitter.product_mixer.core.model.marshalling.response.urp.PageHeader
import com.twitter.product_mixer.core.model.marshalling.response.urp.PageNavBar
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mixer.core.pipeline.PipelineQuery

/**
 * Trait for our builder which given a query and page info will return an `Option[TimelineScribeConfig]`
 *
 * @tparam Query
 */
trait TimelineScribeConfigBuilder[-Query <: PipelineQuery] {

  def build(
    query: Query,
    pageBody: PageBody,
    pageHeader: Option[PageHeader],
    pageNavBar: Option[PageNavBar]
  ): Option[TimelineScribeConfig]
}
