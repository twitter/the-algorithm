package com.X.product_mixer.core.functional_component.decorator.urt.builder.item.user

import com.X.product_mixer.component_library.model.candidate.BaseUserCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.marshalling.response.urt.item.user.UserReactiveTriggers
import com.X.product_mixer.core.pipeline.PipelineQuery

trait BaseUserReactiveTriggersBuilder[-Query <: PipelineQuery, -Candidate <: BaseUserCandidate] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[UserReactiveTriggers]
}
