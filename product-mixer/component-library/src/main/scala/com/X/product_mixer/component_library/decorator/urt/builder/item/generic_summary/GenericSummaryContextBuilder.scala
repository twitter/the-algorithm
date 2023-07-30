package com.X.product_mixer.component_library.decorator.urt.builder.item.generic_summary

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.richtext.BaseRichTextBuilder
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.X.product_mixer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryContext
import com.X.product_mixer.core.pipeline.PipelineQuery

case class GenericSummaryContextBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  richTextBuilder: BaseRichTextBuilder[Query, Candidate],
  icon: Option[HorizonIcon] = None) {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): GenericSummaryContext = GenericSummaryContext(
    richTextBuilder.apply(query, candidate, candidateFeatures),
    icon
  )
}
