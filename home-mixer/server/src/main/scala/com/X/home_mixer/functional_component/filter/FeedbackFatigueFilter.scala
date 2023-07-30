package com.X.home_mixer.functional_component.filter

import com.X.conversions.DurationOps._
import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.FeedbackHistoryFeature
import com.X.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.X.home_mixer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.X.home_mixer.util.CandidatesUtil
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelines.common.thriftscala.FeedbackEntity
import com.X.timelineservice.model.FeedbackEntry
import com.X.timelineservice.{thriftscala => tls}

object FeedbackFatigueFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("FeedbackFatigue")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean =
    query.features.exists(_.getOrElse(FeedbackHistoryFeature, Seq.empty).nonEmpty)

  private val DurationForFiltering = 14.days

  override def apply(
    query: pipeline.PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val feedbackEntriesByEngagementType =
      query.features
        .getOrElse(FeatureMap.empty).getOrElse(FeedbackHistoryFeature, Seq.empty)
        .filter { entry =>
          val timeSinceFeedback = query.queryTime.minus(entry.timestamp)
          timeSinceFeedback < DurationForFiltering &&
          entry.feedbackType == tls.FeedbackType.SeeFewer
        }.groupBy(_.engagementType)

    val authorsToFilter =
      getUserIds(
        feedbackEntriesByEngagementType.getOrElse(tls.FeedbackEngagementType.Tweet, Seq.empty))
    val likersToFilter =
      getUserIds(
        feedbackEntriesByEngagementType.getOrElse(tls.FeedbackEngagementType.Like, Seq.empty))
    val followersToFilter =
      getUserIds(
        feedbackEntriesByEngagementType.getOrElse(tls.FeedbackEngagementType.Follow, Seq.empty))
    val retweetersToFilter =
      getUserIds(
        feedbackEntriesByEngagementType.getOrElse(tls.FeedbackEngagementType.Retweet, Seq.empty))

    val (removed, kept) = candidates.partition { candidate =>
      val originalAuthorId = CandidatesUtil.getOriginalAuthorId(candidate.features)
      val authorId = candidate.features.getOrElse(AuthorIdFeature, None)

      val likers = candidate.features.getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty)
      val eligibleLikers = likers.filterNot(likersToFilter.contains)

      val followers = candidate.features.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty)
      val eligibleFollowers = followers.filterNot(followersToFilter.contains)

      originalAuthorId.exists(authorsToFilter.contains) ||
      (likers.nonEmpty && eligibleLikers.isEmpty) ||
      (followers.nonEmpty && eligibleFollowers.isEmpty && likers.isEmpty) ||
      (candidate.features.getOrElse(IsRetweetFeature, false) &&
      authorId.exists(retweetersToFilter.contains))
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }

  private def getUserIds(
    feedbackEntries: Seq[FeedbackEntry],
  ): Set[Long] =
    feedbackEntries.collect {
      case FeedbackEntry(_, _, FeedbackEntity.UserId(userId), _, _) => userId
    }.toSet
}
