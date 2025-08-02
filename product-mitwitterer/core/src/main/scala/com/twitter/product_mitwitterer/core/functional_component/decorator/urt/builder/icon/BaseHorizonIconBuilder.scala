package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.icon

import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseHorizonIconBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[HorizonIcon]
}
