package com.twitter.home_mitwitterer.product.subscribed

import com.twitter.clientapp.{thriftscala => ca}
import com.twitter.home_mitwitterer.candidate_pipeline.ConversationServiceCandidatePipelineConfigBuilder
import com.twitter.home_mitwitterer.candidate_pipeline.EditedTweetsCandidatePipelineConfig
import com.twitter.home_mitwitterer.candidate_pipeline.NewTweetsPillCandidatePipelineConfig
import com.twitter.home_mitwitterer.functional_component.decorator.HomeConversationServiceCandidateDecorator
import com.twitter.home_mitwitterer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mitwitterer.functional_component.feature_hydrator._
import com.twitter.home_mitwitterer.functional_component.selector.UpdateHomeClientEventDetails
import com.twitter.home_mitwitterer.functional_component.selector.UpdateNewTweetsPillDecoration
import com.twitter.home_mitwitterer.functional_component.side_effect._
import com.twitter.home_mitwitterer.model.GapIncludeInstruction
import com.twitter.home_mitwitterer.param.HomeGlobalParams.MatwitterNumberReplaceInstructionsParam
import com.twitter.home_mitwitterer.param.HomeMitwittererFlagName.ScribeClientEventsFlag
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.home_mitwitterer.product.subscribed.model.SubscribedQuery
import com.twitter.home_mitwitterer.product.subscribed.param.SubscribedParam.ServerMatwitterResultsParam
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.inject.annotations.Flag
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweetsQueryFeatureHydrator
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersQueryFeatureHydrator
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.SGSSubscribedUsersQueryFeatureHydrator
import com.twitter.product_mitwitterer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.UrtDomainMarshaller
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.AddEntriesWithReplaceAndShowAlertInstructionBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.OrderedBottomCursorBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.OrderedGapCursorBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.ReplaceAllEntries
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.ReplaceEntryInstructionBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.ShowAlertInstructionBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.StaticTimelineScribeConfigBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtMetadataBuilder
import com.twitter.product_mitwitterer.component_library.selector.DropMatwitterCandidates
import com.twitter.product_mitwitterer.component_library.selector.InsertAppendResults
import com.twitter.product_mitwitterer.component_library.selector.SelectConditionally
import com.twitter.product_mitwitterer.component_library.selector.UpdateSortCandidates
import com.twitter.product_mitwitterer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mitwitterer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.UrtTransportMarshaller
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.MitwittererPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineModule
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineScribeConfig
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mitwitterer.core.pipeline.FailOpenPolicy
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.mitwitterer.MitwittererPipelineConfig
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

@Singleton
class SubscribedMitwittererPipelineConfig @Inject() (
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
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter],
  urtTransportMarshaller: UrtTransportMarshaller,
  @Flag(ScribeClientEventsFlag) enableScribeClientEvents: Boolean)
    etwittertends MitwittererPipelineConfig[SubscribedQuery, Timeline, urt.TimelineResponse] {

  override val identifier: MitwittererPipelineIdentifier = MitwittererPipelineIdentifier("Subscribed")

  private val dependentCandidatesStep = MitwittererPipelineConfig.dependentCandidatePipelinesStep
  private val resultSelectorsStep = MitwittererPipelineConfig.resultSelectorsStep

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
    DropMatwitterCandidates(
      candidatePipeline = editedTweetsCandidatePipelineConfig.identifier,
      matwitterSelectionsParam = MatwitterNumberReplaceInstructionsParam
    ),
    DropMatwitterCandidates(
      candidatePipeline = conversationServiceCandidatePipelineConfig.identifier,
      matwitterSelectionsParam = ServerMatwitterResultsParam
    ),
    InsertAppendResults(candidatePipeline = conversationServiceCandidatePipelineConfig.identifier),
    // This selector must come after the tweets are inserted into the results
    UpdateNewTweetsPillDecoration(
      pipelineScope = SpecificPipelines(
        conversationServiceCandidatePipelineConfig.identifier,
        newTweetsPillCandidatePipelineConfig.identifier
      ),
      stringCenter = stringCenterProvider.get(),
      seeNewTweetsString = etwitterternalStrings.seeNewTweetsString,
      tweetedString = etwitterternalStrings.tweetedString
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
      // etwitterclude ads while determining tweet cursor values
      case item: TweetItem if item.promotedMetadata.isEmpty => item.id
      case module: TimelineModule
          if module.items.headOption.etwitterists(_.item.isInstanceOf[TweetItem]) =>
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
