package com.X.home_mixer.service

import com.X.home_mixer.{thriftscala => t}
import com.X.product_mixer.core.model.marshalling.request.Request
import com.X.product_mixer.core.pipeline.product.ProductPipelineRequest
import com.X.product_mixer.core.product.registry.ProductPipelineRegistry
import com.X.stitch.Stitch
import com.X.timelines.configapi.Params
import javax.inject.Inject
import javax.inject.Singleton
import scala.reflect.runtime.universe._

@Singleton
class ScoredTweetsService @Inject() (productPipelineRegistry: ProductPipelineRegistry) {

  def getScoredTweetsResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
    implicit requestTypeTag: TypeTag[RequestType]
  ): Stitch[t.ScoredTweetsResponse] = productPipelineRegistry
    .getProductPipeline[RequestType, t.ScoredTweetsResponse](request.product)
    .process(ProductPipelineRequest(request, params))
}
