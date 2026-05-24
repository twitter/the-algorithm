package com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleFooter
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseUrlBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleFooterBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures

case class ModuleFooterBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  textBuilder: BaseStr[Query, Candidate],
  urlBuilder: Option[BaseUrlBuilder[Query, Candidate]])
    extends BaseModuleFooterBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[ModuleFooter] = {
    candidates.headOption.map { candidate =>
      ModuleFooter(
        text = textBuilder(query, candidate.candidate, candidate.features),
        landingUrl = urlBuilder.map(_.apply(query, candidate.candidate, candidate.features))
      )
    }
  }
}
