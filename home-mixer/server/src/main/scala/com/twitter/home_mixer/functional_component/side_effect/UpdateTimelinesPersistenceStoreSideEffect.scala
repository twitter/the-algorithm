package com.twitter.home_mixer.functional_component.side_effect

import com.twitter.home_mixer.functional_component.decorator.EntryPointPivotModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.ForYouTweetCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.KeywordTrendsModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.StoriesModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.TuneFeedModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.VideoCarouselModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.urt.builder.BookmarksModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.urt.builder.PinnedTweetsModuleCandidateDecorator
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.model.PredictedDwellScoreFeature
import com.twitter.home_mixer.model.PredictedFavoriteScoreFeature
import com.twitter.home_mixer.model.PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature
import com.twitter.home_mixer.model.PredictedGoodClickConvoDescUamGt2ScoreFeature
import com.twitter.home_mixer.model.PredictedGoodProfileClickScoreFeature
import com.twitter.home_mixer.model.PredictedNegativeFeedbackV2ScoreFeature
import com.twitter.home_mixer.model.PredictedReplyEngagedByAuthorScoreFeature
import com.twitter.home_mixer.model.PredictedReplyScoreFeature
import com.twitter.home_mixer.model.PredictedRetweetScoreFeature
import com.twitter.home_mixer.model.PredictedShareScoreFeature
import com.twitter.home_mixer.model.PredictedVideoQualityViewScoreFeature
import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePersistenceDebug
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.pipeline.candidate.communities_to_join.CommunitiesToJoinCandidateDecorator
import com.twitter.product_mixer.component_library.pipeline.candidate.job.RecommendedJobsCandidateDecorator
import com.twitter.product_mixer.component_library.pipeline.candidate.recruiting_organization.RecommendedRecruitingOrganizationsCandidateDecorator
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowCandidateDecorator
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_subscribe_module.WhoToSubscribeCandidateDecorator
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.ReplaceEntryTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.ShowCoverInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.PromptItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.PromotedMetadata
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemixer.clients.persistence.EntryWithItemIds
import com.twitter.timelinemixer.clients.persistence.ItemIds
import com.twitter.timelinemixer.clients.persistence.TimelineResponseBatchesClient
import com.twitter.timelinemixer.clients.persistence.TimelineResponseV3
import com.twitter.timelines.persistence.{thriftscala => persistence}
import com.twitter.timelineservice.model.PredictedScores
import com.twitter.timelineservice.model.TimelineQuery
import com.twitter.timelineservice.model.TimelineQueryOptions
import com.twitter.timelineservice.model.TweetScoreV1
import com.twitter.timelineservice.model.core.TimelineKind
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.util.Time
import com.twitter.{timelineservice => tls}
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Side effect that updates the Timelines Persistence Store (Manhattan) with the entries being returned.
 */
@Singleton
class UpdateTimelinesPersistenceStoreSideEffect @Inject() (
  timelineResponseBatchesClient: TimelineResponseBatchesClient[TimelineResponseV3])
    extends PipelineResultSideEffect[PipelineQuery, Timeline] {

  override val identifier: SideEffectIdentifier =
    SideEffectIdentifier("UpdateTimelinesPersistenceStore")

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, Timeline]
  ): Stitch[Unit] = {
    if (inputs.response.instructions.nonEmpty) {
      val timelineKind = inputs.query.product match {
        case FollowingProduct => TimelineKind.homeLatest
        case ForYouProduct => TimelineKind.home
        case other => throw new UnsupportedOperationException(s"Unknown product: $other")
      }

      val debugEnabled = inputs.query.params(EnablePersistenceDebug)

      val timelineQuery = TimelineQuery(
        id = inputs.query.getRequiredUserId,
        kind = timelineKind,
        options = TimelineQueryOptions(
          contextualUserId = inputs.query.getOptionalUserId,
          deviceContext = tls.DeviceContext.empty.copy(
            userAgent = inputs.query.clientContext.userAgent,
            clientAppId = inputs.query.clientContext.appId)
        )
      )

      val tweetIdToItemCandidateMap: Map[Long, ItemCandidateWithDetails] =
        inputs.selectedCandidates.flatMap {
          case item: ItemCandidateWithDetails if item.candidate.id.isInstanceOf[Long] =>
            Seq((item.candidateIdLong, item))
          case module: ModuleCandidateWithDetails
              if module.candidates.headOption.exists(_.candidate.id.isInstanceOf[Long]) =>
            module.candidates.map(item => (item.candidateIdLong, item))
          case _ => Seq.empty
        }.toMap

      val entries = inputs.response.instructions.collect {
        case AddEntriesTimelineInstruction(entries) =>
          entries.collect {
            // includes tweets, tweet previews, and promoted tweets
            case entry: TweetItem if entry.sortIndex.isDefined =>
              val tweetEntry = buildTweetEntryWithItemIds(
                tweetIdToItemCandidateMap(entry.id),
                entry.sortIndex.get,
                debugEnabled,
                entry.promotedMetadata
              )
              Seq(tweetEntry)
            case entry: PromptItem =>
              Seq(
                EntryWithItemIds(
                  // Temporarily use annotation here as entity type until we come up with a proper one
                  entityIdType = EntityIdType.Annotation,
                  sortIndex = entry.sortIndex.getOrElse(0L),
                  size = 1,
                  itemIds = None
                ))
            case module: TimelineModule if module.sortIndex.isDefined && module.items.nonEmpty =>
              if (module.entryNamespace == ForYouTweetCandidateDecorator.ConvoModuleEntryNamespace) {
                module.items.map { item =>
                  buildTweetEntryWithItemIds(
                    tweetIdToItemCandidateMap(item.item.id.asInstanceOf[Long]),
                    module.sortIndex.get,
                    debugEnabled
                  )
                }
              } else if (module.entryNamespace == StoriesModuleCandidateDecorator.TrendsEntryNamespace
                || module.entryNamespace == KeywordTrendsModuleCandidateDecorator.KeywordTrendsEntryNamespace) {
                Seq(
                  EntryWithItemIds(
                    entityIdType = EntityIdType.Trends,
                    sortIndex = module.sortIndex.get,
                    size = module.items.size.toShort,
                    itemIds = None
                  )
                )
              } else {
                val moduleItems = module.items.map(_.item.id.asInstanceOf[Long])

                val (entityId, items) = module.entryNamespace.toString match {
                  case WhoToFollowCandidateDecorator.EntryNamespaceString =>
                    val itemIds = moduleItems.map(id => ItemIds(userId = Some(id)))
                    (EntityIdType.WhoToFollow, itemIds)
                  case WhoToSubscribeCandidateDecorator.EntryNamespaceString =>
                    val itemIds = moduleItems.map(id => ItemIds(userId = Some(id)))
                    (EntityIdType.WhoToSubscribe, itemIds)
                  case CommunitiesToJoinCandidateDecorator.EntryNamespaceString =>
                    val itemIds = moduleItems.map(id => ItemIds(communityId = Some(id)))
                    (EntityIdType.CommunityModule, itemIds)
                  case RecommendedJobsCandidateDecorator.EntryNamespaceString =>
                    val itemIds = moduleItems.map(id => ItemIds(jobId = Some(id)))
                    (EntityIdType.JobModule, itemIds)
                  case RecommendedRecruitingOrganizationsCandidateDecorator.EntryNamespaceString =>
                    val itemIds =
                      moduleItems.map(id => ItemIds(recruitingOrganizationId = Some(id)))
                    (EntityIdType.RecruitingOrganizationModule, itemIds)
                  case BookmarksModuleCandidateDecorator.entryNamespaceString =>
                    (EntityIdType.BookmarksModule, Seq.empty)
                  case PinnedTweetsModuleCandidateDecorator.entryNamespaceString =>
                    (EntityIdType.PinnedTweetsModule, Seq.empty)
                  case VideoCarouselModuleCandidateDecorator.entryNamespaceString =>
                    (EntityIdType.VideoCarouselModule, Seq.empty)
                  case EntryPointPivotModuleCandidateDecorator.EntryNamespaceString =>
                    (EntityIdType.EntryPointPivot, Seq.empty)
                  case TuneFeedModuleCandidateDecorator.EntryNamespaceString =>
                    (EntityIdType.TuneFeedModule, Seq.empty)
                  case other => throw new IllegalStateException("Invalid namespace: " + other)
                }

                val entry = EntryWithItemIds(
                  entityIdType = entityId,
                  sortIndex = module.sortIndex.get,
                  size = module.items.size.toShort,
                  itemIds = Some(items)
                )
                Seq(entry)
              }
          }.flatten
        case ShowCoverInstruction(cover) =>
          val entry = EntryWithItemIds(
            entityIdType = EntityIdType.Prompt,
            sortIndex = cover.sortIndex.get,
            size = 1,
            itemIds = None
          )
          Seq(entry)
        case ReplaceEntryTimelineInstruction(replace) =>
          val namespaceLength = TweetItem.TweetEntryNamespace.toString.length
          val itemId = ItemIds(
            tweetId = replace.entryIdToReplace.map(_.substring(namespaceLength + 1).toLong),
            entryIdToReplace = replace.entryIdToReplace,
          )
          val entry = EntryWithItemIds(
            entityIdType = EntityIdType.Tweet,
            sortIndex = replace.sortIndex.get,
            size = 1,
            itemIds = Some(Seq(itemId))
          )
          Seq(entry)
      }.flatten

      val servedId = inputs.query.features.flatMap(_.getOrElse(ServedIdFeature, None))

      val response = TimelineResponseV3(
        clientPlatform = timelineQuery.clientPlatform,
        servedTime = Time.now,
        requestType = requestTypeFromQuery(inputs.query),
        entries = entries,
        servedId = servedId,
      )

      Stitch.callFuture(timelineResponseBatchesClient.insertResponse(timelineQuery, response))
    } else Stitch.Unit
  }

  private def buildTweetEntryWithItemIds(
    candidate: ItemCandidateWithDetails,
    sortIndex: Long,
    debug: Boolean,
    promotedMetadata: Option[PromotedMetadata] = None
  ): EntryWithItemIds = {
    val features = candidate.features
    val sourceAuthorId =
      if (features.getOrElse(IsRetweetFeature, false)) features.getOrElse(SourceUserIdFeature, None)
      else features.getOrElse(AuthorIdFeature, None)
    val quoteAuthorId =
      if (features.getOrElse(QuotedTweetIdFeature, None).nonEmpty)
        features.getOrElse(SourceUserIdFeature, None)
      else None
    val tweetScore = features.getOrElse(ScoreFeature, None)
    val debugInfo = features.getOrElse(DebugStringFeature, None)
    val predictionRequestId = features.getOrElse(PredictionRequestIdFeature, None)
    val servedType = candidate.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined).name
    val isPreview = features.getOrElse(IsTweetPreviewFeature, default = false)
    val entityType = if (isPreview) EntityIdType.TweetPreview else EntityIdType.Tweet
    val grokAnnotations = if (debug) features.getOrElse(GrokAnnotationsFeature, None) else None
    val topics = grokAnnotations.map(_.topics)
    val tags = grokAnnotations.map(_.tags)
    val impressionId = if (debug) promotedMetadata.flatMap(_.impressionString) else None
    val predictedScores =
      if (debug)
        Some(
          PredictedScores(
            favoriteScore = features.getOrElse(PredictedFavoriteScoreFeature, None),
            replyScore = features.getOrElse(PredictedReplyScoreFeature, None),
            retweetScore = features.getOrElse(PredictedRetweetScoreFeature, None),
            replyEngagedByAuthorScore =
              features.getOrElse(PredictedReplyEngagedByAuthorScoreFeature, None),
            goodClickConvoDescFavoritedOrRepliedScore = features
              .getOrElse(PredictedGoodClickConvoDescFavoritedOrRepliedScoreFeature, None),
            goodClickConvoDescUamGt2Score =
              features.getOrElse(PredictedGoodClickConvoDescUamGt2ScoreFeature, None),
            goodProfileClickScore = features.getOrElse(PredictedGoodProfileClickScoreFeature, None),
            videoQualityViewScore = features.getOrElse(PredictedVideoQualityViewScoreFeature, None),
            shareScore = features.getOrElse(PredictedShareScoreFeature, None),
            dwellScore = features.getOrElse(PredictedDwellScoreFeature, None),
            negativeFeedbackV2Score =
              features.getOrElse(PredictedNegativeFeedbackV2ScoreFeature, None)
          ))
      else None

    val itemId = ItemIds(
      tweetId = Some(candidate.candidateIdLong),
      sourceTweetId = features.getOrElse(SourceTweetIdFeature, None),
      quoteTweetId = features.getOrElse(QuotedTweetIdFeature, None),
      sourceAuthorId = sourceAuthorId,
      quoteAuthorId = quoteAuthorId,
      inReplyToTweetId = features.getOrElse(InReplyToTweetIdFeature, None),
      inReplyToAuthorId = features.getOrElse(DirectedAtUserIdFeature, None),
      semanticCoreId = features.getOrElse(SemanticCoreIdFeature, None),
      tweetScore = tweetScore.map(
        TweetScoreV1(
          _,
          Some(servedType),
          debugInfo,
          predictionRequestId,
          topics,
          tags,
          predictedScores)
      ),
      impressionId = impressionId
    )

    EntryWithItemIds(
      entityIdType = entityType,
      sortIndex = sortIndex,
      size = 1.toShort,
      itemIds = Some(Seq(itemId))
    )
  }

  private def requestTypeFromQuery(query: PipelineQuery): persistence.RequestType = {
    val features = query.features.getOrElse(FeatureMap.empty)

    val featureToRequestType = Seq(
      (PollingFeature, persistence.RequestType.Polling),
      (GetInitialFeature, persistence.RequestType.Initial),
      (GetNewerFeature, persistence.RequestType.Newer),
      (GetMiddleFeature, persistence.RequestType.Middle),
      (GetOlderFeature, persistence.RequestType.Older)
    )

    featureToRequestType
      .collectFirst {
        case (feature, requestType) if features.getOrElse(feature, false) => requestType
      }.getOrElse(persistence.RequestType.Other)
  }

  override val alerts = Seq(HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8))
}
