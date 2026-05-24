package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.TweetInfoFeatures._
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableVideoTweetFilter

/**
 * Filters out tweets that are not videos
 */
case class IsVideoTweetFilter(
  candidatePipelinesToInclude: Option[Set[CandidatePipelineIdentifier]] = None,
  candidatePipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty)
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate]
    with ShouldIgnoreCandidatePipelinesFilter {
  override val identifier: FilterIdentifier = FilterIdentifier("IsVideoTweet")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableVideoTweetFilter)

  def isVideo(features: FeatureMap): Boolean = {
    val tweetBooleanInfo = features.get(TweetBooleanInfoFeature).getOrElse(0)
    val isReply = isFeatureSet(IsReply, tweetBooleanInfo)
    val hasVideo = isFeatureSet(HasVideo, tweetBooleanInfo)
    val hasMultipleMedia = isFeatureSet(HasMultipleMedia, tweetBooleanInfo)
    val hasUrl = isFeatureSet(HasUrl, tweetBooleanInfo)
    val isHighMediaResolution = isFeatureSet(IsHighMediaResolution, tweetBooleanInfo)
    val isInCandidateScope = candidatePipelinesToInclude
      .map(candidatePipelines =>
        features.get(CandidatePipelines).exists(candidatePipelines.contains(_))).getOrElse(true)

    (!isReply && !hasMultipleMedia && !hasUrl && hasVideo && isHighMediaResolution) || !isInCandidateScope
  }
  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (kept, removed) = candidates
      .partition { candidate => isVideo(candidate.features) || shouldIgnore(candidate) }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
