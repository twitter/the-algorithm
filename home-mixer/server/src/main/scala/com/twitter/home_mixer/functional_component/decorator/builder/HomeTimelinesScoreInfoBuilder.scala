package com.twitter.home_mixer.functional_component.decorator.builder

import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableSendScoresToClient
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.item.tweet.BaseTimelinesScoreInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TimelinesScoreInfo
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object HomeTimelinesScoreInfoBuilder
    extends BaseTimelinesScoreInfoBuilder[PipelineQuery, TweetCandidate] {

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
