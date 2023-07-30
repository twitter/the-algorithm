package com.X.product_mixer.component_library.premarshaller.urp.builder

import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.model.marshalling.response.urp.PageHeader
import com.X.product_mixer.core.pipeline.PipelineQuery

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
