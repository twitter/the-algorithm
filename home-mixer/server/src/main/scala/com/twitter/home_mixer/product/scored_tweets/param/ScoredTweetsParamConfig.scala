package com.twitter.home_mixer.product.scored_tweets.param

import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam._
import com.twitter.product_mixer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsParamConfig @Inject() () extends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableScoredTweetsProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val booleanDeciderOverrides = Seq(EnablePublishCommonFeaturesKafkaDeciderParam)

  override val boundedDoubleDeciderOverrides = Seq(
    LiveContentScaleFactorParam,
    MtlNormalization.AlphaParam
  )

  override val booleanFSOverrides = Seq(
    CandidateSourceParams.EnableCommunitiesCandidateSourceParam,
    CandidateSourceParams.EnableInNetworkCandidateSourceParam,
    CandidateSourceParams.InNetworkIncludeRepliesParam,
    CandidateSourceParams.InNetworkIncludeRetweetsParam,
    CandidateSourceParams.InNetworkIncludeExtendedRepliesParam,
    CandidateSourceParams.EnableUTEGCandidateSourceParam,
    CandidateSourceParams.EnableStaticSourceParam,
    EnableBackfillCandidatePipelineParam,
    EnableContentExplorationCandidatePipelineParam,
    EnableContentExplorationSimclusterColdPostsCandidateBoostingParam,
    EnableContentExplorationCandidateMaxCountParam,
    EnableContentExplorationScoreScribingParam,
    EnableContentExplorationMixedCandidateBoostingParam,
    EnableDeepRetrievalMixedCandidateBoostingParam,
    EnableScribeScoredCandidatesParam,
    EnableCacheRetrievalSignalParam,
    EnableCacheRequestInfoParam,
    EnableScoredPhoenixCandidatesKafkaSideEffectParam,
    FeatureHydration.EnableFollowedUserScoreBackfillFeaturesParam,
    FeatureHydration.EnableRealTimeEntityRealGraphFeaturesParam,
    FeatureHydration.EnableTopicSocialProofFeaturesParam,
    FeatureHydration.EnableTweetTextV8EmbeddingFeatureParam,
    FeatureHydration.EnableMediaClusterFeatureHydrationParam,
    FeatureHydration.EnableMediaCompletionRateFeatureHydrationParam,
    FeatureHydration.EnableClipImagesClusterIdFeatureHydrationParam,
    FeatureHydration.EnableMultiModalEmbeddingsFeatureHydratorParam,
    FeatureHydration.EnableUserEngagedLanguagesFeaturesParam,
    FeatureHydration.EnableUserIdentifierFeaturesParam,
    FeatureHydration.EnableUserHistoryEventsFeaturesParam,
    FeatureHydration.EnableUserActionsFeatureParam,
    FeatureHydration.EnableDenseUserActionsHydrationParam,
    FeatureHydration.EnableMediaClusterDecayParam,
    FeatureHydration.EnableImageClusterDecayParam,
    EnableControlAiParam,
    EnableHeartbeatOptimizerWeightsParam,
    EnableHeuristicScoringPipeline,
    EnablePhoenixScoreParam,
    EnablePhoenixRescoreParam,
    EnableColdStartFilterParam,
    EnableImpressionBasedAuthorDecay,
    EnableCandidateSourceDiversityDecay,
    EnableHomeMixerFeaturesService,
    EnableContentFeatureFromTesService,
    EnableLowSignalUserCheck,
    EnableDeepRetrievalMaxCountParam,
    EnableEvergreenDeepRetrievalMaxCountParam,
    EnableEvergreenDeepRetrievalCrossBorderMaxCountParam,
    EnableScoredCandidateFeatureKeysKafkaPublishingParam,
    EnableEarlybirdCommunitiesQueryLinearRankingParam,
    EnableRecentFeedbackCheckParam,
    EnableMediaDedupingParam,
    EnableMediaClusterDedupingParam,
    EnableClipImageClusterDedupingParam,
    MtlNormalization.EnableMtlNormalizationParam,
    EnableRecentEngagementCacheRefreshParam,
    EnableLanguageFilter,
    EnableGrokAutoTranslateLanguageFilter
  )

  override val boundedIntFSOverrides = Seq(
    CachedScoredTweets.MinCachedTweetsParam,
    EarlybirdMaxResultsPerPartitionParam,
    ServerMaxResultsParam,
    DefaultRequestedMaxResultsParam,
    ContentExplorationBoostPosParam,
    ContentExplorationViewerMaxFollowersParam,
    DeepRetrievalBoostPosParam,
    FeatureHydration.UserHistoryEventsLengthParam,
    FetchParams.FRSMaxTweetsToFetchParam,
    FetchParams.InNetworkMaxTweetsToFetchParam,
    FetchParams.TweetMixerMaxTweetsToFetchParam,
    FetchParams.UTEGMaxTweetsToFetchParam,
    QualityFactor.BackfillMaxTweetsToScoreParam,
    QualityFactor.TweetMixerMaxTweetsToScoreParam,
    QualityFactor.InNetworkMaxTweetsToScoreParam,
    QualityFactor.ListsMaxTweetsToScoreParam,
    QualityFactor.UtegMaxTweetsToScoreParam,
    QualityFactor.CommunitiesMaxTweetsToScoreParam,
    DeepRetrievalMaxCountParam,
    EvergreenDeepRetrievalMaxCountParam,
    EvergreenDeepRetrievalCrossBorderMaxCountParam,
    ScribedScoredCandidateNumParam,
    LowSignalUserMaxSignalCount,
    EarlyBirdCommunitiesMaxSearchResultsParam,
    FeatureHydration.CategoryDiversityKParam,
    MultiModalEmbeddingRescorerNumCandidatesParam
  )

  override val boundedLongFSOverrides = Seq(
    MtlNormalization.BetaParam,
    MtlNormalization.GammaParam,
  )

  override val boundedDurationFSOverrides = Seq(
    CachedScoredTweets.TTLParam,
  )

  override val stringFSOverrides = Seq(
    ContentExplorationCandidateVersionParam,
    EarlybirdTensorflowModel.InNetworkParam,
    EarlybirdTensorflowModel.FrsParam,
    EarlybirdTensorflowModel.UtegParam,
    HeartbeatOptimizerParamsMHPkey,
    TweetMixerRankingModeForStatsRecallAtKParam,
  )

  override val boundedDoubleFSOverrides = Seq(
    CategoryColdStartTierOneProbabilityParam,
    CategoryColdStartProbabilisticReturnParam,
    ControlAiShowLessScaleFactorParam,
    ControlAiShowMoreScaleFactorParam,
    ControlAiEmbeddingSimilarityThresholdParam,
    CreatorInNetworkMultiplierParam,
    CreatorOutOfNetworkMultiplierParam,
    DeepRetrievalI2iProbabilityParam,
    OutOfNetworkScaleFactorParam,
    ReplyScaleFactorParam,
    AuthorDiversityDecayFactor,
    AuthorDiversityOutNetworkDecayFactor,
    AuthorDiversityInNetworkDecayFactor,
    SmallFollowGraphAuthorDiversityDecayFactor,
    AuthorDiversityFloor,
    AuthorDiversityOutNetworkFloor,
    AuthorDiversityInNetworkFloor,
    CandidateSourceDiversityDecayFactor,
    CandidateSourceDiversityFloor,
    SmallFollowGraphAuthorDiversityFloor,
    FeatureHydration.TwhinDiversityRescoringWeightParam,
    FeatureHydration.TwhinDiversityRescoringRatioParam,
    FeatureHydration.CategoryDiversityRescoringWeightParam,
    GrokSlopScoreDecayValueParam,
    MultiModalEmbeddingRescorerGammaParam,
    MultiModalEmbeddingRescorerMinScoreParam
  )

  override def longSetFSOverrides: Seq[Param[Set[Long]] with FSName] = Seq.empty
}
