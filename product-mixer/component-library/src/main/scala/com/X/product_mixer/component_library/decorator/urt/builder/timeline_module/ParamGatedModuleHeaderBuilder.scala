package com.X.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderBuilder
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.Param

case class ParamGatedModuleHeaderBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  enableParam: Param[Boolean],
  enabledBuilder: BaseModuleHeaderBuilder[Query, Candidate],
  defaultBuilder: Option[BaseModuleHeaderBuilder[Query, Candidate]] = None)
    extends BaseModuleHeaderBuilder[Query, Candidate] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[ModuleHeader] = {
    if (query.params(enableParam)) {
      enabledBuilder(query, candidates)
    } else {
      defaultBuilder.flatMap(_.apply(query, candidates))
    }
  }
}
