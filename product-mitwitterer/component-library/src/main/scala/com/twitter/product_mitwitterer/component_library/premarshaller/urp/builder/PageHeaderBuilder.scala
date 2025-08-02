package com.twitter.product_mitwitterer.component_library.premarshaller.urp.builder

import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.PageHeader
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

/**
 * Trait for our builder which given a query and selections will return an `Option[PageHeader]`
 *
 * @tparam Query
 */
trait PageHeaderBuilder[-Query <: PipelineQuery] {

  def build(
    query: Query,
    selections: Seq[CandidateWithDetails]
  ): Option[PageHeader]
}
