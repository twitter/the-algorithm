package com.twitter.product_mixer.component_library.decorator.urt.builder.conversations

import com.twitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleMetadataBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleConversationMetadata
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleMetadata
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class ConversationModuleMetadataBuilder[
  Query <: PipelineQuery,
  Candidate <: BaseTweetCandidate
](
  ancestorIdsFeature: Feature[_, Seq[Long]],
  allIdsOrdering: Ordering[Long])
    extends BaseModuleMetadataBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): ModuleMetadata = {

    val ancestors = candidates.last.features.getOrElse(ancestorIdsFeature, Seq.empty)
    val sortedAllTweetIds = (candidates.last.candidate.id +: ancestors).sorted(allIdsOrdering)

    ModuleMetadata(
      adsMetadata = None,
      conversationMetadata = Some(
        ModuleConversationMetadata(
          allTweetIds = Some(sortedAllTweetIds),
          socialContext = None,
          enableDeduplication = Some(true)
        )),
      gridCarouselMetadata = None
    )
  }
}
