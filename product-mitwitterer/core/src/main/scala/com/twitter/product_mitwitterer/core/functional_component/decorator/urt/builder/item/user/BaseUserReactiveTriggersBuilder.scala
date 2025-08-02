package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.user

import com.twitter.product_mitwitterer.component_library.model.candidate.BaseUserCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user.UserReactiveTriggers
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseUserReactiveTriggersBuilder[-Query <: PipelineQuery, -Candidate <: BaseUserCandidate] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[UserReactiveTriggers]
}
