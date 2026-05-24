package com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait BaseClientEventDetailsBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  /**
   * @return a [[ClientEventDetails]] for the provided [[Candidate]]
   * @see [[ClientEventDetails]]
   */
  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[ClientEventDetails]
}
