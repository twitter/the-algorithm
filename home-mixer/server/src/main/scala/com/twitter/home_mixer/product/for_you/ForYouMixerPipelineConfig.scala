package com.twitter.home_mixer.product.for_you

import com.twitter.clientapp.{thriftscala => ca}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.goldfinch.api.AdsInjectionSurfaceAreas
import com.twitter.home_mixer.candidate_pipeline.EditedTweetsCandidatePipelineConfig
import com.twitter.home_mixer.candidate_pipeline.NewTweetsPillCandidatePipelineConfig
import com.twitter.home_mixer.candidate_pipeline.VerifiedPromptCandidatePipelineConfig
import com.twitter.home_mixer.functional_component.feature_hydrator._
import com.twitter.home_mixer.functional_component.selector.UpdateConversationModuleId
import com.twitter.home_mixer.functional_component.selector.UpdateHomeClientEventDetails
import com.twitter.home_mixer.functional_component.selector.UpdateNewTweetsPillDecoration
import com.twitter.home_mixer.functional_component.side_effect._
import com.twitter.home_mixer.param.HomeGlobalParams.EnableSSPAdsBrandSafetySettingsFeatureHydratorParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnableUserActionsShadowScribeParam
import com.twitter.home_mixer.param.HomeGlobalParams.MaxNumberReplaceInstructionsParam
import com.twitter.home_mixer.param.HomeMixerFlagName.ScribeClientEventsFlag
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.product.for_you.feature_hydrator.DisplayedGrokTopicQueryFeatureHydrator
import com.twitter.home_mixer.product.for_you.feature_hydrator.FollowingSportsAccountsQueryFeatureHydrator
import com.twitter.home_mixer.product.for_you.feature_hydrator.TimelineServiceTweetsQueryFeatureHydrator
import com.twitter.home_mixer.product.for_you.feature_hydrator.ViewerHasJobRecommendationsFeatureHydrator
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableAdsDebugParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableEntryPointPivotParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableFlipInjectionModuleCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableFollowedGrokTopicsHydrationParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableForYouAppUpsellParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableForYouTimelineAdsSurface
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableForYouTopicSelectorParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableGrokEntryPointPivotParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EntryPointPivotMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ExplorationTweetsTimelinePosition
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ForYouAppUpsellJetfuelRouteParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ForYouAppUpsellPosition
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ForYouTopicSelectorJetfuelRouteParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ForYouTopicSelectorPosition
import com.twitter.home_mixer.product.for_you.param.ForYouParam.GrokEntryPointPivotMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.GrokPivotModuleTimelinePosition
import com.twitter.home_mixer.product.for_you.param.ForYouParam.MaxNumberExplorationTweetsParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.RelevancePromptTweetPositionParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ServerMaxResultsParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.StaticParamValueFive
import com.twitter.home_mixer.product.for_you.param.ForYouParam.StaticParamValueZero
import com.twitter.home_mixer.product.for_you.param.ForYouParam.SuperbowlModuleTimelinePosition
import com.twitter.home_mixer.product.for_you.param.ForYouParam.TuneFeedTimelinePosition
import com.twitter.home_mixer.product.for_you.param.ForYouParam.VideoCarouselNumCandidates
import com.twitter.home_mixer.product.for_you.param.ForYouParam.VideoCarouselNumTweetCandidatesToDedupeAgainstParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.VideoCarouselTimelinePosition
import com.twitter.home_mixer.product.for_you.selector.DebugUpdateSortAdsResult
import com.twitter.home_mixer.product.for_you.selector.RemoveDuplicateCandidatesOutsideModule
import com.twitter.home_mixer.product.for_you.side_effect.ServedCandidateFeatureKeysKafkaSideEffectBuilder
import com.twitter.home_mixer.product.for_you.side_effect.ServedStatsSideEffect
import com.twitter.home_mixer.product.for_you.side_effect.VideoServedStatsSideEffect
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.inject.annotations.Flag
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mixer.component_library.feature_hydrator.query.ads.SSPAdsBrandSafetySettingsFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.AsyncParamGatedQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.PreviewCreatorsQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersQueryFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.FlipPromptCandidatePipelineConfigBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.selector.FlipPromptDynamicInsertionPosition
import com.twitter.product_mixer.component_library.pipeline.candidate.jetfuel_entry_point.JetfuelCandidatePipelineConfigBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowCandidatePipelineConfig
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidatePipelineConfig
import com.twitter.product_mixer.component_library.selector.DropDuplicateCandidates
import com.twitter.product_mixer.component_library.selector.DropMaxCandidates
import com.twitter.product_mixer.component_library.selector.DropMaxModuleItemCandidates
import com.twitter.product_mixer.component_library.selector.DropModuleTooFewModuleItemResults
import com.twitter.product_mixer.component_library.selector.DropOrthogonalCandidates
import com.twitter.product_mixer.component_library.selector.IdAndClassDuplicationKey
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.component_library.selector.InsertDynamicPositionResults
import com.twitter.product_mixer.component_library.selector.InsertFixedPositionResults
import com.twitter.product_mixer.component_library.selector.PickFirstCandidateMerger
import com.twitter.product_mixer.component_library.selector.SelectConditionally
import com.twitter.product_mixer.component_library.selector.UpdateSortCandidates
import com.twitter.product_mixer.component_library.selector.UpdateSortModuleItemCandidates
import com.twitter.product_mixer.component_library.selector.ads.AdsInjector
import com.twitter.product_mixer.component_library.selector.ads.InsertAdResults
import com.twitter.product_mixer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mixer.core.functional_component.configapi.StaticParam
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.UrtTransportMarshaller
import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.MixerPipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.pipeline.FailOpenPolicy
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.twitter.product_mixer.core.pipeline.mixer.MixerPipelineConfig
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ForYouMixerPipelineConfig @Inject() (
  forYouAdsCandidatePipelineBuilder: ForYouAdsCandidatePipelineBuilder,
  forYouCommunitiesToJoinCandidatePipelineConfig: ForYouCommunitiesToJoinCandidatePipelineConfig,
  forYouScoredTweetsCandidatePipelineConfig: ForYouScoredTweetsCandidatePipelineConfig,
  forYouWhoToFollowCandidatePipelineConfigBuilder: ForYouWhoToFollowCandidatePipelineConfigBuilder,
  forYouWhoToSubscribeCandidatePipelineConfigBuilder: ForYouWhoToSubscribeCandidatePipelineConfigBuilder,
  forYouEntryPointPivotCandidatePipelineBuilder: ForYouEntryPointPivotCandidatePipelineBuilder,
  forYouRecommendedJobsCandidatePipelineConfig: ForYouRecommendedJobsCandidatePipelineConfig,
  forYouRecommendedRecruitingOrganizationsCandidatePipelineConfig: ForYouRecommendedRecruitingOrganizationsCandidatePipelineConfig,
  forYouBookmarksCandidatePipelineConfig: ForYouBookmarksCandidatePipelineConfig,
  forYouExplorationTweetsCandidatePipelineConfig: ForYouExplorationTweetsCandidatePipelineConfig,
  forYouJetfuelFrameCandidatePipelineConfig: ForYouJetfuelFrameCandidatePipelineConfig,
  forYouPinnedTweetsCandidatePipelineConfig: ForYouPinnedTweetsCandidatePipelineConfig,
  forYouStoriesCandidatePipelineConfig: ForYouStoriesCandidatePipelineConfig,
  flipPromptCandidatePipelineConfigBuilder: FlipPromptCandidatePipelineConfigBuilder,
  forYouKeywordTrendsCandidatePipelineConfig: ForYouKeywordTrendsCandidatePipelineConfig,
  forYouScoredVideoTweetsCandidatePipelineConfig: ForYouScoredVideoTweetsCandidatePipelineConfig,
  forYouRelevancePromptCandidatePipelineConfig: ForYouRelevancePromptCandidatePipelineConfig,
  editedTweetsCandidatePipelineConfig: EditedTweetsCandidatePipelineConfig,
  forYouTuneFeedCandidatePipelineConfig: ForYouTuneFeedCandidatePipelineConfig,
  newTweetsPillCandidatePipelineConfig: NewTweetsPillCandidatePipelineConfig[ForYouQuery],
  forYouTweetPreviewsCandidatePipelineConfig: ForYouTweetPreviewsCandidatePipelineConfig,
  verifiedPromptCandidatePipelineConfig: VerifiedPromptCandidatePipelineConfig,
  jetfuelCandidatePipelineConfigBuilder: JetfuelCandidatePipelineConfigBuilder,
  dismissInfoQueryFeatureHydrator: DismissInfoQueryFeatureHydrator,
  followingSportsAccountsQueryFeatureHydrator: FollowingSportsAccountsQueryFeatureHydrator,
  gizmoduckUserQueryFeatureHydrator: GizmoduckUserQueryFeatureHydrator,
  impressionBloomFilterQueryFeatureHydrator: ImpressionBloomFilterQueryFeatureHydrator,
  persistenceStoreQueryFeatureHydrator: PersistenceStoreQueryFeatureHydrator,
  rateLimitQueryFeatureHydrator: RateLimitQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[ForYouQuery],
  timelineServiceTweetsQueryFeatureHydrator: TimelineServiceTweetsQueryFeatureHydrator,
  previewCreatorsQueryFeatureHydrator: PreviewCreatorsQueryFeatureHydrator,
  sgsFollowedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  userSubscriptionQueryFeatureHydrator: UserSubscriptionQueryFeatureHydrator,
  displayedGrokTopicQueryFeatureHydrator: DisplayedGrokTopicQueryFeatureHydrator,
  sspAdsBrandSafetySettingsFeatureHydrator: SSPAdsBrandSafetySettingsFeatureHydrator,
  viewerHasJobRecommendationsFeatureHydrator: ViewerHasJobRecommendationsFeatureHydrator,
  userActionsArrayByteQueryFeatureHydrator: UserActionsArrayByteQueryFeatureHydrator,
  adsInjector: AdsInjector,
  updateTimelinesPersistenceStoreSideEffect: UpdateTimelinesPersistenceStoreSideEffect,
  truncateTimelinesPersistenceStoreSideEffect: TruncateTimelinesPersistenceStoreSideEffect,
  homeScribeServedCandidatesSideEffect: HomeScribeServedCandidatesSideEffect,
  servedCandidateFeatureKeysKafkaSideEffectBuilder: ServedCandidateFeatureKeysKafkaSideEffectBuilder,
  clientEventsScribeEventPublisher: EventPublisher[ca.LogEvent],
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  urtTransportMarshaller: UrtTransportMarshaller,
  @Flag(ScribeClientEventsFlag) enableScribeClientEvents: Boolean,
  statsReceiver: StatsReceiver)
    extends MixerPipelineConfig[ForYouQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MixerPipelineIdentifier = MixerPipelineIdentifier("ForYou")

  private val dependentCandidatesStep = MixerPipelineConfig.dependentCandidatePipelinesStep

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[ForYouQuery]] = Seq(
    rateLimitQueryFeatureHydrator,
    requestQueryFeatureHydrator,
    persistenceStoreQueryFeatureHydrator,
    impressionBloomFilterQueryFeatureHydrator,
    timelineServiceTweetsQueryFeatureHydrator,
    previewCreatorsQueryFeatureHydrator,
    sgsFollowedUsersQueryFeatureHydrator,
    gizmoduckUserQueryFeatureHydrator,
    viewerHasJobRecommendationsFeatureHydrator,
    userSubscriptionQueryFeatureHydrator,
    ParamGatedQueryFeatureHydrator(
      EnableFollowedGrokTopicsHydrationParam,
      displayedGrokTopicQueryFeatureHydrator
    ),
    ParamGatedQueryFeatureHydrator(
      EnableSSPAdsBrandSafetySettingsFeatureHydratorParam,
      sspAdsBrandSafetySettingsFeatureHydrator
    ),
    ParamGatedQueryFeatureHydrator(
      EnableEntryPointPivotParam,
      followingSportsAccountsQueryFeatureHydrator
    ),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, dismissInfoQueryFeatureHydrator),
    AsyncParamGatedQueryFeatureHydrator(
      EnableUserActionsShadowScribeParam,
      MixerPipelineConfig.resultSelectorsStep,
      userActionsArrayByteQueryFeatureHydrator
    )
  )

  private val forYouAdsCandidatePipelineConfig = forYouAdsCandidatePipelineBuilder.build()

  private val forYouWhoToFollowCandidatePipelineConfig =
    forYouWhoToFollowCandidatePipelineConfigBuilder.build()

  private val forYouWhoToSubscribeCandidatePipelineConfig =
    forYouWhoToSubscribeCandidatePipelineConfigBuilder.build()

  private val flipPromptCandidatePipelineConfig =
    flipPromptCandidatePipelineConfigBuilder.build[ForYouQuery](
      supportedClientParam = Some(EnableFlipInjectionModuleCandidatePipelineParam)
    )

  private val forYouEventsEntryPointPivotCandidatePipelineConfig =
    forYouEntryPointPivotCandidatePipelineBuilder.build(
      entryPointPivotType =
        ForYouEntryPointPivotCandidatePipelineBuilder.EntryPointPivotType.Events,
      supportedClientParam = EnableEntryPointPivotParam,
      pivotMinInjectionIntervalParam = EntryPointPivotMinInjectionIntervalParam
    )

  private val forYouGrokEntryPointPivotCandidatePipelineConfig =
    forYouEntryPointPivotCandidatePipelineBuilder.build(
      entryPointPivotType = ForYouEntryPointPivotCandidatePipelineBuilder.EntryPointPivotType.Grok,
      supportedClientParam = EnableGrokEntryPointPivotParam,
      pivotMinInjectionIntervalParam = GrokEntryPointPivotMinInjectionIntervalParam
    )

  private val forYouTopicSelectorCandidatePipelineConfig = {
    jetfuelCandidatePipelineConfigBuilder.build[ForYouQuery](
      frameId = "ForYouTopicSelector",
      identifier = CandidatePipelineIdentifier("ForYouTopicSelector"),
      route = ForYouTopicSelectorJetfuelRouteParam,
      gates = Seq(
        ParamGate(name = "ForYouTopicSelector", param = EnableForYouTopicSelectorParam)
      )
    )
  }

  private val forYouAppUpsellCandidatePipelineConfig = {
    jetfuelCandidatePipelineConfigBuilder.build[ForYouQuery](
      frameId = "ForYouAppUpsell",
      identifier = CandidatePipelineIdentifier("ForYouAppUpsell"),
      route = ForYouAppUpsellJetfuelRouteParam,
      gates = Seq(
        ParamGate(name = "ForYouAppUpsell", param = EnableForYouAppUpsellParam)
      )
    )
  }

  override val candidatePipelines: Seq[CandidatePipelineConfig[ForYouQuery, _, _, _]] = Seq(
    forYouScoredTweetsCandidatePipelineConfig,
    forYouAdsCandidatePipelineConfig,
    forYouCommunitiesToJoinCandidatePipelineConfig,
    forYouWhoToFollowCandidatePipelineConfig,
    forYouWhoToSubscribeCandidatePipelineConfig,
    forYouTweetPreviewsCandidatePipelineConfig,
    forYouRecommendedJobsCandidatePipelineConfig,
    forYouEventsEntryPointPivotCandidatePipelineConfig,
    forYouGrokEntryPointPivotCandidatePipelineConfig,
    forYouRecommendedRecruitingOrganizationsCandidatePipelineConfig,
    forYouBookmarksCandidatePipelineConfig,
    forYouExplorationTweetsCandidatePipelineConfig,
    forYouPinnedTweetsCandidatePipelineConfig,
    forYouStoriesCandidatePipelineConfig,
    forYouScoredVideoTweetsCandidatePipelineConfig,
    forYouTuneFeedCandidatePipelineConfig,
    flipPromptCandidatePipelineConfig,
    forYouKeywordTrendsCandidatePipelineConfig,
    forYouJetfuelFrameCandidatePipelineConfig,
    forYouRelevancePromptCandidatePipelineConfig,
    forYouTopicSelectorCandidatePipelineConfig,
    forYouAppUpsellCandidatePipelineConfig,
  )

  override val dependentCandidatePipelines: Seq[
    DependentCandidatePipelineConfig[ForYouQuery, _, _, _]
  ] = Seq(
    editedTweetsCandidatePipelineConfig,
    newTweetsPillCandidatePipelineConfig,
    verifiedPromptCandidatePipelineConfig
  )

  override val failOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] = Map(
    forYouScoredTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouAdsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouCommunitiesToJoinCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouWhoToFollowCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouWhoToSubscribeCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouTweetPreviewsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouRecommendedJobsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouRecommendedRecruitingOrganizationsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouBookmarksCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouExplorationTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouPinnedTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouStoriesCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouScoredVideoTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    flipPromptCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    editedTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouTuneFeedCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    newTweetsPillCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouKeywordTrendsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouJetfuelFrameCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouEventsEntryPointPivotCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouGrokEntryPointPivotCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouRelevancePromptCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouTopicSelectorCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouAppUpsellCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
  )

  override val resultSelectors: Seq[Selector[ForYouQuery]] = Seq(
    UpdateSortCandidates(
      ordering = CandidatesUtil.scoreOrdering,
      candidatePipeline = forYouScoredTweetsCandidatePipelineConfig.identifier
    ),
    UpdateSortModuleItemCandidates(
      candidatePipeline = forYouScoredTweetsCandidatePipelineConfig.identifier,
      ordering = CandidatesUtil.conversationModuleTweetsOrdering
    ),
    UpdateSortCandidates(
      ordering = CandidatesUtil.scoreOrdering,
      candidatePipeline = forYouPinnedTweetsCandidatePipelineConfig.identifier
    ),
    UpdateSortModuleItemCandidates(
      ordering = CandidatesUtil.scoreOrdering,
      candidatePipeline = forYouPinnedTweetsCandidatePipelineConfig.identifier
    ),
    UpdateConversationModuleId(
      pipelineScope = SpecificPipeline(forYouScoredTweetsCandidatePipelineConfig.identifier)
    ),
    RemoveDuplicateCandidatesOutsideModule(
      pipelineScope = SpecificPipeline(forYouScoredVideoTweetsCandidatePipelineConfig.identifier),
      candidatePipelinesOutsideModule = Set(forYouScoredTweetsCandidatePipelineConfig.identifier),
      numCandidatesToCompareAgainst = VideoCarouselNumTweetCandidatesToDedupeAgainstParam
    ),
    RemoveDuplicateCandidatesOutsideModule(
      pipelineScope = SpecificPipeline(forYouTuneFeedCandidatePipelineConfig.identifier),
      candidatePipelinesOutsideModule = Set(forYouScoredTweetsCandidatePipelineConfig.identifier),
      numCandidatesToCompareAgainst = StaticParam(10)
    ),
    DropMaxCandidates(
      candidatePipeline = forYouScoredTweetsCandidatePipelineConfig.identifier,
      maxSelectionsParam = ServerMaxResultsParam
    ),
    DropMaxCandidates(
      candidatePipeline = editedTweetsCandidatePipelineConfig.identifier,
      maxSelectionsParam = MaxNumberReplaceInstructionsParam
    ),
    DropMaxCandidates(
      candidatePipeline = forYouExplorationTweetsCandidatePipelineConfig.identifier,
      maxSelectionsParam = MaxNumberExplorationTweetsParam
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouWhoToFollowCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(WhoToFollowCandidatePipelineConfig.MaxCandidatesSize)
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouWhoToSubscribeCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(WhoToSubscribeCandidatePipelineConfig.MaxCandidatesSize)
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouBookmarksCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(10)
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouScoredVideoTweetsCandidatePipelineConfig.identifier,
      maxModuleItemsParam = VideoCarouselNumCandidates
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouPinnedTweetsCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(10)
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouTuneFeedCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(3)
    ),
    DropMaxCandidates(
      candidatePipeline = forYouPinnedTweetsCandidatePipelineConfig.identifier,
      maxSelectionsParam = StaticParam(1)
    ),
    DropDuplicateCandidates(
      pipelineScope = SpecificPipelines(
        Set(
          forYouScoredTweetsCandidatePipelineConfig.identifier,
          forYouExplorationTweetsCandidatePipelineConfig.identifier
        )),
      duplicationKey = IdAndClassDuplicationKey,
      mergeStrategy = PickFirstCandidateMerger
    ),
    InsertAppendResults(
      candidatePipeline = forYouScoredTweetsCandidatePipelineConfig.identifier
    ),
    DropOrthogonalCandidates(
      orthogonalCandidatePipelines = Seq(
        forYouKeywordTrendsCandidatePipelineConfig.identifier,
        forYouStoriesCandidatePipelineConfig.identifier,
        forYouWhoToFollowCandidatePipelineConfig.identifier,
        forYouWhoToSubscribeCandidatePipelineConfig.identifier,
        forYouTweetPreviewsCandidatePipelineConfig.identifier,
        forYouRecommendedJobsCandidatePipelineConfig.identifier,
        forYouRecommendedRecruitingOrganizationsCandidatePipelineConfig.identifier,
        forYouCommunitiesToJoinCandidatePipelineConfig.identifier,
        forYouBookmarksCandidatePipelineConfig.identifier,
      )
    ),
    DropOrthogonalCandidates(
      orthogonalCandidatePipelines = Seq(
        forYouEventsEntryPointPivotCandidatePipelineConfig.identifier,
        forYouGrokEntryPointPivotCandidatePipelineConfig.identifier,
      )
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouAppUpsellCandidatePipelineConfig.identifier,
      positionParam = ForYouAppUpsellPosition
    ),
    InsertFixedPositionResults(
      candidatePipeline = verifiedPromptCandidatePipelineConfig.identifier,
      positionParam = StaticParamValueZero
    ),
    InsertDynamicPositionResults(
      candidatePipeline = flipPromptCandidatePipelineConfig.identifier,
      dynamicInsertionPosition = FlipPromptDynamicInsertionPosition(StaticParamValueZero)
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouExplorationTweetsCandidatePipelineConfig.identifier,
      positionParam = ExplorationTweetsTimelinePosition
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouJetfuelFrameCandidatePipelineConfig.identifier,
      positionParam = SuperbowlModuleTimelinePosition
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouEventsEntryPointPivotCandidatePipelineConfig.identifier,
      positionParam = SuperbowlModuleTimelinePosition
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouGrokEntryPointPivotCandidatePipelineConfig.identifier,
      positionParam = GrokPivotModuleTimelinePosition
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouPinnedTweetsCandidatePipelineConfig.identifier,
      positionParam = StaticParam(3)
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouScoredVideoTweetsCandidatePipelineConfig.identifier,
      positionParam = VideoCarouselTimelinePosition
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouTuneFeedCandidatePipelineConfig.identifier,
      positionParam = TuneFeedTimelinePosition
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouTopicSelectorCandidatePipelineConfig.identifier,
      positionParam = ForYouTopicSelectorPosition
    ),
    InsertFixedPositionResults(
      candidatePipelines = Set(
        forYouKeywordTrendsCandidatePipelineConfig.identifier,
        forYouStoriesCandidatePipelineConfig.identifier,
        forYouWhoToFollowCandidatePipelineConfig.identifier,
        forYouWhoToSubscribeCandidatePipelineConfig.identifier,
        forYouTweetPreviewsCandidatePipelineConfig.identifier,
        forYouRecommendedJobsCandidatePipelineConfig.identifier,
        forYouRecommendedRecruitingOrganizationsCandidatePipelineConfig.identifier,
        forYouCommunitiesToJoinCandidatePipelineConfig.identifier,
        forYouBookmarksCandidatePipelineConfig.identifier,
      ),
      positionParam = StaticParamValueFive
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouWhoToFollowCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(WhoToFollowCandidatePipelineConfig.MinCandidatesSize)
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouWhoToSubscribeCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(WhoToSubscribeCandidatePipelineConfig.MinCandidatesSize)
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouBookmarksCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(2)
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouScoredVideoTweetsCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(3)
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouStoriesCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(3)
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouKeywordTrendsCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(3)
    ),
    SelectConditionally.paramNotGated(
      InsertAdResults(
        surfaceAreaName = AdsInjectionSurfaceAreas.HomeTimeline,
        adsInjector = adsInjector.forSurfaceArea(AdsInjectionSurfaceAreas.HomeTimeline),
        adsCandidatePipeline = forYouAdsCandidatePipelineConfig.identifier
      ),
      EnableForYouTimelineAdsSurface
    ),
    SelectConditionally.paramGated(
      InsertAdResults(
        surfaceAreaName = AdsInjectionSurfaceAreas.ForYouTimeline,
        adsInjector = adsInjector.forSurfaceArea(AdsInjectionSurfaceAreas.ForYouTimeline),
        adsCandidatePipeline = forYouAdsCandidatePipelineConfig.identifier
      ),
      EnableForYouTimelineAdsSurface
    ),
    SelectConditionally(
      DebugUpdateSortAdsResult(forYouAdsCandidatePipelineConfig.identifier),
      includeSelector = (query, _, _) => query.params(EnableAdsDebugParam)
    ),
    // This selector must come after the tweets are inserted into the results
    UpdateNewTweetsPillDecoration(
      pipelineScope = SpecificPipelines(
        forYouScoredTweetsCandidatePipelineConfig.identifier,
        newTweetsPillCandidatePipelineConfig.identifier
      ),
      stringCenter = stringCenterProvider.get(),
      seeNewTweetsString = externalStrings.seeNewTweetsString,
      tweetedString = externalStrings.tweetedString
    ),
    InsertAppendResults(candidatePipeline = editedTweetsCandidatePipelineConfig.identifier),
    SelectConditionally(
      selector =
        InsertAppendResults(candidatePipeline = newTweetsPillCandidatePipelineConfig.identifier),
      includeSelector = (_, _, results) => CandidatesUtil.containsType[TweetCandidate](results)
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouRelevancePromptCandidatePipelineConfig.identifier,
      positionParam = RelevancePromptTweetPositionParam
    ),
    UpdateHomeClientEventDetails(
      candidatePipelines = Set(
        forYouScoredTweetsCandidatePipelineConfig.identifier,
        forYouTweetPreviewsCandidatePipelineConfig.identifier,
        forYouExplorationTweetsCandidatePipelineConfig.identifier,
        forYouBookmarksCandidatePipelineConfig.identifier,
        forYouPinnedTweetsCandidatePipelineConfig.identifier,
        forYouScoredVideoTweetsCandidatePipelineConfig.identifier,
        forYouTuneFeedCandidatePipelineConfig.identifier,
      )
    )
  )

  private val servedCandidateFeatureKeysKafkaSideEffect =
    servedCandidateFeatureKeysKafkaSideEffectBuilder.build(
      Set(forYouScoredTweetsCandidatePipelineConfig.identifier))

  private val homeScribeClientEventSideEffect = HomeScribeClientEventSideEffect(
    enableScribeClientEvents = enableScribeClientEvents,
    logPipelinePublisher = clientEventsScribeEventPublisher,
    injectedTweetsCandidatePipelineIdentifiers =
      Seq(forYouScoredTweetsCandidatePipelineConfig.identifier),
    adsCandidatePipelineIdentifier = Some(forYouAdsCandidatePipelineConfig.identifier),
    whoToFollowCandidatePipelineIdentifier =
      Some(forYouWhoToFollowCandidatePipelineConfig.identifier),
    whoToSubscribeCandidatePipelineIdentifier =
      Some(forYouWhoToSubscribeCandidatePipelineConfig.identifier),
    forYouCommunitiesToJoinCandidatePipelineIdentifier =
      Some(forYouCommunitiesToJoinCandidatePipelineConfig.identifier),
    forYouRelevancePromptCandidatePipelineIdentifier =
      Some(forYouRelevancePromptCandidatePipelineConfig.identifier)
  )

  override val resultSideEffects: Seq[PipelineResultSideEffect[ForYouQuery, Timeline]] = Seq(
    updateTimelinesPersistenceStoreSideEffect,
    truncateTimelinesPersistenceStoreSideEffect,
    homeScribeClientEventSideEffect,
    homeScribeServedCandidatesSideEffect,
    servedCandidateFeatureKeysKafkaSideEffect,
    ServedStatsSideEffect(
      candidatePipelines = Set(forYouScoredTweetsCandidatePipelineConfig.identifier),
      statsReceiver = statsReceiver
    ),
    VideoServedStatsSideEffect(
      candidatePipelines = Set(forYouScoredTweetsCandidatePipelineConfig.identifier),
      statsReceiver = statsReceiver
    ),
  )

  override val domainMarshaller: DomainMarshaller[ForYouQuery, Timeline] =
    ForYouResponseDomainMarshaller

  override val transportMarshaller: TransportMarshaller[Timeline, urt.TimelineResponse] =
    urtTransportMarshaller
}
