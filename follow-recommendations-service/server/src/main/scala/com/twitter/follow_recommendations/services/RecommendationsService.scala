package com.twitter.follow_recommendations.services

import com.twitter.follow_recommendations.configapi.deciders.DeciderParams
import com.twitter.follow_recommendations.logging.FrsLogger
import com.twitter.follow_recommendations.models.{RecommendationRequest, RecommendationResponse}
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params
import javax.inject.{Inject, Singleton}

@Singleton
class RecommendationsService @Inject()(
  productRecommenderService: ProductRecommenderService,
  resultLogger: FrsLogger
) {

  type Result[A] = Either[String, A]
  type Recommendations = Result[Stitch[RecommendationResponse]]

  def getRecommendations(request: RecommendationRequest, params: Params): Recommendations = {
    if (params(DeciderParams.EnableRecommendations)) {
      try {
        val response = productRecommenderService.getRecommendations(request, params).map(RecommendationResponse)
        resultLogger.logRecommendationResult(request, response)
        Right(response)
      } catch {
        case e: Exception => Left(e.getMessage)
      }
    } else Right(Stitch.value(RecommendationResponse(Nil)))
  }
}
