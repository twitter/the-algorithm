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
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.EvergreenVideosSearchByTweetQuery
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.SemanticVideoCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.EvergreenVideosResponseFeatureTransformer
import com.twitter.tweet_mixer.functional_component.transformer.TwitterClipV0LongVideoQueryTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.SemanticVideoCandidatePipelineEnabled
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SemanticVideoCandidatePipelineConfigFactory @Inject() (
  semanticVideoCandidateSource: SemanticVideoCandidateSource) {
  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): SemanticVideoCandidatePipelineConfig[Query] = {
    val identifier = CandidatePipelineIdentifier(
      identifierPrefix + CandidatePipelineConstants.SemanticVideo)
    new SemanticVideoCandidatePipelineConfig(
      identifier,
      semanticVideoCandidateSource,
      TwitterClipV0LongVideoQueryTransformer(signalFn, identifier)
    )
  }
}

class SemanticVideoCandidatePipelineConfig[Query <: PipelineQuery](
  override val identifier: CandidatePipelineIdentifier,
  semanticVideoCandidateSource: CandidateSource[
    EvergreenVideosSearchByTweetQuery,
    TweetMixerCandidate,
  ],
  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    EvergreenVideosSearchByTweetQuery
  ]
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      EvergreenVideosSearchByTweetQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "SemanticVideoCandidatePipelineEnabled",
      param = SemanticVideoCandidatePipelineEnabled
    )
  )

  override def candidateSource: CandidateSource[
    EvergreenVideosSearchByTweetQuery,
    TweetMixerCandidate,
  ] = semanticVideoCandidateSource

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
    defaultEmptyResponseRateAlert(warnThreshold = 60, criticalThreshold = 95)
  )
}
