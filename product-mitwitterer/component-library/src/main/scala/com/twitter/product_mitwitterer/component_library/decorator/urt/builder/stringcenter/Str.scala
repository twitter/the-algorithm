package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.stringcenter

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.stringcenter.BaseStringCenterPlaceholderBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stringcenter.client.StringCenter
import com.twitter.stringcenter.client.core.EtwitterternalString

case class StrStatic(
  tetwittert: String)
    etwittertends BaseStr[PipelineQuery, UniversalNoun[Any]] {
  def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap
  ): String = tetwittert
}

case class Str[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  tetwittert: EtwitterternalString,
  stringCenter: StringCenter,
  stringCenterPlaceholderBuilder: Option[BaseStringCenterPlaceholderBuilder[Query, Candidate]] =
    None)
    etwittertends BaseStr[Query, Candidate] {

  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): String = {
    val placeholderMapOpt =
      stringCenterPlaceholderBuilder.map(_.apply(query, candidate, candidateFeatures))
    stringCenter.prepare(
      etwitterternalString = tetwittert,
      placeholders = placeholderMapOpt.getOrElse(Map.empty[String, Any])
    )
  }
}
