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
import com.twitter.tweet_mixer.candidate_source.twhin_ann.TwHINRebuildANNKey
import com.twitter.tweet_mixer.candidate_source.twhin_ann.TwHINRebuildANNCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinRebuildTweetSimilarityEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinRebuildTweetSimilarityDatasetParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinRebuildTweetSimilarityDatasetEnum
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.TwhinRebuildTweetSimilarityMaxCandidates
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwHINRebuildTweetSimilarityCandidatePipelineConfigFactory @Inject() (
  twHINANNCandidateSource: TwHINRebuildANNCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): TwHINRebuildTweetSimilarityCandidatePipelineConfig[Query] = {
    new TwHINRebuildTweetSimilarityCandidatePipelineConfig(
      twHINANNCandidateSource,
      signalFn,
      identifierPrefix
    )
  }
}

class TwHINRebuildTweetSimilarityCandidatePipelineConfig[Query <: PipelineQuery](
  twHINANNCandidateSource: TwHINRebuildANNCandidateSource,
  signalFn: PipelineQuery => Seq[Long],
  identifierPrefix: String
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      Seq[TwHINRebuildANNKey],
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.TwhinRebuildTweetSimilarity)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "TwHINRebuildTweetSimilarity",
      param = TwhinRebuildTweetSimilarityEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    Seq[TwHINRebuildANNKey]
  ] = { query =>
    val dataset = query.params(TwhinRebuildTweetSimilarityDatasetParam)
    val versionId = TwhinRebuildTweetSimilarityDatasetEnum.enumToTwhinRebuildVersionIdMap(dataset)
    val maxCandidates = query.params(TwhinRebuildTweetSimilarityMaxCandidates)
    signalFn(query).map(tweetId =>
      TwHINRebuildANNKey(tweetId, dataset.toString, versionId, maxCandidates))
  }

  override def candidateSource: CandidateSource[
    Seq[TwHINRebuildANNKey],
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
