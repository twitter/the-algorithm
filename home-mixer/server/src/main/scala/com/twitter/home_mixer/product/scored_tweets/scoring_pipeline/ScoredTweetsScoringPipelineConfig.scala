package com.twitter.home_mixer.product.scored_tweets.scoring_pipeline

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator._
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.Phase1EdgeAggregateFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.Phase2EdgeAggregateFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates._
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdScoreFeature
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsCrMixerCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsFrsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsInNetworkCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsUtegCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.QualityFactor
import com.twitter.home_mixer.product.scored_tweets.scorer.HomeNaviModelDataRecordScorer
import com.twitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.DropMaxCandidates
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.component_library.selector.UpdateSortCandidates
import com.twitter.product_mixer.core.feature.featuremap.datarecord.AllFeatures
import com.twitter.product_mixer.core.functional_component.common.AllExceptPipelines
import com.twitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.BaseGate
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.UnexpectedCandidateResult
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import com.twitter.timelines.clients.predictionservice.PredictionGRPCService
import com.twitter.timelines.clients.predictionservice.PredictionServiceGRPCClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsScoringPipelineConfig @Inject() (
  scoredTweetsInNetworkCandidatePipelineConfig: ScoredTweetsInNetworkCandidatePipelineConfig,
  scoredTweetsUtegCandidatePipelineConfig: ScoredTweetsUtegCandidatePipelineConfig,
  scoredTweetsCrMixerCandidatePipelineConfig: ScoredTweetsCrMixerCandidatePipelineConfig,
  scoredTweetsFrsCandidatePipelineConfig: ScoredTweetsFrsCandidatePipelineConfig,
  predictionGRPCService: PredictionGRPCService,
  ancestorFeatureHydrator: AncestorFeatureHydrator,
  authorFeatureHydrator: AuthorFeatureHydrator,
  earlybirdFeatureHydrator: EarlybirdFeatureHydrator,
  metricCenterUserCountingFeatureHydrator: MetricCenterUserCountingFeatureHydrator,
  tweetypieContentFeatureHydrator: TweetypieContentFeatureHydrator,
  gizmoduckAuthorSafetyFeatureHydrator: GizmoduckAuthorSafetyFeatureHydrator,
  graphTwoHopFeatureHydrator: GraphTwoHopFeatureHydrator,
  socialGraphServiceFeatureHydrator: SocialGraphServiceFeatureHydrator,
  twhinAuthorFollow20220101FeatureHydrator: TwhinAuthorFollow20220101FeatureHydrator,
  userFollowedTopicIdsFeatureHydrator: UserFollowedTopicIdsFeatureHydrator,
  utegFeatureHydrator: UtegFeatureHydrator,
  realGraphViewerAuthorFeatureHydrator: RealGraphViewerAuthorFeatureHydrator,
  realGraphViewerRelatedUsersFeatureHydrator: RealGraphViewerRelatedUsersFeatureHydrator,
  realTimeInteractionGraphEdgeFeatureHydrator: RealTimeInteractionGraphEdgeFeatureHydrator,
  engagementsReceivedByAuthorRealTimeAggregateFeatureHydrator: EngagementsReceivedByAuthorRealTimeAggregateFeatureHydrator,
  topicCountryEngagementRealTimeAggregateFeatureHydrator: TopicCountryEngagementRealTimeAggregateFeatureHydrator,
  topicEngagementRealTimeAggregateFeatureHydrator: TopicEngagementRealTimeAggregateFeatureHydrator,
  tspInferredTopicFeatureHydrator: TSPInferredTopicFeatureHydrator,
  tweetCountryEngagementRealTimeAggregateFeatureHydrator: TweetCountryEngagementRealTimeAggregateFeatureHydrator,
  tweetEngagementRealTimeAggregateFeatureHydrator: TweetEngagementRealTimeAggregateFeatureHydrator,
  twitterListEngagementRealTimeAggregateFeatureHydrator: TwitterListEngagementRealTimeAggregateFeatureHydrator,
  userAuthorEngagementRealTimeAggregateFeatureHydrator: UserAuthorEngagementRealTimeAggregateFeatureHydrator,
  simClustersEngagementSimilarityFeatureHydrator: SimClustersEngagementSimilarityFeatureHydrator,
  phase1EdgeAggregateFeatureHydrator: Phase1EdgeAggregateFeatureHydrator,
  phase2EdgeAggregateFeatureHydrator: Phase2EdgeAggregateFeatureHydrator,
  statsReceiver: StatsReceiver)
    extends ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScoringPipelineIdentifier = ScoringPipelineIdentifier("ScoredTweets")

  private val nonCachedScoringPipelineScope = AllExceptPipelines(
    pipelinesToExclude = Set(CachedScoredTweetsCandidatePipelineConfig.Identifier)
  )

  override val gates: Seq[BaseGate[ScoredTweetsQuery]] = Seq(
    NonEmptyCandidatesGate(nonCachedScoringPipelineScope)
  )

  private val earlybirdScorePipelineScope = Set(
    scoredTweetsInNetworkCandidatePipelineConfig.identifier,
    scoredTweetsUtegCandidatePipelineConfig.identifier,
    scoredTweetsFrsCandidatePipelineConfig.identifier
  )

  private val earlybirdScoreOrdering: Ordering[CandidateWithDetails] =
    Ordering.by[CandidateWithDetails, Double] {
      case ItemCandidateWithDetails(_, _, features) =>
        -features.getOrElse(EarlybirdScoreFeature, None).getOrElse(0.0)
      case _ => throw PipelineFailure(UnexpectedCandidateResult, "Invalid candidate type")
    }

  override val selectors: Seq[Selector[ScoredTweetsQuery]] = Seq(
    UpdateSortCandidates(SpecificPipelines(earlybirdScorePipelineScope), earlybirdScoreOrdering),
    new DropMaxCandidates(
      pipelineScope = SpecificPipelines(earlybirdScorePipelineScope),
      maxSelector = (query, _, _) =>
        (query.getQualityFactorCurrentValue(identifier) *
          query.params(QualityFactor.MaxTweetsToScoreParam)).toInt
    ),
    new DropMaxCandidates(
      pipelineScope = SpecificPipelines(scoredTweetsCrMixerCandidatePipelineConfig.identifier),
      maxSelector = (query, _, _) =>
        (query.getQualityFactorCurrentValue(identifier) *
          query.params(QualityFactor.CrMixerMaxTweetsToScoreParam)).toInt
    ),
    // Select candidates for Heavy Ranker Feature Hydration and Scoring
    InsertAppendResults(nonCachedScoringPipelineScope)
  )

  override val preScoringFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(
    ancestorFeatureHydrator,
    authorFeatureHydrator,
    earlybirdFeatureHydrator,
    gizmoduckAuthorSafetyFeatureHydrator,
    graphTwoHopFeatureHydrator,
    metricCenterUserCountingFeatureHydrator,
    socialGraphServiceFeatureHydrator,
    TweetMetaDataFeatureHydrator,
    tweetypieContentFeatureHydrator,
    twhinAuthorFollow20220101FeatureHydrator,
    userFollowedTopicIdsFeatureHydrator,
    utegFeatureHydrator,
    realTimeInteractionGraphEdgeFeatureHydrator,
    realGraphViewerAuthorFeatureHydrator,
    // real time aggregates
    engagementsReceivedByAuthorRealTimeAggregateFeatureHydrator,
    simClustersEngagementSimilarityFeatureHydrator,
    tspInferredTopicFeatureHydrator,
    tweetCountryEngagementRealTimeAggregateFeatureHydrator,
    tweetEngagementRealTimeAggregateFeatureHydrator,
    twitterListEngagementRealTimeAggregateFeatureHydrator,
    userAuthorEngagementRealTimeAggregateFeatureHydrator,
    // offline aggregates
    phase1EdgeAggregateFeatureHydrator
  )

  override val preScoringFeatureHydrationPhase2: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(
    realGraphViewerRelatedUsersFeatureHydrator,
    TimeFeaturesHydrator,
    topicCountryEngagementRealTimeAggregateFeatureHydrator,
    topicEngagementRealTimeAggregateFeatureHydrator,
    phase2EdgeAggregateFeatureHydrator
  )

  private val homeNaviModelDataRecordScorer: Scorer[ScoredTweetsQuery, TweetCandidate] = {
    val modelClient = new PredictionServiceGRPCClient(
      service = predictionGRPCService,
      statsReceiver = statsReceiver,
      requestBatchSize = HomeNaviModelDataRecordScorer.RequestBatchSize,
      useCompact = false
    )
    HomeNaviModelDataRecordScorer(
      identifier = ScorerIdentifier("HomeNaviModel"),
      modelClient = modelClient,
      candidateFeatures = AllFeatures(),
      resultFeatures = HomeNaviModelDataRecordScorer.PredictedScoreFeatures.toSet,
      statsReceiver = statsReceiver
    )
  }

  override val scorers: Seq[Scorer[ScoredTweetsQuery, TweetCandidate]] =
    Seq(homeNaviModelDataRecordScorer)
}
