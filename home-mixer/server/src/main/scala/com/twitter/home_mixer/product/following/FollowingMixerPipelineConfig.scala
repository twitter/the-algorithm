package com.twitter.home_mixer.product.following

import com.twitter.clientapp.{thriftscala => ca}
import com.twitter.goldfinch.api.AdsInjectionSurfaceAreas
import com.twitter.home_mixer.candidate_pipeline.ConversationServiceCandidatePipelineConfigBuilder
import com.twitter.home_mixer.candidate_pipeline.EditedTweetsCandidatePipelineConfig
import com.twitter.home_mixer.candidate_pipeline.NewTweetsPillCandidatePipelineConfig
import com.twitter.home_mixer.functional_component.decorator.HomeConversationServiceCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.HomeFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.decorator.urt.builder.AddEntriesWithReplaceAndShowAlertAndCoverInstructionBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator._
import com.twitter.home_mixer.functional_component.selector.UpdateHomeClientEventDetails
import com.twitter.home_mixer.functional_component.selector.UpdateNewTweetsPillDecoration
import com.twitter.home_mixer.functional_component.side_effect._
import com.twitter.home_mixer.model.GapIncludeInstruction
import com.twitter.home_mixer.param.HomeGlobalParams.MaxNumberReplaceInstructionsParam
import com.twitter.home_mixer.product.following.model.FollowingQuery
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.product.following.param.FollowingParam.EnableFlipInjectionModuleCandidatePipelineParam
import com.twitter.home_mixer.product.following.param.FollowingParam.FlipInlineInjectionModulePosition
import com.twitter.home_mixer.product.following.param.FollowingParam.ServerMaxResultsParam
import com.twitter.home_mixer.product.following.param.FollowingParam.WhoToFollowPositionParam
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.FlipPromptDependentCandidatePipelineConfigBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowArmCandidatePipelineConfig
import com.twitter.product_mixer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedGapCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceAllEntries
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceEntryInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ShowAlertInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ShowCoverInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.twitter.product_mixer.component_library.selector.DropMaxCandidates
import com.twitter.product_mixer.component_library.selector.DropMaxModuleItemCandidates
import com.twitter.product_mixer.component_library.selector.DropModuleTooFewModuleItemResults
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
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
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.MixerPipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
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
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
  followingAdsCandidatePipelineBuilder: FollowingAdsCandidatePipelineBuilder,
  followingWhoToFollowArmCandidatePipelineConfigBuilder: FollowingWhoToFollowArmCandidatePipelineConfigBuilder,
  flipPromptDependentCandidatePipelineConfigBuilder: FlipPromptDependentCandidatePipelineConfigBuilder,
  editedTweetsCandidatePipelineConfig: EditedTweetsCandidatePipelineConfig,
  newTweetsPillCandidatePipelineConfig: NewTweetsPillCandidatePipelineConfig[FollowingQuery],
  dismissInfoQueryFeatureHydrator: DismissInfoQueryFeatureHydrator,
  gizmoduckUserQueryFeatureHydrator: GizmoduckUserQueryFeatureHydrator,
  persistenceStoreQueryFeatureHydrator: PersistenceStoreQueryFeatureHydrator,
  realGraphInNetworkSourceQueryHydrator: RealGraphInNetworkScoresQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[FollowingQuery],
  sgsFollowedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  tweetImpressionsQueryFeatureHydrator: TweetImpressionsQueryFeatureHydrator[FollowingQuery],
  lastNonPollingTimeQueryFeatureHydrator: LastNonPollingTimeQueryFeatureHydrator,
  adsInjector: AdsInjector,
  updateLastNonPollingTimeSideEffect: UpdateLastNonPollingTimeSideEffect[FollowingQuery, Timeline],
  publishClientSentImpressionsEventBusSideEffect: PublishClientSentImpressionsEventBusSideEffect,
  publishClientSentImpressionsManhattanSideEffect: PublishClientSentImpressionsManhattanSideEffect,
  updateTimelinesPersistenceStoreSideEffect: UpdateTimelinesPersistenceStoreSideEffect,
  truncateTimelinesPersistenceStoreSideEffect: TruncateTimelinesPersistenceStoreSideEffect,
  homeTimelineServedEntriesSideEffect: HomeScribeServedEntriesSideEffect,
  clientEventsScribeEventPublisher: EventPublisher[ca.LogEvent],
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  urtTransportMarshaller: UrtTransportMarshaller)
    extends MixerPipelineConfig[FollowingQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MixerPipelineIdentifier = MixerPipelineIdentifier("Following")

  private val dependentCandidatesStep = MixerPipelineConfig.dependentCandidatePipelinesStep
  private val resultSelectorsStep = MixerPipelineConfig.resultSelectorsStep

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[FollowingQuery]] = Seq(
    requestQueryFeatureHydrator,
    sgsFollowedUsersQueryFeatureHydrator,
    realGraphInNetworkSourceQueryHydrator,
    AsyncQueryFeatureHydrator(dependentCandidatesStep, dismissInfoQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, gizmoduckUserQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, persistenceStoreQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, lastNonPollingTimeQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep, tweetImpressionsQueryFeatureHydrator),
  )

  private val earlybirdCandidatePipelineScope =
    SpecificPipeline(followingEarlybirdCandidatePipelineConfig.identifier)

  private val conversationServiceCandidatePipelineConfig =
    conversationServiceCandidatePipelineConfigBuilder.build(
      Seq(NonEmptyCandidatesGate(earlybirdCandidatePipelineScope)),
      HomeConversationServiceCandidateDecorator(homeFeedbackActionInfoBuilder)
    )

  private val followingAdsCandidatePipelineConfig =
    followingAdsCandidatePipelineBuilder.build(earlybirdCandidatePipelineScope)

  private val followingWhoToFollowArmCandidatePipelineConfig =
    followingWhoToFollowArmCandidatePipelineConfigBuilder.build(earlybirdCandidatePipelineScope)

  private val flipPromptCandidatePipelineConfig =
    flipPromptDependentCandidatePipelineConfigBuilder.build[FollowingQuery](
      supportedClientParam = Some(EnableFlipInjectionModuleCandidatePipelineParam)
    )

  override val candidatePipelines: Seq[CandidatePipelineConfig[FollowingQuery, _, _, _]] =
    Seq(followingEarlybirdCandidatePipelineConfig)

  override val dependentCandidatePipelines: Seq[
    DependentCandidatePipelineConfig[FollowingQuery, _, _, _]
  ] = Seq(
    conversationServiceCandidatePipelineConfig,
    followingAdsCandidatePipelineConfig,
    followingWhoToFollowArmCandidatePipelineConfig,
    flipPromptCandidatePipelineConfig,
    editedTweetsCandidatePipelineConfig,
    newTweetsPillCandidatePipelineConfig
  )

  override val failOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] = Map(
    followingAdsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    followingWhoToFollowArmCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
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
      candidatePipeline = followingWhoToFollowArmCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(WhoToFollowArmCandidatePipelineConfig.MinCandidatesSize)
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = followingWhoToFollowArmCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(WhoToFollowArmCandidatePipelineConfig.MaxCandidatesSize)
    ),
    InsertAppendResults(candidatePipeline = conversationServiceCandidatePipelineConfig.identifier),
    InsertFixedPositionResults(
      candidatePipeline = followingWhoToFollowArmCandidatePipelineConfig.identifier,
      positionParam = WhoToFollowPositionParam
    ),
    InsertFixedPositionResults(
      candidatePipeline = flipPromptCandidatePipelineConfig.identifier,
      positionParam = FlipInlineInjectionModulePosition
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
    ),
  )

  private val homeScribeClientEventSideEffect = HomeScribeClientEventSideEffect(
    logPipelinePublisher = clientEventsScribeEventPublisher,
    injectedTweetsCandidatePipelineIdentifiers =
      Seq(conversationServiceCandidatePipelineConfig.identifier),
    adsCandidatePipelineIdentifier = followingAdsCandidatePipelineConfig.identifier,
    whoToFollowCandidatePipelineIdentifier =
      Some(followingWhoToFollowArmCandidatePipelineConfig.identifier),
  )

  override val resultSideEffects: Seq[PipelineResultSideEffect[FollowingQuery, Timeline]] = Seq(
    updateLastNonPollingTimeSideEffect,
    publishClientSentImpressionsEventBusSideEffect,
    publishClientSentImpressionsManhattanSideEffect,
    homeScribeClientEventSideEffect,
    updateTimelinesPersistenceStoreSideEffect,
    truncateTimelinesPersistenceStoreSideEffect,
    homeTimelineServedEntriesSideEffect
  )

  override val domainMarshaller: DomainMarshaller[FollowingQuery, Timeline] = {
    val instructionBuilders = Seq(
      ReplaceEntryInstructionBuilder(ReplaceAllEntries),
      AddEntriesWithReplaceAndShowAlertAndCoverInstructionBuilder(),
      ShowAlertInstructionBuilder(),
      ShowCoverInstructionBuilder(),
    )

    val idSelector: PartialFunction[UniversalNoun[_], Long] = {
      // exclude ads while determining tweet cursor values
      case item: TweetItem if item.promotedMetadata.isEmpty => item.id
      case module: TimelineModule
          if module.items.headOption.exists(_.item.isInstanceOf[TweetItem]) =>
        module.items.last.item match { case item: TweetItem => item.id }
    }
    val topCursorBuilder = OrderedTopCursorBuilder(idSelector)
    val bottomCursorBuilder =
      OrderedBottomCursorBuilder(idSelector, GapIncludeInstruction.inverse())
    val gapCursorBuilder = OrderedGapCursorBuilder(idSelector, GapIncludeInstruction)

    val metadataBuilder = UrtMetadataBuilder(
      title = None,
      scribeConfigBuilder = Some(
        StaticTimelineScribeConfigBuilder(
          TimelineScribeConfig(page = Some("following"), section = None, entityToken = None)))
    )

    UrtDomainMarshaller(
      instructionBuilders = instructionBuilders,
      metadataBuilder = Some(metadataBuilder),
      cursorBuilders = Seq(topCursorBuilder, bottomCursorBuilder, gapCursorBuilder)
    )
  }

  override val transportMarshaller: TransportMarshaller[Timeline, urt.TimelineResponse] =
    urtTransportMarshaller
}
