package com.ExTwitter.follow_recommendations.services

import com.ExTwitter.follow_recommendations.configapi.deciders.DeciderParams
import com.ExTwitter.follow_recommendations.logging.FrsLogger
import com.ExTwitter.follow_recommendations.models.RecommendationRequest
import com.ExTwitter.follow_recommendations.models.RecommendationResponse
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.Params
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
