package com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module

import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.X.product_mixer.core.pipeline.PipelineQuery

trait BaseModuleHeaderBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[ModuleHeader]
}
