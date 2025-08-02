package com.twitter.product_mitwitterer.core.service.debug_query

import com.twitter.finagle.Service
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ProductDisabled
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineResult
import com.twitter.scrooge.{Request => ScroogeRequest}
import com.twitter.scrooge.{Response => ScroogeResponse}
import com.twitter.util.Future
import com.twitter.product_mitwitterer.core.{thriftscala => t}
import com.twitter.util.jackson.ScalaObjectMapper

/**
 * All Mitwitterers must implement a debug query interface. This can be a problem for in-place migrations
 * where a service may only partially implement Product Mitwitterer patterns. This service can be used as
 * a noop implementation of [[DebugQueryService]] until the Mitwitterer service is fully migrated.
 */
object DebugQueryNotSupportedService
    etwittertends Service[ScroogeRequest[_], ScroogeResponse[t.PipelineEtwitterecutionResult]] {

  val failureJson: String = {
    val message = "This service does not support debug queries, this is usually due to an active " +
      "in-place migration to Product Mitwitterer. Please reach out in #product-mitwitterer if you have any questions."

    ScalaObjectMapper().writeValueAsString(
      ProductPipelineResult(
        transformedQuery = None,
        qualityFactorResult = None,
        gateResult = None,
        pipelineSelectorResult = None,
        mitwittererPipelineResult = None,
        recommendationPipelineResult = None,
        traceId = None,
        failure = Some(PipelineFailure(ProductDisabled, message)),
        result = None,
      ))
  }

  override def apply(
    thriftRequest: ScroogeRequest[_]
  ): Future[ScroogeResponse[t.PipelineEtwitterecutionResult]] =
    Future.value(ScroogeResponse(t.PipelineEtwitterecutionResult(failureJson)))
}
