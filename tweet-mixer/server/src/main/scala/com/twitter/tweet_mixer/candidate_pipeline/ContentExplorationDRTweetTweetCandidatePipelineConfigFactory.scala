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
import com.twitter.tweet_mixer.functional_component.gate.ProbablisticPassGate
import com.twitter.tweet_mixer.functional_component.hydrator.ContentExplorationEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.ContentExplorationEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.hydrator.ContentMediaEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.ContentMediaEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.hydrator.DeepRetrievalTweetEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.DeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRTweetTweetEnableRandomEmbedding
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRTweetTweetEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRTweetTweetMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRTweetTweetScoreThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationDRTweetTweetVectorDBCollectionName
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import com.twitter.tweet_mixer.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentExplorationDRTweetTweetCandidatePipelineConfigFactory @Inject() (
  deepRetrievalTweetTweetEmbeddingANNCandidateSourceFactory: DeepRetrievalTweetTweetEmbeddingANNCandidateSourceFactory,
  deepRetrievalTweetEmbeddingQueryFeatureHydratorFactory: DeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory,
  contentExplorationEmbeddingQueryFeatureHydratorFactory: ContentExplorationEmbeddingQueryFeatureHydratorFactory,
  contentMediaEmbeddingQueryFeatureHydratorFactory: ContentMediaEmbeddingQueryFeatureHydratorFactory,
  statsReceiver: StatsReceiver) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    signalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): ContentExplorationDRTweetTweetCandidatePipelineConfig[Query] = {
    val candidateSource = deepRetrievalTweetTweetEmbeddingANNCandidateSourceFactory.build(
      CandidatePipelineConstants.ContentExplorationDRTweetTweet
    )
    new ContentExplorationDRTweetTweetCandidatePipelineConfig(
      candidateSource,
      signalFn,
      deepRetrievalTweetEmbeddingQueryFeatureHydratorFactory,
      contentExplorationEmbeddingQueryFeatureHydratorFactory,
      contentMediaEmbeddingQueryFeatureHydratorFactory,
      identifierPrefix,
      statsReceiver)
  }
}

class ContentExplorationDRTweetTweetCandidatePipelineConfig[Query <: PipelineQuery](
  deepRetrievalTweetTweetEmbeddingANNCandidateSource: DeepRetrievalTweetTweetEmbeddingANNCandidateSource,
  signalFn: PipelineQuery => Seq[Long],
  deepRetrievalTweetEmbeddingQueryFeatureHydratorFactory: DeepRetrievalTweetEmbeddingQueryFeatureHydratorFactory,
  contentExplorationEmbeddingQueryFeatureHydratorFactory: ContentExplorationEmbeddingQueryFeatureHydratorFactory,
  contentMediaEmbeddingQueryFeatureHydratorFactory: ContentMediaEmbeddingQueryFeatureHydratorFactory,
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
    identifierPrefix + CandidatePipelineConstants.ContentExplorationDRTweetTweet)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "ContentExplorationDeepRetrievalTweetTweetSimilarity",
      param = ContentExplorationDRTweetTweetEnabled
    ),
    MinTimeSinceLastRequestGate,
    ProbablisticPassGate
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[PipelineQuery, _]] = Seq(
    deepRetrievalTweetEmbeddingQueryFeatureHydratorFactory.build(
      signalFn
    ),
    contentExplorationEmbeddingQueryFeatureHydratorFactory.build(
      signalFn
    ),
    contentMediaEmbeddingQueryFeatureHydratorFactory.build(
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
    val collectionName =
      query.params(ContentExplorationDRTweetTweetVectorDBCollectionName)
    val maxCandidates =
      query.params(ContentExplorationDRTweetTweetMaxCandidates)
    val scoreThreshold =
      query.params(ContentExplorationDRTweetTweetScoreThreshold)
    val useRandomEmbedding =
      query.params(ContentExplorationDRTweetTweetEnableRandomEmbedding)

    val embeddings: Map[Long, Seq[Int]] =
      query.features
        .flatMap(_.get(DeepRetrievalTweetEmbeddingFeature))
        .getOrElse(Map.empty[Long, Seq[Int]])
    val textEmbeddingMap: Map[Long, Seq[Double]] =
      query.features
        .flatMap(_.get(ContentExplorationEmbeddingFeature))
        .getOrElse(Map.empty[Long, Seq[Double]])
    val mediaEmbeddingMap: Map[Long, Seq[Double]] =
      query.features
        .flatMap(_.get(ContentMediaEmbeddingFeature))
        .getOrElse(Map.empty[Long, Seq[Double]])
    val filteredEmbeddings = embeddings.filter {
      case (tweetId, _) => textEmbeddingMap.contains(tweetId) && mediaEmbeddingMap.contains(tweetId)
    }

    val tweetSize: Int = tweetIds.size
    val embeddingSize: Int = embeddings.size
    tweetIdsLenStats.add(tweetSize)
    embeddingLenStats.add(embeddingSize)

    val tweetsANNKeys: Seq[DRANNKey] = filteredEmbeddings match {
      case embeddingsMap if embeddingsMap.nonEmpty =>
        embeddingsMap.map {
          case (tweetId, embeddingSeq) =>
            DRANNKey(
              id = tweetId,
              embedding =
                if (useRandomEmbedding)
                  Some(Utils.generateRandomIntBits(embeddingSeq))
                else Some(embeddingSeq),
              collectionName = collectionName,
              maxCandidates = maxCandidates,
              scoreThreshold = scoreThreshold,
              tier = Some("tier1")
            )
        }.toSeq
      case _ =>
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
