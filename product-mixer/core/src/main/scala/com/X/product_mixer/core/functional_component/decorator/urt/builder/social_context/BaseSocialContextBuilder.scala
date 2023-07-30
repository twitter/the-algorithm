package com.X.product_mixer.core.functional_component.decorator.urt.builder.social_context

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.X.product_mixer.core.pipeline.PipelineQuery

trait BaseSocialContextBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext]
}
