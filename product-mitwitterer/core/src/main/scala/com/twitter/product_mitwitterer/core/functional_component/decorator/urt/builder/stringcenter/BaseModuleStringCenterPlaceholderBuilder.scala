package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.stringcenter

import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseModuleStringCenterPlaceholderBuilder[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidates: Seq[CandidateWithFeatures[Candidate]]): Map[String, Any]
}
