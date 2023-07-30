package com.X.follow_recommendations.services

import com.X.follow_recommendations.configapi.deciders.DeciderParams
import com.X.follow_recommendations.logging.FrsLogger
import com.X.follow_recommendations.models.RecommendationRequest
import com.X.follow_recommendations.models.RecommendationResponse
import com.X.stitch.Stitch
import com.X.timelines.configapi.Params
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationsService @Inject() (
  productRecommenderService: ProductRecommenderService,
  resultLogger: FrsLogger) {
  def get(request: RecommendationRequest, params: Params): Stitch[RecommendationResponse] = {
    if (params(DeciderParams.EnableRecommendations)) {
      productRecommenderService
        .getRecommendations(request, params).map(RecommendationResponse).onSuccess { response =>
          if (resultLogger.shouldLog(request.debugParams)) {
            resultLogger.logRecommendationResult(request, response)
          }
        }
    } else {
      Stitch.value(RecommendationResponse(Nil))
    }
  }
}
