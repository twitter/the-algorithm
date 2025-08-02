package com.twitter.product_mitwitterer.core.service.slice

import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistry
import com.twitter.stitch.Stitch
import com.twitter.strato.graphql.thriftscala.SliceResult
import com.twitter.timelines.configapi.Params

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.reflect.runtime.universe.TypeTag

/**
 * Look up and etwitterecute Slice products in the [[ProductPipelineRegistry]]
 */
@Singleton
class SliceService @Inject() (productPipelineRegistry: ProductPipelineRegistry) {

  def getSliceResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
    implicit requestTypeTag: TypeTag[RequestType]
  ): Stitch[SliceResult] =
    productPipelineRegistry
      .getProductPipeline[RequestType, SliceResult](request.product)
      .process(ProductPipelineRequest(request, params))
}
