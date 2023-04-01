package com.twitter.home_mixer.product.scored_tweets

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.functional_component.feature_hydrator.LastNonPollingTimeQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RealGraphInNetworkScoresQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RealGraphQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RealTimeInteractionGraphUserVertexQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.RequestQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetImpressionsQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TwhinUserEngagementQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TwhinUserFollowQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserLanguagesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.UserStateQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.PartAAggregateQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.PartBAggregateQueryFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates.UserEngagementRealTimeAggregatesFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.KeepBestOutOfNetworkTweetPerAuthorFilter
import com.twitter.home_mixer.functional_component.filter.OutOfNetworkCompetitorFilter
import com.twitter.home_mixer.functional_component.filter.OutOfNetworkCompetitorURLFilter
import com.twitter.home_mixer.functional_component.filter.PreviouslySeenTweetsFilter
import com.twitter.home_mixer.functional_component.filter.PreviouslyServedTweetsFilter
import com.twitter.home_mixer.functional_component.filter.RejectTweetFromViewerFilter
import com.twitter.home_mixer.functional_component.filter.RetweetDeduplicationFilter
import com.twitter.home_mixer.functional_component.side_effect.PublishClientSentImpressionsEventBusSideEffect
import com.twitter.home_mixer.functional_component.side_effect.PublishClientSentImpressionsManhattanSideEffect
import com.twitter.home_mixer.functional_component.side_effect.UpdateLastNonPollingTimeSideEffect
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.param.HomeMixerFlagName.TargetFetchLatency
import com.twitter.home_mixer.param.HomeMixerFlagName.TargetScoringLatency
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsCrMixerCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsFrsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsInNetworkCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsUtegCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.CachedScoredTweetsQueryFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.marshaller.ScoredTweetsResponseDomainMarshaller
import com.twitter.home_mixer.product.scored_tweets.marshaller.ScoredTweetsResponseTransportMarshaller
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ServerMaxResultsParam
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsDiversityScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsRescoreOONScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsRescoreVerifiedAuthorScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.scoring_pipeline.ScoredTweetsWeightedScoresSumScoringPipelineConfig
import com.twitter.home_mixer.product.scored_tweets.side_effect.CachedScoredTweetsSideEffect
import com.twitter.home_mixer.product.scored_tweets.side_effect.ScribeServedCommonFeaturesAndCandidateFeaturesSideEffect
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.inject.annotations.Flag
import com.twitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweetsQueryFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.DropDuplicateCandidates
import com.twitter.product_mixer.component_library.selector.DropMaxCandidates
import com.twitter.product_mixer.component_library.selector.IdAndClassDuplicationKey
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.component_library.selector.PickFirstCandidateMerger
import com.twitter.product_mixer.component_library.selector.UpdateSortCandidates
import com.twitter.product_mixer.component_library.selector.sorter.FeatureValueSorter
import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier
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
  scoredTweetsInNetworkCandidatePipelineConfig: ScoredTweetsInNetworkCandidatePipelineConfig,
  scoredTweetsUtegCandidatePipelineConfig: ScoredTweetsUtegCandidatePipelineConfig,
  scoredTweetsCrMixerCandidatePipelineConfig: ScoredTweetsCrMixerCandidatePipelineConfig,
  scoredTweetsFrsCandidatePipelineConfig: ScoredTweetsFrsCandidatePipelineConfig,
  cachedScoredTweetsCandidatePipelineConfig: CachedScoredTweetsCandidatePipelineConfig,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[ScoredTweetsQuery],
  lastNonPollingTimeQueryFeatureHydrator: LastNonPollingTimeQueryFeatureHydrator,
  realTimeInteractionGraphUserVertexQueryFeatureHydrator: RealTimeInteractionGraphUserVertexQueryFeatureHydrator,
  userStateQueryFeatureHydrator: UserStateQueryFeatureHydrator,
  userEngagementRealTimeAggregatesFeatureHydrator: UserEngagementRealTimeAggregatesFeatureHydrator,
  twhinUserEngagementQueryFeatureHydrator: TwhinUserEngagementQueryFeatureHydrator,
  twhinUserFollowQueryFeatureHydrator: TwhinUserFollowQueryFeatureHydrator,
  cachedScoredTweetsQueryFeatureHydrator: CachedScoredTweetsQueryFeatureHydrator,
  scoredTweetsScoringPipelineConfig: ScoredTweetsScoringPipelineConfig,
  scoredTweetsWeightedScoresSumScoringPipelineConfig: ScoredTweetsWeightedScoresSumScoringPipelineConfig,
  manhattanTweetImpressionsQueryFeatureHydrator: TweetImpressionsQueryFeatureHydrator[
    ScoredTweetsQuery
  ],
  memcacheTweetImpressionsQueryFeatureHydrator: ImpressedTweetsQueryFeatureHydrator,
  publishClientSentImpressionsEventBusSideEffect: PublishClientSentImpressionsEventBusSideEffect,
  publishClientSentImpressionsManhattanSideEffect: PublishClientSentImpressionsManhattanSideEffect,
  realGraphInNetworkScoresQueryFeatureHydrator: RealGraphInNetworkScoresQueryFeatureHydrator,
  realGraphQueryFeatureHydrator: RealGraphQueryFeatureHydrator,
  userLanguagesFeatureHydrator: UserLanguagesFeatureHydrator,
  partAAggregateQueryFeatureHydrator: PartAAggregateQueryFeatureHydrator,
  partBAggregateQueryFeatureHydrator: PartBAggregateQueryFeatureHydrator,
  cachedScoredTweetsSideEffect: CachedScoredTweetsSideEffect,
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

  private val scoringStep = RecommendationPipelineConfig.scoringPipelinesStep

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[ScoredTweetsQuery]] = Seq(
    requestQueryFeatureHydrator,
    realGraphInNetworkScoresQueryFeatureHydrator,
    cachedScoredTweetsQueryFeatureHydrator,
    manhattanTweetImpressionsQueryFeatureHydrator,
    memcacheTweetImpressionsQueryFeatureHydrator,
    AsyncQueryFeatureHydrator(scoringStep, realGraphQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, lastNonPollingTimeQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(scoringStep, userStateQueryFeatureHydrator),
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
    scoredTweetsCrMixerCandidatePipelineConfig,
    scoredTweetsFrsCandidatePipelineConfig
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
    PreviouslyServedTweetsFilter,
    PreviouslySeenTweetsFilter,
    OutOfNetworkCompetitorFilter
  )

  override val candidatePipelineFailOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] =
    Map(
      cachedScoredTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsInNetworkCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsUtegCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsCrMixerCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
      scoredTweetsFrsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always
    )

  override val scoringPipelineFailOpenPolicies: Map[ScoringPipelineIdentifier, FailOpenPolicy] =
    Map(
      ScoredTweetsRescoreOONScoringPipelineConfig.identifier -> FailOpenPolicy.Always,
      ScoredTweetsRescoreVerifiedAuthorScoringPipelineConfig.identifier -> FailOpenPolicy.Always,
      ScoredTweetsDiversityScoringPipelineConfig.identifier -> FailOpenPolicy.Always
    )

  private val candidatePipelineQualityFactorConfig = LinearLatencyQualityFactorConfig(
    qualityFactorBounds = BoundsWithDefault(minInclusive = 0.1, maxInclusive = 1.0, default = 0.4),
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
    scoredTweetsCrMixerCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    scoredTweetsFrsCandidatePipelineConfig.identifier -> candidatePipelineQualityFactorConfig,
    // scoring pipelines
    scoredTweetsScoringPipelineConfig.identifier -> scoringPipelineQualityFactorConfig,
  )

  override val scoringPipelines: Seq[ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate]] =
    Seq(
      // scoring pipielines - run on non-cached candidates only since cached ones are already scored
      scoredTweetsScoringPipelineConfig,
      scoredTweetsWeightedScoresSumScoringPipelineConfig,
      // re-scoring pipielines - run on all candidates since these are request specific
      ScoredTweetsRescoreOONScoringPipelineConfig,
      ScoredTweetsRescoreVerifiedAuthorScoringPipelineConfig,
      ScoredTweetsDiversityScoringPipelineConfig
    )

  override val resultSelectors: Seq[Selector[ScoredTweetsQuery]] = Seq(
    UpdateSortCandidates(AllPipelines, FeatureValueSorter.descending(ScoreFeature)),
    DropMaxCandidates(AllPipelines, ServerMaxResultsParam),
    InsertAppendResults(AllPipelines)
  )

  override val postSelectionFilters = Seq(
    OutOfNetworkCompetitorURLFilter,
    KeepBestOutOfNetworkTweetPerAuthorFilter,
  )

  override val resultSideEffects: Seq[
    PipelineResultSideEffect[ScoredTweetsQuery, ScoredTweetsResponse]
  ] = Seq(
    cachedScoredTweetsSideEffect,
    scribeServedCommonFeaturesAndCandidateFeaturesSideEffect,
    publishClientSentImpressionsEventBusSideEffect,
    publishClientSentImpressionsManhattanSideEffect,
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
