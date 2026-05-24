package com.twitter.product_mixer.component_library.decorator.urt.builder.icon

import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.icon.BaseHorizonIconBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class HorizonIconBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  icon: HorizonIcon)
    extends BaseHorizonIconBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Option[HorizonIcon] = Some(icon)
}
