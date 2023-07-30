package com.X.product_mixer.component_library.decorator.urt.builder.timeline_module

import com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleHeaderDisplayTypeBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.Classic
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeaderDisplayType
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.pipeline.PipelineQuery

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
