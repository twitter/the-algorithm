package com.twitter.home_mixer.product.following

import com.twitter.clientapp.{thriftscala => ca}
import com.twitter.goldfinch.api.AdsInjectionSurfaceAreas
import com.twitter.home_mixer.candidate_pipeline.ConversationServiceCandidatePipelineConfigBuilder
import com.twitter.home_mixer.candidate_pipeline.EditedTweetsCandidatePipelineConfig
import com.twitter.home_mixer.candidate_pipeline.NewTweetsPillCandidatePipelineConfig
import com.twitter.home_mixer.candidate_pipeline.VerifiedPromptCandidatePipelineConfig
import com.twitter.home_mixer.functional_component.decorator.urt.builder.AddEntriesWithReplaceAndShowAlertAndCoverInstructionBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator._
import com.twitter.home_mixer.functional_component.selector.UpdateHomeClientEventDetails
import com.twitter.home_mixer.functional_component.selector.UpdateNewTweetsPillDecoration
import com.twitter.home_mixer.functional_component.side_effect._
import com.twitter.home_mixer.model.ClearCacheIncludeInstruction
import com.twitter.home_mixer.model.NavigationIncludeInstruction
import com.twitter.home_mixer.param.HomeGlobalParams.EnableSSPAdsBrandSafetySettingsFeatureHydratorParam
import com.twitter.home_mixer.param.HomeGlobalParams.MaxNumberReplaceInstructionsParam
import com.twitter.home_mixer.param.HomeMixerFlagName.ScribeClientEventsFlag
import com.twitter.home_mixer.product.following.model.FollowingQuery
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.product.following.param.FollowingParam.ClearCache
import com.twitter.home_mixer.product.following.param.FollowingParam.EnableFlipInjectionModuleCandidatePipelineParam
import com.twitter.home_mixer.product.following.param.FollowingParam.EnablePostContextFeatureHydratorParam
import com.twitter.home_mixer.product.following.param.FollowingParam.Navigation
import com.twitter.home_mixer.product.following.param.FollowingParam.ServerMaxResultsParam
import com.twitter.home_mixer.product.following.param.FollowingParam.StaticParamValueFive
import com.twitter.home_mixer.product.following.param.FollowingParam.StaticParamValueZero
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.inject.annotations.Flag
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mixer.component_library.feature_hydrator.query.ads.SSPAdsBrandSafetySettingsFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweetsQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.location.UserLocationQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersQueryFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.FlipPromptDependentCandidatePipelineConfigBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.selector.FlipPromptDynamicInsertionPosition
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfig
import com.twitter.product_mixer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ClearCacheInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.NavigationInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedCursorIdSelector.TweetIdSelector
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedGapCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceAllEntries
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceEntryInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ShowAlertInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ShowCoverInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.earlybird.EarlybirdGapIncludeInstruction
import com.twitter.product_mixer.component_library.selector.DropMaxCandidates
import com.twitter.product_mixer.component_library.selector.DropMaxModuleItemCandidates
import com.twitter.product_mixer.component_library.selector.DropModuleTooFewModuleItemResults
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.component_library.selector.InsertDynamicPositionResults
import com.twitter.product_mixer.component_library.selector.InsertFixedPositionResults
import com.twitter.product_mixer.component_library.selector.SelectConditionally
import com.twitter.product_mixer.component_library.selector.UpdateSortCandidates
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
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.MixerPipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
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
class FollowingMixerPipelineConfig @Inject() (
  followingEarlybirdCandidatePipelineConfig: FollowingEarlybirdCandidatePipelineConfig,
  conversationServiceCandidatePipelineConfigBuilder: ConversationServiceCandidatePipelineConfigBuilder[
    FollowingQuery
  ],
  followingAdsCandidatePipelineBuilder: FollowingAdsCandidatePipelineBuilder,
  followingWhoToFollowCandidatePipelineConfigBuilder: FollowingWhoToFollowCandidatePipelineConfigBuilder,
  flipPromptDependentCandidatePipelineConfigBuilder: FlipPromptDependentCandidatePipelineConfigBuilder,
  editedTweetsCandidatePipelineConfig: EditedTweetsCandidatePipelineConfig,
  newTweetsPillCandidatePipelineConfig: NewTweetsPillCandidatePipelineConfig[FollowingQuery],
  verifiedPromptCandidatePipelineConfig: VerifiedPromptCandidatePipelineConfig,
  dismissInfoQueryFeatureHydrator: DismissInfoQueryFeatureHydrator,
  gizmoduckUserQueryFeatureHydrator: GizmoduckUserQueryFeatureHydrator,
  persistenceStoreQueryFeatureHydrator: PersistenceStoreQueryFeatureHydrator,
  rateLimitQueryFeatureHydrator: RateLimitQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[FollowingQuery],
  sgsFollowedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  memcacheTweetImpressionsQueryFeatureHydrator: ImpressedTweetsQueryFeatureHydrator,
  lastNonPollingTimeQueryFeatureHydrator: LastNonPollingTimeQueryFeatureHydrator,
  userLocationQueryFeatureHydrator: UserLocationQueryFeatureHydrator,
  userSubscriptionQueryFeatureHydrator: UserSubscriptionQueryFeatureHydrator,
  sspAdsBrandSafetySettingsFeatureHydrator: SSPAdsBrandSafetySettingsFeatureHydrator,
  adsInjector: AdsInjector,
  updateLastNonPollingTimeSideEffect: UpdateLastNonPollingTimeSideEffect[FollowingQuery, Timeline],
  publishClientSentImpressionsEventBusSideEffect: PublishClientSentImpressionsEventBusSideEffect,
  updateTimelinesPersistenceStoreSideEffect: UpdateTimelinesPersistenceStoreSideEffect,
  truncateTimelinesPersistenceStoreSideEffect: TruncateTimelinesPersistenceStoreSideEffect,
  homeTimelineServedCandidatesSideEffect: HomeScribeServedCandidatesSideEffect,
  clientEventsScribeEventPublisher: EventPublisher[ca.LogEvent],
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  urtTransportMarshaller: UrtTransportMarshaller,
  @Flag(ScribeClientEventsFlag) enableScribeClientEvents: Boolean)
    extends MixerPipelineConfig[FollowingQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MixerPipelineIdentifier = MixerPipelineIdentifier("Following")

  private val dependentCandidatesStep = MixerPipelineConfig.dependentCandidatePipelinesStep
  private val resultSelectorsStep = MixerPipelineConfig.resultSelectorsStep

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[FollowingQuery]] = Seq(
    requestQueryFeatureHydrator,
    sgsFollowedUsersQueryFeatureHydrator,
    rateLimitQueryFeatureHydrator,
    gizmoduckUserQueryFeatureHydrator,
    userSubscriptionQueryFeatureHydrator,
    ParamGatedQueryFeatureHydrator(
      EnableSSPAdsBrandSafetySettingsFeatureHydratorParam,
      sspAdsBrandSafetySettingsFeatureHydrator
    ),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, dismissInfoQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, persistenceStoreQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, lastNonPollingTimeQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, userLocationQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep, memcacheTweetImpressionsQueryFeatureHydrator),
  )

  private val earlybirdCandidatePipelineScope =
    SpecificPipeline(followingEarlybirdCandidatePipelineConfig.identifier)

  private val conversationServiceCandidatePipelineConfig =
    conversationServiceCandidatePipelineConfigBuilder.build(
      earlybirdCandidatePipelineScope,
      hmt.ServedType.FollowingInNetwork,
      EnablePostContextFeatureHydratorParam
    )

  private val followingAdsCandidatePipelineConfig = followingAdsCandidatePipelineBuilder.build()

  private val followingWhoToFollowCandidatePipelineConfig =
    followingWhoToFollowCandidatePipelineConfigBuilder.build(earlybirdCandidatePipelineScope)

  private val flipPromptCandidatePipelineConfig =
    flipPromptDependentCandidatePipelineConfigBuilder.build[FollowingQuery](
      supportedClientParam = Some(EnableFlipInjectionModuleCandidatePipelineParam)
    )

  override val candidatePipelines: Seq[CandidatePipelineConfig[FollowingQuery, _, _, _]] = Seq(
    followingEarlybirdCandidatePipelineConfig,
    followingAdsCandidatePipelineConfig
  )

  override val dependentCandidatePipelines: Seq[
    DependentCandidatePipelineConfig[FollowingQuery, _, _, _]
  ] = Seq(
    conversationServiceCandidatePipelineConfig,
    followingWhoToFollowCandidatePipelineConfig,
    flipPromptCandidatePipelineConfig,
    editedTweetsCandidatePipelineConfig,
    newTweetsPillCandidatePipelineConfig,
    verifiedPromptCandidatePipelineConfig
  )

  override val failOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] = Map(
    followingAdsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    followingWhoToFollowCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    flipPromptCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    editedTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    newTweetsPillCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
  )

  override val resultSelectors: Seq[Selector[FollowingQuery]] = Seq(
    UpdateSortCandidates(
      ordering = CandidatesUtil.reverseChronTweetsOrdering,
      candidatePipeline = conversationServiceCandidatePipelineConfig.identifier
    ),
    DropMaxCandidates(
      candidatePipeline = editedTweetsCandidatePipelineConfig.identifier,
      maxSelectionsParam = MaxNumberReplaceInstructionsParam
    ),
    DropMaxCandidates(
      candidatePipeline = conversationServiceCandidatePipelineConfig.identifier,
      maxSelectionsParam = ServerMaxResultsParam
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = followingWhoToFollowCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(WhoToFollowArmCandidatePipelineConfig.MinCandidatesSize)
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = followingWhoToFollowCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(WhoToFollowArmCandidatePipelineConfig.MaxCandidatesSize)
    ),
    InsertAppendResults(candidatePipeline = conversationServiceCandidatePipelineConfig.identifier),
    InsertFixedPositionResults(
      candidatePipeline = verifiedPromptCandidatePipelineConfig.identifier,
      positionParam = StaticParamValueZero
    ),
    InsertDynamicPositionResults(
      candidatePipeline = flipPromptCandidatePipelineConfig.identifier,
      dynamicInsertionPosition = FlipPromptDynamicInsertionPosition(StaticParamValueZero)
    ),
    InsertFixedPositionResults(
      candidatePipeline = followingWhoToFollowCandidatePipelineConfig.identifier,
      positionParam = StaticParamValueFive
    ),
    InsertAdResults(
      surfaceAreaName = AdsInjectionSurfaceAreas.HomeTimeline,
      adsInjector = adsInjector.forSurfaceArea(AdsInjectionSurfaceAreas.HomeTimeline),
      adsCandidatePipeline = followingAdsCandidatePipelineConfig.identifier
    ),
    // This selector must come after the tweets are inserted into the results
    UpdateNewTweetsPillDecoration(
      pipelineScope = SpecificPipelines(
        conversationServiceCandidatePipelineConfig.identifier,
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
    UpdateHomeClientEventDetails(
      candidatePipelines = Set(conversationServiceCandidatePipelineConfig.identifier)
    )
  )

  private val homeScribeClientEventSideEffect = HomeScribeClientEventSideEffect(
    enableScribeClientEvents = enableScribeClientEvents,
    logPipelinePublisher = clientEventsScribeEventPublisher,
    injectedTweetsCandidatePipelineIdentifiers =
      Seq(conversationServiceCandidatePipelineConfig.identifier),
    adsCandidatePipelineIdentifier = Some(followingAdsCandidatePipelineConfig.identifier),
    whoToFollowCandidatePipelineIdentifier =
      Some(followingWhoToFollowCandidatePipelineConfig.identifier),
  )

  override val resultSideEffects: Seq[PipelineResultSideEffect[FollowingQuery, Timeline]] = Seq(
    homeScribeClientEventSideEffect,
    homeTimelineServedCandidatesSideEffect,
    publishClientSentImpressionsEventBusSideEffect,
    truncateTimelinesPersistenceStoreSideEffect,
    updateLastNonPollingTimeSideEffect,
    updateTimelinesPersistenceStoreSideEffect,
  )

  override val domainMarshaller: DomainMarshaller[FollowingQuery, Timeline] = {
    val instructionBuilders = Seq(
      ClearCacheInstructionBuilder(
        ClearCacheIncludeInstruction(
          ClearCache.PtrEnableParam,
          ClearCache.ColdStartEnableParam,
          ClearCache.WarmStartEnableParam,
          ClearCache.ManualRefreshEnableParam,
          ClearCache.NavigateEnableParam,
          ClearCache.MinEntriesParam
        )),
      ReplaceEntryInstructionBuilder(ReplaceAllEntries),
      AddEntriesWithReplaceAndShowAlertAndCoverInstructionBuilder(),
      ShowAlertInstructionBuilder(),
      ShowCoverInstructionBuilder(),
      NavigationInstructionBuilder(
        NavigationIncludeInstruction(
          Navigation.PtrEnableParam,
          Navigation.ColdStartEnableParam,
          Navigation.WarmStartEnableParam,
          Navigation.ManualRefreshEnableParam,
          Navigation.NavigateEnableParam
        ))
    )

    val topCursorBuilder = OrderedTopCursorBuilder(TweetIdSelector)
    val bottomCursorBuilder =
      OrderedBottomCursorBuilder(TweetIdSelector, EarlybirdGapIncludeInstruction.inverse())
    val gapCursorBuilder = OrderedGapCursorBuilder(TweetIdSelector, EarlybirdGapIncludeInstruction)

    val scribeConfigBuilder =
      StaticTimelineScribeConfigBuilder(TimelineScribeConfig(Some("following"), None, None))
    val metadataBuilder = UrtMetadataBuilder(scribeConfigBuilder = Some(scribeConfigBuilder))

    UrtDomainMarshaller(
      instructionBuilders = instructionBuilders,
      metadataBuilder = Some(metadataBuilder),
      cursorBuilders = Seq(topCursorBuilder, bottomCursorBuilder, gapCursorBuilder)
    )
  }

  override val transportMarshaller: TransportMarshaller[Timeline, urt.TimelineResponse] =
    urtTransportMarshaller
}
