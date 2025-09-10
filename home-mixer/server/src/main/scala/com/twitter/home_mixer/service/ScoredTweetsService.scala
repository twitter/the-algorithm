package com.twitter.home_mixer.service

import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.model.marshalling.request.Request
import com.twitter.product_mixer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mixer.core.product.registry.ProductPipelineRegistry
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params
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
