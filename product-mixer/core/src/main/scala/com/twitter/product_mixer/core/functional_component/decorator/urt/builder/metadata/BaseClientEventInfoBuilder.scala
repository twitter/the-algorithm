package com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait BaseClientEventInfoBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  /**
   * @return a [[ClientEventInfo]] for the provided [[Candidate]]
   * @see [[ClientEventInfo]]
   */
  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap,
    element: Option[String]
  ): Option[ClientEventInfo]
}
