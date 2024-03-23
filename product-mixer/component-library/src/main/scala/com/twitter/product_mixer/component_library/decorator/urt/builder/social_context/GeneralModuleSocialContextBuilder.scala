package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.social_context

import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseModuleStr
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseModuleSocialContextBuilder
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.GeneralContext
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.GeneralContextType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

/**
 * This class works the same as [[GeneralSocialContextBuilder]] but passes a list of candidates
 * into [[BaseModuleStr]] when rendering the string.
 */
case class GeneralModuleSocialContextBuilder[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any]
](
  textBuilder: BaseModuleStr[Query, Candidate],
  contextType: GeneralContextType,
  url: Option[String] = None,
  contextImageUrls: Option[List[String]] = None,
  landingUrl: Option[Url] = None)
    extends BaseModuleSocialContextBuilder[Query, Candidate] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[GeneralContext] =
    Some(
      GeneralContext(
        text = textBuilder(query, candidates),
        contextType = contextType,
        url = url,
        contextImageUrls = contextImageUrls,
        landingUrl = landingUrl))
}
