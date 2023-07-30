package com.X.product_mixer.core.functional_component.decorator.urt.builder.icon

import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.X.product_mixer.core.pipeline.PipelineQuery

trait BaseHorizonIconBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[HorizonIcon]
}
