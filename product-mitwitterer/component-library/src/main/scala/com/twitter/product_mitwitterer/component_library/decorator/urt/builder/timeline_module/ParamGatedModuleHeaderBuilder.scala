package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderBuilder
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

case class ParamGatedModuleHeaderBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  enableParam: Param[Boolean],
  enabledBuilder: BaseModuleHeaderBuilder[Query, Candidate],
  defaultBuilder: Option[BaseModuleHeaderBuilder[Query, Candidate]] = None)
    etwittertends BaseModuleHeaderBuilder[Query, Candidate] {

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
