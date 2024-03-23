package com.ExTwitter.product_mixer.component_library.filter
import com.ExTwitter.product_mixer.component_library.feature_hydrator.candidate.tweet_tweetypie.IsReplyFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.filter.FilterResult
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

/**
 * Filters out tweets that is a reply to a tweet
 */
case class TweetIsNotReplyFilter[Candidate <: BaseTweetCandidate]()
    extends Filter[PipelineQuery, Candidate] {
  override val identifier: FilterIdentifier = FilterIdentifier("TweetIsNotReply")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {

    val (kept, removed) = candidates
      .partition { candidate =>
        !candidate.features.get(IsReplyFeature)
      }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }

}
