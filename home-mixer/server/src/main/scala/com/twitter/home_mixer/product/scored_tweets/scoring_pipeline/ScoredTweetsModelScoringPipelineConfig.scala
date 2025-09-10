package com.twitter.home_mixer.product.scored_tweets.scoring_pipeline

import com.twitter.home_mixer.functional_component.feature_hydrator._
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.TopicEdgeAggregateFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.TopicEdgeTruncatedAggregateFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.TweetContentEdgeAggregateFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.UserEngagerEdgeAggregateFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.UserEntityAggregateFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.MediaClusterIdFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates._
import com.twitter.home_mixer.functional_component.scorer.NaviModelScorer
import com.twitter.home_mixer.functional_component.scorer.PhoenixScorer
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdScoreFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePhoenixScorerParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableGeoduckAuthorLocationHydatorParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableSimclustersSparseTweetFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTransformerPostEmbeddingJointBlueFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetLanguageFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinRebuildTweetFeaturesOnlineParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinTweetFeaturesOnlineParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableViewCountFeaturesParam
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.CachedScoredTweetsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsBackfillCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsDirectUtegCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsListsCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsStaticCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsTweetMixerCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.earlybird.ScoredTweetsCommunitiesCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.earlybird.ScoredTweetsEarlybirdInNetworkCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.EarlybirdFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.ReplyFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.SemanticCoreFeatureHydrator
import com.twitter.home_mixer.product.scored_tweets.gate.DenyLowSignalUserGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableClipImagesClusterIdFeatureHydrationParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableMediaCompletionRateFeatureHydrationParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableMultiModalEmbeddingsFeatureHydratorParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableTweetTextV8EmbeddingFeatureParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.QualityFactor
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.embedding.TweetTextV8EmbeddingFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedBulkCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.scorer.param_gated.ParamGatedScorer
import com.twitter.product_mixer.component_library.selector.DropMaxCandidates
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.component_library.selector.UpdateSortCandidates
import com.twitter.product_mixer.core.functional_component.common.AllExceptPipelines
import com.twitter.product_mixer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.gate.BaseGate
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.UnexpectedCandidateResult
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import com.twitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsModelScoringPipelineConfig @Inject() (
  // Candidate sources
  scoredTweetsTweetMixerCandidatePipelineConfig: ScoredTweetsTweetMixerCandidatePipelineConfig,
  scoredTweetsListsCandidatePipelineConfig: ScoredTweetsListsCandidatePipelineConfig,
  scoredTweetsBackfillCandidatePipelineConfig: ScoredTweetsBackfillCandidatePipelineConfig,
  scoredTweetsEarlybirdInNetworkCandidatePipelineConfig: ScoredTweetsEarlybirdInNetworkCandidatePipelineConfig,
  scoredTweetsCommunitiesCandidatePipelineConfig: ScoredTweetsCommunitiesCandidatePipelineConfig,
  scoredTweetsDirectUtegCandidatePipelineConfig: ScoredTweetsDirectUtegCandidatePipelineConfig,
  // Feature hydrators
  ancestorFeatureHydrator: AncestorFeatureHydrator,
  authorFeatureHydrator: AuthorFeatureHydrator,
  broadcastStateFeatureHydrator: BroadcastStateFeatureHydrator,
  earlybirdFeatureHydrator: EarlybirdFeatureHydrator,
  gizmoduckAuthorFeatureHydrator: GizmoduckAuthorFeatureHydrator,
  geoduckAuthorLocationHydrator: GeoduckAuthorLocationHydrator,
  graphTwoHopFeatureHydrator: GraphTwoHopFeatureHydrator,
  mediaClusterIdFeatureHydrator: MediaClusterIdFeatureHydrator,
  mediaCompletionRateFeatureHydrator: MediaCompletionRateFeatureHydrator,
  clipImagesClusterIdFeatureHydrator: ClipImageClusterIdFeatureHydrator,
  viralContentCreatorMetricsFeatureHydrator: ViralContentCreatorMetricsFeatureHydrator,
  multiModalEmbeddingsFeatureHydrator: MultiModalEmbeddingsFeatureHydrator,
  realGraphViewerAuthorFeatureHydrator: RealGraphViewerAuthorFeatureHydrator,
  realGraphViewerRelatedUsersFeatureHydrator: RealGraphViewerRelatedUsersFeatureHydrator,
  realTimeInteractionGraphEdgeFeatureHydrator: RealTimeInteractionGraphEdgeFeatureHydrator,
  replyFeatureHydrator: ReplyFeatureHydrator,
  sgsValidSocialContextFeatureHydrator: SGSValidSocialContextFeatureHydrator,
  simClustersEngagementSimilarityFeatureHydrator: SimClustersEngagementSimilarityFeatureHydrator,
  simClustersUserTweetScoresHydrator: SimClustersUserTweetScoresHydrator,
  spaceStateFeatureHydrator: SpaceStateFeatureHydrator,
  tspInferredTopicFeatureHydrator: TSPInferredTopicFeatureHydrator,
  tweetEntityServiceContentFeatureHydrator: TweetEntityServiceContentFeatureHydrator,
  tweetTextV8EmbeddingFeatureHydrator: TweetTextV8EmbeddingFeatureHydrator,
  twhinAuthorFollowFeatureHydrator: TwhinAuthorFollowFeatureHydrator,
  twhinTweetFeatureHydrator: TwhinTweetFeatureHydrator,
  twhinRebuildTweetFeatureHydrator: TwhinRebuildTweetFeatureHydrator,
  utegFeatureHydrator: UtegFeatureHydrator,
  slopAuthorFeatureHydrator: SlopAuthorFeatureHydrator,
  grokAnnotationsFeatureHydrator: GrokAnnotationsFeatureHydrator,
  // Real time aggregate feature hydrators
  engagementsReceivedByAuthorRealTimeAggregateFeatureHydrator: EngagementsReceivedByAuthorRealTimeAggregateFeatureHydrator,
  topicCountryEngagementRealTimeAggregateFeatureHydrator: TopicCountryEngagementRealTimeAggregateFeatureHydrator,
  topicEngagementRealTimeAggregateFeatureHydrator: TopicEngagementRealTimeAggregateFeatureHydrator,
  tweetCountryEngagementRealTimeAggregateFeatureHydrator: TweetCountryEngagementRealTimeAggregateFeatureHydrator,
  tweetEngagementRealTimeAggregateFeatureHydrator: TweetEngagementRealTimeAggregateFeatureHydrator,
  tweetLanguageFeatureHydrator: TweetLanguageFeatureHydrator,
  twitterListEngagementRealTimeAggregateFeatureHydrator: TwitterListEngagementRealTimeAggregateFeatureHydrator,
  userAuthorEngagementRealTimeAggregateFeatureHydrator: UserAuthorEngagementRealTimeAggregateFeatureHydrator,
  viewCountsFeatureHydrator: ViewCountsFeatureHydrator,
  // Large embeddings hydrators
  authorLargeEmbeddingsFeatureHydrator: AuthorLargeEmbeddingsFeatureHydrator,
  originalAuthorLargeEmbeddingsFeatureHydrator: OriginalAuthorLargeEmbeddingsFeatureHydrator,
  tweetLargeEmbeddingsFeatureHydrator: TweetLargeEmbeddingsFeatureHydrator,
  originalTweetLargeEmbeddingsFeatureHydrator: OriginalTweetLargeEmbeddingsFeatureHydrator,
  // Transformer embeddings hydrators
  transformerPostEmbeddingBlueFeatureHydrator: TransformerPostEmbeddingHomeBlueFeatureHydrator,
  transformerPostEmbeddingGreenFeatureHydrator: TransformerPostEmbeddingHomeGreenFeatureHydrator,
  transformerPostEmbeddingJointBlueFeatureHydrator: TransformerPostEmbeddingJointBlueFeatureHydrator,
  simClustersLogFavBasedTweetFeatureHydrator: SimClustersLogFavBasedTweetFeatureHydrator,
  // Scorers
  naviModelScorer: NaviModelScorer,
  phoenixScorer: PhoenixScorer)
    extends ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScoringPipelineIdentifier =
    ScoringPipelineIdentifier("ScoredTweetsModel")

  private val nonCachedScoringPipelineScope = AllExceptPipelines(
    pipelinesToExclude = Set(
      CachedScoredTweetsCandidatePipelineConfig.Identifier,
      ScoredTweetsStaticCandidatePipelineConfig.Identifier
    )
  )

  override val gates: Seq[BaseGate[ScoredTweetsQuery]] = Seq(
    DenyLowSignalUserGate,
    NonEmptyCandidatesGate(nonCachedScoringPipelineScope)
  )

  private val earlybirdScorePipelineScope = Set(
    scoredTweetsEarlybirdInNetworkCandidatePipelineConfig.identifier,
    scoredTweetsDirectUtegCandidatePipelineConfig.identifier
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
        (query.getQualityFactorCurrentValue(identifier) * query.params(qualityFactorParam)).toInt
    )
  }

  override val selectors: Seq[Selector[ScoredTweetsQuery]] = Seq(
    UpdateSortCandidates(SpecificPipelines(earlybirdScorePipelineScope), earlybirdScoreOrdering),
    UpdateSortCandidates(
      SpecificPipeline(scoredTweetsBackfillCandidatePipelineConfig.identifier),
      CandidatesUtil.reverseChronTweetsOrdering
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
      scoredTweetsBackfillCandidatePipelineConfig.identifier,
      QualityFactor.BackfillMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsEarlybirdInNetworkCandidatePipelineConfig.identifier,
      QualityFactor.InNetworkMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsCommunitiesCandidatePipelineConfig.identifier,
      QualityFactor.CommunitiesMaxTweetsToScoreParam
    ),
    qualityFactorDropMaxCandidates(
      scoredTweetsDirectUtegCandidatePipelineConfig.identifier,
      QualityFactor.UtegMaxTweetsToScoreParam
    ),
    // Select candidates for Heavy Ranker Feature Hydration and Scoring
    InsertAppendResults(nonCachedScoringPipelineScope)
  )

  override val preScoringFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ScoredTweetsQuery, TweetCandidate, _]
  ] = Seq(
    DependentBulkCandidateFeatureHydrator(ancestorFeatureHydrator, Seq(replyFeatureHydrator)),
    authorFeatureHydrator,
    DependentBulkCandidateFeatureHydrator(
      earlybirdFeatureHydrator,
      Seq(spaceStateFeatureHydrator, broadcastStateFeatureHydrator, TweetTimeFeatureHydrator)),
    gizmoduckAuthorFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnableGeoduckAuthorLocationHydatorParam,
      geoduckAuthorLocationHydrator,
    ),
    graphTwoHopFeatureHydrator,
    InNetworkFeatureHydrator,
    realGraphViewerAuthorFeatureHydrator,
    realTimeInteractionGraphEdgeFeatureHydrator,
    simClustersEngagementSimilarityFeatureHydrator,
    simClustersUserTweetScoresHydrator,
    DependentBulkCandidateFeatureHydrator(
      tspInferredTopicFeatureHydrator,
      Seq(
        TopicEdgeAggregateFeatureHydrator,
        TopicEdgeTruncatedAggregateFeatureHydrator,
        topicCountryEngagementRealTimeAggregateFeatureHydrator,
        topicEngagementRealTimeAggregateFeatureHydrator
      )
    ),
    TweetMetaDataFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnableTweetTextV8EmbeddingFeatureParam,
      tweetTextV8EmbeddingFeatureHydrator
    ),
    DependentBulkCandidateFeatureHydrator(
      tweetEntityServiceContentFeatureHydrator,
      Seq(
        SemanticCoreFeatureHydrator,
        TweetContentEdgeAggregateFeatureHydrator,
        mediaClusterIdFeatureHydrator)),
    twhinAuthorFollowFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnableTwhinTweetFeaturesOnlineParam,
      twhinTweetFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      EnableTwhinRebuildTweetFeaturesOnlineParam,
      twhinRebuildTweetFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      EnableViewCountFeaturesParam,
      viewCountsFeatureHydrator
    ),
    DependentBulkCandidateFeatureHydrator(
      utegFeatureHydrator,
      Seq(
        realGraphViewerRelatedUsersFeatureHydrator,
        sgsValidSocialContextFeatureHydrator,
        UserEngagerEdgeAggregateFeatureHydrator
      )
    ),
    slopAuthorFeatureHydrator,
    // Real time aggregates
    engagementsReceivedByAuthorRealTimeAggregateFeatureHydrator,
    tweetCountryEngagementRealTimeAggregateFeatureHydrator,
    tweetEngagementRealTimeAggregateFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnableTweetLanguageFeaturesParam,
      tweetLanguageFeatureHydrator
    ),
    twitterListEngagementRealTimeAggregateFeatureHydrator,
    userAuthorEngagementRealTimeAggregateFeatureHydrator,
    // Offline aggregates
    UserEntityAggregateFeatureHydrator,
    viralContentCreatorMetricsFeatureHydrator,
    GrokGorkContentCreatorFeatureHydrator,
    // Large Embeddings
    authorLargeEmbeddingsFeatureHydrator,
    originalAuthorLargeEmbeddingsFeatureHydrator,
    tweetLargeEmbeddingsFeatureHydrator,
    originalTweetLargeEmbeddingsFeatureHydrator,
    // Transformers
    transformerPostEmbeddingBlueFeatureHydrator,
    transformerPostEmbeddingGreenFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnableTransformerPostEmbeddingJointBlueFeaturesParam,
      transformerPostEmbeddingJointBlueFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      EnableSimclustersSparseTweetFeaturesParam,
      simClustersLogFavBasedTweetFeatureHydrator
    ),
    grokAnnotationsFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnableMediaCompletionRateFeatureHydrationParam,
      mediaCompletionRateFeatureHydrator,
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      EnableClipImagesClusterIdFeatureHydrationParam,
      clipImagesClusterIdFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      EnableMultiModalEmbeddingsFeatureHydratorParam,
      multiModalEmbeddingsFeatureHydrator
    )
  )

  override val scorers: Seq[Scorer[ScoredTweetsQuery, TweetCandidate]] = Seq(
    naviModelScorer,
    ParamGatedScorer(EnablePhoenixScorerParam, phoenixScorer)
  )
}
