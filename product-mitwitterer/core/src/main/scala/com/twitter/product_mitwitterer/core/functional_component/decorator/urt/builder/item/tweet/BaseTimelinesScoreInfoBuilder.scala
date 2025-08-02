package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.tweet

import com.twitter.product_mitwitterer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TimelinesScoreInfo
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseTimelinesScoreInfoBuilder[-Query <: PipelineQuery, -Candidate <: BaseTweetCandidate] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[TimelinesScoreInfo]
}
