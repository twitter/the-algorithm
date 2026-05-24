package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.ImpressionBloomFilterFeature
import com.twitter.home_mixer.model.HomeFeatures.UserRecentEngagementTweetIdsFeature
import com.twitter.home_mixer.model.request.HasSeenTweetIds
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilterItem

/**
 * Filter out users' previously seen tweets from Impression Bloom Filter
 */
object PreviouslySeenTweetsFilter
    extends Filter[PipelineQuery with HasSeenTweetIds, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslySeenTweets")

  override def apply(
    query: PipelineQuery with HasSeenTweetIds,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val bloomFilterSeq = query.features.map(_.get(ImpressionBloomFilterFeature)).get
    val bloomFilters =
      bloomFilterSeq.entries.map(ImpressionBloomFilterItem.fromThrift(_).bloomFilter)

    val seenTweetIds = query.seenTweetIds.getOrElse(Seq.empty).toSet
    val engagedTweetIds =
      query.features
        .map(_.getOrElse(UserRecentEngagementTweetIdsFeature, Seq.empty).toSet)
        .getOrElse(Set.empty)

    val (removed, kept) = candidates.partition { candidate =>
      CandidatesUtil.getTweetIdAndSourceId(candidate).exists { tweetId =>
        seenTweetIds.contains(tweetId) || engagedTweetIds.contains(tweetId) ||
        bloomFilters.exists(filter => filter.mayContain(tweetId))
      }
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
