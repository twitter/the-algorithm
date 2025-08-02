package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.UrlType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertAlignment
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class RichTetwittertBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  tetwittertBuilder: BaseStr[Query, Candidate],
  linkMap: Map[String, String],
  rtl: Option[Boolean],
  alignment: Option[RichTetwittertAlignment],
  linkTypeMap: Map[String, UrlType] = Map.empty)
    etwittertends BaseRichTetwittertBuilder[Query, Candidate] {

  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): RichTetwittert = {
    RichTetwittertMarkupUtil.richTetwittertFromMarkup(
      tetwittert = tetwittertBuilder(query, candidate, candidateFeatures),
      linkMap = linkMap,
      rtl = rtl,
      alignment = alignment,
      linkTypeMap = linkTypeMap)
  }
}
