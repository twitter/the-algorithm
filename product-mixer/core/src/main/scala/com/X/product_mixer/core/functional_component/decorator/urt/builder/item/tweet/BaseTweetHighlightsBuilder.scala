package com.X.product_mixer.core.functional_component.decorator.urt.builder.item.tweet

import com.X.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetHighlights
import com.X.product_mixer.core.pipeline.PipelineQuery

trait BaseTweetHighlightsBuilder[-Query <: PipelineQuery, -Candidate <: BaseTweetCandidate] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[TweetHighlights]
}
