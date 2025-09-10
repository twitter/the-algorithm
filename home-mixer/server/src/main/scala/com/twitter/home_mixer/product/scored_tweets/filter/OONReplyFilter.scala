package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * This filter removes recommended replies to not followed users
 */
object OONReplyFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("OONReply")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val sgsFollowedUsers =
      query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty)).toSet.flatten
    val (removed, kept) = candidates.partition { candidate =>
      val isValidRecommendedReply =
        !candidate.features.getOrElse(IsRetweetFeature, false) &&
          candidate.features
            .getOrElse(InReplyToUserIdFeature, None).exists(sgsFollowedUsers.contains)

      val isRecommendedReply =
        candidate.features.getOrElse(InReplyToTweetIdFeature, None).nonEmpty &&
          !candidate.features.getOrElse(AuthorIdFeature, None).exists(sgsFollowedUsers.contains)

      isRecommendedReply && !isValidRecommendedReply
    }
    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
