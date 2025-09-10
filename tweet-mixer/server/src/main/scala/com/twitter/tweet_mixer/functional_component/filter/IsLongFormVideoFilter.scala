package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.TweetInfoFeatures._
import com.twitter.tweet_mixer.model.request.HasVideoType
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableLongFormVideoFilter
import com.twitter.tweet_mixer.{thriftscala => t}

/**
 * Filters out tweets that are not long videos for the given set of candidate pipelines
 */
case class IsLongFormVideoFilter(
  candidatePipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty)
    extends Filter[PipelineQuery with HasVideoType, TweetCandidate]
    with Filter.Conditionally[PipelineQuery with HasVideoType, TweetCandidate]
    with ShouldIgnoreCandidatePipelinesFilter {
  override val identifier: FilterIdentifier = FilterIdentifier("IsLongFormVideo")

  override def onlyIf(
    query: PipelineQuery with HasVideoType,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.params(EnableLongFormVideoFilter) || query.videoType.contains(t.VideoType.LongForm)
  }

  def isLongFormVideo(features: FeatureMap): Boolean = {
    val tweetBooleanInfo = features.get(TweetBooleanInfoFeature).getOrElse(0)
    isFeatureSet(IsLongFormVideo, tweetBooleanInfo)
  }

  override def apply(
    query: PipelineQuery with HasVideoType,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (kept, removed) = candidates
      .partition { candidate => isLongFormVideo(candidate.features) || shouldIgnore(candidate) }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
