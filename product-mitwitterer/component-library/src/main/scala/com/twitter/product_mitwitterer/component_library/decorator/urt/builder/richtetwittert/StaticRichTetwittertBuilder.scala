package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class StaticRichTetwittertBuilder(richTetwittert: RichTetwittert)
    etwittertends BaseRichTetwittertBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap
  ): RichTetwittert = richTetwittert
}
