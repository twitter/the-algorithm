package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.candidate_source.uteg.UtegCandidateSource
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.recos.user_tweet_entity_graph.{thriftscala => uteg}
import com.twitter.tweet_mixer.functional_component.transformer.UtegQueryTransformer
import com.twitter.tweet_mixer.functional_component.transformer.UtegResponseFeatureTransformer
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UTEGEnabled
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UTEGCandidatePipelineConfigFactory @Inject() (
  utegCandidateSource: UtegCandidateSource) {

  def build[Query <: PipelineQuery with HasExcludedIds](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): UTEGCandidatePipelineConfig[Query] = {
    new UTEGCandidatePipelineConfig(
      identifierPrefix,
      utegCandidateSource
    )
  }
}

class UTEGCandidatePipelineConfig[Query <: PipelineQuery with HasExcludedIds](
  identifierPrefix: String,
  utegCandidateSource: UtegCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      uteg.RecommendTweetEntityRequest,
      uteg.TweetRecommendation,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.UTEG)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "UTEGEnabled",
      param = UTEGEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    uteg.RecommendTweetEntityRequest
  ] = UtegQueryTransformer(identifier)

  override def candidateSource: CandidateSource[
    uteg.RecommendTweetEntityRequest,
    uteg.TweetRecommendation,
  ] = utegCandidateSource

  override val featuresFromCandidateSourceTransformers = Seq(UtegResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    uteg.TweetRecommendation,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(id = candidate.tweetId)
  }

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert()
  )
}
