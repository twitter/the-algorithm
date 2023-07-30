package com.X.home_mixer.product.subscribed

import com.X.clientapp.{thriftscala => ca}
import com.X.home_mixer.candidate_pipeline.ConversationServiceCandidatePipelineConfigBuilder
import com.X.home_mixer.candidate_pipeline.EditedTweetsCandidatePipelineConfig
import com.X.home_mixer.candidate_pipeline.NewTweetsPillCandidatePipelineConfig
import com.X.home_mixer.functional_component.decorator.HomeConversationServiceCandidateDecorator
import com.X.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.X.home_mixer.functional_component.feature_hydrator._
import com.X.home_mixer.functional_component.selector.UpdateHomeClientEventDetails
import com.X.home_mixer.functional_component.selector.UpdateNewTweetsPillDecoration
import com.X.home_mixer.functional_component.side_effect._
import com.X.home_mixer.model.GapIncludeInstruction
import com.X.home_mixer.param.HomeGlobalParams.MaxNumberReplaceInstructionsParam
import com.X.home_mixer.param.HomeMixerFlagName.ScribeClientEventsFlag
import com.X.home_mixer.product.following.model.HomeMixerExternalStrings
import com.X.home_mixer.product.subscribed.model.SubscribedQuery
import com.X.home_mixer.product.subscribed.param.SubscribedParam.ServerMaxResultsParam
import com.X.home_mixer.util.CandidatesUtil
import com.X.inject.annotations.Flag
import com.X.logpipeline.client.common.EventPublisher
import com.X.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.X.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweetsQueryFeatureHydrator
import com.X.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersQueryFeatureHydrator
import com.X.product_mixer.component_library.feature_hydrator.query.social_graph.SGSSubscribedUsersQueryFeatureHydrator
import com.X.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.X.product_mixer.component_library.premarshaller.urt.builder.AddEntriesWithReplaceAndShowAlertInstructionBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.OrderedGapCursorBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.ReplaceAllEntries
import com.X.product_mixer.component_library.premarshaller.urt.builder.ReplaceEntryInstructionBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.ShowAlertInstructionBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.X.product_mixer.component_library.selector.DropMaxCandidates
import com.X.product_mixer.component_library.selector.InsertAppendResults
import com.X.product_mixer.component_library.selector.SelectConditionally
import com.X.product_mixer.component_library.selector.UpdateSortCandidates
import com.X.product_mixer.core.functional_component.common.SpecificPipeline
import com.X.product_mixer.core.functional_component.common.SpecificPipelines
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.UrtTransportMarshaller
import com.X.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.X.product_mixer.core.functional_component.selector.Selector
import com.X.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.common.identifier.MixerPipelineIdentifier
import com.X.product_mixer.core.model.marshalling.response.urt.Timeline
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.X.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.X.product_mixer.core.pipeline.FailOpenPolicy
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.X.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.X.product_mixer.core.pipeline.mixer.MixerPipelineConfig
import com.X.product_mixer.core.product.guice.scope.ProductScoped
import com.X.stringcenter.client.StringCenter
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class SubscribedMixerPipelineConfig @Inject() (
  subscribedEarlybirdCandidatePipelineConfig: SubscribedEarlybirdCandidatePipelineConfig,
  conversationServiceCandidatePipelineConfigBuilder: ConversationServiceCandidatePipelineConfigBuilder[
    SubscribedQuery
  ],
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder,
  editedTweetsCandidatePipelineConfig: EditedTweetsCandidatePipelineConfig,
  newTweetsPillCandidatePipelineConfig: NewTweetsPillCandidatePipelineConfig[SubscribedQuery],
  dismissInfoQueryFeatureHydrator: DismissInfoQueryFeatureHydrator,
  gizmoduckUserQueryFeatureHydrator: GizmoduckUserQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[SubscribedQuery],
  sgsFollowedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  sgsSubscribedUsersQueryFeatureHydrator: SGSSubscribedUsersQueryFeatureHydrator,
  manhattanTweetImpressionsQueryFeatureHydrator: TweetImpressionsQueryFeatureHydrator[
    SubscribedQuery
  ],
  memcacheTweetImpressionsQueryFeatureHydrator: ImpressedTweetsQueryFeatureHydrator,
  publishClientSentImpressionsEventBusSideEffect: PublishClientSentImpressionsEventBusSideEffect,
  publishClientSentImpressionsManhattanSideEffect: PublishClientSentImpressionsManhattanSideEffect,
  homeTimelineServedCandidatesSideEffect: HomeScribeServedCandidatesSideEffect,
  clientEventsScribeEventPublisher: EventPublisher[ca.LogEvent],
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  urtTransportMarshaller: UrtTransportMarshaller,
  @Flag(ScribeClientEventsFlag) enableScribeClientEvents: Boolean)
    extends MixerPipelineConfig[SubscribedQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MixerPipelineIdentifier = MixerPipelineIdentifier("Subscribed")

  private val dependentCandidatesStep = MixerPipelineConfig.dependentCandidatePipelinesStep
  private val resultSelectorsStep = MixerPipelineConfig.resultSelectorsStep

  override val fetchQueryFeatures: Seq[QueryFeatureHydrator[SubscribedQuery]] = Seq(
    requestQueryFeatureHydrator,
    sgsFollowedUsersQueryFeatureHydrator,
    sgsSubscribedUsersQueryFeatureHydrator,
    AsyncQueryFeatureHydrator(dependentCandidatesStep, dismissInfoQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(dependentCandidatesStep, gizmoduckUserQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep, manhattanTweetImpressionsQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep, memcacheTweetImpressionsQueryFeatureHydrator)
  )

  private val earlybirdCandidatePipelineScope =
    SpecificPipeline(subscribedEarlybirdCandidatePipelineConfig.identifier)

  private val conversationServiceCandidatePipelineConfig =
    conversationServiceCandidatePipelineConfigBuilder.build(
      Seq(NonEmptyCandidatesGate(earlybirdCandidatePipelineScope)),
      HomeConversationServiceCandidateDecorator(homeFeedbackActionInfoBuilder)
    )

  override val candidatePipelines: Seq[CandidatePipelineConfig[SubscribedQuery, _, _, _]] =
    Seq(subscribedEarlybirdCandidatePipelineConfig)

  override val dependentCandidatePipelines: Seq[
    DependentCandidatePipelineConfig[SubscribedQuery, _, _, _]
  ] = Seq(
    conversationServiceCandidatePipelineConfig,
    editedTweetsCandidatePipelineConfig,
    newTweetsPillCandidatePipelineConfig
  )

  override val failOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] = Map(
    editedTweetsCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
    newTweetsPillCandidatePipelineConfig.identifier -> FailOpenPolicy.Always,
  )

  override val resultSelectors: Seq[Selector[SubscribedQuery]] = Seq(
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
    InsertAppendResults(candidatePipeline = conversationServiceCandidatePipelineConfig.identifier),
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
    enableScribeClientEvents = enableScribeClientEvents,
    logPipelinePublisher = clientEventsScribeEventPublisher,
    injectedTweetsCandidatePipelineIdentifiers =
      Seq(conversationServiceCandidatePipelineConfig.identifier),
  )

  override val resultSideEffects: Seq[PipelineResultSideEffect[SubscribedQuery, Timeline]] = Seq(
    homeScribeClientEventSideEffect,
    homeTimelineServedCandidatesSideEffect,
    publishClientSentImpressionsEventBusSideEffect,
    publishClientSentImpressionsManhattanSideEffect
  )

  override val domainMarshaller: DomainMarshaller[SubscribedQuery, Timeline] = {
    val instructionBuilders = Seq(
      ReplaceEntryInstructionBuilder(ReplaceAllEntries),
      AddEntriesWithReplaceAndShowAlertInstructionBuilder(),
      ShowAlertInstructionBuilder(),
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
          TimelineScribeConfig(page = Some("subscribed"), section = None, entityToken = None)))
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
