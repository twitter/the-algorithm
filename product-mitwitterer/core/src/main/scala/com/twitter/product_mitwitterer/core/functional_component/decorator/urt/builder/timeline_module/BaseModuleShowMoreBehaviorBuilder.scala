package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module

import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleShowMoreBehavior
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseModuleShowMoreBehaviorBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): ModuleShowMoreBehavior
}
