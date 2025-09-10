package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.HistoricalEvergreenVideosCandidateSource
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.EvergreenVideosSearchByUserIdsQuery
import com.twitter.tweet_mixer.functional_component.transformer.EvergreenVideosQueryTransformer
import com.twitter.tweet_mixer.functional_component.transformer.EvergreenVideosResponseFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EvergreenVideosEnabled
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EvergreenVideosCandidatePipelineConfigFactory @Inject() (
  historicalEvergreenVideosCandidateSource: HistoricalEvergreenVideosCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): EvergreenVideosCandidatePipelineConfig[Query] = {
    new EvergreenVideosCandidatePipelineConfig(
      identifierPrefix,
      historicalEvergreenVideosCandidateSource
    )
  }
}

class EvergreenVideosCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  historicalEvergreenVideosCandidateSource: HistoricalEvergreenVideosCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      EvergreenVideosSearchByUserIdsQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.EvergreenVideos)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "EvergreenVideosEnabled",
      param = EvergreenVideosEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    EvergreenVideosSearchByUserIdsQuery
  ] = EvergreenVideosQueryTransformer(identifier)

  override def candidateSource: CandidateSource[
    EvergreenVideosSearchByUserIdsQuery,
    TweetMixerCandidate,
  ] = historicalEvergreenVideosCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { tweet =>
    TweetCandidate(
      id = tweet.tweetId
    )
  }

  override val featuresFromCandidateSourceTransformers = Seq(
    EvergreenVideosResponseFeatureTransformer)

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert()
  )
}
