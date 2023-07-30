package com.X.follow_recommendations.common.transforms.recommendation_flow_identifier

import com.google.inject.Inject
import com.X.follow_recommendations.common.base.Transform
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasRecommendationFlowIdentifier
import com.X.stitch.Stitch

class AddRecommendationFlowIdentifierTransform @Inject()
    extends Transform[HasRecommendationFlowIdentifier, CandidateUser] {

  override def transform(
    target: HasRecommendationFlowIdentifier,
    items: Seq[CandidateUser]
  ): Stitch[Seq[CandidateUser]] = {
    Stitch.value(items.map { candidateUser =>
      candidateUser.copy(recommendationFlowIdentifier = target.recommendationFlowIdentifier)
    })
  }
}
