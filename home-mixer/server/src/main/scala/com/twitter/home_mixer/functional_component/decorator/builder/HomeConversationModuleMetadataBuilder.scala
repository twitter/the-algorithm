package com.ExTwitter.home_mixer.functional_component.decorator.builder

import com.ExTwitter.home_mixer.model.HomeFeatures.AncestorsFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleMetadataBuilder
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleConversationMetadata
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleMetadata
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

case class HomeConversationModuleMetadataBuilder[
  -Query <: PipelineQuery,
  -Candidate <: BaseTweetCandidate
]() extends BaseModuleMetadataBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): ModuleMetadata = ModuleMetadata(
    adsMetadata = None,
    conversationMetadata = Some(
      ModuleConversationMetadata(
        allTweetIds = Some((candidates.last.candidate.id +:
          candidates.last.features.getOrElse(AncestorsFeature, Seq.empty).map(_.tweetId)).reverse),
        socialContext = None,
        enableDeduplication = Some(true)
      )),
    gridCarouselMetadata = None
  )
}
