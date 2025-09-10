package com.twitter.tweet_mixer.functional_component.selector

import com.twitter.product_mixer.component_library.selector.sorter.SorterFromOrdering
import com.twitter.product_mixer.component_library.selector.sorter.SorterProvider
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.tweet_mixer.feature.ScoreFeature
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.HasVideo
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.isFeatureSet

object UprankVideoSorterProvider extends SorterProvider {

  private val VideoWeight = 1.8

  override def sorter(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SorterFromOrdering = SorterFromOrdering(
    Ordering.by[CandidateWithDetails, Double] {
      case ItemCandidateWithDetails(_, _, features) =>
        val tweetBooleanInfo = features.getOrElse(TweetBooleanInfoFeature, None).getOrElse(0)
        val hasVideo = isFeatureSet(HasVideo, tweetBooleanInfo)
        val score = features.getOrElse(ScoreFeature, 0.0)
        val updatedScore = if (hasVideo) score * VideoWeight else score
        -updatedScore
      case _ => throw PipelineFailure(IllegalStateFailure, "Unexpected candidate type")
    }
  )
}
