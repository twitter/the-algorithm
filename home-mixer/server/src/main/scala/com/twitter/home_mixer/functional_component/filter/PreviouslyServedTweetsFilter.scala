package com.ExTwitter.home_mixer.functional_component.filter

import com.ExTwitter.home_mixer.model.HomeFeatures.GetOlderFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.ServedTweetIdsFeature
import com.ExTwitter.home_mixer.util.CandidatesUtil
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.filter.FilterResult
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

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
