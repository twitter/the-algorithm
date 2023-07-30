package com.X.home_mixer.functional_component.filter

import com.X.home_mixer.model.HomeFeatures.GetOlderFeature
import com.X.home_mixer.model.HomeFeatures.ServedTweetIdsFeature
import com.X.home_mixer.util.CandidatesUtil
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

object PreviouslyServedTweetsFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslyServedTweets")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.features.exists(_.getOrElse(GetOlderFeature, false))
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val servedTweetIds =
      query.features.map(_.getOrElse(ServedTweetIdsFeature, Seq.empty)).toSeq.flatten.toSet

    val (removed, kept) = candidates.partition { candidate =>
      val tweetIdAndSourceId = CandidatesUtil.getTweetIdAndSourceId(candidate)
      tweetIdAndSourceId.exists(servedTweetIds.contains)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
