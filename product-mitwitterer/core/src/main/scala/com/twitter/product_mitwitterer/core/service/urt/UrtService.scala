package com.twitter.product_mitwitterer.core.service.urt

import com.fastertwitterml.jackson.databind.SerializationFeature
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.UrtTransportMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ProductDisabled
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistry
import com.twitter.product_mitwitterer.core.{thriftscala => t}
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params
import com.twitter.timelines.render.{thriftscala => urt}
import com.twitter.util.jackson.ScalaObjectMapper

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.reflect.runtime.universe.TypeTag

/**
 * Look up and etwitterecute products in the [[ProductPipelineRegistry]]
 */
@Singleton
class UrtService @Inject() (productPipelineRegistry: ProductPipelineRegistry) {

  def getUrtResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
    implicit requestTypeTag: TypeTag[RequestType]
  ): Stitch[urt.TimelineResponse] =
    productPipelineRegistry
      .getProductPipeline[RequestType, urt.TimelineResponse](request.product)
      .process(ProductPipelineRequest(request, params))
      .handle {
        // Detect ProductDisabled and convert it to TimelineUnavailable
        case pipelineFailure: PipelineFailure if pipelineFailure.category == ProductDisabled =>
          UrtTransportMarshaller.unavailable("")
      }

  /**
   * Get detailed pipeline etwitterecution as a serialized JSON String
   */
  def getPipelineEtwitterecutionResult[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
    implicit requestTypeTag: TypeTag[RequestType]
  ): Stitch[t.PipelineEtwitterecutionResult] =
    productPipelineRegistry
      .getProductPipeline[RequestType, urt.TimelineResponse](request.product)
      .arrow(ProductPipelineRequest(request, params)).map { detailedResult =>
        val mapper = ScalaObjectMapper()
        // configure so that etwitterception is not thrown whenever case class is not serializable
        mapper.underlying.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        val serializedJSON = mapper.writePrettyString(detailedResult)
        t.PipelineEtwitterecutionResult(serializedJSON)
      }

}
