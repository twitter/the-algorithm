package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.social_contetwittert

import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseModuleStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseModuleSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittertType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

/**
 * This class works the same as [[GeneralSocialContetwittertBuilder]] but passes a list of candidates
 * into [[BaseModuleStr]] when rendering the string.
 */
case class GeneralModuleSocialContetwittertBuilder[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any]
](
  tetwittertBuilder: BaseModuleStr[Query, Candidate],
  contetwittertType: GeneralContetwittertType,
  url: Option[String] = None,
  contetwittertImageUrls: Option[List[String]] = None,
  landingUrl: Option[Url] = None)
    etwittertends BaseModuleSocialContetwittertBuilder[Query, Candidate] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[GeneralContetwittert] =
    Some(
      GeneralContetwittert(
        tetwittert = tetwittertBuilder(query, candidates),
        contetwittertType = contetwittertType,
        url = url,
        contetwittertImageUrls = contetwittertImageUrls,
        landingUrl = landingUrl))
}
