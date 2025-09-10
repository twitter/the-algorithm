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
import com.twitter.tweet_mixer.candidate_source.ndr_ann.EmbeddingANNKey
import com.twitter.tweet_mixer.candidate_source.ndr_ann.EmbeddingANNCandidateSource
import com.twitter.tweet_mixer.candidate_source.ndr_ann.EmbeddingANNCandidateSourceFactory
import com.twitter.tweet_mixer.candidate_source.ndr_ann.EmbeddingMultipleANNQuery
import com.twitter.tweet_mixer.functional_component.gate.MinTimeSinceLastRequestGate
import com.twitter.tweet_mixer.functional_component.hydrator.ContentExplorationEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.ContentMediaEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.MultimodalEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.ContentExplorationEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.hydrator.ContentMediaEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.hydrator.MultimodalEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationEmbeddingSimilarityTierTwoEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationEmbeddingSimilarityTierTwoMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationEmbeddingSimilarityTierTwoScoreThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationEmbeddingSimilarityTierTwoVectorDBCollectionName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationMediaEmbeddingSimilarityEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationMultimodalEnabled
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentExplorationEmbeddingSimilarityTierTwoCandidatePipelineConfigFactory @Inject() (
  contentEmbeddingANNCandidateSourceFactory: EmbeddingANNCandidateSourceFactory,
  contentExplorationEmbeddingQueryFeatureHydratorFactory: ContentExplorationEmbeddingQueryFeatureHydratorFactory,
  contentMediaEmbeddingQueryFeatureHydratorFactory: ContentMediaEmbeddingQueryFeatureHydratorFactory,
  multimodalEmbeddingQueryFeatureHydratorFactory: MultimodalEmbeddingQueryFeatureHydratorFactory,
  statsReceiver: StatsReceiver) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): ContentExplorationEmbeddingSimilarityTierTwoCandidatePipelineConfig[Query] = {
    val candidateSource = contentEmbeddingANNCandidateSourceFactory.build(
      CandidatePipelineConstants.ContentExplorationEmbeddingSimilarityTierTwo
    )
    new ContentExplorationEmbeddingSimilarityTierTwoCandidatePipelineConfig(
      candidateSource,
      signalFn,
      contentExplorationEmbeddingQueryFeatureHydratorFactory,
      contentMediaEmbeddingQueryFeatureHydratorFactory,
      multimodalEmbeddingQueryFeatureHydratorFactory,
      identifierPrefix,
      statsReceiver)
  }
}

class ContentExplorationEmbeddingSimilarityTierTwoCandidatePipelineConfig[Query <: PipelineQuery](
  contentEmbeddingANNCandidateSource: EmbeddingANNCandidateSource,
  signalFn: PipelineQuery => Seq[Long],
  contentExplorationEmbeddingQueryFeatureHydratorFactory: ContentExplorationEmbeddingQueryFeatureHydratorFactory,
  contentMediaEmbeddingQueryFeatureHydratorFactory: ContentMediaEmbeddingQueryFeatureHydratorFactory,
  multimodalEmbeddingQueryFeatureHydratorFactory: MultimodalEmbeddingQueryFeatureHydratorFactory,
  identifierPrefix: String,
  statsReceiver: StatsReceiver
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      EmbeddingMultipleANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.ContentExplorationEmbeddingSimilarityTierTwo)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "ContentExplorationEmbeddingSimilarityTier2",
      param = ContentExplorationEmbeddingSimilarityTierTwoEnabled
    ),
    MinTimeSinceLastRequestGate
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[PipelineQuery, _]] = Seq(
    contentExplorationEmbeddingQueryFeatureHydratorFactory.build(
      signalFn
    ),
    contentMediaEmbeddingQueryFeatureHydratorFactory.build(
      signalFn
    ),
    multimodalEmbeddingQueryFeatureHydratorFactory.build(
      signalFn
    )
  )

  private val scopedStats: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val tweetIdsLenStats = scopedStats.stat("tweetIdsLen")
  private val embeddingLenStats = scopedStats.stat("embeddingLen")

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    EmbeddingMultipleANNQuery
  ] = { query =>
    val tweetIds = signalFn(query)
    val collectionName =
      query.params(ContentExplorationEmbeddingSimilarityTierTwoVectorDBCollectionName)
    val maxCandidates = query.params(ContentExplorationEmbeddingSimilarityTierTwoMaxCandidates)
    val scoreThreshold = query.params(ContentExplorationEmbeddingSimilarityTierTwoScoreThreshold)
    val textEmbeddings = query.features
      .flatMap(_.getOrElse(ContentExplorationEmbeddingFeature, Some(Map.empty[Long, Seq[Double]])))
    val textEmbeddingMap: Map[Long, Seq[Double]] =
      query.features
        .flatMap(_.get(ContentExplorationEmbeddingFeature))
        .getOrElse(Map.empty[Long, Seq[Double]])

    val mediaEmbeddingMap: Map[Long, Seq[Double]] =
      query.features
        .flatMap(_.get(ContentMediaEmbeddingFeature))
        .getOrElse(Map.empty[Long, Seq[Double]])

    val multimodalEmbeddingMap: Map[Long, Option[Seq[Double]]] =
      query.features
        .flatMap(_.get(MultimodalEmbeddingFeature))
        .getOrElse(Map.empty[Long, Option[Seq[Double]]])

    val filteredMultimodalEmbeddingMap: Map[Long, Seq[Double]] =
      multimodalEmbeddingMap
        .filter { case (_, embeddingOpt) => embeddingOpt.isDefined }
        .map { case (tweetId, embeddingOpt) => tweetId -> embeddingOpt.get }

    val multimodalEmbeddingMapOpt: Option[Map[Long, Seq[Double]]] =
      if (filteredMultimodalEmbeddingMap.isEmpty) None else Some(filteredMultimodalEmbeddingMap)

    val mergedEmbeddingMap: Map[Long, Seq[Double]] =
      textEmbeddingMap ++ mediaEmbeddingMap

    val mergedEmbeddingMapOpt: Option[Map[Long, Seq[Double]]] =
      if (mergedEmbeddingMap.isEmpty) None else Some(mergedEmbeddingMap)

    val embeddings =
      if (query.params(
          ContentExplorationMediaEmbeddingSimilarityEnabled) && mergedEmbeddingMapOpt.isDefined)
        mergedEmbeddingMapOpt
      else if (query.params(
          ContentExplorationMultimodalEnabled) && multimodalEmbeddingMapOpt.isDefined)
        multimodalEmbeddingMapOpt
      else textEmbeddings

    val tweetSize: Int = tweetIds.size
    val embeddingSize: Int = embeddings.map(_.size).getOrElse(0)
    tweetIdsLenStats.add(tweetSize)
    embeddingLenStats.add(embeddingSize)

    val tweetsANNKeys: Seq[EmbeddingANNKey] = embeddings match {
      case Some(embeddingsMap) =>
        embeddingsMap.map {
          case (tweetId, embeddingSeq) =>
            EmbeddingANNKey(
              tweetId,
              Some(embeddingSeq),
              collectionName,
              maxCandidates,
              scoreThreshold,
              tier = Some("tier2")
            )
        }.toSeq
      case None =>
        Seq.empty[EmbeddingANNKey]
    }

    EmbeddingMultipleANNQuery(
      annKeys = tweetsANNKeys,
      enableCache = true
    )
  }

  override def candidateSource: CandidateSource[
    EmbeddingMultipleANNQuery,
    TweetMixerCandidate
  ] = contentEmbeddingANNCandidateSource

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
