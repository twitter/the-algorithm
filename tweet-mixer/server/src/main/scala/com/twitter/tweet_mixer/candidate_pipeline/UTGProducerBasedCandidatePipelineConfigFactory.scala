package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.candidate_source.UTG.UTGProducerBasedRequest
import com.twitter.tweet_mixer.candidate_source.UTG.UserTweetGraphProducerBasedCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.functional_component.transformer.UTGProducerBasedQueryTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UTGProducerBasedEnabled
import com.twitter.tweet_mixer.param.UTGParams._
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UTGProducerBasedCandidatePipelineConfigFactory @Inject() (
  userTweetGraphProducerBasedCandidateSource: UserTweetGraphProducerBasedCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): UTGProducerBasedCandidatePipelineConfig[Query] = {
    new UTGProducerBasedCandidatePipelineConfig(
      identifierPrefix,
      signalFn,
      userTweetGraphProducerBasedCandidateSource
    )
  }
}

class UTGProducerBasedCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  signalsFn: PipelineQuery => Seq[Long],
  userTweetGraphProducerBasedCandidateSource: UserTweetGraphProducerBasedCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      UTGProducerBasedRequest,
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.UTGProducerBased)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "UTGProducerBasedEnabled",
      param = UTGProducerBasedEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    UTGProducerBasedRequest
  ] = UTGProducerBasedQueryTransformer(
    identifier = TransformerIdentifier("UTGProducerBased"),
    signalsFn = signalsFn,
    minScoreParam = TweetBasedMinScoreParam
  )

  override def candidateSource: CandidateSource[
    UTGProducerBasedRequest,
    TweetMixerCandidate
  ] = userTweetGraphProducerBasedCandidateSource

  override val featuresFromCandidateSourceTransformers = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(id = candidate.tweetId)
  }

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert()
  )
}
