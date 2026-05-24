package com.twitter.tweet_mixer.functional_component.selector

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.sorter.SorterFromOrdering
import com.twitter.product_mixer.component_library.selector.sorter.SorterProvider
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.HasVideo
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.IsLongVideo
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.isFeatureSet
import com.twitter.tweet_mixer.feature.HydraScoreFeature
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.VideoScoreWeightParam

object HydraBasedSorterProvider extends SorterProvider {

  // Should actually do this sort of stuff in hydra (YQian mentioned this sometime ago, save features in hydra on which scoring depends)
  def longVideo(features: FeatureMap): Boolean = {
    isFeatureSet(IsLongVideo, features.getOrElse(TweetBooleanInfoFeature, None).getOrElse(0))
  }

  def hasVideo(features: FeatureMap): Boolean = {
    isFeatureSet(HasVideo, features.getOrElse(TweetBooleanInfoFeature, None).getOrElse(0))
  }

  def bool2int(b: Boolean) = if (b) 1 else 0

  def sigmoid(x: Double): Double = {
    if (x < -45) {
      0.0
    } else if (x > 45) {
      1.0
    } else {
      1.0 / (1.0 + math.exp(-x))
    }
  }

  val IS_SELECTED_PREDS = "is_selected_preds"
  val IS_VIDEO_PREDS = "is_video_preds"

  override def sorter(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SorterFromOrdering = {

    val video_score = query.params(VideoScoreWeightParam)
    SorterFromOrdering(
      Ordering
        .by[CandidateWithDetails, Double] {
          case ItemCandidateWithDetails(candidate: TweetCandidate, _, features) =>
            val isLongVideo = bool2int(longVideo(features))
            val isVideo = bool2int(hasVideo(features))
            val hydraScoreMap = features.getOrElse(HydraScoreFeature, Map.empty[String, Double])
            val hydraScore = hydraScoreMap match {
              case scores if scores.isEmpty => Double.NegativeInfinity
              case scores if scores.size == 1 =>
                sigmoid(scores.values.sum) * (1 + video_score * isVideo)
              case scores if scores.size == 2 =>
                scores.getOrElse(
                  IS_SELECTED_PREDS,
                  Double.NegativeInfinity) + video_score * isLongVideo * scores
                  .getOrElse(IS_VIDEO_PREDS, Double.NegativeInfinity)
              case _ => throw PipelineFailure(IllegalStateFailure, "Not expected")
            }
            hydraScore
          case _ => throw PipelineFailure(IllegalStateFailure, "Unexpected candidate type")
        }.reverse
    )
  }
}
