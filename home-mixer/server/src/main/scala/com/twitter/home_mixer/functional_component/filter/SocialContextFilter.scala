package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.ConversationModuleFocalTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object SocialContextFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("SocialContext")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val validTweetIds = candidates
      .filter { candidate =>
        candidate.features.getOrElse(InNetworkFeature, true) ||
        hasLikedBySocialContext(candidate.features) ||
        hasFollowedBySocialContext(candidate.features) ||
        hasTopicSocialContext(candidate.features) ||
        candidate.features.getOrElse(ConversationModuleFocalTweetIdFeature, None).isDefined
      }.map(_.candidate.id).toSet

    val (kept, removed) =
      candidates.map(_.candidate).partition(candidate => validTweetIds.contains(candidate.id))

    Stitch.value(FilterResult(kept = kept, removed = removed))
  }

  private def hasLikedBySocialContext(candidateFeatures: FeatureMap): Boolean =
    candidateFeatures
      .getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty)
      .exists(
        candidateFeatures
          .getOrElse(PerspectiveFilteredLikedByUserIdsFeature, Seq.empty)
          .toSet.contains
      )

  private def hasFollowedBySocialContext(candidateFeatures: FeatureMap): Boolean =
    candidateFeatures.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty).nonEmpty

  private def hasTopicSocialContext(candidateFeatures: FeatureMap): Boolean =
    candidateFeatures.getOrElse(TopicIdSocialContextFeature, None).isDefined &&
      candidateFeatures.getOrElse(TopicContextFunctionalityTypeFeature, None).isDefined
}
