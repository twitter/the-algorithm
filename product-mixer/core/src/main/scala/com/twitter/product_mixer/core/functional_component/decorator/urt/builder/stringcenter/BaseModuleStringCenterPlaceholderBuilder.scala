package com.twitter.product_mixer.core.functional_component.decorator.urt.builder.stringcenter

import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait BaseModuleStringCenterPlaceholderBuilder[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidates: Seq[CandidateWithFeatures[Candidate]]): Map[String, Any]
}
