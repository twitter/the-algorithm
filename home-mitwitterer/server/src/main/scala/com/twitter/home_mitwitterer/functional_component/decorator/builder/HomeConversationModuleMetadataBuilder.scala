package com.twitter.home_mitwitterer.functional_component.decorator.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.AncestorsFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleMetadataBuilder
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleConversationMetadata
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleMetadata
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class HomeConversationModuleMetadataBuilder[
  -Query <: PipelineQuery,
  -Candidate <: BaseTweetCandidate
]() etwittertends BaseModuleMetadataBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): ModuleMetadata = ModuleMetadata(
    adsMetadata = None,
    conversationMetadata = Some(
      ModuleConversationMetadata(
        allTweetIds = Some((candidates.last.candidate.id +:
          candidates.last.features.getOrElse(AncestorsFeature, Seq.empty).map(_.tweetId)).reverse),
        socialContetwittert = None,
        enableDeduplication = Some(true)
      )),
    gridCarouselMetadata = None
  )
}
