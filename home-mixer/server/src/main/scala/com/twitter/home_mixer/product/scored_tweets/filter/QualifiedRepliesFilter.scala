package com.twitter.home_mixer.product.scored_tweets.filter

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsInReplyToReplyOrDirectedFeature
import com.twitter.home_mixer.model.HomeFeatures.IsInReplyToRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object QualifiedRepliesFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("QualifiedReplies")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val sgsFollowedUsers =
      query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty)).toSet.flatten

    val (removed, kept) = candidates.partition { candidate =>
      val isRetweet = candidate.features.getOrElse(IsRetweetFeature, false)
      val authorId = candidate.features.getOrElse(AuthorIdFeature, None).getOrElse(0L)
      val inReplyToUser = candidate.features.getOrElse(InReplyToUserIdFeature, None)
      val replyToFollowed = inReplyToUser.exists(sgsFollowedUsers.contains)
      val isValidReplyToUser = inReplyToUser.exists { user =>
        user != query.getRequiredUserId && user != authorId
      }
      val inReplyToRetweetOrReplyOrDirected =
        candidate.features.getOrElse(IsInReplyToReplyOrDirectedFeature, false) ||
          candidate.features.getOrElse(IsInReplyToRetweetFeature, false)

      inReplyToUser.nonEmpty && !replyToFollowed && sgsFollowedUsers.contains(authorId) &&
      (isRetweet || !isValidReplyToUser || inReplyToRetweetOrReplyOrDirected)
    }
    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
