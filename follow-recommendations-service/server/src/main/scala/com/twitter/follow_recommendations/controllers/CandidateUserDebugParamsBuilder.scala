package com.twitter.follow_recommendations.controllers

import com.twitter.follow_recommendations.common.models._
import com.twitter.follow_recommendations.configapi.ParamsFactory
import com.twitter.follow_recommendations.models.CandidateUserDebugParams
import com.twitter.follow_recommendations.models.FeatureValue
import com.twitter.follow_recommendations.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CandidateUserDebugParamsBuilder @Inject() (paramsFactory: ParamsFactory) {
  def fromThrift(req: t.ScoringUserRequest): CandidateUserDebugParams = {
    val clientContext = ClientContextConverter.fromThrift(req.clientContext)
    val displayLocation = DisplayLocation.fromThrift(req.displayLocation)

    CandidateUserDebugParams(req.candidates.map { candidate =>
      candidate.userId -> paramsFactory(
        clientContext,
        displayLocation,
        candidate.featureOverrides
          .map(_.mapValues(FeatureValue.fromThrift).toMap).getOrElse(Map.empty))
    }.toMap)
  }
}
