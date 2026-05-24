package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRANNKey
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRMultipleANNQuery
import com.twitter.tweet_mixer.candidate_source.ndr_ann.MediaDeepRetrievalUserTweetANNCandidateSourceFactory
import com.twitter.tweet_mixer.functional_component.hydrator.MediaDeepRetrievalSignalTweetEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.MediaDeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams._
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaDeepRetrievalTweetTweetSimilarityCandidatePipelineConfigFactory @Inject() (
  mediaDeepRetrievalTweetEmbeddingFeatureHydratorFactory: MediaDeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory,
  mediaDeepRetrievalUserTweetANNCandidateSourceFactory: MediaDeepRetrievalUserTweetANNCandidateSourceFactory) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long],
    enableFs: FSParam[Boolean],
    maxCandidates: FSBoundedParam[Int]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): MediaDeepRetrievalTweetTweetSimilarityCandidatePipelineConfig[Query] = {
    new MediaDeepRetrievalTweetTweetSimilarityCandidatePipelineConfig(
      mediaDeepRetrievalTweetEmbeddingFeatureHydratorFactory,
      signalFn,
      mediaDeepRetrievalUserTweetANNCandidateSourceFactory,
      identifierPrefix + CandidatePipelineConstants.MediaDeepRetrievalTweetTweetSimilarity,
      MediaDeepRetrievalVectorDBCollectionName,
      enableFs,
      maxCandidates
    )
  }
}

class MediaDeepRetrievalTweetTweetSimilarityCandidatePipelineConfig[Query <: PipelineQuery](
  mediaDeepRetrievalTweetEmbeddingFeatureHydratorFactory: MediaDeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory,
  signalFn: PipelineQuery => Seq[Long],
  mediaDeepRetrievalUserTweetANNCandidateSourceFactory: MediaDeepRetrievalUserTweetANNCandidateSourceFactory,
  identifierFullName: String,
  vecDBCollectionName: FSParam[String],
  enableMediaDeepRetrievalTweetAnn: FSParam[Boolean],
  candidate: FSBoundedParam[Int],
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      DRMultipleANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierFullName)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "MediaDeepRetrievalTweetANN",
      param = enableMediaDeepRetrievalTweetAnn
    )
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[Query, _]] = Seq(
    mediaDeepRetrievalTweetEmbeddingFeatureHydratorFactory.build(signalFn))

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    DRMultipleANNQuery
  ] = { query =>
    val signals = query.features
      .getOrElse(FeatureMap.empty)
      .getOrElse(
        MediaDeepRetrievalSignalTweetEmbeddingFeature,
        MediaDeepRetrievalSignalTweetEmbeddingFeature.defaultValue)

    val keysOptSeq = signals.map { signal =>
      DRANNKey(
        id = signal._1,
        embedding = Some(signal._2),
        collectionName = query.params(vecDBCollectionName),
        maxCandidates = query.params(candidate)
      )
    }.toSeq
    DRMultipleANNQuery(keysOptSeq, false)
  }

  override def candidateSource: CandidateSource[
    DRMultipleANNQuery,
    TweetMixerCandidate
  ] = mediaDeepRetrievalUserTweetANNCandidateSourceFactory.build(identifierFullName)

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
