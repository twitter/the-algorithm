package com.twitter.home_mitwitterer.functional_component.decorator.builder

import com.twitter.home_mitwitterer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mitwitterer.param.HomeGlobalParams.EnableSendScoresToClient
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.tweet.BaseTimelinesScoreInfoBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TimelinesScoreInfo
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object HomeTimelinesScoreInfoBuilder
    etwittertends BaseTimelinesScoreInfoBuilder[PipelineQuery, TweetCandidate] {

  private val UndefinedTweetScore = -1.0

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[TimelinesScoreInfo] = {
    if (query.params(EnableSendScoresToClient)) {
      val score = candidateFeatures.getOrElse(ScoreFeature, None).getOrElse(UndefinedTweetScore)
      Some(TimelinesScoreInfo(score))
    } else None
  }
}
