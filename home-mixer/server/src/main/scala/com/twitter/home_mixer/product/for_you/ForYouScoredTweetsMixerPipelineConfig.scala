package com.ExTwitter.home_mixer.product.for_you

import com.ExTwitter.clientapp.{thriftscala => ca}
import com.ExTwitter.goldfinch.api.AdsInjectionSurfaceAreas
import com.ExTwitter.home_mixer.candidate_pipeline.EditedTweetsCandidatePipelineConfig
import com.ExTwitter.home_mixer.candidate_pipeline.NewTweetsPillCandidatePipelineConfig
import com.ExTwitter.home_mixer.functional_component.decorator.urt.builder.AddEntriesWithReplaceAndShowAlertAndCoverInstructionBuilder
import com.ExTwitter.home_mixer.functional_component.feature_hydrator._
import com.ExTwitter.home_mixer.functional_component.selector.DebunchCandidates
import com.ExTwitter.home_mixer.functional_component.selector.UpdateConversationModuleId
import com.ExTwitter.home_mixer.functional_component.selector.UpdateHomeClientEventDetails
import com.ExTwitter.home_mixer.functional_component.selector.UpdateNewTweetsPillDecoration
import com.ExTwitter.home_mixer.functional_component.side_effect._
import com.ExTwitter.home_mixer.model.ClearCacheIncludeInstruction
import com.ExTwitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.ExTwitter.home_mixer.param.HomeGlobalParams.MaxNumberReplaceInstructionsParam
import com.ExTwitter.home_mixer.param.HomeMixerFlagName.ScribeClientEventsFlag
import com.ExTwitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.ExTwitter.home_mixer.product.for_you.feature_hydrator.TimelineServiceTweetsQueryFeatureHydrator
import com.ExTwitter.home_mixer.product.for_you.model.ForYouQuery
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.ClearCacheOnPtr
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.EnableFlipInjectionModuleCandidatePipelineParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.FlipInlineInjectionModulePosition
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.ServerMaxResultsParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.TweetPreviewsPositionParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.WhoToFollowPositionParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.WhoToSubscribePositionParam
import com.ExTwitter.home_mixer.product.for_you.side_effect.ServedCandidateFeatureKeysKafkaSideEffectBuilder
import com.ExTwitter.home_mixer.product.for_you.side_effect.ServedCandidateKeysKafkaSideEffectBuilder
import com.ExTwitter.home_mixer.product.for_you.side_effect.ServedStatsSideEffect
import com.ExTwitter.home_mixer.util.CandidatesUtil
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.logpipeline.client.common.EventPublisher
import com.ExTwitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.ExTwitter.product_mixer.component_library.feature_hydrator.query.social_graph.PreviewCreatorsQueryFeatureHydrator
import com.ExTwitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersQueryFeatureHydrator
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.FlipPromptCandidatePipelineConfigBuilder
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowCandidatePipelineConfig
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidatePipelineConfig
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.ClearCacheInstructionBuilder
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceAllEntries
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceEntryInstructionBuilder
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.ShowAlertInstructionBuilder
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.ShowCoverInstructionBuilder
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.ExTwitter.product_mixer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.ExTwitter.product_mixer.component_library.selector.DropMaxCandidates
import com.ExTwitter.product_mixer.component_library.selector.DropMaxModuleItemCandidates
import com.ExTwitter.product_mixer.component_library.selector.DropModuleTooFewModuleItemResults
import com.ExTwitter.product_mixer.component_library.selector.InsertAppendResults
import com.ExTwitter.product_mixer.component_library.selector.InsertFixedPositionResults
import com.ExTwitter.product_mixer.component_library.selector.SelectConditionally
import com.ExTwitter.product_mixer.component_library.selector.UpdateSortCandidates
import com.ExTwitter.product_mixer.component_library.selector.UpdateSortModuleItemCandidates
import com.ExTwitter.product_mixer.component_library.selector.ads.AdsInjector
import com.ExTwitter.product_mixer.component_library.selector.ads.InsertAdResults
import com.ExTwitter.product_mixer.core.functional_component.common.SpecificPipeline
import com.ExTwitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.ExTwitter.product_mixer.core.functional_component.configapi.StaticParam
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.ExTwitter.product_mixer.core.functional_component.marshaller.response.urt.UrtTransportMarshaller
import com.ExTwitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.ExTwitter.product_mixer.core.functional_component.selector.Selector
import com.ExTwitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.model.common.identifier.MixerPipelineIdentifier
import com.ExTwitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.ExTwitter.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.ExTwitter.product_mixer.core.pipeline.FailOpenPolicy
import com.ExTwitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.ExTwitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.ExTwitter.product_mixer.core.pipeline.mixer.MixerPipelineConfig
import com.ExTwitter.product_mixer.core.product.guice.scope.ProductScoped
import com.ExTwitter.stringcenter.client.StringCenter
import com.ExTwitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ForYouScoredTweetsMixerPipelineConfig @Inject() (
  forYouAdsDependentCandidatePipelineBuilder: ForYouAdsDependentCandidatePipelineBuilder,
  forYouConversationServiceCandidatePipelineConfig: ForYouConversationServiceCandidatePipelineConfig,
  forYouPushToHomeTweetCandidatePipelineConfig: ForYouPushToHomeTweetCandidatePipelineConfig,
  forYouScoredTweetsCandidatePipelineConfig: ForYouScoredTweetsCandidatePipelineConfig,
  forYouWhoToFollowCandidatePipelineConfigBuilder: ForYouWhoToFollowCandidatePipelineConfigBuilder,
  forYouWhoToSubscribeCandidatePipelineConfigBuilder: ForYouWhoToSubscribeCandidatePipelineConfigBuilder,
  flipPromptCandidatePipelineConfigBuilder: FlipPromptCandidatePipelineConfigBuilder,
  editedTweetsCandidatePipelineConfig: EditedTweetsCandidatePipelineConfig,
  newTweetsPillCandidatePipelineConfig: NewTweetsPillCandidatePipelineConfig[ForYouQuery],
  forYouTweetPreviewsCandidatePipelineConfig: ForYouTweetPreviewsCandidatePipelineConfig,
  dismissInfoQueryFeatureHydrator: DismissInfoQueryFeatureHydrator,
  gizmoduckUserQueryFeatureHydrator: GizmoduckUserQueryFeatureHydrator,
  persistenceStoreQueryFeatureHydrator: PersistenceStoreQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[ForYouQuery],
  timelineServiceTweetsQueryFeatureHydrator: TimelineServiceTweetsQueryFeatureHydrator,
  previewCreatorsQueryFeatureHydrator: PreviewCreatorsQueryFeatureHydrator,
  sgsFollowedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  adsInjector: AdsInjector,
  servedCandidateKeysKafkaSideEffectBuilder: ServedCandidateKeysKafkaSideEffectBuilder,
  servedCandidateFeatureKeysKafkaSideEffectBuilder: ServedCandidateFeatureKeysKafkaSideEffectBuilder,
  updateTimelinesPersistenceStoreSideEffect: UpdateTimelinesPersistenceStoreSideEffect,
  truncateTimelinesPersistenceStoreSideEffect: TruncateTimelinesPersistenceStoreSideEffect,
  homeScribeServedCandidatesSideEffect: HomeScribeServedCandidatesSideEffect,
  servedStatsSideEffect: ServedStatsSideEffect,
  clientEventsScribeEventPublisher: EventPublisher[ca.LogEvent],
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  urtTransportMarshaller: UrtTransportMarshaller,
  @Flag(ScribeClientEventsFlag) enableScribeClientEvents: Boolean)
    extends MixerPipelineConfig[ForYouQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MixerPipelineIdentifier = MixerPipelineIdentifier("ForYouScoredTweets")

  private val MaxConsecutiveOutOfNetworkCandidates = 2

  private val PushToHomeTweetPosition = 0

  private val dependentCandidatesStep = MixerPipelineConfig.dependentCandidatePipelinesStep

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[ForYouQuery]] = Seq(
    requestQueryFeatureHydrator,
    persistenceStoreQueryFeatureHydrator,
    timelineServiceTweetsQueryFeatureHydrator,
    previewCreatorsQueryFeatureHydrator,
    sgsFollowedUsersQueryFeatureHydrator,
    AsyncQueryFeatureHydrator(dependentCandidatesStep, dismissInfoQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, gizmoduckUserQueryFeatureHydrator),
  )

  private val scoredTweetsCandidatePipelineScope =
    SpecificPipeline(forYouScoredTweetsCandidatePipelineConfig.identifier)

  private val forYouAdsCandidatePipelineConfig = forYouAdsDependentCandidatePipelineBuilder
    .build(scoredTweetsCandidatePipelineScope)

  private val forYouWhoToFollowCandidatePipelineConfig =
    forYouWhoToFollowCandidatePipelineConfigBuilder.build()

  private val forYouWhoToSubscribeCandidatePipelineConfig =
    forYouWhoToSubscribeCandidatePipelineConfigBuilder.build()

  private val flipPromptCandidatePipelineConfig =
    flipPromptCandidatePipelineConfigBuilder.build[ForYouQuery](
      supportedClientParam = Some(EnableFlipInjectionModuleCandidatePipelineParam)
    )

  override val candidatePipelines: Seq[CandidatePipelineConfig[ForYouQuery, _, _, _]] = Seq(
    forYouScoredTweetsCandidatePipelineConfig,
    forYouPushToHomeTweetCandidatePipelineConfig,
    forYouWhoToFollowCandidatePipelineConfig,
    forYouWhoToSubscribeCandidatePipelineConfig,
    forYouTweetPreviewsCandidatePipelineConfig,
    flipPromptCandidatePipelineConfig
  )

  override val dependentCandidatePipelines: Seq[
    DependentCandidatePipelineConfig[ForYouQuery, _, _, _]
  ] = Seq(
    forYouAdsCandidatePipelineConfig,
    forYouConversationServiceCandidatePipelineConfig,
    editedTweetsCandidatePipelineConfig,
    newTweetsPillCandidatePipelineConfig
  )

  override val failOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] = Map(
    forYouScoredTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouAdsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouWhoToFollowCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouWhoToSubscribeCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    forYouTweetPreviewsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
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
      candidatePipeline = forYouScoredTweetsCandidatePipelineConfig.identifier
    ),
    UpdateSortModuleItemCandidates(
      candidatePipeline = forYouScoredTweetsCandidatePipelineConfig.identifier,
      ordering = CandidatesUtil.conversationModuleTweetsOrdering
    ),
    DebunchCandidates(
      pipelineScope = SpecificPipeline(forYouScoredTweetsCandidatePipelineConfig.identifier),
      mustDebunch = {
        case item: ItemCandidateWithDetails =>
          !item.features.getOrElse(InNetworkFeature, false)
        case module: ModuleCandidateWithDetails =>
          !module.candidates.last.features.getOrElse(InNetworkFeature, false)
      },
      maxBunchSize = MaxConsecutiveOutOfNetworkCandidates
    ),
    UpdateConversationModuleId(
      pipelineScope = SpecificPipeline(forYouScoredTweetsCandidatePipelineConfig.identifier)
    ),
    DropMaxCandidates(
      candidatePipeline = forYouConversationServiceCandidatePipelineConfig.identifier,
      maxSelectionsParam = ServerMaxResultsParam
    ),
    DropMaxCandidates(
      candidatePipeline = forYouScoredTweetsCandidatePipelineConfig.identifier,
      maxSelectionsParam = ServerMaxResultsParam
    ),
    DropMaxCandidates(
      candidatePipeline = editedTweetsCandidatePipelineConfig.identifier,
      maxSelectionsParam = MaxNumberReplaceInstructionsParam
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouWhoToFollowCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(WhoToFollowCandidatePipelineConfig.MaxCandidatesSize)
    ),
    DropMaxModuleItemCandidates(
      candidatePipeline = forYouWhoToSubscribeCandidatePipelineConfig.identifier,
      maxModuleItemsParam = StaticParam(WhoToSubscribeCandidatePipelineConfig.MaxCandidatesSize)
    ),
    // The Conversation Service pipeline will only run if the Scored Tweets pipeline returned nothing
    InsertAppendResults(
      candidatePipeline = forYouConversationServiceCandidatePipelineConfig.identifier
    ),
    InsertAppendResults(
      candidatePipeline = forYouScoredTweetsCandidatePipelineConfig.identifier
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouTweetPreviewsCandidatePipelineConfig.identifier,
      positionParam = TweetPreviewsPositionParam
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouWhoToFollowCandidatePipelineConfig.identifier,
      positionParam = WhoToFollowPositionParam
    ),
    InsertFixedPositionResults(
      candidatePipeline = forYouWhoToSubscribeCandidatePipelineConfig.identifier,
      positionParam = WhoToSubscribePositionParam
    ),
    InsertFixedPositionResults(
      candidatePipeline = flipPromptCandidatePipelineConfig.identifier,
      positionParam = FlipInlineInjectionModulePosition
    ),
    // Insert Push To Home Tweet at top of Timeline
    InsertFixedPositionResults(
      candidatePipeline = forYouPushToHomeTweetCandidatePipelineConfig.identifier,
      positionParam = StaticParam(PushToHomeTweetPosition)
    ),
    InsertAdResults(
      surfaceAreaName = AdsInjectionSurfaceAreas.HomeTimeline,
      adsInjector = adsInjector.forSurfaceArea(AdsInjectionSurfaceAreas.HomeTimeline),
      adsCandidatePipeline = forYouAdsCandidatePipelineConfig.identifier
    ),
    // This selector must come after the tweets are inserted into the results
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouWhoToFollowCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(WhoToFollowCandidatePipelineConfig.MinCandidatesSize)
    ),
    DropModuleTooFewModuleItemResults(
      candidatePipeline = forYouWhoToSubscribeCandidatePipelineConfig.identifier,
      minModuleItemsParam = StaticParam(WhoToSubscribeCandidatePipelineConfig.MinCandidatesSize)
    ),
    UpdateNewTweetsPillDecoration(
      pipelineScope = SpecificPipelines(
        forYouConversationServiceCandidatePipelineConfig.identifier,
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
    UpdateHomeClientEventDetails(
      candidatePipelines = Set(
        forYouConversationServiceCandidatePipelineConfig.identifier,
        forYouScoredTweetsCandidatePipelineConfig.identifier
      )
    ),
  )

  private val servedCandidateKeysKafkaSideEffect =
    servedCandidateKeysKafkaSideEffectBuilder.build(
      Set(forYouScoredTweetsCandidatePipelineConfig.identifier))

  private val servedCandidateFeatureKeysKafkaSideEffect =
    servedCandidateFeatureKeysKafkaSideEffectBuilder.build(
      Set(forYouScoredTweetsCandidatePipelineConfig.identifier))

  private val homeScribeClientEventSideEffect = HomeScribeClientEventSideEffect(
    enableScribeClientEvents = enableScribeClientEvents,
    logPipelinePublisher = clientEventsScribeEventPublisher,
    injectedTweetsCandidatePipelineIdentifiers = Seq(
      forYouScoredTweetsCandidatePipelineConfig.identifier,
      forYouConversationServiceCandidatePipelineConfig.identifier
    ),
    adsCandidatePipelineIdentifier = Some(forYouAdsCandidatePipelineConfig.identifier),
    whoToFollowCandidatePipelineIdentifier = Some(
      forYouWhoToFollowCandidatePipelineConfig.identifier
    ),
    whoToSubscribeCandidatePipelineIdentifier =
      Some(forYouWhoToSubscribeCandidatePipelineConfig.identifier)
  )

  override val resultSideEffects: Seq[PipelineResultSideEffect[ForYouQuery, Timeline]] = Seq(
    servedCandidateKeysKafkaSideEffect,
    servedCandidateFeatureKeysKafkaSideEffect,
    updateTimelinesPersistenceStoreSideEffect,
    truncateTimelinesPersistenceStoreSideEffect,
    homeScribeClientEventSideEffect,
    homeScribeServedCandidatesSideEffect,
    servedStatsSideEffect
  )

  override val domainMarshaller: DomainMarshaller[ForYouQuery, Timeline] = {
    val instructionBuilders = Seq(
      ClearCacheInstructionBuilder(
        ClearCacheIncludeInstruction(
          ClearCacheOnPtr.EnableParam,
          ClearCacheOnPtr.MinEntriesParam,
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
            page = Some("for_you_scored_tweets"),
            section = None,
            entityToken = None)))
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
