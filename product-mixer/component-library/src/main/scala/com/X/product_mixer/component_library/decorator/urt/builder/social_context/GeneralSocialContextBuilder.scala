package com.X.product_mixer.component_library.decorator.urt.builder.social_context

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.X.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.GeneralContext
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.GeneralContextType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.X.product_mixer.core.pipeline.PipelineQuery

case class GeneralSocialContextBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  textBuilder: BaseStr[Query, Candidate],
  contextType: GeneralContextType,
  url: Option[String] = None,
  contextImageUrls: Option[List[String]] = None,
  landingUrl: Option[Url] = None)
    extends BaseSocialContextBuilder[Query, Candidate] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[GeneralContext] =
    Some(
      GeneralContext(
        text = textBuilder(query, candidate, candidateFeatures),
        contextType = contextType,
        url = url,
        contextImageUrls = contextImageUrls,
        landingUrl = landingUrl))
}
