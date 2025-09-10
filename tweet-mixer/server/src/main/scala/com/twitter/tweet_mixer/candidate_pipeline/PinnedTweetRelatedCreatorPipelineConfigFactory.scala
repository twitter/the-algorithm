package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.candidate_source.timeline_service.TimelineServiceTweetCandidateSource
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelineservice.{thriftscala => tlsthrift}
import com.twitter.tweet_mixer.functional_component.transformer.TimelineQueryTransformer
import com.twitter.tweet_mixer.functional_component.transformer.TweetFeatureTimelineServiceTransformer
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableRelatedCreatorParam
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PinnedTweetRelatedCreatorPipelineConfigFactory @Inject() (
  timelineServiceTweetCandidateSource: TimelineServiceTweetCandidateSource) {
  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): PinnedTweetRelatedCreatorPipelineConfig[Query] = {
    new PinnedTweetRelatedCreatorPipelineConfig(
      identifierPrefix,
      signalFn,
      timelineServiceTweetCandidateSource)
  }
}

class PinnedTweetRelatedCreatorPipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  signalFn: PipelineQuery => Seq[Long],
  timelineServiceTweetCandidateSource: TimelineServiceTweetCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      tlsthrift.TimelineQuery,
      tlsthrift.Tweet,
      TweetCandidate
    ] {

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "EnableRelatedCreatorParam",
      param = EnableRelatedCreatorParam
    )
  )

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.RelatedCreator)

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    tlsthrift.TimelineQuery
  ] = TimelineQueryTransformer(tlsthrift.TimelineType.Media, signalFn, Some(true))
  override def candidateSource: BaseCandidateSource[tlsthrift.TimelineQuery, tlsthrift.Tweet] =
    timelineServiceTweetCandidateSource

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[tlsthrift.Tweet]
  ] = Seq(TweetFeatureTimelineServiceTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    tlsthrift.Tweet,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(
      id = candidate._1
    )
  }

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert()
  )
}
