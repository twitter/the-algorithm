package com.ExTwitter.home_mixer.product.scored_tweets.scoring_pipeline

import com.ExTwitter.home_mixer.functional_component.feature_hydrator._
import com.ExTwitter.home_mixer.model.HomeFeatures.EarlybirdScoreFeature
import com.ExTwitter.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig
import com.ExTwitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsBackfillCandidatePipelineConfig
import com.ExTwitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsFrsCandidatePipelineConfig
import com.ExTwitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsInNetworkCandidatePipelineConfig
import com.ExTwitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsListsCandidatePipelineConfig
import com.ExTwitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsPopularVideosCandidatePipelineConfig
import com.ExTwitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsTweetMixerCandidatePipelineConfig
import com.ExTwitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsUtegCandidatePipelineConfig
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.AncestorFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.AuthorFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.AuthorIsCreatorFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.EarlybirdFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.GizmoduckAuthorFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.GraphTwoHopFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.MetricCenterUserCountingFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.RealGraphViewerAuthorFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.RealGraphViewerRelatedUsersFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.RealTimeInteractionGraphEdgeFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.SimClustersEngagementSimilarityFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.SimClustersUserTweetScoresHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.TSPInferredTopicFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.TweetMetaDataFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.TweetTimeFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.TweetypieContentFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.TwhinAuthorFollowFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.UtegFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.Phase1EdgeAggregateFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.Phase2EdgeAggregateFeatureHydrator
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.real_time_aggregates._
import com.ExTwitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.ExTwitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.QualityFactor
import com.ExTwitter.home_mixer.product.scored_tweets.scorer.NaviModelScorer
import com.ExTwitter.home_mixer.util.CandidatesUtil
import com.ExTwitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.component_library.selector.DropMaxCandidates
import com.ExTwitter.product_mixer.component_library.selector.InsertAppendResults
import com.ExTwitter.product_mixer.component_library.selector.UpdateSortCandidates
import com.ExTwitter.product_mixer.core.functional_component.common.AllExceptPipelines
import com.ExTwitter.product_mixer.core.functional_component.common.SpecificPipeline
import com.ExTwitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.functional_component.gate.BaseGate
import com.ExTwitter.product_mixer.core.functional_component.scorer.Scorer
import com.ExTwitter.product_mixer.core.functional_component.selector.Selector
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.ExTwitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.ExTwitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.UnexpectedCandidateResult
import com.ExTwitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import com.ExTwitter.timelines.configapi.Param

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsModelScoringPipelineConfig @Inject() (
  // candidate sources
  scoredTweetsInNetworkCandidatePipelineConfig: ScoredTweetsInNetworkCandidatePipelineConfig,
  scoredTweetsUtegCandidatePipelineConfig: ScoredTweetsUtegCandidatePipelineConfig,
  scoredTweetsTweetMixerCandidatePipelineConfig: ScoredTweetsTweetMixerCandidatePipelineConfig,
  scoredTweetsFrsCandidatePipelineConfig: ScoredTweetsFrsCandidatePipelineConfig,
  scoredTweetsListsCandidatePipelineConfig: ScoredTweetsListsCandidatePipelineConfig,
  scoredTweetsPopularVideosCandidatePipelineConfig: ScoredTweetsPopularVideosCandidatePipelineConfig,
  scoredTweetsBackfillCandidatePipelineConfig: ScoredTweetsBackfillCandidatePipelineConfig,
  // feature hydrators
  ancestorFeatureHydrator: AncestorFeatureHydrator,
  authorFeatureHydrator: AuthorFeatureHydrator,
  authorIsCreatorFeatureHydrator: AuthorIsCreatorFeatureHydrator,
  earlybirdFeatureHydrator: EarlybirdFeatureHydrator,
  gizmoduckAuthorSafetyFeatureHydrator: GizmoduckAuthorFeatureHydrator,
  graphTwoHopFeatureHydrator: GraphTwoHopFeatureHydrator,
  metricCenterUserCountingFeatureHydrator: MetricCenterUserCountingFeatureHydrator,
  perspectiveFilteredSocialContextFeatureHydrator: PerspectiveFilteredSocialContextFeatureHydrator,
  realGraphViewerAuthorFeatureHydrator: RealGraphViewerAuthorFeatureHydrator,
  realGraphViewerRelatedUsersFeatureHydrator: RealGraphViewerRelatedUsersFeatureHydrator,
  realTimeInteractionGraphEdgeFeatureHydrator: RealTimeInteractionGraphEdgeFeatureHydrator,
  sgsValidSocialContextFeatureHydrator: SGSValidSocialContextFeatureHydrator,
  simClustersEngagementSimilarityFeatureHydrator: SimClustersEngagementSimilarityFeatureHydrator,
  simClustersUserTweetScoresHydrator: SimClustersUserTweetScoresHydrator,
  tspInferredTopicFeatureHydrator: TSPInferredTopicFeatureHydrator,
  tweetypieContentFeatureHydrator: TweetypieContentFeatureHydrator,
  twhinAuthorFollowFeatureHydrator: TwhinAuthorFollowFeatureHydrator,
  utegFeatureHydrator: UtegFeatureHydrator,
  // real time aggregate feature hydrators
  engagementsReceivedByAuthorRealTimeAggregateFeatureHydrator: EngagementsReceivedByAuthorRealTimeAggregateFeatureHydrator,
  topicCountryEngagementRealTimeAggregateFeatureHydrator: TopicCountryEngagementRealTimeAggregateFeatureHydrator,
  topicEngagementRealTimeAggregateFeatureHydrator: TopicEngagementRealTimeAggregateFeatureHydrator,
  tweetCountryEngagementRealTimeAggregateFeatureHydrator: TweetCountryEngagementRealTimeAggregateFeatureHydrator,
  tweetEngagementRealTimeAggregateFeatureHydrator: TweetEngagementRealTimeAggregateFeatureHydrator,
  ExTwitterListEngagementRealTimeAggregateFeatureHydrator: ExTwitterListEngagementRealTimeAggregateFeatureHydrator,
  userAuthorEngagementRealTimeAggregateFeatureHydrator: UserAuthorEngagementRealTimeAggregateFeatureHydrator,
  // offline aggregate feature hydrators
  phase1EdgeAggregateFeatureHydrator: Phase1EdgeAggregateFeatureHydrator,
  phase2EdgeAggregateFeatureHydrator: Phase2EdgeAggregateFeatureHydrator,
  // model
  naviModelScorer: NaviModelScorer)
    extends ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScoringPipelineIdentifier =
    ScoringPipelineIdentifier("ScoredTweetsModel")

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

  private def qualityFactorDropMaxCandidates(
    pipelineIdentifier: CandidatePipelineIdentifier,
    qualityFactorParam: Param[Int]
  ): DropMaxCandidates[ScoredTweetsQuery] = {
    new DropMaxCandidates(
      pipelineScope = SpecificPipelines(pipelineIdentifier),
      maxSelector = (query, _, _) =>
        (query.getQualityFactorCurrentValue(identifier) *
          query.params(qualityFactorParam)).toInt
    )
  }

  override val selectors: Seq[Selector[ScoredTweetsQuery]] = Seq(
    UpdateSortCandidates(SpecificPipelines(earlybirdScorePipelineScope), earlybirdScoreOrdering),
    UpdateSortCandidates(
      SpecificPipeline(scoredTweetsBackfillCandidatePipelineConfig.identifier),
      CandidatesUtil.reverseChronTweetsOrdering
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsInNetworkCandidatePipelineConfig.identifier,
      QualityFactor.InNetworkMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsUtegCandidatePipelineConfig.identifier,
      QualityFactor.UtegMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsFrsCandidatePipelineConfig.identifier,
      QualityFactor.FrsMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsTweetMixerCandidatePipelineConfig.identifier,
      QualityFactor.TweetMixerMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsListsCandidatePipelineConfig.identifier,
      QualityFactor.ListsMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsPopularVideosCandidatePipelineConfig.identifier,
      QualityFactor.PopularVideosMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsBackfillCandidatePipelineConfig.identifier,
      QualityFactor.BackfillMaxTweetsToScoreParam
    ),
    // Select candidates for Heavy Ranker Feature Hydration and Scoring
    InsertAppendResults(nonCachedScoringPipelineScope)
  )

  override val preScoringFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(
    TweetMetaDataFeatureHydrator,
    ancestorFeatureHydrator,
    authorFeatureHydrator,
    authorIsCreatorFeatureHydrator,
    earlybirdFeatureHydrator,
    gizmoduckAuthorSafetyFeatureHydrator,
    graphTwoHopFeatureHydrator,
    metricCenterUserCountingFeatureHydrator,
    realTimeInteractionGraphEdgeFeatureHydrator,
    realGraphViewerAuthorFeatureHydrator,
    simClustersEngagementSimilarityFeatureHydrator,
    simClustersUserTweetScoresHydrator,
    InNetworkFeatureHydrator,
    tspInferredTopicFeatureHydrator,
    tweetypieContentFeatureHydrator,
    twhinAuthorFollowFeatureHydrator,
    utegFeatureHydrator,
    // real time aggregates
    engagementsReceivedByAuthorRealTimeAggregateFeatureHydrator,
    tweetCountryEngagementRealTimeAggregateFeatureHydrator,
    tweetEngagementRealTimeAggregateFeatureHydrator,
    ExTwitterListEngagementRealTimeAggregateFeatureHydrator,
    userAuthorEngagementRealTimeAggregateFeatureHydrator,
    // offline aggregates
    phase1EdgeAggregateFeatureHydrator
  )

  override val preScoringFeatureHydrationPhase2: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(
    perspectiveFilteredSocialContextFeatureHydrator,
    phase2EdgeAggregateFeatureHydrator,
    realGraphViewerRelatedUsersFeatureHydrator,
    sgsValidSocialContextFeatureHydrator,
    TweetTimeFeatureHydrator,
    topicCountryEngagementRealTimeAggregateFeatureHydrator,
    topicEngagementRealTimeAggregateFeatureHydrator
  )

  override val scorers: Seq[Scorer[ScoredTweetsQuery, TweetCandidate]] = Seq(naviModelScorer)
}