package com.twitter.tweet_mixer.service

import com.twitter.product_mixer.core.model.marshalling.request.Request
import com.twitter.product_mixer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mixer.core.product.registry.ProductPipelineRegistry
import com.twitter.tweet_mixer.thriftscala.TweetMixerRecommendationResponse
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params
import javax.inject.Inject
import javax.inject.Singleton
import scala.reflect.runtime.universe._

@Singleton
class TweetMixerService @Inject() (productPipelineRegistry: ProductPipelineRegistry) {

  def getTweetMixerRecommendationResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
    implicit requestTypeTag: TypeTag[RequestType]
  ): Stitch[TweetMixerRecommendationResponse] =
    productPipelineRegistry
      .getProductPipeline[RequestType, TweetMixerRecommendationResponse](request.product)
      .process(ProductPipelineRequest(request, params))
}
