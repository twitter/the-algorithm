package com.ExTwitter.follow_recommendations.controllers

import com.ExTwitter.finatra.thrift.Controller
import com.ExTwitter.follow_recommendations.configapi.ParamsFactory
import com.ExTwitter.follow_recommendations.services.ProductPipelineSelector
import com.ExTwitter.follow_recommendations.services.UserScoringService
import com.ExTwitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService
import com.ExTwitter.follow_recommendations.thriftscala.FollowRecommendationsThriftService._
import com.ExTwitter.stitch.Stitch
import javax.inject.Inject

class ThriftController @Inject() (
  userScoringService: UserScoringService,
  recommendationRequestBuilder: RecommendationRequestBuilder,
  scoringUserRequestBuilder: ScoringUserRequestBuilder,
  productPipelineSelector: ProductPipelineSelector,
  paramsFactory: ParamsFactory)
    extends Controller(FollowRecommendationsThriftService) {

  handle(GetRecommendations) { args: GetRecommendations.Args =>
    val stitch = recommendationRequestBuilder.fromThrift(args.request).flatMap { request =>
      val params = paramsFactory(
        request.clientContext,
        request.displayLocation,
        request.debugParams.flatMap(_.featureOverrides).getOrElse(Map.empty))
      productPipelineSelector.selectPipeline(request, params).map(_.toThrift)
    }
    Stitch.run(stitch)
  }

  handle(ScoreUserCandidates) { args: ScoreUserCandidates.Args =>
    val stitch = scoringUserRequestBuilder.fromThrift(args.request).flatMap { request =>
      val params = paramsFactory(
        request.clientContext,
        request.displayLocation,
        request.debugParams.flatMap(_.featureOverrides).getOrElse(Map.empty))
      userScoringService.get(request.copy(params = params)).map(_.toThrift)
    }
    Stitch.run(stitch)
  }
}
