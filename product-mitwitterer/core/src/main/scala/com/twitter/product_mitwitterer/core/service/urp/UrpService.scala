package com.twitter.product_mitwitterer.core.service.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineRequest
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistry
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.reflect.runtime.universe.TypeTag

@Singleton
class UrpService @Inject() (productPipelineRegistry: ProductPipelineRegistry) {

  def getUrpResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
    implicit requestTypeTag: TypeTag[RequestType]
  ): Stitch[urp.Page] =
    productPipelineRegistry
      .getProductPipeline[RequestType, urp.Page](request.product)
      .process(ProductPipelineRequest(request, params))
}
