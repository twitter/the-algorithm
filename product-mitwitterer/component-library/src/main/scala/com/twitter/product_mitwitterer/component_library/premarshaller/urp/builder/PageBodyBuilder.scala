package com.twitter.product_mitwitterer.component_library.premarshaller.urp.builder

import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.PageBody
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

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
