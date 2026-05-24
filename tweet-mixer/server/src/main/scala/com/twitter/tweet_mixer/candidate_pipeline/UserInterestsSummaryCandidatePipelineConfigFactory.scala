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
import com.twitter.tweet_mixer.candidate_source.ndr_ann.ContentEmbeddingUserANNKey
import com.twitter.tweet_mixer.candidate_source.ndr_ann.UserInterestEmbeddingANNCandidateSource
import com.twitter.tweet_mixer.candidate_source.ndr_ann.UserInterestEmbeddingANNCandidateSourceFactory
import com.twitter.tweet_mixer.candidate_source.ndr_ann.ContentEmbeddingMultipleUserANNQuery
import com.twitter.tweet_mixer.functional_component.gate.MinTimeSinceLastRequestGate
import com.twitter.tweet_mixer.functional_component.hydrator.UserInterestSummaryEmbeddingFeature
import com.twitter.tweet_mixer.functional_component.hydrator.UserInterestSummaryEmbeddingQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UserInterestSummarySimilarityMaxCandidates
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UserInterestSummarySimilarityScoreThreshold
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UserInterestSummarySimilarityEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UserInterestSummarySimilarityVectorDBCollectionName
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInterestsSummaryCandidatePipelineConfigFactory @Inject() (
  contentEmbeddingANNCandidateSourceFactory: UserInterestEmbeddingANNCandidateSourceFactory,
  userInterestSummaryEmbeddingQueryFeatureHydratorFactory: UserInterestSummaryEmbeddingQueryFeatureHydratorFactory,
  statsReceiver: StatsReceiver) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): UserInterestsSummaryCandidatePipelineConfig[Query] = {
    val candidateSource = contentEmbeddingANNCandidateSourceFactory.build(
      CandidatePipelineConstants.UserInterestSummary
    )
    new UserInterestsSummaryCandidatePipelineConfig(
      candidateSource,
      userInterestSummaryEmbeddingQueryFeatureHydratorFactory,
      identifierPrefix,
      statsReceiver
    )
  }
}

class UserInterestsSummaryCandidatePipelineConfig[Query <: PipelineQuery](
  contentEmbeddingANNCandidateSource: UserInterestEmbeddingANNCandidateSource,
  userInterestSummaryEmbeddingQueryFeatureHydratorFactory: UserInterestSummaryEmbeddingQueryFeatureHydratorFactory,
  identifierPrefix: String,
  statsReceiver: StatsReceiver
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      ContentEmbeddingMultipleUserANNQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {
  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.UserInterestSummary
  )

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = "UserInterestsSummary",
      param = UserInterestSummarySimilarityEnabled
    ),
    MinTimeSinceLastRequestGate
  )

  override val queryFeatureHydration: Seq[BaseQueryFeatureHydrator[PipelineQuery, _]] = Seq(
    userInterestSummaryEmbeddingQueryFeatureHydratorFactory.build()
  )

  private val scopedStats: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val tweetIdsLenStats = scopedStats.stat("tweetIdsLen")
  private val embeddingLenStats = scopedStats.stat("embeddingLen")

  override val queryTransformer: CandidatePipelineQueryTransformer[
    Query,
    ContentEmbeddingMultipleUserANNQuery
  ] = { query =>
    val userId = query.getRequiredUserId
    val collectionName = query.params(UserInterestSummarySimilarityVectorDBCollectionName)
    val maxCandidates = query.params(UserInterestSummarySimilarityMaxCandidates)
    val embeddings = query.features
      .flatMap(_.getOrElse(UserInterestSummaryEmbeddingFeature, None))
    val scoreThreshold = query.params(UserInterestSummarySimilarityScoreThreshold)

    val tweetSize: Int = 0 // Placeholder, as we're not using tweet IDs directly
    val embeddingSize: Int = embeddings.map(_.size).getOrElse(0)
    tweetIdsLenStats.add(tweetSize)
    embeddingLenStats.add(embeddingSize)

    val tweetsANNKeys: Seq[ContentEmbeddingUserANNKey] = embeddings match {
      case Some(embeddingSeqs) if embeddingSeqs.nonEmpty =>
        embeddingSeqs.zipWithIndex.map {
          case (embeddingSeq, index) =>
            ContentEmbeddingUserANNKey(
              index.toLong, // Using index as a placeholder since we don't have tweet IDs
              userId,
              Some(embeddingSeq),
              collectionName,
              maxCandidates,
              scoreThreshold
            )
        }
      case _ =>
        Seq.empty[ContentEmbeddingUserANNKey]
    }

    ContentEmbeddingMultipleUserANNQuery(
      annKeys = tweetsANNKeys,
      enableCache = true
    )
  }

  override def candidateSource: CandidateSource[
    ContentEmbeddingMultipleUserANNQuery,
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
