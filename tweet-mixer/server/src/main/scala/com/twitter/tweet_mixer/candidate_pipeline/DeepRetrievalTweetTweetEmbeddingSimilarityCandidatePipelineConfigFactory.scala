package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
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
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRANNKey
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DRMultipleANNQuery
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DeepRetrievalTweetTweetEmbeddingANNCandidateSource
import com.twitter.tweet_mixer.candidate_source.ndr_ann.DeepRetrievalTweetTweetEmbeddingANNCandidateSourceFactory
import com.twitter.tweet_mixer.functional_component.gate.MinTimeSinceLastRequestGate
import com.twitter.tweet_mixer.functional_component.hydrator.DeepRetrievalTweetEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.DeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingANNEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingANNMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalI2iEmbVectorDBCollectionName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalTweetTweetEmbeddingANNScoreThreshold
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepRetrievalTweetTweetEmbeddingSimilarityCandidatePipelineConfigFactory @Inject() (
  deepRetrievalTweetTweetEmbeddingANNCandidateSourceFactory: DeepRetrievalTweetTweetEmbeddingANNCandidateSourceFactory,
  deepRetrievalTweetEmbeddingQueryFeatureHydratorFactory: DeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory,
  statsReceiver: StatsReceiver) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): DeepRetrievalTweetTweetEmbeddingSimilarityCandidatePipelineConfig[Query] = {
    val candidateSource = deepRetrievalTweetTweetEmbeddingANNCandidateSourceFactory.build(
      CandidatePipelineConstants.DeepRetrievalTweetTweetEmbeddingSimilarity
    )
    new DeepRetrievalTweetTweetEmbeddingSimilarityCandidatePipelineConfig(
      candidateSource,
      signalFn,
      deepRetrievalTweetEmbeddingQueryFeatureHydratorFactory,
      identifierPrefix,
      statsReceiver)
  }
}

class DeepRetrievalTweetTweetEmbeddingSimilarityCandidatePipelineConfig[Query <: PipelineQuery](
  deepRetrievalTweetTweetEmbeddingANNCandidateSource: DeepRetrievalTweetTweetEmbeddingANNCandidateSource,
  signalFn: PipelineQuery => Seq[Long],
  deepRetrievalTweetEmbeddingQueryFeatureHydratorFactory: DeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory,
  identifierPrefix: String,
  statsReceiver: StatsReceiver
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      DRMultipleANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.DeepRetrievalTweetTweetEmbeddingSimilarity)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "DeepRetrievalTweetTweetEmbeddingANN",
      param = DeepRetrievalTweetTweetEmbeddingANNEnabled
    ),
    MinTimeSinceLastRequestGate
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[PipelineQuery, _]] = Seq(
    deepRetrievalTweetEmbeddingQueryFeatureHydratorFactory.build(
      signalFn
    )
  )

  private val scopedStats: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val tweetIdsLenStats = scopedStats.stat("tweetIdsLen")
  private val embeddingLenStats = scopedStats.stat("embeddingLen")

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    DRMultipleANNQuery
  ] = { query =>
    val tweetIds = signalFn(query)
    val collectionName = query.params(DeepRetrievalI2iEmbVectorDBCollectionName)
    val maxCandidates = query.params(DeepRetrievalTweetTweetEmbeddingANNMaxCandidates)
    val scoreThreshold = query.params(DeepRetrievalTweetTweetEmbeddingANNScoreThreshold)
    val embeddings = query.features
      .flatMap(_.getOrElse(DeepRetrievalTweetEmbeddingFeature, Some(Map.empty[Long, Seq[Int]])))

    val tweetSize: Int = tweetIds.size
    val embeddingSize: Int = embeddings.map(_.size).getOrElse(0)
    tweetIdsLenStats.add(tweetSize)
    embeddingLenStats.add(embeddingSize)

    val tweetsANNKeys: Seq[DRANNKey] = embeddings match {
      case Some(embeddingsMap) =>
        embeddingsMap.map {
          case (tweetId, embeddingSeq) =>
            DRANNKey(
              tweetId,
              Some(embeddingSeq),
              collectionName,
              maxCandidates,
              scoreThreshold
            )
        }.toSeq
      case None =>
        Seq.empty[DRANNKey]
    }

    DRMultipleANNQuery(
      annKeys = tweetsANNKeys,
      enableCache = true
    )
  }

  override def candidateSource: CandidateSource[
    DRMultipleANNQuery,
    TweetMixerCandidate
  ] = deepRetrievalTweetTweetEmbeddingANNCandidateSource

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
