package com.twitter.product_mixer.component_library.premarshaller.urp.builder

import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urp.PageBody
import com.twitter.product_mixer.core.pipeline.PipelineQuery

/**
 * Trait for our builder which given a query and selections will return a `PageBody`
 *
 * @tparam Query
 */
trait PageBodyBuilder[-Query <: PipelineQuery] {

  def build(
    query: Query,
    selections: Seq[CandidateWithDetails]
  ): PageBody
}
