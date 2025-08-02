package com.twitter.home_mitwitterer.product.scored_tweets.filter

import com.twitter.home_mitwitterer.model.HomeFeatures._
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsSocialContetwittertFilter etwittertends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("ScoredTweetsSocialContetwittert")

  // Tweets from candidate sources which don't need generic like/follow/topic proof
  private val AllowedSources: Set[st.SuggestType] = Set(
    st.SuggestType.RankedListTweet,
    st.SuggestType.RecommendedTrendTweet,
    st.SuggestType.MediaTweet
  )

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val validTweetIds = candidates
      .filter { candidate =>
        candidate.features.getOrElse(InNetworkFeature, true) ||
        candidate.features.getOrElse(SuggestTypeFeature, None).etwitterists(AllowedSources.contains) ||
        candidate.features.getOrElse(InReplyToTweetIdFeature, None).isDefined ||
        hasLikedBySocialContetwittert(candidate.features) ||
        hasFollowedBySocialContetwittert(candidate.features) ||
        hasTopicSocialContetwittert(candidate.features)
      }.map(_.candidate.id).toSet

    val (kept, removed) =
      candidates.map(_.candidate).partition(candidate => validTweetIds.contains(candidate.id))

    Stitch.value(FilterResult(kept = kept, removed = removed))
  }

  private def hasLikedBySocialContetwittert(candidateFeatures: FeatureMap): Boolean =
    candidateFeatures
      .getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty)
      .etwitterists(
        candidateFeatures
          .getOrElse(PerspectiveFilteredLikedByUserIdsFeature, Seq.empty)
          .toSet.contains
      )

  private def hasFollowedBySocialContetwittert(candidateFeatures: FeatureMap): Boolean =
    candidateFeatures.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty).nonEmpty

  private def hasTopicSocialContetwittert(candidateFeatures: FeatureMap): Boolean = {
    candidateFeatures.getOrElse(TopicIdSocialContetwittertFeature, None).isDefined &&
    candidateFeatures.getOrElse(TopicContetwittertFunctionalityTypeFeature, None).isDefined
  }
}
