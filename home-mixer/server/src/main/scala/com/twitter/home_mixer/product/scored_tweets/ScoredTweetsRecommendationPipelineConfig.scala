package com.twitter.home_mixer.product.scored_tweets

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.functional_component.feature_hydrator.FollowableUttTopicsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.CategoryDiversityRescoringFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.DiversityRescoringFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.FeedbackHistoryQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.GizmoduckUserQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.GrokTranslatedPostIsCachedFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.HeartbeatOptimizerParamsHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.HeavyRankerWeightsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.ImpressedMediaClusterIdsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.ImpressionBloomFilterQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.ListIdsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.NaviClientConfigQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.OnPremRealGraphQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.OptimizerWeightsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.PhoenixRescoringFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RealGraphInNetworkScoresQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RealGraphQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RealTimeEntityRealGraphQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RealTimeInteractionGraphUserVertexQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RequestQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RequestTimeQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.SimClustersUserSparseEmbeddingsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetTypeMetricsFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TwhinRebuildUserEngagementQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TwhinRebuildUserPositiveQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TwhinUserEngagementQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TwhinUserFollowQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TwhinUserNegativeQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TwhinUserPositiveQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UnifiedUserActionsUserIdentifierFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserActionsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserEngagedGrokCategoriesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserEngagedLanguagesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserHistoryTransformerEmbeddingQueryFeatureHydratorBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator.UserLanguagesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserLargeEmbeddingsFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserStateQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserUnderstandableLanguagesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.PartAAggregateQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.PartBAggregateQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates.UserEngagementRealTimeAggregatesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.user_history.ScoredTweetsUserHistoryEventsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.ClipImageClusterDeduplicationFilter
import com.twitter.home_mixer.functional_component.filter.ClipVideoClusterDeduplicationFilter
import com.twitter.home_mixer.functional_component.filter.FeedbackFatigueFilter
import com.twitter.home_mixer.functional_component.filter.GrokGoreFilter
import com.twitter.home_mixer.functional_component.filter.GrokNsfwFilter
import com.twitter.home_mixer.functional_component.filter.GrokSpamFilter
import com.twitter.home_mixer.functional_component.filter.GrokViolentFilter
import com.twitter.home_mixer.functional_component.filter.HasAuthorFilter
import com.twitter.home_mixer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.twitter.home_mixer.functional_component.filter.LocationFilter
import com.twitter.home_mixer.functional_component.filter.MediaIdDeduplicationFilter
import com.twitter.home_mixer.functional_component.filter.MinVideoDurationFilter
import com.twitter.home_mixer.functional_component.filter.PreviouslySeenTweetsFilter
import com.twitter.home_mixer.functional_component.filter.PreviouslyServedTweetsFilter
import com.twitter.home_mixer.functional_component.filter.QuoteDeduplicationFilter
import com.twitter.home_mixer.functional_component.filter.RejectTweetFromViewerFilter
import com.twitter.home_mixer.functional_component.filter.RetweetDeduplicationFilter
import com.twitter.home_mixer.functional_component.filter.SlopFilter
import com.twitter.home_mixer.functional_component.filter.TweetHydrationFilter
import com.twitter.home_mixer.functional_component.selector.SortFixedPositionContentExplorationMixedCandidates
import com.twitter.home_mixer.functional_component.selector.SortFixedPositionContentExplorationSimclusterColdPostsCandidates
import com.twitter.home_mixer.functional_component.selector.SortFixedPositionDeepRetrievalMixedCandidates
import com.twitter.home_mixer.functional_component.side_effect.PublishClientSentImpressionsEventBusSideEffect
import com.twitter.home_mixer.functional_component.side_effect.UpdateLastNonPollingTimeSideEffect
import com.twitter.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsSupportAccountReplyFeature
import com.twitter.home_mixer.model.HomeFeatures.OonNsfwFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableLargeEmbeddingsFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableRealGraphQueryFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinRebuildUserEngagementFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinRebuildUserPositiveFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinUserNegativeFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinUserPositiveFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableUserFavAvgTextEmbeddingsQueryFeatureParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableUserHistoryTransformerJointBlueEmbeddingFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.CategoryDiversityRescoringParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.TwhinDiversityRescoringParam
import com.twitter.home_mixer.param.HomeMixerFlagName.TargetScoringLatency
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsBackfillCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsContentExplorationCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsDirectUtegCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsListsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsStaticCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsTweetMixerCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.earlybird.ScoredTweetsCommunitiesCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.earlybird.ScoredTweetsEarlybirdInNetworkCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.CachedScoredTweetsQueryFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.InvalidateCachedScoredTweetsQueryFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.IsColdStartPostFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.LowSignalUserQueryFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.SGSMutuallyFollowedUserHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.TweetypieVisibilityFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.ValidLikedByUserIdsFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.filter.ControlAiExcludeFilter
import com.twitter.home_mixer.product.scored_tweets.filter.ControlAiOnlyIncludeFilter
import com.twitter.home_mixer.product.scored_tweets.filter.CustomSnowflakeIdAgeFilter
import com.twitter.home_mixer.product.scored_tweets.filter.DuplicateConversationTweetsFilter
import com.twitter.home_mixer.product.scored_tweets.filter.GrokAutoTranslateLanguageFilter
import com.twitter.home_mixer.product.scored_tweets.filter.IsOutOfNetworkColdStartPostFilter
import com.twitter.home_mixer.product.scored_tweets.filter.LanguageFilter
import com.twitter.home_mixer.product.scored_tweets.filter.SGSAuthorFilter
import com.twitter.home_mixer.product.scored_tweets.marshaller.ScoredTweetsResponseDomainMarshaller
import com.twitter.home_mixer.product.scored_tweets.marshaller.ScoredTweetsResponseTransportMarshaller
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.DefaultRequestedMaxResultsParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableClipImageClusterDedupingParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableColdStartFilterParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableContentExplorationSimclusterColdPostsCandidateBoostingParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableContentExplorationCandidatePipelineParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableContentExplorationMixedCandidateBoostingParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableControlAiParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableDeepRetrievalMixedCandidateBoostingParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableHeartbeatOptimizerWeightsParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableMediaClusterDedupingParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableMediaDedupingParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnablePhoenixRescoreParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableRecentEngagementCacheRefreshParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ServerMaxResultsParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableMediaClusterDecayParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableRealTimeEntityRealGraphFeaturesParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableTopicSocialProofFeaturesParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableUserActionsFeatureParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableUserEngagedLanguagesFeaturesParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableUserHistoryEventsFeaturesParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableUserIdentifierFeaturesParam
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsHeuristicScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsLowSignalScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsModelScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsRerankingScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.selector.KeepTopKCandidatesPerCommunity
import com.twitter.home_mixer.product.scored_tweets.side_effect.CacheCandidateFeaturesSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.CacheRequestInfoSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.CacheRetrievalSignalSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.CachedScoredTweetsSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.CommonFeaturesSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.ScoredCandidateFeatureKeysKafkaSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.ScoredContentExplorationCandidateScoreFeatureKafkaSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.ScoredPhoenixCandidatesKafkaSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.ScoredStatsSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.ScoredTweetsDiversityStatsSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.ScribeScoredCandidatesSideEffect
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.inject.annotations.Flag
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedBulkCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.communities.CommunityMembershipsQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.control_ai.ControlAiQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweetsQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.location.UserLocationQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.AsyncParamGatedQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.user_fav_avg_embeddings.UserFavAvgTextEmbeddingsQueryFeatureHydrator
import com.twitter.product_mixer.component_library.filter.ParamGatedFilter
import com.twitter.product_mixer.component_library.filter.PredicateFeatureFilter
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.DropDuplicateCandidates
import com.twitter.product_mixer.component_library.selector.DropRequestedMaxResults
import com.twitter.product_mixer.component_library.selector.IdAndClassDuplicationKey
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.component_library.selector.PickFirstCandidateMerger
import com.twitter.product_mixer.component_library.selector.SelectConditionally
import com.twitter.product_mixer.component_library.selector.UpdateSortCandidates
import com.twitter.product_mixer.component_library.selector.sorter.FeatureValueSorter
import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.configapi.StaticParam
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.model.common.identifier.RecommendationPipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mixer.core.pipeline.FailOpenPolicy
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mixer.core.pipeline.recommendation.RecommendationPipelineConfig
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import com.twitter.product_mixer.core.quality_factor.BoundsWithDefault
import com.twitter.product_mixer.core.quality_factor.LinearLatencyQualityFactorConfig
import com.twitter.product_mixer.core.quality_factor.QualityFactorConfig
import com.twitter.util.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsRecommendationPipelineConfig @Inject() (
  // Candidate pipelines
  scoredTweetsTweetMixerCandidatePipelineConfig: ScoredTweetsTweetMixerCandidatePipelineConfig,
  scoredTweetsStaticCandidatePipelineConfig: ScoredTweetsStaticCandidatePipelineConfig,
  scoredTweetsListsCandidatePipelineConfig: ScoredTweetsListsCandidatePipelineConfig,
  scoredTweetsBackfillCandidatePipelineConfig: ScoredTweetsBackfillCandidatePipelineConfig,
  scoredTweetsEarlybirdInNetworkCandidatePipelineConfig: ScoredTweetsEarlybirdInNetworkCandidatePipelineConfig,
  scoredTweetsCommunitiesCandidatePipelineConfig: ScoredTweetsCommunitiesCandidatePipelineConfig,
  scoredTweetsDirectUtegCandidatePipelineConfig: ScoredTweetsDirectUtegCandidatePipelineConfig,
  scoredTweetsContentExplorationCandidatePipelineConfig: ScoredTweetsContentExplorationCandidatePipelineConfig,
  cachedScoredTweetsCandidatePipelineConfig: CachedScoredTweetsCandidatePipelineConfig,
  // Query feature hydrators
  followableUttTopicsQueryFeatureHydrator: FollowableUttTopicsQueryFeatureHydrator,
  gizmoduckUserQueryFeatureHydrator: GizmoduckUserQueryFeatureHydrator,
  controlAiQueryFeatureHydrator: ControlAiQueryFeatureHydrator,
  lowSignalUserQueryFeatureHydrator: LowSignalUserQueryFeatureHydrator,
  naviClientConfigQueryFeatureHydrator: NaviClientConfigQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[ScoredTweetsQuery],
  requestTimeQueryFeatureHydrator: RequestTimeQueryFeatureHydrator,
  realTimeInteractionGraphUserVertexQueryFeatureHydrator: RealTimeInteractionGraphUserVertexQueryFeatureHydrator,
  sgsMutuallyFollowedUserHydrator: SGSMutuallyFollowedUserHydrator,
  simClustersUserSparseEmbeddingsQueryFeatureHydrator: SimClustersUserSparseEmbeddingsQueryFeatureHydrator,
  userLocationQueryFeatureHydrator: UserLocationQueryFeatureHydrator,
  userStateQueryFeatureHydrator: UserStateQueryFeatureHydrator,
  userEngagementRealTimeAggregatesFeatureHydrator: UserEngagementRealTimeAggregatesFeatureHydrator,
  twhinRebuildUserEngagementQueryFeatureHydrator: TwhinRebuildUserEngagementQueryFeatureHydrator,
  twhinRebuildUserPositiveQueryFeatureHydrator: TwhinRebuildUserPositiveQueryFeatureHydrator,
  twhinUserEngagementQueryFeatureHydrator: TwhinUserEngagementQueryFeatureHydrator,
  twhinUserFollowQueryFeatureHydrator: TwhinUserFollowQueryFeatureHydrator,
  twhinUserPositiveQueryFeatureHydrator: TwhinUserPositiveQueryFeatureHydrator,
  twhinUserNegativeQueryFeatureHydrator: TwhinUserNegativeQueryFeatureHydrator,
  userHistoryTransformerEmbeddingQueryFeatureHydratorBuilder: UserHistoryTransformerEmbeddingQueryFeatureHydratorBuilder,
  cachedScoredTweetsQueryFeatureHydrator: CachedScoredTweetsQueryFeatureHydrator,
  sgsFollowedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  scoredTweetsModelScoringPipelineConfig: ScoredTweetsModelScoringPipelineConfig,
  scoredTweetsRerankingScoringPipelineConfig: ScoredTweetsRerankingScoringPipelineConfig,
  impressionBloomFilterQueryFeatureHydrator: ImpressionBloomFilterQueryFeatureHydrator,
  memcacheTweetImpressionsQueryFeatureHydrator: ImpressedTweetsQueryFeatureHydrator,
  userEngagementsAvgTextEmbeddingsQueryFeatureHydrator: UserFavAvgTextEmbeddingsQueryFeatureHydrator,
  listIdsQueryFeatureHydrator: ListIdsQueryFeatureHydrator,
  feedbackHistoryQueryFeatureHydrator: FeedbackHistoryQueryFeatureHydrator,
  communityMembershipsQueryFeatureHydrator: CommunityMembershipsQueryFeatureHydrator,
  tweetypieVisibilityFeatureHydrator: TweetypieVisibilityFeatureHydrator,
  realGraphInNetworkScoresQueryFeatureHydrator: RealGraphInNetworkScoresQueryFeatureHydrator,
  realGraphQueryFeatureHydrator: RealGraphQueryFeatureHydrator,
  onPremRealGraphQueryFeatureHydrator: OnPremRealGraphQueryFeatureHydrator,
  entityRealGraphQueryFeatureHydrator: RealTimeEntityRealGraphQueryFeatureHydrator,
  unifiedUserActionsUserIdentifierFeatureHydrator: UnifiedUserActionsUserIdentifierFeatureHydrator,
  userEngagedLanguagesFeatureHydrator: UserEngagedLanguagesFeatureHydrator,
  userLanguagesFeatureHydrator: UserLanguagesFeatureHydrator,
  userUnderstandableLanguagesFeatureHydrator: UserUnderstandableLanguagesFeatureHydrator,
  partAAggregateQueryFeatureHydrator: PartAAggregateQueryFeatureHydrator,
  partBAggregateQueryFeatureHydrator: PartBAggregateQueryFeatureHydrator,
  heavyRankerWeightsQueryFeatureHydrator: HeavyRankerWeightsQueryFeatureHydrator,
  userLargeEmbeddingsFeatureHydrator: UserLargeEmbeddingsFeatureHydrator,
  userHistoryEventsQueryFeatureHydrator: ScoredTweetsUserHistoryEventsQueryFeatureHydrator,
  userActionsQueryFeatureHydrator: UserActionsQueryFeatureHydrator,
  impressedMediaClusterIdsQueryFeatureHydrator: ImpressedMediaClusterIdsQueryFeatureHydrator,
  heartbeatOptimizerParamsHydrator: HeartbeatOptimizerParamsHydrator,
  optimizerWeightsQueryFeatureHydrator: OptimizerWeightsQueryFeatureHydrator,
  userEngagedGrokCategoriesFeatureHydrator: UserEngagedGrokCategoriesFeatureHydrator,
  grokTranslatedPostIsCachedFeatureHydrator: GrokTranslatedPostIsCachedFeatureHydrator,
  isColdStartPostFeatureHydrator: IsColdStartPostFeatureHydrator,
  // Filters
  invalidSubscriptionTweetFilter: InvalidSubscriptionTweetFilter,
  sgsAuthorFilter: SGSAuthorFilter,
  // Side effects
  cacheCandidateFeaturesSideEffect: CacheCandidateFeaturesSideEffect,
  cachedScoredTweetsSideEffect: CachedScoredTweetsSideEffect,
  cacheRetrievalSignalSideEffect: CacheRetrievalSignalSideEffect,
  cacheRequestInfoSideEffect: CacheRequestInfoSideEffect,
  scoredCandidateFeatureKeysKafkaSideEffect: ScoredCandidateFeatureKeysKafkaSideEffect,
  scoredContentExplorationCandidateScoreFeatureKafkaSideEffect: ScoredContentExplorationCandidateScoreFeatureKafkaSideEffect,
  publishClientSentImpressionsEventBusSideEffect: PublishClientSentImpressionsEventBusSideEffect,
  scoredStatsSideEffect: ScoredStatsSideEffect,
  scoredTweetsDiversityStatsSideEffect: ScoredTweetsDiversityStatsSideEffect,
  scoredPhoenixCandidatesKafkaSideEffect: ScoredPhoenixCandidatesKafkaSideEffect,
  scribeCommonFeaturesSideEffect: CommonFeaturesSideEffect,
  scribeScoredCandidatesSideEffect: ScribeScoredCandidatesSideEffect,
  updateLastNonPollingTimeSideEffect: UpdateLastNonPollingTimeSideEffect[
    ScoredTweetsQuery,
    ScoredTweetsResponse
  ],
  @Flag(TargetScoringLatency) targetScoringLatency: Duration)
    extends RecommendationPipelineConfig[
      ScoredTweetsQuery,
      TweetCandidate,
      ScoredTweetsResponse,
      t.ScoredTweetsResponse
    ] {

  override val identifier: RecommendationPipelineIdentifier =
    RecommendationPipelineIdentifier("ScoredTweets")

  private val SubscriptionReplyFilterId = "SubscriptionReply"
  private val OutOfNetworkNSFWFilterId = "OutOfNetworkNSFW"

  private val scoringStep = RecommendationPipelineConfig.scoringPipelinesStep

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[ScoredTweetsQuery]] = Seq(
    naviClientConfigQueryFeatureHydrator,
    requestQueryFeatureHydrator,
    realGraphInNetworkScoresQueryFeatureHydrator,
    cachedScoredTweetsQueryFeatureHydrator,
    sgsFollowedUsersQueryFeatureHydrator,
    memcacheTweetImpressionsQueryFeatureHydrator,
    listIdsQueryFeatureHydrator,
    communityMembershipsQueryFeatureHydrator,
    userStateQueryFeatureHydrator,
    lowSignalUserQueryFeatureHydrator,
    feedbackHistoryQueryFeatureHydrator,
    requestTimeQueryFeatureHydrator,
    userUnderstandableLanguagesFeatureHydrator,
    ParamGatedQueryFeatureHydrator(
      EnableContentExplorationCandidatePipelineParam,
      userEngagedGrokCategoriesFeatureHydrator
    ),
    AsyncQueryFeatureHydrator(
      RecommendationPipelineConfig.globalFiltersStep,
      userLocationQueryFeatureHydrator
    ),
    AsyncQueryFeatureHydrator(
      RecommendationPipelineConfig.globalFiltersStep,
      impressionBloomFilterQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableRealGraphQueryFeaturesParam,
      scoringStep,
      realGraphQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableRealGraphQueryFeaturesParam,
      scoringStep,
      onPremRealGraphQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableRealTimeEntityRealGraphFeaturesParam,
      scoringStep,
      entityRealGraphQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableTwhinRebuildUserEngagementFeaturesParam,
      scoringStep,
      twhinRebuildUserEngagementQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableTwhinRebuildUserPositiveFeaturesParam,
      scoringStep,
      twhinRebuildUserPositiveQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableTwhinUserNegativeFeaturesParam,
      scoringStep,
      twhinUserNegativeQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableTwhinUserPositiveFeaturesParam,
      scoringStep,
      twhinUserPositiveQueryFeatureHydrator
    ),
    AsyncQueryFeatureHydrator(
      scoringStep,
      userHistoryTransformerEmbeddingQueryFeatureHydratorBuilder.buildHomeBlueHydrator()
    ),
    AsyncQueryFeatureHydrator(
      scoringStep,
      userHistoryTransformerEmbeddingQueryFeatureHydratorBuilder.buildHomeGreenHydrator()
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableUserHistoryTransformerJointBlueEmbeddingFeaturesParam,
      scoringStep,
      userHistoryTransformerEmbeddingQueryFeatureHydratorBuilder.buildJointBlueHydrator()
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableTopicSocialProofFeaturesParam,
      scoringStep,
      followableUttTopicsQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableUserEngagedLanguagesFeaturesParam,
      scoringStep,
      userEngagedLanguagesFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableLargeEmbeddingsFeatureHydrationParam,
      scoringStep,
      userLargeEmbeddingsFeatureHydrator,
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableUserHistoryEventsFeaturesParam,
      scoringStep,
      userHistoryEventsQueryFeatureHydrator,
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableUserActionsFeatureParam,
      scoringStep,
      userActionsQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableMediaClusterDecayParam,
      scoringStep,
      impressedMediaClusterIdsQueryFeatureHydrator,
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableUserIdentifierFeaturesParam,
      scoringStep,
      unifiedUserActionsUserIdentifierFeatureHydrator
    ),
    AsyncQueryFeatureHydrator(scoringStep, userLanguagesFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, sgsMutuallyFollowedUserHydrator),
    AsyncQueryFeatureHydrator(scoringStep, userEngagementRealTimeAggregatesFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, realTimeInteractionGraphUserVertexQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, twhinUserFollowQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, twhinUserEngagementQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, partAAggregateQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, partBAggregateQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, heavyRankerWeightsQueryFeatureHydrator),
    ParamGatedQueryFeatureHydrator(
      EnableHeartbeatOptimizerWeightsParam,
      heartbeatOptimizerParamsHydrator
    ),
    AsyncQueryFeatureHydrator(scoringStep, simClustersUserSparseEmbeddingsQueryFeatureHydrator),
    AsyncParamGatedQueryFeatureHydrator(
      EnableControlAiParam,
      scoringStep,
      controlAiQueryFeatureHydrator
    ),
    AsyncParamGatedQueryFeatureHydrator(
      EnableUserFavAvgTextEmbeddingsQueryFeatureParam,
      RecommendationPipelineConfig.resultSideEffectsStep,
      userEngagementsAvgTextEmbeddingsQueryFeatureHydrator
    ),
  )

  override val fetchQueryFeaturesPhase2: Seq[QueryFeatureHydrator[ScoredTweetsQuery]] = Seq(
    AsyncParamGatedQueryFeatureHydrator(
      EnableHeartbeatOptimizerWeightsParam,
      scoringStep,
      optimizerWeightsQueryFeatureHydrator
    ),
    ParamGatedQueryFeatureHydrator(
      EnableRecentEngagementCacheRefreshParam,
      InvalidateCachedScoredTweetsQueryFeatureHydrator
    )
  )

  override val candidatePipelines: Seq[
    CandidatePipelineConfig[ScoredTweetsQuery, _, _, TweetCandidate]
  ] = Seq(
    // Order matters. Duplicated candidates take the first occurrence in the pipelines when merger
    // strategy is PickFirstCandidateMerger.
    scoredTweetsStaticCandidatePipelineConfig,
    cachedScoredTweetsCandidatePipelineConfig,
    scoredTweetsEarlybirdInNetworkCandidatePipelineConfig,
    scoredTweetsDirectUtegCandidatePipelineConfig,
    scoredTweetsTweetMixerCandidatePipelineConfig,
    scoredTweetsContentExplorationCandidatePipelineConfig,
    scoredTweetsListsCandidatePipelineConfig,
    scoredTweetsBackfillCandidatePipelineConfig,
    scoredTweetsCommunitiesCandidatePipelineConfig
  )

  override val postCandidatePipelinesSelectors: Seq[Selector[ScoredTweetsQuery]] = Seq(
    DropDuplicateCandidates(
      pipelineScope = AllPipelines,
      duplicationKey = IdAndClassDuplicationKey,
      mergeStrategy = PickFirstCandidateMerger
    ),
    InsertAppendResults(AllPipelines)
  )

  override val globalFilters: Seq[Filter[ScoredTweetsQuery, TweetCandidate]] = Seq(
    // sort these to have the "cheaper" filters run first
    CustomSnowflakeIdAgeFilter(StaticParam(48.hours)),
    HasAuthorFilter,
    RejectTweetFromViewerFilter,
    RetweetDeduplicationFilter,
    LocationFilter,
    PreviouslySeenTweetsFilter,
    PreviouslyServedTweetsFilter,
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(SubscriptionReplyFilterId),
      shouldKeepCandidate = { features =>
        features.getOrElse(InReplyToTweetIdFeature, None).isEmpty ||
        features.getOrElse(ExclusiveConversationAuthorIdFeature, None).isEmpty
      }
    ),
    FeedbackFatigueFilter,
    invalidSubscriptionTweetFilter,
    sgsAuthorFilter
  )

  override val candidatePipelineFailOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] =
    Map(
      scoredTweetsStaticCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      cachedScoredTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsTweetMixerCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsListsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsBackfillCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsEarlybirdInNetworkCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsCommunitiesCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsDirectUtegCandidatePipelineConfig.identifier -> FailOpenPolicy.Always
    )

  override val scoringPipelineFailOpenPolicies: Map[ScoringPipelineIdentifier, FailOpenPolicy] =
    Map(
      ScoredTweetsHeuristicScoringPipelineConfig.identifier -> FailOpenPolicy.Always,
      ScoredTweetsLowSignalScoringPipelineConfig.identifier -> FailOpenPolicy.Always
    )

  private val scoringPipelineQualityFactorConfig = LinearLatencyQualityFactorConfig(
    qualityFactorBounds = BoundsWithDefault(minInclusive = 0.1, maxInclusive = 1.0, default = 0.95),
    initialDelay = 60.seconds,
    targetLatency = targetScoringLatency,
    targetLatencyPercentile = 95.0,
    delta = 0.00125
  )

  override val qualityFactorConfigs: Map[ComponentIdentifier, QualityFactorConfig] = Map(
    scoredTweetsModelScoringPipelineConfig.identifier -> scoringPipelineQualityFactorConfig,
  )

  override val scoringPipelines: Seq[ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate]] =
    Seq(
      // scoring pipeline - run on non-cached candidates only since cached ones are already scored
      scoredTweetsModelScoringPipelineConfig,
      scoredTweetsRerankingScoringPipelineConfig,
      // re-scoring pipeline - run on all candidates since these are request specific
      ScoredTweetsHeuristicScoringPipelineConfig,
      ScoredTweetsLowSignalScoringPipelineConfig,
    )

  override val postScoringFilters = Seq(
    MinVideoDurationFilter,
    ParamGatedFilter(
      EnableControlAiParam,
      ControlAiExcludeFilter
    ),
    ParamGatedFilter(
      EnableControlAiParam,
      ControlAiOnlyIncludeFilter
    ),
    SlopFilter,
    GrokGoreFilter,
    GrokNsfwFilter,
    GrokSpamFilter,
    GrokViolentFilter,
    LanguageFilter,
    GrokAutoTranslateLanguageFilter,
    DuplicateConversationTweetsFilter,
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier("IsSupportAccountReply"),
      shouldKeepCandidate = { features =>
        !features.getOrElse(IsSupportAccountReplyFeature, false)
      }
    ),
  )

  override val resultSelectors: Seq[Selector[ScoredTweetsQuery]] = Seq(
    KeepTopKCandidatesPerCommunity(AllPipelines),
    UpdateSortCandidates(AllPipelines, FeatureValueSorter.descending(ScoreFeature)),
    SelectConditionally.paramGated(
      SortFixedPositionContentExplorationMixedCandidates,
      EnableContentExplorationMixedCandidateBoostingParam
    ),
    SelectConditionally.paramGated(
      SortFixedPositionDeepRetrievalMixedCandidates,
      EnableDeepRetrievalMixedCandidateBoostingParam
    ),
    SelectConditionally.paramGated(
      SortFixedPositionContentExplorationSimclusterColdPostsCandidates,
      EnableContentExplorationSimclusterColdPostsCandidateBoostingParam
    ),
    InsertAppendResults(AllPipelines),
    DropRequestedMaxResults(
      defaultRequestedMaxResultsParam = DefaultRequestedMaxResultsParam,
      serverMaxResultsParam = ServerMaxResultsParam
    )
  )

  override val postSelectionFeatureHydration = Seq(
    grokTranslatedPostIsCachedFeatureHydrator,
    tweetypieVisibilityFeatureHydrator,
    TweetTypeMetricsFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnablePhoenixRescoreParam,
      PhoenixRescoringFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      TwhinDiversityRescoringParam,
      DiversityRescoringFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      CategoryDiversityRescoringParam,
      CategoryDiversityRescoringFeatureHydrator
    ),
    ParamGatedCandidateFeatureHydrator(
      EnableColdStartFilterParam,
      isColdStartPostFeatureHydrator
    ),
    ValidLikedByUserIdsFeatureHydrator
  )

  override val postSelectionFilters = Seq(
    TweetHydrationFilter,
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(OutOfNetworkNSFWFilterId),
      shouldKeepCandidate = { features => !features.getOrElse(OonNsfwFeature, false) }
    ),
    QuoteDeduplicationFilter,
    ParamGatedFilter(
      EnableMediaDedupingParam,
      MediaIdDeduplicationFilter
    ),
    ParamGatedFilter(
      EnableMediaClusterDedupingParam,
      ClipVideoClusterDeduplicationFilter
    ),
    ParamGatedFilter(
      EnableClipImageClusterDedupingParam,
      ClipImageClusterDeduplicationFilter
    ),
    ParamGatedFilter(
      EnableColdStartFilterParam,
      IsOutOfNetworkColdStartPostFilter
    )
  )

  override val resultSideEffects: Seq[
    PipelineResultSideEffect[ScoredTweetsQuery, ScoredTweetsResponse]
  ] = Seq(
    cacheCandidateFeaturesSideEffect,
    cachedScoredTweetsSideEffect,
    scoredCandidateFeatureKeysKafkaSideEffect,
    scoredContentExplorationCandidateScoreFeatureKafkaSideEffect,
    scoredPhoenixCandidatesKafkaSideEffect,
    publishClientSentImpressionsEventBusSideEffect,
    scoredStatsSideEffect,
    scoredTweetsDiversityStatsSideEffect,
    scribeCommonFeaturesSideEffect,
    scribeScoredCandidatesSideEffect,
    updateLastNonPollingTimeSideEffect,
    cacheRetrievalSignalSideEffect,
    cacheRequestInfoSideEffect
  )

  override val domainMarshaller: DomainMarshaller[
    ScoredTweetsQuery,
    ScoredTweetsResponse
  ] = ScoredTweetsResponseDomainMarshaller

  override val transportMarshaller: TransportMarshaller[
    ScoredTweetsResponse,
    t.ScoredTweetsResponse
  ] = ScoredTweetsResponseTransportMarshaller
}
