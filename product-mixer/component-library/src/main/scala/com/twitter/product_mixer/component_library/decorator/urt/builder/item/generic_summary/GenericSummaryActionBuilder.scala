package com.twitter.product_mixer.component_library.decorator.urt.builder.item.generic_summary

import com.twitter.product_mixer.component_library.decorator.urt.builder.item.generic_summary.GenericSummaryActionBuilder.GenericSummaryActionClientEventInfoElement
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseUrlBuilder
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryAction
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object GenericSummaryActionBuilder {
  val GenericSummaryActionClientEventInfoElement: String = "genericsummary-action"
}

case class GenericSummaryActionBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  urlBuilder: BaseUrlBuilder[Query, Candidate],
  clientEventInfoBuilder: Option[BaseClientEventInfoBuilder[Query, Candidate]] = None) {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): GenericSummaryAction = GenericSummaryAction(
    url = urlBuilder.apply(query, candidate, candidateFeatures),
    clientEventInfo = clientEventInfoBuilder.flatMap(
      _.apply(
        query,
        candidate,
        candidateFeatures,
        Some(GenericSummaryActionClientEventInfoElement)))
  )
}
