package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata

import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseModuleStr[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidates: Seq[CandidateWithFeatures[Candidate]]): String
}
