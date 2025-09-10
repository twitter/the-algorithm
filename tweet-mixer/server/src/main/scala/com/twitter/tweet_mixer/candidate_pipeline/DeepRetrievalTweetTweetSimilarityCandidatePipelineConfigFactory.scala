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
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRANNKey
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRMultipleANNQuery
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DeepRetrievalTweetTweetANNCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalI2iVectorDBCollectionName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetANNEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetANNMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetANNScoreThreshold
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepRetrievalTweetTweetSimilarityCandidatePipelineConfigFactory @Inject() (
  deepRetrievalTweetTweetANNCandidateSource: DeepRetrievalTweetTweetANNCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): DeepRetrievalTweetTweetSimilarityCandidatePipelineConfig[Query] = {
    new DeepRetrievalTweetTweetSimilarityCandidatePipelineConfig(
      deepRetrievalTweetTweetANNCandidateSource,
      signalFn,
      identifierPrefix)
  }
}

class DeepRetrievalTweetTweetSimilarityCandidatePipelineConfig[Query <: PipelineQuery](
  deepRetrievalTweetTweetANNCandidateSource: DeepRetrievalTweetTweetANNCandidateSource,
  signalFn: PipelineQuery => Seq[Long],
  identifierPrefix: String
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      DRMultipleANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.DeepRetrievalTweetTweetSimilarity)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "DeepRetrievalTweetTweetANN",
      param = DeepRetrievalTweetTweetANNEnabled
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    DRMultipleANNQuery
  ] = { query =>
    val tweetIds = signalFn(query)
    val collectionName = query.params(DeepRetrievalI2iVectorDBCollectionName)
    val maxCandidates = query.params(DeepRetrievalTweetTweetANNMaxCandidates)
    val scoreThreshold = query.params(DeepRetrievalTweetTweetANNScoreThreshold)

    DRMultipleANNQuery(
      annKeys = tweetIds.map { tweetId =>
        DRANNKey(tweetId, None, collectionName, maxCandidates, scoreThreshold)
      },
      enableCache = true
    )
  }

  override def candidateSource: CandidateSource[
    DRMultipleANNQuery,
    TweetMixerCandidate
  ] = deepRetrievalTweetTweetANNCandidateSource

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
