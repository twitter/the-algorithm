package com.twitter.home_mixer.functional_component.side_effect

import com.twitter.finagle.tracing.Trace
import com.twitter.home_mixer.marshaller.timeline_logging.PromotedTweetDetailsMarshaller
import com.twitter.home_mixer.marshaller.timeline_logging.TweetDetailsMarshaller
import com.twitter.home_mixer.marshaller.timeline_logging.WhoToFollowDetailsMarshaller
import com.twitter.home_mixer.model.HomeFeatures.GetInitialFeature
import com.twitter.home_mixer.model.HomeFeatures.GetMiddleFeature
import com.twitter.home_mixer.model.HomeFeatures.GetNewerFeature
import com.twitter.home_mixer.model.HomeFeatures.GetOlderFeature
import com.twitter.home_mixer.model.HomeFeatures.HasDarkRequestFeature
import com.twitter.home_mixer.model.HomeFeatures.RequestJoinIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceSignalFeature
import com.twitter.home_mixer.model.HomeFeatures.UserActionsByteArrayFeature
import com.twitter.home_mixer.model.HomeFeatures.UserActionsContainsExplicitSignalsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserActionsSizeFeature
import com.twitter.home_mixer.model.PhoenixPredictedScoreFeature
import com.twitter.home_mixer.model.PredictedScoreFeature
import com.twitter.home_mixer.model.request.DeviceContext.RequestContext
import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.model.request.HasSeenTweetIds
import com.twitter.home_mixer.model.request.SubscribedProduct
import com.twitter.home_mixer.param.HomeGlobalParams.EnableScribeServedCandidatesParam
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixInferenceClusterParam
import com.twitter.home_mixer.param.HomeMixerFlagName.ScribeServedCandidatesFlag
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.inject.annotations.Flag
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mixer.component_library.model.candidate.BaseUserCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowCandidateDecorator
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidateDecorator
import com.twitter.product_mixer.component_library.side_effect.ScribeLogEventSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.ModuleItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.timeline_logging.thriftscala.Prediction
import com.twitter.timelines.timeline_logging.{thriftscala => thrift}
import com.twitter.util.Time
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Side effect that logs home timeline served candidates to Scribe.
 */
@Singleton
class HomeScribeServedCandidatesSideEffect @Inject() (
  @Flag(ScribeServedCandidatesFlag) enableScribeServedCandidates: Boolean,
  scribeEventPublisher: EventPublisher[thrift.ServedEntry])
    extends ScribeLogEventSideEffect[
      thrift.ServedEntry,
      PipelineQuery with HasSeenTweetIds with HasDeviceContext,
      Timeline
    ]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery with HasSeenTweetIds with HasDeviceContext,
      Timeline
    ] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("HomeScribeServedCandidates")

  override def onlyIf(
    query: PipelineQuery with HasSeenTweetIds with HasDeviceContext,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Boolean = enableScribeServedCandidates && query.params(EnableScribeServedCandidatesParam)

  override def buildLogEvents(
    query: PipelineQuery with HasSeenTweetIds with HasDeviceContext,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Seq[thrift.ServedEntry] = {
    val timelineType = query.product match {
      case FollowingProduct => thrift.TimelineType.HomeLatest
      case ForYouProduct => thrift.TimelineType.Home
      case SubscribedProduct => thrift.TimelineType.HomeSubscribed
      case other => throw new UnsupportedOperationException(s"Unknown product: $other")
    }
    val requestProvenance = query.deviceContext.map { deviceContext =>
      deviceContext.requestContextValue match {
        case RequestContext.Foreground => thrift.RequestProvenance.Foreground
        case RequestContext.Launch => thrift.RequestProvenance.Launch
        case RequestContext.PullToRefresh => thrift.RequestProvenance.Ptr
        case _ => thrift.RequestProvenance.Other
      }
    }
    val queryType = query.features.map { featureMap =>
      if (featureMap.getOrElse(GetOlderFeature, false)) thrift.QueryType.GetOlder
      else if (featureMap.getOrElse(GetNewerFeature, false)) thrift.QueryType.GetNewer
      else if (featureMap.getOrElse(GetMiddleFeature, false)) thrift.QueryType.GetMiddle
      else if (featureMap.getOrElse(GetInitialFeature, false)) thrift.QueryType.GetInitial
      else thrift.QueryType.Other
    }

    val phoenixCluster =
      s"phoenix.${query.params(PhoenixInferenceClusterParam).toString.toLowerCase}."

    val tweetIdToItemCandidateMap: Map[Long, ItemCandidateWithDetails] =
      selectedCandidates.flatMap {
        case item: ItemCandidateWithDetails if item.candidate.isInstanceOf[BaseTweetCandidate] =>
          Seq((item.candidateIdLong, item))
        case module: ModuleCandidateWithDetails
            if module.candidates.headOption.exists(_.candidate.isInstanceOf[BaseTweetCandidate]) =>
          module.candidates.map(item => (item.candidateIdLong, item))
        case _ => Seq.empty
      }.toMap

    val userIdToItemCandidateMap: Map[Long, ItemCandidateWithDetails] =
      selectedCandidates.flatMap {
        case module: ModuleCandidateWithDetails
            if module.candidates.forall(_.candidate.isInstanceOf[BaseUserCandidate]) =>
          module.candidates.map { item =>
            (item.candidateIdLong, item)
          }
        case _ => Seq.empty
      }.toMap

    val userActions = selectedCandidates.collectFirst {
      case candidate if candidate.features.getOrElse(UserActionsSizeFeature, None).isDefined =>
        (
          candidate.features.getOrElse(UserActionsSizeFeature, None),
          candidate.features.getOrElse(UserActionsContainsExplicitSignalsFeature, false)
        )
    }

    val userActionsByteArrayOpt =
      query.features.flatMap(_.getOrElse(UserActionsByteArrayFeature, None))
    val userActionsBuffer = userActionsByteArrayOpt.map(ua => ByteBuffer.wrap(ua))

    val requestInfo = thrift.RequestInfo(
      requestTimeMs = query.queryTime.inMilliseconds,
      traceId = Trace.id.traceId.toLong,
      userId = query.getOptionalUserId,
      clientAppId = query.clientContext.appId,
      hasDarkRequest = query.features.flatMap(_.getOrElse(HasDarkRequestFeature, None)),
      parentId = Some(Trace.id.parentId.toLong),
      spanId = Some(Trace.id.spanId.toLong),
      timelineType = Some(timelineType),
      ipAddress = query.clientContext.ipAddress,
      userAgent = query.clientContext.userAgent,
      queryType = queryType,
      requestProvenance = requestProvenance,
      languageCode = query.clientContext.languageCode,
      countryCode = query.clientContext.countryCode,
      requestEndTimeMs = Some(Time.now.inMilliseconds),
      servedRequestId = query.features.flatMap(_.getOrElse(ServedIdFeature, None)),
      requestJoinId = query.features.flatMap(_.getOrElse(RequestJoinIdFeature, None)),
      userActionsSize = userActions.flatMap(_._1),
      userActionsContainsExplicitSignals = userActions.map(_._2),
      userActions = userActionsBuffer
    )

    response.instructions
      .collect {
        case AddEntriesTimelineInstruction(entries) =>
          entries.zipWithIndex.collect {
            case (entry: TweetItem, index) if entry.promotedMetadata.isDefined =>
              val promotedTweetDetails = PromotedTweetDetailsMarshaller(entry, index)
              Seq(
                thrift.EntryInfo(
                  id = entry.id,
                  position = index.shortValue(),
                  entryId = entry.entryIdentifier,
                  entryType = thrift.EntryType.PromotedTweet,
                  sortIndex = entry.sortIndex,
                  verticalSize = Some(1),
                  displayType = Some(entry.displayType.toString),
                  details = Some(thrift.ItemDetails.PromotedTweetDetails(promotedTweetDetails))
                ))
            case (entry: TweetItem, index) =>
              val candidate = tweetIdToItemCandidateMap(entry.id)
              val tweetDetails = TweetDetailsMarshaller(entry, candidate)
              Seq(
                thrift.EntryInfo(
                  id = candidate.candidateIdLong,
                  position = index.shortValue(),
                  entryId = entry.entryIdentifier,
                  entryType = thrift.EntryType.Tweet,
                  sortIndex = entry.sortIndex,
                  verticalSize = Some(1),
                  score = candidate.features.getOrElse(ScoreFeature, None),
                  displayType = Some(entry.displayType.toString),
                  details = Some(thrift.ItemDetails.TweetDetails(tweetDetails)),
                  predictionScores = Some(extractPredictionScores(candidate, phoenixCluster)),
                  sourceSignal = candidate.features.getOrElse(SourceSignalFeature, None).map {
                    signal =>
                      thrift.SourceSignal(
                        id = Some(signal.id),
                        signalType = signal.signalType,
                        signalEntity = signal.signalEntity,
                        authorId = signal.authorId,
                      )
                  }
                ))
            case (module: TimelineModule, _)
                if module.entryNamespace.toString == WhoToFollowCandidateDecorator.EntryNamespaceString =>
              module.items.zipWithIndex.collect {
                case (ModuleItem(entry: UserItem, _, _, _), index) =>
                  val candidate = userIdToItemCandidateMap(entry.id)
                  val whoToFollowDetails = WhoToFollowDetailsMarshaller(entry, candidate)
                  thrift.EntryInfo(
                    id = entry.id,
                    position = index.shortValue(),
                    entryId = module.entryIdentifier,
                    entryType = thrift.EntryType.WhoToFollowModule,
                    sortIndex = module.sortIndex,
                    score = candidate.features.getOrElse(ScoreFeature, None),
                    displayType = Some(entry.displayType.toString),
                    details = Some(thrift.ItemDetails.WhoToFollowDetails(whoToFollowDetails))
                  )
              }
            case (module: TimelineModule, _)
                if module.entryNamespace.toString == WhoToSubscribeCandidateDecorator.EntryNamespaceString =>
              module.items.zipWithIndex.collect {
                case (ModuleItem(entry: UserItem, _, _, _), index) =>
                  val candidate = userIdToItemCandidateMap(entry.id)
                  val whoToSubscribeDetails = WhoToFollowDetailsMarshaller(entry, candidate)
                  thrift.EntryInfo(
                    id = entry.id,
                    position = index.shortValue(),
                    entryId = module.entryIdentifier,
                    entryType = thrift.EntryType.WhoToSubscribeModule,
                    sortIndex = module.sortIndex,
                    score = candidate.features.getOrElse(ScoreFeature, None),
                    displayType = Some(entry.displayType.toString),
                    details = Some(thrift.ItemDetails.WhoToFollowDetails(whoToSubscribeDetails))
                  )
              }
            case (module: TimelineModule, _)
                if module.sortIndex.isDefined && module.items.headOption.exists(
                  _.item.isInstanceOf[TweetItem]) =>
              module.items.zipWithIndex.collect {
                case (ModuleItem(entry: TweetItem, _, _, _), index) =>
                  val candidate = tweetIdToItemCandidateMap(entry.id)
                  thrift.EntryInfo(
                    id = entry.id,
                    position = index.shortValue(),
                    entryId = module.entryIdentifier,
                    entryType = thrift.EntryType.ConversationModule,
                    sortIndex = module.sortIndex,
                    score = candidate.features.getOrElse(ScoreFeature, None),
                    displayType = Some(entry.displayType.toString),
                    predictionScores = Some(extractPredictionScores(candidate, phoenixCluster)),
                    sourceSignal =
                      candidate.features.getOrElse(SourceSignalFeature, None).map { signal =>
                        thrift.SourceSignal(
                          id = Some(signal.id),
                          signalType = signal.signalType
                        )
                      }
                  )
              }
            case _ => Seq.empty
          }.flatten
        // Other instructions
        case _ => Seq.empty[thrift.EntryInfo]
      }.flatten.map { entryInfo =>
        thrift.ServedEntry(
          entry = Some(entryInfo),
          request = requestInfo
        )
      }
  }

  override val logPipelinePublisher: EventPublisher[thrift.ServedEntry] =
    scribeEventPublisher

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert()
  )

  private def extractPredictionScores(
    candidate: ItemCandidateWithDetails,
    phoenixCluster: String
  ): Set[Prediction] = {
    val naviPredictionScores = PredictedScoreFeature.PredictedScoreFeatureSet.map { feature =>
      Prediction(Some(feature.featureName), candidate.features.getOrElse(feature, None))
    }

    val phoenixPredictionScores =
      PhoenixPredictedScoreFeature.PhoenixPredictedScoreFeatureSet.map { feature =>
        Prediction(
          Some(phoenixCluster + feature.featureName),
          candidate.features.getOrElse(feature, None)
        )
      }

    (naviPredictionScores ++ phoenixPredictionScores).filter(_.score.isDefined)
  }
}
