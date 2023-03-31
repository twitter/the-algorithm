package com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderDisplayTypeBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Classic
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeaderDisplayType
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class ModuleHeaderDisplayTypeBuilder[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any]
](
  moduleHeaderDisplayType: ModuleHeaderDisplayType = Classic)
    extends BaseModuleHeaderDisplayTypeBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): ModuleHeaderDisplayType = moduleHeaderDisplayType

}
