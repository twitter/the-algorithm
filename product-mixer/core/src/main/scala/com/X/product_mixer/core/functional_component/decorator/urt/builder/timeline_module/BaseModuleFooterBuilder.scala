package com.X.product_mixer.core.functional_component.decorator.urt.builder.timeline_module

import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleFooter
import com.X.product_mixer.core.pipeline.PipelineQuery

trait BaseModuleFooterBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[ModuleFooter]
}
