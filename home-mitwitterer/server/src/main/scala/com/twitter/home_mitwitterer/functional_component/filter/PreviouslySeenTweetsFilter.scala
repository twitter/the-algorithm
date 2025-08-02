package com.twitter.home_mitwitterer.functional_component.filter

import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.home_mitwitterer.util.TweetImpressionsHelper
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Filter out users' previously seen tweets from 2 sources:
 * 1. Heron Topology Impression Store in Memcache;
 * 2. Manhattan Impression Store;
 */
object PreviouslySeenTweetsFilter etwittertends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslySeenTweets")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val seenTweetIds =
      query.features.map(TweetImpressionsHelper.tweetImpressions).getOrElse(Set.empty)

    val (removed, kept) = candidates.partition { candidate =>
      val tweetIdAndSourceId = CandidatesUtil.getTweetIdAndSourceId(candidate)
      tweetIdAndSourceId.etwitterists(seenTweetIds.contains)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
