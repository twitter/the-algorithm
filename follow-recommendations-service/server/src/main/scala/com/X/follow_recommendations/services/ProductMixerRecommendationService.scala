package com.X.follow_recommendations.services

import com.X.finagle.stats.StatsReceiver
import javax.inject.Inject
import javax.inject.Singleton
import com.X.timelines.configapi.Params
import com.X.follow_recommendations.common.utils.DisplayLocationProductConverterUtil
import com.X.follow_recommendations.configapi.deciders.DeciderParams
import com.X.follow_recommendations.logging.FrsLogger
import com.X.follow_recommendations.models.{DebugParams => FrsDebugParams}
import com.X.follow_recommendations.models.RecommendationRequest
import com.X.follow_recommendations.models.RecommendationResponse
import com.X.follow_recommendations.models.Request
import com.X.product_mixer.core.model.marshalling.request.{
  DebugParams => ProductMixerDebugParams
}
import com.X.product_mixer.core.product.registry.ProductPipelineRegistry
import com.X.product_mixer.core.pipeline.product.ProductPipelineRequest
import com.X.stitch.Stitch

@Singleton
class ProductMixerRecommendationService @Inject() (
  productPipelineRegistry: ProductPipelineRegistry,
  resultLogger: FrsLogger,
  baseStats: StatsReceiver) {

  private val stats = baseStats.scope("product_mixer_recos_service_stats")
  private val loggingStats = stats.scope("logged")

  def get(request: RecommendationRequest, params: Params): Stitch[RecommendationResponse] = {
    if (params(DeciderParams.EnableRecommendations)) {
      val productMixerRequest = convertToProductMixerRequest(request)

      productPipelineRegistry
        .getProductPipeline[Request, RecommendationResponse](productMixerRequest.product)
        .process(ProductPipelineRequest(productMixerRequest, params)).onSuccess { response =>
          if (resultLogger.shouldLog(request.debugParams)) {
            loggingStats.counter().incr()
            resultLogger.logRecommendationResult(request, response)
          }
        }
    } else {
      Stitch.value(RecommendationResponse(Nil))
    }

  }

  def convertToProductMixerRequest(frsRequest: RecommendationRequest): Request = {
    Request(
      maxResults = frsRequest.maxResults,
      debugParams = convertToProductMixerDebugParams(frsRequest.debugParams),
      productContext = None,
      product =
        DisplayLocationProductConverterUtil.displayLocationToProduct(frsRequest.displayLocation),
      clientContext = frsRequest.clientContext,
      serializedRequestCursor = frsRequest.cursor,
      frsDebugParams = frsRequest.debugParams,
      displayLocation = frsRequest.displayLocation,
      excludedIds = frsRequest.excludedIds,
      fetchPromotedContent = frsRequest.fetchPromotedContent,
      userLocationState = frsRequest.userLocationState
    )
  }

  private def convertToProductMixerDebugParams(
    frsDebugParams: Option[FrsDebugParams]
  ): Option[ProductMixerDebugParams] = {
    frsDebugParams.map { debugParams =>
      ProductMixerDebugParams(debugParams.featureOverrides, None)
    }
  }
}
