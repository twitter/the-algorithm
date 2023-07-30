package com.X.product_mixer.core.service.debug_query

import com.X.finagle.Service
import com.X.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.X.product_mixer.core.pipeline.pipeline_failure.ProductDisabled
import com.X.product_mixer.core.pipeline.product.ProductPipelineResult
import com.X.scrooge.{Request => ScroogeRequest}
import com.X.scrooge.{Response => ScroogeResponse}
import com.X.util.Future
import com.X.product_mixer.core.{thriftscala => t}
import com.X.util.jackson.ScalaObjectMapper

/**
 * All Mixers must implement a debug query interface. This can be a problem for in-place migrations
 * where a service may only partially implement Product Mixer patterns. This service can be used as
 * a noop implementation of [[DebugQueryService]] until the Mixer service is fully migrated.
 */
object DebugQueryNotSupportedService
    extends Service[ScroogeRequest[_], ScroogeResponse[t.PipelineExecutionResult]] {

  val failureJson: String = {
    val message = "This service does not support debug queries, this is usually due to an active " +
      "in-place migration to Product Mixer. Please reach out in #product-mixer if you have any questions."

    ScalaObjectMapper().writeValueAsString(
      ProductPipelineResult(
        transformedQuery = None,
        qualityFactorResult = None,
        gateResult = None,
        pipelineSelectorResult = None,
        mixerPipelineResult = None,
        recommendationPipelineResult = None,
        traceId = None,
        failure = Some(PipelineFailure(ProductDisabled, message)),
        result = None,
      ))
  }

  override def apply(
    thriftRequest: ScroogeRequest[_]
  ): Future[ScroogeResponse[t.PipelineExecutionResult]] =
    Future.value(ScroogeResponse(t.PipelineExecutionResult(failureJson)))
}
