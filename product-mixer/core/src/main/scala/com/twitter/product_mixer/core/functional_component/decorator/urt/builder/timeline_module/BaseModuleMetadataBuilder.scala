try {
package com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module

import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleMetadata
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait BaseModuleMetadataBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): ModuleMetadata
}

} catch {
  case e: Exception =>
}
