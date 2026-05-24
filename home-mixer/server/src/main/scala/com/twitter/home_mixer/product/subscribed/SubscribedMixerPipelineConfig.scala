package com.twitter.home_mixer.product.subscribed

import com.twitter.clientapp.{thriftscala => ca}
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.candidate_pipeline.ConversationServiceCandidatePipelineConfigBuilder
import com.twitter.home_mixer.candidate_pipeline.EditedTweetsCandidatePipelineConfig
import com.twitter.home_mixer.candidate_pipeline.NewTweetsPillCandidatePipelineConfig
import com.twitter.home_mixer.functional_component.feature_hydrator._
import com.twitter.home_mixer.functional_component.selector.UpdateHomeClientEventDetails
import com.twitter.home_mixer.functional_component.selector.UpdateNewTweetsPillDecoration
import com.twitter.home_mixer.functional_component.side_effect._
import com.twitter.home_mixer.param.HomeGlobalParams.MaxNumberReplaceInstructionsParam
import com.twitter.home_mixer.param.HomeMixerFlagName.ScribeClientEventsFlag
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.product.subscribed.model.SubscribedQuery
import com.twitter.home_mixer.product.subscribed.param.SubscribedParam.EnablePostContextFeatureHydratorParam
import com.twitter.home_mixer.product.subscribed.param.SubscribedParam.ServerMaxResultsParam
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.inject.annotations.Flag
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweetsQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.location.UserLocationQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSSubscribedUsersQueryFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.AddEntriesWithReplaceAndShowAlertInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedCursorIdSelector.TweetIdSelector
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedGapCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceAllEntries
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ReplaceEntryInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.ShowAlertInstructionBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.earlybird.EarlybirdGapIncludeInstruction
import com.twitter.product_mixer.component_library.selector.DropMaxCandidates
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.component_library.selector.SelectConditionally
import com.twitter.product_mixer.component_library.selector.UpdateSortCandidates
import com.twitter.product_mixer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mixer.core.functional_component.common.SpecificPipelines
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
class SubscribedMixerPipelineConfig @Inject() (
  subscribedEarlybirdCandidatePipelineConfig: SubscribedEarlybirdCandidatePipelineConfig,
  conversationServiceCandidatePipelineConfigBuilder: ConversationServiceCandidatePipelineConfigBuilder[
    SubscribedQuery
  ],
  editedTweetsCandidatePipelineConfig: EditedTweetsCandidatePipelineConfig,
  newTweetsPillCandidatePipelineConfig: NewTweetsPillCandidatePipelineConfig[SubscribedQuery],
  dismissInfoQueryFeatureHydrator: DismissInfoQueryFeatureHydrator,
  gizmoduckUserQueryFeatureHydrator: GizmoduckUserQueryFeatureHydrator,
  requestQueryFeatureHydrator: RequestQueryFeatureHydrator[SubscribedQuery],
  sgsFollowedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  sgsSubscribedUsersQueryFeatureHydrator: SGSSubscribedUsersQueryFeatureHydrator,
  memcacheTweetImpressionsQueryFeatureHydrator: ImpressedTweetsQueryFeatureHydrator,
  userLocationQueryFeatureHydrator: UserLocationQueryFeatureHydrator,
  publishClientSentImpressionsEventBusSideEffect: PublishClientSentImpressionsEventBusSideEffect,
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
    AsyncQueryFeatureHydrator(dependentCandidatesStep, userLocationQueryFeatureHydrator),
    AsyncQueryFeatureHydrator(resultSelectorsStep, memcacheTweetImpressionsQueryFeatureHydrator)
  )

  private val earlybirdCandidatePipelineScope =
    SpecificPipeline(subscribedEarlybirdCandidatePipelineConfig.identifier)

  private val conversationServiceCandidatePipelineConfig =
    conversationServiceCandidatePipelineConfigBuilder.build(
      earlybirdCandidatePipelineScope,
      hmt.ServedType.Subscribed,
      EnablePostContextFeatureHydratorParam
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
    publishClientSentImpressionsEventBusSideEffect
  )

  override val domainMarshaller: DomainMarshaller[SubscribedQuery, Timeline] = {
    val instructionBuilders = Seq(
      ReplaceEntryInstructionBuilder(ReplaceAllEntries),
      AddEntriesWithReplaceAndShowAlertInstructionBuilder(),
      ShowAlertInstructionBuilder(),
    )

    val topCursorBuilder = OrderedTopCursorBuilder(TweetIdSelector)
    val bottomCursorBuilder =
      OrderedBottomCursorBuilder(TweetIdSelector, EarlybirdGapIncludeInstruction.inverse())
    val gapCursorBuilder = OrderedGapCursorBuilder(TweetIdSelector, EarlybirdGapIncludeInstruction)

    val scribeConfigBuilder =
      StaticTimelineScribeConfigBuilder(TimelineScribeConfig(Some("subscribed"), None, None))
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
