package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert

import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseModuleSocialContetwittertBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[SocialContetwittert]
}
