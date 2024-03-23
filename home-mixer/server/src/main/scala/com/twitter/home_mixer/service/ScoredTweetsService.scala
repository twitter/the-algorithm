package com.ExTwitter.home_mixer.service

import com.ExTwitter.home_mixer.{thriftscala => t}
import com.ExTwitter.product_mixer.core.model.marshalling.request.Request
import com.ExTwitter.product_mixer.core.pipeline.product.ProductPipelineRequest
import com.ExTwitter.product_mixer.core.product.registry.ProductPipelineRegistry
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.Params
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
