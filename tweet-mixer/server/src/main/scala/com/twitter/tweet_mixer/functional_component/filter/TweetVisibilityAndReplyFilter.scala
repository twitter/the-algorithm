package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.FromInNetworkSourceFeature
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.IsReply
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.isFeatureSet
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableDebugMode

/**
 * Filters out tweets that has reply column unfilled
 */
case class TweetVisibilityAndReplyFilter(
  candidatePipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty)
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate]
    with ShouldIgnoreCandidatePipelinesFilter {

  override val identifier: FilterIdentifier = FilterIdentifier("TweetVisibilityPolicy")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = !query.params(EnableDebugMode)

  private def isVisible(tweetBooleanInfo: Option[Int]): Boolean = {
    tweetBooleanInfo.isDefined
  }
  private def isNotReply(tweetBooleanInfo: Option[Int]): Boolean = {
    val isReply = isFeatureSet(IsReply, tweetBooleanInfo.getOrElse(0))
    !isReply
  }

  private def isInNetwork(candidate: CandidateWithFeatures[TweetCandidate]): Boolean =
    candidate.features.getOrElse(FromInNetworkSourceFeature, false)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (kept, removed) = candidates.partition { candidate =>
      val tweetBooleanInfo = candidate.features.getOrElse(TweetBooleanInfoFeature, None)
      isInNetwork(candidate) || (isVisible(tweetBooleanInfo) && isNotReply(
        tweetBooleanInfo)) || shouldIgnore(candidate)
    }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
