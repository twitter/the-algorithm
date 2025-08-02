package com.twitter.follow_recommendations.services

import com.twitter.finagle.stats.StatsReceiver
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.timelines.configapi.Params
import com.twitter.follow_recommendations.common.utils.DisplayLocationProductConverterUtil
import com.twitter.follow_recommendations.configapi.deciders.DeciderParams
import com.twitter.follow_recommendations.logging.FrsLogger
import com.twitter.follow_recommendations.models.{DebugParams => FrsDebugParams}
import com.twitter.follow_recommendations.models.RecommendationRequest
import com.twitter.follow_recommendations.models.RecommendationResponse
import com.twitter.follow_recommendations.models.Request
import com.twitter.product_mitwitterer.core.model.marshalling.request.{
  DebugParams => ProductMitwittererDebugParams
}
import com.twitter.product_mitwitterer.core.product.registry.ProductPipelineRegistry
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineRequest
import com.twitter.stitch.Stitch

@Singleton
class ProductMitwittererRecommendationService @Inject() (
  productPipelineRegistry: ProductPipelineRegistry,
  resultLogger: FrsLogger,
  baseStats: StatsReceiver) {

  private val stats = baseStats.scope("product_mitwitterer_recos_service_stats")
  private val loggingStats = stats.scope("logged")

  def get(request: RecommendationRequest, params: Params): Stitch[RecommendationResponse] = {
    if (params(DeciderParams.EnableRecommendations)) {
      val productMitwittererRequest = convertToProductMitwittererRequest(request)

      productPipelineRegistry
        .getProductPipeline[Request, RecommendationResponse](productMitwittererRequest.product)
        .process(ProductPipelineRequest(productMitwittererRequest, params)).onSuccess { response =>
          if (resultLogger.shouldLog(request.debugParams)) {
            loggingStats.counter().incr()
            resultLogger.logRecommendationResult(request, response)
          }
        }
    } else {
      Stitch.value(RecommendationResponse(Nil))
    }

  }

  def convertToProductMitwittererRequest(frsRequest: RecommendationRequest): Request = {
    Request(
      matwitterResults = frsRequest.matwitterResults,
      debugParams = convertToProductMitwittererDebugParams(frsRequest.debugParams),
      productContetwittert = None,
      product =
        DisplayLocationProductConverterUtil.displayLocationToProduct(frsRequest.displayLocation),
      clientContetwittert = frsRequest.clientContetwittert,
      serializedRequestCursor = frsRequest.cursor,
      frsDebugParams = frsRequest.debugParams,
      displayLocation = frsRequest.displayLocation,
      etwittercludedIds = frsRequest.etwittercludedIds,
      fetchPromotedContent = frsRequest.fetchPromotedContent,
      userLocationState = frsRequest.userLocationState
    )
  }

  private def convertToProductMitwittererDebugParams(
    frsDebugParams: Option[FrsDebugParams]
  ): Option[ProductMitwittererDebugParams] = {
    frsDebugParams.map { debugParams =>
      ProductMitwittererDebugParams(debugParams.featureOverrides, None)
    }
  }
}
