package com.twitter.home_mitwitterer.functional_component.filter

import com.twitter.home_mitwitterer.model.HomeFeatures.GetOlderFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.ServedTweetIdsFeature
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object PreviouslyServedTweetsFilter
    etwittertends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslyServedTweets")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.features.etwitterists(_.getOrElse(GetOlderFeature, false))
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val servedTweetIds =
      query.features.map(_.getOrElse(ServedTweetIdsFeature, Seq.empty)).toSeq.flatten.toSet

    val (removed, kept) = candidates.partition { candidate =>
      val tweetIdAndSourceId = CandidatesUtil.getTweetIdAndSourceId(candidate)
      tweetIdAndSourceId.etwitterists(servedTweetIds.contains)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
