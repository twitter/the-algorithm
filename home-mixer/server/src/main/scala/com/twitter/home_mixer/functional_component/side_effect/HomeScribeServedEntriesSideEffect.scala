package com.twitter.home_mixer.functional_component.side_effect

import com.twitter.finagle.tracing.Trace
import com.twitter.home_mixer.marshaller.timeline_logging.ConversationEntryMarshaller
import com.twitter.home_mixer.marshaller.timeline_logging.PromotedTweetEntryMarshaller
import com.twitter.home_mixer.marshaller.timeline_logging.TweetEntryMarshaller
import com.twitter.home_mixer.marshaller.timeline_logging.WhoToFollowEntryMarshaller
import com.twitter.home_mixer.model.HomeFeatures.GetInitialFeature
import com.twitter.home_mixer.model.HomeFeatures.GetMiddleFeature
import com.twitter.home_mixer.model.HomeFeatures.GetNewerFeature
import com.twitter.home_mixer.model.HomeFeatures.GetOlderFeature
import com.twitter.home_mixer.model.HomeFeatures.HasDarkRequestFeature
import com.twitter.home_mixer.model.HomeFeatures.RequestJoinIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedRequestIdFeature
import com.twitter.home_mixer.model.request.DeviceContext.RequestContext
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.model.request.HasSeenTweetIds
import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mixer.component_library.model.candidate.BaseUserCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowCandidateDecorator
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.ModuleItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.timeline_logging.{thriftscala => thrift}
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Side effect that logs home timeline served entries to Scribe.
 */
@Singleton
class HomeScribeServedEntriesSideEffect @Inject() (
  scribeEventPublisher: EventPublisher[thrift.Timeline])
    extends PipelineResultSideEffect[
      PipelineQuery with HasSeenTweetIds with HasDeviceContext,
      Timeline
    ] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("HomeScribeServedEntries")

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[
      PipelineQuery with HasSeenTweetIds with HasDeviceContext,
      Timeline
    ]
  ): Stitch[Unit] = {
    val timelineThrift = buildTimeline(inputs)
    Stitch.callFuture(scribeEventPublisher.publish(timelineThrift)).unit
  }

  def buildTimeline(
    inputs: PipelineResultSideEffect.Inputs[
      PipelineQuery with HasSeenTweetIds with HasDeviceContext,
      Timeline
    ]
  ): thrift.Timeline = {
    val timelineType = inputs.query.product match {
      case FollowingProduct => thrift.TimelineType.HomeLatest
      case ForYouProduct => thrift.TimelineType.Home
      case other => throw new UnsupportedOperationException(s"Unknown product: $other")
    }
    val requestProvenance = inputs.query.deviceContext.map { deviceContext =>
      deviceContext.requestContextValue match {
        case RequestContext.Foreground => thrift.RequestProvenance.Foreground
        case RequestContext.Launch => thrift.RequestProvenance.Launch
        case RequestContext.PullToRefresh => thrift.RequestProvenance.Ptr
        case _ => thrift.RequestProvenance.Other
      }
    }
    val queryType = inputs.query.features.map { featureMap =>
      if (featureMap.getOrElse(GetOlderFeature, false)) thrift.QueryType.GetOlder
      else if (featureMap.getOrElse(GetNewerFeature, false)) thrift.QueryType.GetNewer
      else if (featureMap.getOrElse(GetMiddleFeature, false)) thrift.QueryType.GetMiddle
      else if (featureMap.getOrElse(GetInitialFeature, false)) thrift.QueryType.GetInitial
      else thrift.QueryType.Other
    }

    val tweetIdToItemCandidateMap: Map[Long, ItemCandidateWithDetails] =
      inputs.selectedCandidates.flatMap {
        case item: ItemCandidateWithDetails if item.candidate.isInstanceOf[BaseTweetCandidate] =>
          Seq((item.candidateIdLong, item))
        case module: ModuleCandidateWithDetails
            if module.candidates.headOption.exists(_.candidate.isInstanceOf[BaseTweetCandidate]) =>
          module.candidates.map(item => (item.candidateIdLong, item))
        case _ => Seq.empty
      }.toMap

    val userIdToItemCandidateMap: Map[Long, ItemCandidateWithDetails] =
      inputs.selectedCandidates.flatMap {
        case module: ModuleCandidateWithDetails
            if module.candidates.forall(_.candidate.isInstanceOf[BaseUserCandidate]) =>
          module.candidates.map { item =>
            (item.candidateIdLong, item)
          }
        case _ => Seq.empty
      }.toMap

    val timelineEntries = inputs.response.instructions.zipWithIndex.collect {
      case (AddEntriesTimelineInstruction(entries), index) =>
        entries.collect {
          case entry: TweetItem if entry.promotedMetadata.isDefined =>
            val promotedTweetEntry = PromotedTweetEntryMarshaller(entry, index)
            Seq(
              thrift.TimelineEntry(
                content = thrift.Content.PromotedTweetEntry(promotedTweetEntry),
                position = index.shortValue(),
                entryId = entry.entryIdentifier,
                entryType = thrift.EntryType.PromotedTweet,
                sortIndex = entry.sortIndex,
                verticalSize = Some(1)
              )
            )
          case entry: TweetItem =>
            val candidate = tweetIdToItemCandidateMap(entry.id)
            val tweetEntry = TweetEntryMarshaller(entry, candidate)
            Seq(
              thrift.TimelineEntry(
                content = thrift.Content.TweetEntry(tweetEntry),
                position = index.shortValue(),
                entryId = entry.entryIdentifier,
                entryType = thrift.EntryType.Tweet,
                sortIndex = entry.sortIndex,
                verticalSize = Some(1)
              )
            )
          case module: TimelineModule
              if module.entryNamespace.toString == WhoToFollowCandidateDecorator.EntryNamespaceString =>
            val whoToFollowEntries = module.items.collect {
              case ModuleItem(entry: UserItem, _, _) =>
                val candidate = userIdToItemCandidateMap(entry.id)
                val whoToFollowEntry = WhoToFollowEntryMarshaller(entry, candidate)
                thrift.AtomicEntry.WtfEntry(whoToFollowEntry)
            }
            Seq(
              thrift.TimelineEntry(
                content = thrift.Content.Entries(whoToFollowEntries),
                position = index.shortValue(),
                entryId = module.entryIdentifier,
                entryType = thrift.EntryType.WhoToFollowModule,
                sortIndex = module.sortIndex
              )
            )
          case module: TimelineModule
              if module.sortIndex.isDefined && module.items.headOption.exists(
                _.item.isInstanceOf[TweetItem]) =>
            val conversationTweetEntries = module.items.collect {
              case ModuleItem(entry: TweetItem, _, _) =>
                val candidate = tweetIdToItemCandidateMap(entry.id)
                val conversationEntry = ConversationEntryMarshaller(entry, candidate)
                thrift.AtomicEntry.ConversationEntry(conversationEntry)
            }
            Seq(
              thrift.TimelineEntry(
                content = thrift.Content.Entries(conversationTweetEntries),
                position = index.shortValue(),
                entryId = module.entryIdentifier,
                entryType = thrift.EntryType.ConversationModule,
                sortIndex = module.sortIndex
              )
            )
          case _ => Seq.empty
        }.flatten
      // Other instructions
      case _ => Seq.empty[thrift.TimelineEntry]
    }.flatten

    thrift.Timeline(
      timelineEntries = timelineEntries,
      requestTimeMs = inputs.query.queryTime.inMilliseconds,
      traceId = Trace.id.traceId.toLong,
      userId = inputs.query.getOptionalUserId,
      clientAppId = inputs.query.clientContext.appId,
      sourceJobInstance = None,
      hasDarkRequest = inputs.query.features.flatMap(_.getOrElse(HasDarkRequestFeature, None)),
      parentId = Some(Trace.id.parentId.toLong),
      spanId = Some(Trace.id.spanId.toLong),
      timelineType = Some(timelineType),
      ipAddress = inputs.query.clientContext.ipAddress,
      userAgent = inputs.query.clientContext.userAgent,
      queryType = queryType,
      requestProvenance = requestProvenance,
      sessionId = None,
      timeZone = None,
      browserNotificationPermission = None,
      lastNonePollingTimeMs = None,
      languageCode = inputs.query.clientContext.languageCode,
      countryCode = inputs.query.clientContext.countryCode,
      requestEndTimeMs = Some(Time.now.inMilliseconds),
      servedRequestId = inputs.query.features.flatMap(_.getOrElse(ServedRequestIdFeature, None)),
      requestJoinId = inputs.query.features.flatMap(_.getOrElse(RequestJoinIdFeature, None)),
      requestSeenTweetIds = inputs.query.seenTweetIds
    )
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert()
  )
}
