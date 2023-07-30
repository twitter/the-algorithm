package com.X.product_mixer.core.service.slice

import com.X.product_mixer.core.model.marshalling.request.Request
import com.X.product_mixer.core.pipeline.product.ProductPipelineRequest
import com.X.product_mixer.core.product.registry.ProductPipelineRegistry
import com.X.stitch.Stitch
import com.X.strato.graphql.thriftscala.SliceResult
import com.X.timelines.configapi.Params

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
