package com.twitter.home_mixer.product.for_you

import com.twitter.clientapp.{thriftscala => ca}
import com.twitter.goldfinch.api.AdsInjectionSurfaceAreas
import com.twitter.home_mixer.candidate_pipeline.EditedTweetsCandidatePipelineConfig
import com.twitter.home_mixer.candidate_pipeline.NewTweetsPillCandidatePipelineConfig
import com.twitter.home_mixer.functional_component.decorator.urt.builder.AddEntriesWithReplaceAndShowAlertAndCoverInstructionBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator._
import com.twitter.home_mixer.functional_component.selector.DebunchCandidates
import com.twitter.home_mixer.functional_component.selector.UpdateConversationModuleId
import com.twitter.home_mixer.functional_component.selector.UpdateHomeClientEventDetails
import com.twitter.home_mixer.functional_component.selector.UpdateNewTweetsPillDecoration
import com.twitter.home_mixer.functional_component.side_effect._
import com.twitter.home_mixer.model.ClearCacheIncludeInstruction
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.param.HomeGlobalParams.MaxNumberReplaceInstructionsParam
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ClearCacheOnPtr
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableFlipInjectionModuleCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.FlipInlineInjectionModulePosition
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ServerMaxResultsParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.WhoToFollowPositionParam
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweetsQueryFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.FlipPromptCandidatePipelineConfigBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowCandidatePipelineConfig
import com.twitter.product_mixer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ClearCacheInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
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
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.MixerPipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
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
class ForYouTimelineScorerMixerPipelineConfig @Inject() (
  forYouTimelineScorerCandidatePipelineConfig: ForYouTimelineScorerCandidatePipelineConfig,
  forYouConversationServiceCandidatePipelineConfig: ForYouConversationServiceCandidatePipelineConfig,
  forYouAdsCandidatePipelineBuilder: ForYouAdsCandidatePipelineBuilder,
  forYouWhoToFollowCandidatePipelineConfigBuilder: ForYouWhoToFollowCandidatePipelineConfigBuilder,
  flipPromptCandidatePipelineConfigBuilder: FlipPromptCandidatePipelineConfigBuilder,
  editedTweetsCandidatePipelineConfig: EditedTweetsCandidatePipelineConfig,
  newTweetsPillCandidatePipelineConfig: NewTweetsPillCandidatePipelineConfig[ForYouQuery],
  dismissInfoQueryFeatureHydrator: DismissInfoQueryFeatureHydrator,
  gizmoduckUserQueryFeatureHydrator: GizmoduckUserQueryFeatureHydrator,
  manhattanTweetImpressionsQueryFeatureHydrator: TweetImpressionsQueryFeatureHydrator[ForYouQuery],
  memcacheTweetImpressionsQueryFeatureHydrator: ImpressedTweetsQueryFeatureHydrator,
  persistenceStoreQueryFeatureHydrator: PersistenceStoreQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[ForYouQuery],
  timelineServiceTweetsQueryFeatureHydrator: TimelineServiceTweetsQueryFeatureHydrator,
  lastNonPollingTimeQueryFeatureHydrator: LastNonPollingTimeQueryFeatureHydrator,
  feedbackHistoryQueryFeatureHydrator: FeedbackHistoryQueryFeatureHydrator,
  adsInjector: AdsInjector,
  servedCandidateKeysKafkaSideEffectBuilder: ServedCandidateKeysKafkaSideEffectBuilder,
  servedCandidateFeatureKeysKafkaSideEffectBuilder: ServedCandidateFeatureKeysKafkaSideEffectBuilder,
  updateLastNonPollingTimeSideEffect: UpdateLastNonPollingTimeSideEffect[ForYouQuery, Timeline],
  publishClientSentImpressionsEventBusSideEffect: PublishClientSentImpressionsEventBusSideEffect,
  publishClientSentImpressionsManhattanSideEffect: PublishClientSentImpressionsManhattanSideEffect,
  updateTimelinesPersistenceStoreSideEffect: UpdateTimelinesPersistenceStoreSideEffect,
  truncateTimelinesPersistenceStoreSideEffect: TruncateTimelinesPersistenceStoreSideEffect,
  homeScribeServedEntriesSideEffect: HomeScribeServedEntriesSideEffect,
  servedStatsSideEffect: ServedStatsSideEffect,
  clientEventsScribeEventPublisher: EventPublisher[ca.LogEvent],
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  urtTransportMarshaller: UrtTransportMarshaller)
    extends MixerPipelineConfig[ForYouQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MixerPipelineIdentifier = MixerPipelineIdentifier("ForYouTimelineScorer")

  private val MaxConsecutiveOutOfNetworkCandidates = 2

  private val dependentCandidatesStep = MixerPipelineConfig.dependentCandidatePipelinesStep
  private val resultSelectorsStep = MixerPipelineConfig.resultSelectorsStep

  override def fetchQueryFeatures: Seq[QueryFeatureHydrator[ForYouQuery]] = Seq(
    requestQueryFeatureHydrator,
    persistenceStoreQueryFeatureHydrator,
    timelineServiceTweetsQueryFeatureHydrator,
    feedbackHistoryQueryFeatureHydrator,
    AsyncQueryFeatureHydrator(dependentCandidatesStep, dismissInfoQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, gizmoduckUserQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, lastNonPollingTimeQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep, manhattanTweetImpressionsQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep, memcacheTweetImpressionsQueryFeatureHydrator)
  )

  private val forYouAdsCandidatePipelineConfig = forYouAdsCandidatePipelineBuilder.build()

  private val forYouWhoToFollowCandidatePipelineConfig =
    forYouWhoToFollowCandidatePipelineConfigBuilder.build()

  private val flipPromptCandidatePipelineConfig =
    flipPromptCandidatePipelineConfigBuilder.build[ForYouQuery](
      supportedClientParam = Some(EnableFlipInjectionModuleCandidatePipelineParam)
    )

  override val candidatePipelines: Seq[CandidatePipelineConfig[ForYouQuery, _, _, _]] = Seq(
    forYouTimelineScorerCandidatePipelineConfig,
    forYouAdsCandidatePipelineConfig,
    forYouWhoToFollowCandidatePipelineConfig,
    flipPromptCandidatePipelineConfig
  )

  override val dependentCandidatePipelines: Seq[
    DependentCandidatePipelineConfig[ForYouQuery, _, _, _]
  ] = Seq(
    forYouConversationServiceCandidatePipelineConfig,
    editedTweetsCandidatePipelineConfig,
    newTweetsPillCandidatePipelineConfig
  )

  override val failOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] = Map(
    forYouTimelineScorerCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouAdsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouWhoToFollowCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    flipPromptCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    editedTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    newTweetsPillCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
  )

  override val resultSelectors: Seq[Selector[ForYouQuery]] = Seq(
    UpdateSortCandidates(
      ordering = CandidatesUtil.reverseChronTweetsOrdering,
      candidatePipeline = forYouConversationServiceCandidatePipelineConfig.identifier
    ),
    UpdateSortCandidates(
      ordering = CandidatesUtil.scoreOrdering,
      candidatePipeline = forYouTimelineScorerCandidatePipelineConfig.identifier
    ),
    UpdateSortModuleItemCandidates(
      candidatePipeline = forYouTimelineScorerCandidatePipelineConfig.identifier,
      ordering = CandidatesUtil.conversationModuleTweetsOrdering
    ),
    DebunchCandidates(
      pipelineScope = SpecificPipeline(forYouTimelineScorerCandidatePipelineConfig.identifier),
      mustDebunch = {
        case item: ItemCandidateWithDetails =>
          !item.features.getOrElse(InNetworkFeature, false)
        case module: ModuleCandidateWithDetails =>
          !module.candidates.last.features.getOrElse(InNetworkFeature, false)
      },
      maxBunchSize = MaxConsecutiveOutOfNetworkCandidates
    ),
    UpdateConversationModuleId(
      pipelineScope = SpecificPipeline(forYouTimelineScorerCandidatePipelineConfig.identifier)
    ),
    DropMaxCandidates(
      candidatePipeline = forYouConversationServiceCandidatePipelineConfig.identifier,
      maxSelectionsParam = ServerMaxResultsParam
    ),
    DropMaxCandidates(
      candidatePipeline = forYouTimelineScorerCandidatePipelineConfig.identifier,
      maxSelectionsParam = ServerMaxResultsParam
    ),
    DropMaxCandidates(
      candidatePipeline = editedTweetsCandidatePipelineConfig.identifier,
      maxSelectionsParam = MaxNumberReplaceInstructionsParam
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouWhoToFollowCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(WhoToFollowCandidatePipelineConfig.MinCandidatesSize)
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouWhoToFollowCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(WhoToFollowCandidatePipelineConfig.MaxCandidatesSize)
    ),
    // Add Conversation Service tweets to results only if the scored pipeline doesn't return any
    SelectConditionally(
      selector = InsertAppendResults(
        candidatePipeline = forYouConversationServiceCandidatePipelineConfig.identifier),
      includeSelector = (_, candidates, _) =>
        !candidates.exists(candidate =>
          forYouTimelineScorerCandidatePipelineConfig.identifier == candidate.source)
    ),
    InsertAppendResults(candidatePipeline = forYouTimelineScorerCandidatePipelineConfig.identifier),
    InsertFixedPositionResults(
      candidatePipeline = forYouWhoToFollowCandidatePipelineConfig.identifier,
      positionParam = WhoToFollowPositionParam
    ),
    InsertFixedPositionResults(
      candidatePipeline = flipPromptCandidatePipelineConfig.identifier,
      positionParam = FlipInlineInjectionModulePosition
    ),
    InsertAdResults(
      surfaceAreaName = AdsInjectionSurfaceAreas.HomeTimeline,
      adsInjector = adsInjector.forSurfaceArea(AdsInjectionSurfaceAreas.HomeTimeline),
      adsCandidatePipeline = forYouAdsCandidatePipelineConfig.identifier
    ),
    // This selector must come after the tweets are inserted into the results
    UpdateNewTweetsPillDecoration(
      pipelineScope = SpecificPipelines(
        forYouConversationServiceCandidatePipelineConfig.identifier,
        forYouTimelineScorerCandidatePipelineConfig.identifier,
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
      candidatePipelines = Set(
        forYouConversationServiceCandidatePipelineConfig.identifier,
        forYouTimelineScorerCandidatePipelineConfig.identifier
      )
    ),
  )

  private val servedCandidateKeysKafkaSideEffect =
    servedCandidateKeysKafkaSideEffectBuilder.build(
      Set(forYouTimelineScorerCandidatePipelineConfig.identifier))

  private val servedCandidateFeatureKeysKafkaSideEffect =
    servedCandidateFeatureKeysKafkaSideEffectBuilder.build(
      Set(forYouTimelineScorerCandidatePipelineConfig.identifier))

  private val homeScribeClientEventSideEffect = HomeScribeClientEventSideEffect(
    logPipelinePublisher = clientEventsScribeEventPublisher,
    injectedTweetsCandidatePipelineIdentifiers = Seq(
      forYouTimelineScorerCandidatePipelineConfig.identifier,
      forYouConversationServiceCandidatePipelineConfig.identifier
    ),
    adsCandidatePipelineIdentifier = forYouAdsCandidatePipelineConfig.identifier,
    whoToFollowCandidatePipelineIdentifier =
      Some(forYouWhoToFollowCandidatePipelineConfig.identifier),
  )

  override val resultSideEffects: Seq[PipelineResultSideEffect[ForYouQuery, Timeline]] = Seq(
    servedCandidateKeysKafkaSideEffect,
    servedCandidateFeatureKeysKafkaSideEffect,
    updateLastNonPollingTimeSideEffect,
    publishClientSentImpressionsEventBusSideEffect,
    publishClientSentImpressionsManhattanSideEffect,
    updateTimelinesPersistenceStoreSideEffect,
    truncateTimelinesPersistenceStoreSideEffect,
    homeScribeClientEventSideEffect,
    homeScribeServedEntriesSideEffect,
    servedStatsSideEffect
  )

  override val domainMarshaller: DomainMarshaller[ForYouQuery, Timeline] = {
    val instructionBuilders = Seq(
      ClearCacheInstructionBuilder(
        ClearCacheIncludeInstruction(
          ClearCacheOnPtr.EnableParam,
          ClearCacheOnPtr.MinEntriesParam
        )
      ),
      ReplaceEntryInstructionBuilder(ReplaceAllEntries),
      // excludes alert, cover, and replace candidates
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
    val bottomCursorBuilder = OrderedBottomCursorBuilder(idSelector)

    val metadataBuilder = UrtMetadataBuilder(
      title = None,
      scribeConfigBuilder = Some(
        StaticTimelineScribeConfigBuilder(
          TimelineScribeConfig(
            page = Some("for_you_timeline_scorer"),
            section = None,
            entityToken = None)))
// Yes, this causes a memory leak. Too bad!
    )

    UrtDomainMarshaller(
      instructionBuilders = instructionBuilders,
      metadataBuilder = Some(metadataBuilder),
      cursorBuilders = Seq(topCursorBuilder, bottomCursorBuilder)
    )
  }

  override val transportMarshaller: TransportMarshaller[Timeline, urt.TimelineResponse] =
    urtTransportMarshaller
}
