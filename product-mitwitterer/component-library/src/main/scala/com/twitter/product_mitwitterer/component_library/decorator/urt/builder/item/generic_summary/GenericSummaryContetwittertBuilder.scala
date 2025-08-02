package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.generic_summary

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryContetwittert
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class GenericSummaryContetwittertBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  richTetwittertBuilder: BaseRichTetwittertBuilder[Query, Candidate],
  icon: Option[HorizonIcon] = None) {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): GenericSummaryContetwittert = GenericSummaryContetwittert(
    richTetwittertBuilder.apply(query, candidate, candidateFeatures),
    icon
  )
}
