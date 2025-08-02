package com.twitter.home_mitwitterer.functional_component.side_effect

import com.twitter.finagle.tracing.Trace
import com.twitter.home_mitwitterer.marshaller.timeline_logging.PromotedTweetDetailsMarshaller
import com.twitter.home_mitwitterer.marshaller.timeline_logging.TweetDetailsMarshaller
import com.twitter.home_mitwitterer.marshaller.timeline_logging.WhoToFollowDetailsMarshaller
import com.twitter.home_mitwitterer.model.HomeFeatures.GetInitialFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.GetMiddleFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.GetNewerFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.GetOlderFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.HasDarkRequestFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.RequestJoinIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.ServedRequestIdFeature
import com.twitter.home_mitwitterer.model.request.DeviceContetwittert.RequestContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasSeenTweetIds
import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.home_mitwitterer.model.request.ForYouProduct
import com.twitter.home_mitwitterer.model.request.SubscribedProduct
import com.twitter.home_mitwitterer.param.HomeMitwittererFlagName.ScribeServedCandidatesFlag
import com.twitter.home_mitwitterer.param.HomeGlobalParams.EnableScribeServedCandidatesParam
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.inject.annotations.Flag
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mitwitterer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.BaseUserCandidate
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowCandidateDecorator
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidateDecorator
import com.twitter.product_mitwitterer.component_library.side_effect.ScribeLogEventSideEffect
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ModuleCandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.ModuleItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineModule
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.timeline_logging.{thriftscala => thrift}
import com.twitter.util.Time
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Side effect that logs home timeline served candidates to Scribe.
 */
@Singleton
class HomeScribeServedCandidatesSideEffect @Inject() (
  @Flag(ScribeServedCandidatesFlag) enableScribeServedCandidates: Boolean,
  scribeEventPublisher: EventPublisher[thrift.ServedEntry])
    etwittertends ScribeLogEventSideEffect[
      thrift.ServedEntry,
      PipelineQuery with HasSeenTweetIds with HasDeviceContetwittert,
      Timeline
    ]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery with HasSeenTweetIds with HasDeviceContetwittert,
      Timeline
    ] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("HomeScribeServedCandidates")

  override def onlyIf(
    query: PipelineQuery with HasSeenTweetIds with HasDeviceContetwittert,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Boolean = enableScribeServedCandidates && query.params(EnableScribeServedCandidatesParam)

  override def buildLogEvents(
    query: PipelineQuery with HasSeenTweetIds with HasDeviceContetwittert,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Seq[thrift.ServedEntry] = {
    val timelineType = query.product match {
      case FollowingProduct => thrift.TimelineType.HomeLatest
      case ForYouProduct => thrift.TimelineType.Home
      case SubscribedProduct => thrift.TimelineType.HomeSubscribed
      case other => throw new UnsupportedOperationEtwitterception(s"Unknown product: $other")
    }
    val requestProvenance = query.deviceContetwittert.map { deviceContetwittert =>
      deviceContetwittert.requestContetwittertValue match {
        case RequestContetwittert.Foreground => thrift.RequestProvenance.Foreground
        case RequestContetwittert.Launch => thrift.RequestProvenance.Launch
        case RequestContetwittert.PullToRefresh => thrift.RequestProvenance.Ptr
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
    val requestInfo = thrift.RequestInfo(
      requestTimeMs = query.queryTime.inMilliseconds,
      traceId = Trace.id.traceId.toLong,
      userId = query.getOptionalUserId,
      clientAppId = query.clientContetwittert.appId,
      hasDarkRequest = query.features.flatMap(_.getOrElse(HasDarkRequestFeature, None)),
      parentId = Some(Trace.id.parentId.toLong),
      spanId = Some(Trace.id.spanId.toLong),
      timelineType = Some(timelineType),
      ipAddress = query.clientContetwittert.ipAddress,
      userAgent = query.clientContetwittert.userAgent,
      queryType = queryType,
      requestProvenance = requestProvenance,
      languageCode = query.clientContetwittert.languageCode,
      countryCode = query.clientContetwittert.countryCode,
      requestEndTimeMs = Some(Time.now.inMilliseconds),
      servedRequestId = query.features.flatMap(_.getOrElse(ServedRequestIdFeature, None)),
      requestJoinId = query.features.flatMap(_.getOrElse(RequestJoinIdFeature, None))
    )

    val tweetIdToItemCandidateMap: Map[Long, ItemCandidateWithDetails] =
      selectedCandidates.flatMap {
        case item: ItemCandidateWithDetails if item.candidate.isInstanceOf[BaseTweetCandidate] =>
          Seq((item.candidateIdLong, item))
        case module: ModuleCandidateWithDetails
            if module.candidates.headOption.etwitterists(_.candidate.isInstanceOf[BaseTweetCandidate]) =>
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

    response.instructions.zipWithIndetwitter
      .collect {
        case (AddEntriesTimelineInstruction(entries), indetwitter) =>
          entries.collect {
            case entry: TweetItem if entry.promotedMetadata.isDefined =>
              val promotedTweetDetails = PromotedTweetDetailsMarshaller(entry, indetwitter)
              Seq(
                thrift.EntryInfo(
                  id = entry.id,
                  position = indetwitter.shortValue(),
                  entryId = entry.entryIdentifier,
                  entryType = thrift.EntryType.PromotedTweet,
                  sortIndetwitter = entry.sortIndetwitter,
                  verticalSize = Some(1),
                  displayType = Some(entry.displayType.toString),
                  details = Some(thrift.ItemDetails.PromotedTweetDetails(promotedTweetDetails))
                )
              )
            case entry: TweetItem =>
              val candidate = tweetIdToItemCandidateMap(entry.id)
              val tweetDetails = TweetDetailsMarshaller(entry, candidate)
              Seq(
                thrift.EntryInfo(
                  id = candidate.candidateIdLong,
                  position = indetwitter.shortValue(),
                  entryId = entry.entryIdentifier,
                  entryType = thrift.EntryType.Tweet,
                  sortIndetwitter = entry.sortIndetwitter,
                  verticalSize = Some(1),
                  score = candidate.features.getOrElse(ScoreFeature, None),
                  displayType = Some(entry.displayType.toString),
                  details = Some(thrift.ItemDetails.TweetDetails(tweetDetails))
                )
              )
            case module: TimelineModule
                if module.entryNamespace.toString == WhoToFollowCandidateDecorator.EntryNamespaceString =>
              module.items.collect {
                case ModuleItem(entry: UserItem, _, _) =>
                  val candidate = userIdToItemCandidateMap(entry.id)
                  val whoToFollowDetails = WhoToFollowDetailsMarshaller(entry, candidate)
                  thrift.EntryInfo(
                    id = entry.id,
                    position = indetwitter.shortValue(),
                    entryId = module.entryIdentifier,
                    entryType = thrift.EntryType.WhoToFollowModule,
                    sortIndetwitter = module.sortIndetwitter,
                    score = candidate.features.getOrElse(ScoreFeature, None),
                    displayType = Some(entry.displayType.toString),
                    details = Some(thrift.ItemDetails.WhoToFollowDetails(whoToFollowDetails))
                  )
              }
            case module: TimelineModule
                if module.entryNamespace.toString == WhoToSubscribeCandidateDecorator.EntryNamespaceString =>
              module.items.collect {
                case ModuleItem(entry: UserItem, _, _) =>
                  val candidate = userIdToItemCandidateMap(entry.id)
                  val whoToSubscribeDetails = WhoToFollowDetailsMarshaller(entry, candidate)
                  thrift.EntryInfo(
                    id = entry.id,
                    position = indetwitter.shortValue(),
                    entryId = module.entryIdentifier,
                    entryType = thrift.EntryType.WhoToSubscribeModule,
                    sortIndetwitter = module.sortIndetwitter,
                    score = candidate.features.getOrElse(ScoreFeature, None),
                    displayType = Some(entry.displayType.toString),
                    details = Some(thrift.ItemDetails.WhoToFollowDetails(whoToSubscribeDetails))
                  )
              }
            case module: TimelineModule
                if module.sortIndetwitter.isDefined && module.items.headOption.etwitterists(
                  _.item.isInstanceOf[TweetItem]) =>
              module.items.collect {
                case ModuleItem(entry: TweetItem, _, _) =>
                  val candidate = tweetIdToItemCandidateMap(entry.id)
                  thrift.EntryInfo(
                    id = entry.id,
                    position = indetwitter.shortValue(),
                    entryId = module.entryIdentifier,
                    entryType = thrift.EntryType.ConversationModule,
                    sortIndetwitter = module.sortIndetwitter,
                    score = candidate.features.getOrElse(ScoreFeature, None),
                    displayType = Some(entry.displayType.toString)
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
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert()
  )
}
