package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.filter.ParamGatedFilter
import com.twitter.product_mixer.component_library.filter.TweetLanguageFilter
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.tweet_mixer.functional_component.gate.AllowNonEmptySearchHistoryUserGate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.tweet_mixer.candidate_source.qig_service.QigServiceBatchTweetCandidateSource
import com.twitter.search.query_interaction_graph.service.{thriftscala => t}
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.qig_service.QigTweetCandidate
import com.twitter.tweet_mixer.feature.LanguageCodeFeature
import com.twitter.tweet_mixer.functional_component.transformer.QigBatchQueryTransformer
import com.twitter.tweet_mixer.functional_component.transformer.QigTweetCandidateFeatureTransformer
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.QigSearchHistoryCandidateSourceEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.QigSearchHistoryTweetsEnableLanguageFilter
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.QigSearchHistoryTweetsEnabled
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QigSearchHistoryTweetsCandidatePipelineConfigFactory @Inject() (
  qigServiceBatchTweetCandidateSource: QigServiceBatchTweetCandidateSource,
) {
  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[String]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): QigSearchHistoryTweetsCandidatePipelineConfig[Query] = {
    new QigSearchHistoryTweetsCandidatePipelineConfig(
      identifierPrefix,
      signalFn,
      qigServiceBatchTweetCandidateSource,
    )
  }
}

class QigSearchHistoryTweetsCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  signalFn: PipelineQuery => Seq[String],
  qigServiceBatchTweetCandidateSource: QigServiceBatchTweetCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      Seq[t.QigRequest],
      QigTweetCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.QigSearchHistoryTweets
  )

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(QigSearchHistoryTweetsEnabled)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    AllowNonEmptySearchHistoryUserGate(signalFn),
    ParamGate(
      name = "QigSearchHistoryCandidateSourceEnabled",
      param = QigSearchHistoryCandidateSourceEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    Seq[t.QigRequest]
  ] = QigBatchQueryTransformer(signalFn)

  override val candidateSource: CandidateSource[
    Seq[t.QigRequest],
    QigTweetCandidate
  ] =
    qigServiceBatchTweetCandidateSource

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[QigTweetCandidate]
  ] = Seq(
    QigTweetCandidateFeatureTransformer
  )

  override def filters: Seq[Filter[Query, TweetCandidate]] = Seq(
    ParamGatedFilter(
      QigSearchHistoryTweetsEnableLanguageFilter,
      TweetLanguageFilter(LanguageCodeFeature)),
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[
    QigTweetCandidate,
    TweetCandidate
  ] = { sourceResult =>
    TweetCandidate(
      id = sourceResult.tweetCandidate.tweetId
    )
  }
}
