package com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleFooterBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleFooter
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

case class ParamGatedModuleFooterBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  enableParam: Param[Boolean],
  enabledBuilder: BaseModuleFooterBuilder[Query, Candidate],
  defaultBuilder: Option[BaseModuleFooterBuilder[Query, Candidate]] = None)
    extends BaseModuleFooterBuilder[Query, Candidate] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[ModuleFooter] = {
    if (query.params(enableParam)) {
      enabledBuilder(query, candidates)
    } else {
      defaultBuilder.flatMap(_.apply(query, candidates))
    }
  }
}
