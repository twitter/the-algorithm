package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.TweetInfoFeatures._
import com.twitter.tweet_mixer.model.request.HasVideoType
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnablePortraitVideoFilter
import com.twitter.tweet_mixer.{thriftscala => t}

/**
 * Filters out tweets that are not portrait videos for the given set of candidate pipelines
 */
object IsPortraitVideoFilter
    extends Filter[PipelineQuery with HasVideoType, TweetCandidate]
    with Filter.Conditionally[PipelineQuery with HasVideoType, TweetCandidate] {
  override val identifier: FilterIdentifier = FilterIdentifier("IsPortraitVideo")

  override def onlyIf(
    query: PipelineQuery with HasVideoType,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.params(EnablePortraitVideoFilter) || query.videoType.contains(t.VideoType.ShortForm)
  }

  def isPortraitVideo(features: FeatureMap): Boolean = {
    val tweetBooleanInfo = features.get(TweetBooleanInfoFeature).getOrElse(0)
    val hasVideo = isFeatureSet(HasVideo, tweetBooleanInfo)
    val isPortraitVideo = !isFeatureSet(IsLandscapeVideo, tweetBooleanInfo)
    hasVideo && isPortraitVideo
  }

  override def apply(
    query: PipelineQuery with HasVideoType,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (kept, removed) = candidates
      .partition { candidate => isPortraitVideo(candidate.features) }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
