package com.X.home_mixer.product.scored_tweets

import com.X.conversions.DurationOps._
import com.X.home_mixer.functional_component.feature_hydrator.FeedbackHistoryQueryFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.ImpressionBloomFilterQueryFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.RealGraphInNetworkScoresQueryFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.RequestQueryFeatureHydrator
import com.X.home_mixer.functional_component.feature_hydrator.TweetImpressionsQueryFeatureHydrator
import com.X.home_mixer.functional_component.filter.FeedbackFatigueFilter
import com.X.home_mixer.functional_component.filter.PreviouslySeenTweetsFilter
import com.X.home_mixer.functional_component.filter.PreviouslyServedTweetsFilter
import com.X.home_mixer.functional_component.filter.RejectTweetFromViewerFilter
import com.X.home_mixer.functional_component.filter.RetweetDeduplicationFilter
import com.X.home_mixer.functional_component.side_effect.PublishClientSentImpressionsEventBusSideEffect
import com.X.home_mixer.functional_component.side_effect.PublishClientSentImpressionsManhattanSideEffect
import com.X.home_mixer.functional_component.side_effect.PublishImpressionBloomFilterSideEffect
import com.X.home_mixer.functional_component.side_effect.UpdateLastNonPollingTimeSideEffect
import com.X.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.InNetworkFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.IsSupportAccountReplyFeature
import com.X.home_mixer.model.HomeFeatures.ScoreFeature
import com.X.home_mixer.param.HomeGlobalParams.EnableImpressionBloomFilter
import com.X.home_mixer.param.HomeMixerFlagName.TargetFetchLatency
import com.X.home_mixer.param.HomeMixerFlagName.TargetScoringLatency
import com.X.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig
import com.X.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsBackfillCandidatePipelineConfig
import com.X.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsFrsCandidatePipelineConfig
import com.X.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsInNetworkCandidatePipelineConfig
import com.X.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsListsCandidatePipelineConfig
import com.X.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsPopularVideosCandidatePipelineConfig
import com.X.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsTweetMixerCandidatePipelineConfig
import com.X.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsUtegCandidatePipelineConfig
import com.X.home_mixer.product.scored_tweets.feature_hydrator.CachedScoredTweetsQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.ListIdsQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.RealGraphQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.RealTimeInteractionGraphUserVertexQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.RequestTimeQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.TwhinUserEngagementQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.TwhinUserFollowQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.UserLanguagesFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.UserStateQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.PartAAggregateQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.PartBAggregateQueryFeatureHydrator
import com.X.home_mixer.product.scored_tweets.feature_hydrator.real_time_aggregates.UserEngagementRealTimeAggregatesFeatureHydrator
import com.X.home_mixer.product.scored_tweets.filter.DuplicateConversationTweetsFilter
import com.X.home_mixer.product.scored_tweets.filter.OutOfNetworkCompetitorFilter
import com.X.home_mixer.product.scored_tweets.filter.OutOfNetworkCompetitorURLFilter
import com.X.home_mixer.product.scored_tweets.filter.ScoredTweetsSocialContextFilter
import com.X.home_mixer.product.scored_tweets.marshaller.ScoredTweetsResponseDomainMarshaller
import com.X.home_mixer.product.scored_tweets.marshaller.ScoredTweetsResponseTransportMarshaller
import com.X.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.X.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.X.home_mixer.product.scored_tweets.param.ScoredTweetsParam.MaxInNetworkResultsParam
import com.X.home_mixer.product.scored_tweets.param.ScoredTweetsParam.MaxOutOfNetworkResultsParam
import com.X.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsHeuristicScoringPipelineConfig
import com.X.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsModelScoringPipelineConfig
import com.X.home_mixer.product.scored_tweets.selector.KeepBestOutOfNetworkCandidatePerAuthorPerSuggestType
import com.X.home_mixer.product.scored_tweets.side_effect.CachedScoredTweetsSideEffect
import com.X.home_mixer.product.scored_tweets.side_effect.ScribeScoredCandidatesSideEffect
import com.X.home_mixer.product.scored_tweets.side_effect.ScribeServedCommonFeaturesAndCandidateFeaturesSideEffect
import com.X.home_mixer.{thriftscala => t}
import com.X.inject.annotations.Flag
import com.X.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.X.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweetsQueryFeatureHydrator
import com.X.product_mixer.component_library.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
import com.X.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersQueryFeatureHydrator
import com.X.product_mixer.component_library.filter.PredicateFeatureFilter
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.component_library.selector.DropDuplicateCandidates
import com.X.product_mixer.component_library.selector.DropFilteredMaxCandidates
import com.X.product_mixer.component_library.selector.DropMaxCandidates
import com.X.product_mixer.component_library.selector.IdAndClassDuplicationKey
import com.X.product_mixer.component_library.selector.InsertAppendResults
import com.X.product_mixer.component_library.selector.PickFirstCandidateMerger
import com.X.product_mixer.component_library.selector.UpdateSortCandidates
import com.X.product_mixer.component_library.selector.sorter.FeatureValueSorter
import com.X.product_mixer.core.functional_component.common.AllExceptPipelines
import com.X.product_mixer.core.functional_component.common.AllPipelines
import com.X.product_mixer.core.functional_component.configapi.StaticParam
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.X.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.X.product_mixer.core.functional_component.selector.Selector
import com.X.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.model.common.identifier.RecommendationPipelineIdentifier
import com.X.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.X.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.X.product_mixer.core.pipeline.FailOpenPolicy
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.X.product_mixer.core.pipeline.recommendation.RecommendationPipelineConfig
import com.X.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import com.X.product_mixer.core.quality_factor.BoundsWithDefault
import com.X.product_mixer.core.quality_factor.LinearLatencyQualityFactorConfig
import com.X.product_mixer.core.quality_factor.QualityFactorConfig
import com.X.util.Duration

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsRecommendationPipelineConfig @Inject() (
  scoredTweetsInNetworkCandidatePipelineConfig: ScoredTweetsInNetworkCandidatePipelineConfig,
  scoredTweetsUtegCandidatePipelineConfig: ScoredTweetsUtegCandidatePipelineConfig,
  scoredTweetsTweetMixerCandidatePipelineConfig: ScoredTweetsTweetMixerCandidatePipelineConfig,
  scoredTweetsFrsCandidatePipelineConfig: ScoredTweetsFrsCandidatePipelineConfig,
  scoredTweetsListsCandidatePipelineConfig: ScoredTweetsListsCandidatePipelineConfig,
  scoredTweetsPopularVideosCandidatePipelineConfig: ScoredTweetsPopularVideosCandidatePipelineConfig,
  scoredTweetsBackfillCandidatePipelineConfig: ScoredTweetsBackfillCandidatePipelineConfig,
  cachedScoredTweetsCandidatePipelineConfig: CachedScoredTweetsCandidatePipelineConfig,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[ScoredTweetsQuery],
  requestTimeQueryFeatureHydrator: RequestTimeQueryFeatureHydrator,
  realTimeInteractionGraphUserVertexQueryFeatureHydrator: RealTimeInteractionGraphUserVertexQueryFeatureHydrator,
  userStateQueryFeatureHydrator: UserStateQueryFeatureHydrator,
  userEngagementRealTimeAggregatesFeatureHydrator: UserEngagementRealTimeAggregatesFeatureHydrator,
  twhinUserEngagementQueryFeatureHydrator: TwhinUserEngagementQueryFeatureHydrator,
  twhinUserFollowQueryFeatureHydrator: TwhinUserFollowQueryFeatureHydrator,
  cachedScoredTweetsQueryFeatureHydrator: CachedScoredTweetsQueryFeatureHydrator,
  sgsFollowedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  scoredTweetsModelScoringPipelineConfig: ScoredTweetsModelScoringPipelineConfig,
  impressionBloomFilterQueryFeatureHydrator: ImpressionBloomFilterQueryFeatureHydrator[
    ScoredTweetsQuery
  ],
  manhattanTweetImpressionsQueryFeatureHydrator: TweetImpressionsQueryFeatureHydrator[
    ScoredTweetsQuery
  ],
  memcacheTweetImpressionsQueryFeatureHydrator: ImpressedTweetsQueryFeatureHydrator,
  listIdsQueryFeatureHydrator: ListIdsQueryFeatureHydrator,
  feedbackHistoryQueryFeatureHydrator: FeedbackHistoryQueryFeatureHydrator,
  publishClientSentImpressionsEventBusSideEffect: PublishClientSentImpressionsEventBusSideEffect,
  publishClientSentImpressionsManhattanSideEffect: PublishClientSentImpressionsManhattanSideEffect,
  publishImpressionBloomFilterSideEffect: PublishImpressionBloomFilterSideEffect,
  realGraphInNetworkScoresQueryFeatureHydrator: RealGraphInNetworkScoresQueryFeatureHydrator,
  realGraphQueryFeatureHydrator: RealGraphQueryFeatureHydrator,
  userLanguagesFeatureHydrator: UserLanguagesFeatureHydrator,
  partAAggregateQueryFeatureHydrator: PartAAggregateQueryFeatureHydrator,
  partBAggregateQueryFeatureHydrator: PartBAggregateQueryFeatureHydrator,
  cachedScoredTweetsSideEffect: CachedScoredTweetsSideEffect,
  scribeScoredCandidatesSideEffect: ScribeScoredCandidatesSideEffect,
  scribeServedCommonFeaturesAndCandidateFeaturesSideEffect: ScribeServedCommonFeaturesAndCandidateFeaturesSideEffect,
  updateLastNonPollingTimeSideEffect: UpdateLastNonPollingTimeSideEffect[
    ScoredTweetsQuery,
    ScoredTweetsResponse
  ],
  @Flag(TargetFetchLatency) targetFetchLatency: Duration,
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
  private val MaxBackfillTweets = 50

  private val scoringStep = RecommendationPipelineConfig.scoringPipelinesStep

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[ScoredTweetsQuery]] = Seq(
    requestQueryFeatureHydrator,
    realGraphInNetworkScoresQueryFeatureHydrator,
    cachedScoredTweetsQueryFeatureHydrator,
    sgsFollowedUsersQueryFeatureHydrator,
    ParamGatedQueryFeatureHydrator(
      EnableImpressionBloomFilter,
      impressionBloomFilterQueryFeatureHydrator
    ),
    manhattanTweetImpressionsQueryFeatureHydrator,
    memcacheTweetImpressionsQueryFeatureHydrator,
    listIdsQueryFeatureHydrator,
    userStateQueryFeatureHydrator,
    AsyncQueryFeatureHydrator(scoringStep, feedbackHistoryQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, realGraphQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, requestTimeQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, userLanguagesFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, userEngagementRealTimeAggregatesFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, realTimeInteractionGraphUserVertexQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, twhinUserFollowQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, twhinUserEngagementQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, partAAggregateQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, partBAggregateQueryFeatureHydrator),
  )

  override val candidatePipelines: Seq[
    CandidatePipelineConfig[ScoredTweetsQuery, _, _, TweetCandidate]
  ] = Seq(
    cachedScoredTweetsCandidatePipelineConfig,
    scoredTweetsInNetworkCandidatePipelineConfig,
    scoredTweetsUtegCandidatePipelineConfig,
    scoredTweetsTweetMixerCandidatePipelineConfig,
    scoredTweetsFrsCandidatePipelineConfig,
    scoredTweetsListsCandidatePipelineConfig,
    scoredTweetsPopularVideosCandidatePipelineConfig,
    scoredTweetsBackfillCandidatePipelineConfig
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
    RejectTweetFromViewerFilter,
    RetweetDeduplicationFilter,
    PreviouslySeenTweetsFilter,
    PreviouslyServedTweetsFilter,
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier(SubscriptionReplyFilterId),
      shouldKeepCandidate = { features =>
        features.getOrElse(InReplyToTweetIdFeature, None).isEmpty ||
        features.getOrElse(ExclusiveConversationAuthorIdFeature, None).isEmpty
      }
    ),
    FeedbackFatigueFilter
  )

  override val candidatePipelineFailOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] =
    Map(
      cachedScoredTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsInNetworkCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsUtegCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsTweetMixerCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsFrsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsListsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsPopularVideosCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsBackfillCandidatePipelineConfig.identifier -> FailOpenPolicy.Always
    )

  override val scoringPipelineFailOpenPolicies: Map[ScoringPipelineIdentifier, FailOpenPolicy] =
    Map(
      ScoredTweetsHeuristicScoringPipelineConfig.identifier -> FailOpenPolicy.Always
    )

  private val candidatePipelineQualityFactorConfig = LinearLatencyQualityFactorConfig(
    qualityFactorBounds = BoundsWithDefault(minInclusive = 0.1, maxInclusive = 1.0, default = 0.95),
    initialDelay = 60.seconds,
    targetLatency = targetFetchLatency,
    targetLatencyPercentile = 95.0,
    delta = 0.00125
  )

  private val scoringPipelineQualityFactorConfig =
    candidatePipelineQualityFactorConfig.copy(targetLatency = targetScoringLatency)

  override val qualityFactorConfigs: Map[ComponentIdentifier, QualityFactorConfig] = Map(
    // candidate pipelines
    scoredTweetsInNetworkCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    scoredTweetsUtegCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    scoredTweetsTweetMixerCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    scoredTweetsFrsCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    scoredTweetsListsCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    scoredTweetsPopularVideosCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    scoredTweetsBackfillCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    // scoring pipelines
    scoredTweetsModelScoringPipelineConfig.identifier -> scoringPipelineQualityFactorConfig,
  )

  override val scoringPipelines: Seq[ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate]] =
    Seq(
      // scoring pipeline - run on non-cached candidates only since cached ones are already scored
      scoredTweetsModelScoringPipelineConfig,
      // re-scoring pipeline - run on all candidates since these are request specific
      ScoredTweetsHeuristicScoringPipelineConfig
    )

  override val postScoringFilters = Seq(
    ScoredTweetsSocialContextFilter,
    OutOfNetworkCompetitorFilter,
    OutOfNetworkCompetitorURLFilter,
    DuplicateConversationTweetsFilter,
    PredicateFeatureFilter.fromPredicate(
      FilterIdentifier("IsSupportAccountReply"),
      shouldKeepCandidate = { features =>
        !features.getOrElse(IsSupportAccountReplyFeature, false)
      })
  )

  override val resultSelectors: Seq[Selector[ScoredTweetsQuery]] = Seq(
    KeepBestOutOfNetworkCandidatePerAuthorPerSuggestType(AllPipelines),
    UpdateSortCandidates(AllPipelines, FeatureValueSorter.descending(ScoreFeature)),
    DropFilteredMaxCandidates(
      pipelineScope =
        AllExceptPipelines(Set(scoredTweetsBackfillCandidatePipelineConfig.identifier)),
      filter = {
        case ItemCandidateWithDetails(_, _, features) =>
          features.getOrElse(InNetworkFeature, false)
        case _ => false
      },
      maxSelectionsParam = MaxInNetworkResultsParam
    ),
    DropFilteredMaxCandidates(
      pipelineScope = AllPipelines,
      filter = {
        case ItemCandidateWithDetails(_, _, features) =>
          !features.getOrElse(InNetworkFeature, false)
        case _ => false
      },
      maxSelectionsParam = MaxOutOfNetworkResultsParam
    ),
    DropMaxCandidates(
      candidatePipeline = scoredTweetsBackfillCandidatePipelineConfig.identifier,
      maxSelectionsParam = StaticParam(MaxBackfillTweets)
    ),
    InsertAppendResults(AllPipelines)
  )

  override val resultSideEffects: Seq[
    PipelineResultSideEffect[ScoredTweetsQuery, ScoredTweetsResponse]
  ] = Seq(
    cachedScoredTweetsSideEffect,
    publishClientSentImpressionsEventBusSideEffect,
    publishClientSentImpressionsManhattanSideEffect,
    publishImpressionBloomFilterSideEffect,
    scribeScoredCandidatesSideEffect,
    scribeServedCommonFeaturesAndCandidateFeaturesSideEffect,
    updateLastNonPollingTimeSideEffect
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
