package com.twitter.tweet_mixer.functional_component.selector

import com.twitter.product_mixer.component_library.feature_hydrator.query.control_ai.UserControlAiFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.sorter.SorterFromTransformedOrdering
import com.twitter.product_mixer.component_library.selector.sorter.SorterProvider
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.timelines.control_ai.control.{thriftscala => ci}
import com.twitter.tweet_mixer.candidate_pipeline.ControlAiTopicCandidatePipelineConfig
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.feature.HydraScoreFeature
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.TweetInfoFeatures
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.isFeatureSet
import com.twitter.tweet_mixer.functional_component.transformer.ReplyFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ControlAiEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ControlAiShowLessWeightParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ControlAiShowMoreWeightParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ReplyScoreWeightParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.VideoScoreWeightParam
import scala.collection.immutable.ListSet

private case class ControlAiConfig(
  actions: Seq[ci.Action],
  actionTypeWeights: Map[ci.ActionType, Double])

object HydraBasedTransformedSorterProvider extends SorterProvider {

  private def hasBooleanFeature(features: FeatureMap, feature: String): Boolean = {
    isFeatureSet(feature, features.getOrElse(TweetBooleanInfoFeature, None).getOrElse(0))
  }

  private def bool2int(b: Boolean) = if (b) 1 else 0

  private def sigmoid(x: Double): Double = {
    if (x < -45) {
      0.0
    } else if (x > 45) {
      1.0
    } else {
      1.0 / (1.0 + math.exp(-x))
    }
  }

  private val IS_SELECTED_PREDS = "is_selected_preds"
  private val IS_VIDEO_PREDS = "is_video_preds"

  private def isControlAiMatch(
    action: ci.Action,
    candidate: TweetCandidate,
    features: FeatureMap
  ): Boolean = {
    val conditions: Seq[ci.Condition => Boolean] =
      Seq(
        _.postTopic.forall { topic =>
          features
            .getOrElse(CandidatePipelines, ListSet.empty[CandidatePipelineIdentifier])
            .contains(ControlAiTopicCandidatePipelineConfig.Identifier)
        },
        _.postHasVideo.forall(_ == hasBooleanFeature(features, TweetInfoFeatures.HasVideo)),
        _.postHasImage.forall(_ == hasBooleanFeature(features, TweetInfoFeatures.HasImage)),
        _.postIsReply.forall(_ == hasBooleanFeature(features, TweetInfoFeatures.IsReply)),
        _.postIsRetweet.forall(_ == hasBooleanFeature(features, TweetInfoFeatures.IsRetweet)),
        _.postMaximumAge.forall(maxAge =>
          SnowflakeId.timeFromIdOpt(candidate.id).exists(_.untilNow.inMinutes <= maxAge)),
        _.userFollowsAuthor.forall(
          _ == false
        ),
        _.authorId.forall(aid => features.getOrElse(AuthorIdFeature, None).contains(aid)),
      )

    conditions.forall(_(action.condition))
  }

  private def getHydraScore(
    candidate: CandidateWithDetails,
    controlAiConfig: Option[ControlAiConfig],
    videoWeight: Double,
    replyWeight: Double,
  ): Double = {
    candidate match {
      case ItemCandidateWithDetails(candidate: TweetCandidate, _, features) =>
        val isLongVideo = bool2int(hasBooleanFeature(features, TweetInfoFeatures.IsLongVideo))
        val isVideo = bool2int(hasBooleanFeature(features, TweetInfoFeatures.HasVideo))
        val isReply = bool2int(features.getOrElse(ReplyFeature, false))

        val controlAiWeight = controlAiConfig
          .flatMap { config =>
            val lastMatchedAction = config.actions
              .filter(isControlAiMatch(_, candidate, features)).lastOption
            lastMatchedAction
              .map(action => config.actionTypeWeights.getOrElse(action.actionType, 1.0))
          }.getOrElse(0.0)

        val hydraScoreMap = features.getOrElse(HydraScoreFeature, Map.empty[String, Double])
        val hydraScore = hydraScoreMap match {
          case scores if scores.isEmpty => Double.NegativeInfinity
          case scores if scores.size == 1 =>
            sigmoid(scores.values.sum) *
              (1 + videoWeight * isVideo) * (1 + replyWeight * isReply) * (1 + controlAiWeight)
          case scores if scores.size == 2 =>
            scores.getOrElse(
              IS_SELECTED_PREDS,
              Double.NegativeInfinity) + videoWeight * isLongVideo * scores
              .getOrElse(IS_VIDEO_PREDS, Double.NegativeInfinity)
          case _ => throw PipelineFailure(IllegalStateFailure, "Not expected")
        }
        hydraScore
      case _ => throw PipelineFailure(IllegalStateFailure, "Unexpected candidate type")
    }
  }

  override def sorter(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SorterFromTransformedOrdering[Double] = {
    val videoScore = query.params(VideoScoreWeightParam)
    val replyScore = query.params(ReplyScoreWeightParam)

    val controlAiConfig = {
      if (query.params(ControlAiEnabled)) {
        val actions = query.features
          .flatMap(_.getOrElse(UserControlAiFeature, None)).map(_.actions).getOrElse(Seq.empty)
        Some(
          ControlAiConfig(
            actions,
            Map(
              ci.ActionType.More -> query.params(ControlAiShowMoreWeightParam),
              ci.ActionType.Less -> query.params(ControlAiShowLessWeightParam),
              ci.ActionType.Only -> 10000,
              ci.ActionType.Exclude -> -10000,
            )
          ))
      } else
        None
    }

    SorterFromTransformedOrdering(
      Ordering.Double.reverse,
      getHydraScore(_, controlAiConfig, videoScore, replyScore)
    )
  }
}
