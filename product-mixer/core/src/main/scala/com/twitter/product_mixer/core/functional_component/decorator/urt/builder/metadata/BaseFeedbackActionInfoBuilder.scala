package com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait BaseFeedbackActionInfoBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo]
}
