package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ControlAiEmbeddingSimilarityThresholdParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ControlAiShowLessScaleFactorParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ControlAiShowMoreScaleFactorParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableControlAiParam
import com.twitter.home_mixer.product.scored_tweets.util.ControlAiUtil
import com.twitter.product_mixer.component_library.feature_hydrator.query.control_ai.ControlAiTopicEmbeddingMapFeature
import com.twitter.product_mixer.component_library.feature_hydrator.query.control_ai.UserControlAiFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.control_ai.control.{thriftscala => ci}

sealed trait ControlAiRescorer extends RescoringFactorProvider

object ControlAiRescorer {

  private def buildControlAiRescorer(
    actionType: ci.ActionType,
    factorParam: FSBoundedParam[Double]
  ): ControlAiRescorer = {
    new ControlAiRescorer {
      override def selector(
        query: PipelineQuery,
        candidate: CandidateWithFeatures[TweetCandidate]
      ): Boolean = {
        if (query.params(EnableControlAiParam)) {
          val actions = query.features
            .flatMap(_.getOrElse(UserControlAiFeature, None))
            .map(_.actions).getOrElse(Seq.empty).filter(_.actionType == actionType)
          actions.exists(
            ControlAiUtil.conditionMatch(
              _,
              candidate,
              query.features.map(_.get(ControlAiTopicEmbeddingMapFeature)).getOrElse(Map.empty),
              threshold = query.params(ControlAiEmbeddingSimilarityThresholdParam)
            )
          )
        } else false
      }

      override def factor(
        query: PipelineQuery,
        candidate: CandidateWithFeatures[TweetCandidate]
      ): Double = query.params(factorParam)
    }
  }

  val allRescorers = Seq(
    buildControlAiRescorer(ci.ActionType.More, ControlAiShowMoreScaleFactorParam),
    buildControlAiRescorer(ci.ActionType.Less, ControlAiShowLessScaleFactorParam)
  )
}
