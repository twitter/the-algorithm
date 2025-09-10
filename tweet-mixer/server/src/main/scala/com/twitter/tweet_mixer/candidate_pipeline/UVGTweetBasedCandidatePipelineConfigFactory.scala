package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.candidate_source.UVG.UVGTweetBasedRequest
import com.twitter.tweet_mixer.candidate_source.UVG.UserVideoGraphTweetBasedCandidateSource
import com.twitter.tweet_mixer.functional_component.gate.MaxFollowersGate
import com.twitter.tweet_mixer.functional_component.hydrator.OutlierDeepRetrievalEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.hydrator.UVGOutlierSignalsQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.functional_component.transformer.UVGTweetBasedQueryTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UVGTweetBasedEnabled
import com.twitter.tweet_mixer.param.UVGParams._
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UVGTweetBasedCandidatePipelineConfigFactory @Inject() (
  outlierDeepRetrievalEmbeddingQueryFeatureHydratorFactory: OutlierDeepRetrievalEmbeddingQueryFeatureHydratorFactory,
  uvgOutlierSignalsQueryFeatureHydrator: UVGOutlierSignalsQueryFeatureHydrator,
  userVideoGraphTweetBasedCandidateSource: UserVideoGraphTweetBasedCandidateSource,
) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalsFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): UVGTweetBasedCandidatePipelineConfig[Query] = {
    new UVGTweetBasedCandidatePipelineConfig(
      identifierPrefix,
      signalsFn = signalsFn,
      outlierDeepRetrievalEmbeddingQueryFeatureHydratorFactory,
      uvgOutlierSignalsQueryFeatureHydrator,
      userVideoGraphTweetBasedCandidateSource
    )
  }
}

class UVGTweetBasedCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  signalsFn: PipelineQuery => Seq[Long],
  outlierDeepRetrievalEmbeddingQueryFeatureHydratorFactory: OutlierDeepRetrievalEmbeddingQueryFeatureHydratorFactory,
  uvgOutlierSignalsQueryFeatureHydrator: UVGOutlierSignalsQueryFeatureHydrator,
  userVideoGraphTweetBasedCandidateSource: UserVideoGraphTweetBasedCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      UVGTweetBasedRequest,
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.UVGTweetBased)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(name = "UVGTweetBasedEnabled", param = UVGTweetBasedEnabled),
    MaxFollowersGate
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    UVGTweetBasedRequest
  ] = UVGTweetBasedQueryTransformer(
    identifier = TransformerIdentifier("UVG"),
    query => signalFnSelector(query),
    isExpansionQuery = false,
    minScoreParam = TweetBasedMinScoreParam,
    degreeExponent = TweetBasedDegreeExponentParam
  )

  private def signalFnSelector(query: PipelineQuery): Seq[Long] = {
    signalsFn(query)
  }

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[Query, _]] = Seq(
    ParamGatedQueryFeatureHydrator(
      EnableTweetEmbeddingBasedFilteringParam,
      outlierDeepRetrievalEmbeddingQueryFeatureHydratorFactory.build(
        signalsFn
      )
    )
  )

  override val queryFeatureHydrationPhase2: Seq[BaseQueryFeatureHydrator[Query, _]] = Seq(
    ParamGatedQueryFeatureHydrator(
      EnableTweetEmbeddingBasedFilteringParam,
      uvgOutlierSignalsQueryFeatureHydrator
    )
  )

  override def candidateSource: CandidateSource[
    UVGTweetBasedRequest,
    TweetMixerCandidate
  ] = userVideoGraphTweetBasedCandidateSource

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
