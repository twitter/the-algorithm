package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.social_contetwittert

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittertType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class GeneralSocialContetwittertBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  tetwittertBuilder: BaseStr[Query, Candidate],
  contetwittertType: GeneralContetwittertType,
  url: Option[String] = None,
  contetwittertImageUrls: Option[List[String]] = None,
  landingUrl: Option[Url] = None)
    etwittertends BaseSocialContetwittertBuilder[Query, Candidate] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[GeneralContetwittert] =
    Some(
      GeneralContetwittert(
        tetwittert = tetwittertBuilder(query, candidate, candidateFeatures),
        contetwittertType = contetwittertType,
        url = url,
        contetwittertImageUrls = contetwittertImageUrls,
        landingUrl = landingUrl))
}
