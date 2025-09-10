package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
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
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.tweet_mixer.candidate_source.twhin_ann.TwHINANNCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinTweetSimilarityEnabled
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwHINTweetSimilarityCandidatePipelineConfigFactory @Inject() (
  twHINANNCandidateSource: TwHINANNCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): TwHINTweetSimilarityCandidatePipelineConfig[Query] = {
    new TwHINTweetSimilarityCandidatePipelineConfig(
      twHINANNCandidateSource,
      signalFn,
      identifierPrefix
    )
  }
}

class TwHINTweetSimilarityCandidatePipelineConfig[Query <: PipelineQuery](
  twHINANNCandidateSource: TwHINANNCandidateSource,
  signalFn: PipelineQuery => Seq[Long],
  identifierPrefix: String
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      Seq[TweetId],
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.TwhinTweetSimilarity)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "TwHINTweetSimilarity",
      param = TwhinTweetSimilarityEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    Seq[TweetId]
  ] = { query =>
    signalFn(query)
  }

  override def candidateSource: CandidateSource[
    Seq[TweetId],
    TweetMixerCandidate
  ] = twHINANNCandidateSource

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate =>
    TweetCandidate(id = candidate.tweetId)
  }

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert()
  )
}
