package com.twitter.product_mixer.core.service.slice

import com.twitter.product_mixer.core.model.marshalling.request.Request
import com.twitter.product_mixer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mixer.core.product.registry.ProductPipelineRegistry
import com.twitter.stitch.Stitch
import com.twitter.strato.graphql.thriftscala.SliceResult
import com.twitter.timelines.configapi.Params

import javax.inject.Inject
import javax.inject.Singleton
import scala.reflect.runtime.universe.TypeTag

/**
 * Look up and execute Slice products in the [[ProductPipelineRegistry]]
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
