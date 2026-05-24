package com.twitter.home_mixer.functional_component.feature_hydrator.user_history

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.BaseUserHistoryEventsAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.VideoUserHistoryEventsAdapter
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableScoredVideoTweetsUserHistoryEventsQueryFeatureHydrationDeciderParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClipClusterIdInMemCache
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClusterId95Store
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.UserHistoryEventsLengthParam
import com.twitter.mediaservices.commons.thriftscala.MediaCategory
import com.twitter.mediaservices.commons.tweetmedia.thriftscala.MediaInfo
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.columns.content_understanding.thriftscala.EntityWithMetadata
import com.twitter.strato.columns.content_understanding.thriftscala.TagWithMetadata
import com.twitter.strato.generated.client.content_understanding.DeserializedVideoAnnotationClientColumn
import com.twitter.strato.generated.client.tweetypie.managed.ImmersiveExploreClientEventsJobTesOnTweetClientColumn
import com.twitter.strato.generated.client.user_history_transformer.unhydrated_user_history.UnhydratedUserHistoryMHProdClientColumn
import com.twitter.unified_user_actions.thriftscala.ActionType
import com.twitter.user_history_transformer.thriftscala.SourceType
import com.twitter.user_history_transformer.thriftscala.UnhydratedUserHistory
import com.twitter.user_history_transformer.thriftscala.UserHistory
import com.twitter.user_history_transformer.thriftscala.UserHistoryMetadata
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.tweetypie.{thriftscala => tp}
import com.twitter.strato.catalog.Fetch
import javax.inject.Named
import com.twitter.storehaus.ReadableStore

@Singleton
case class ScoredVideoTweetsUserHistoryEventsQueryFeatureHydrator @Inject() (
  immersiveExploreClientEventsJobTesOnTweetClientColumn: ImmersiveExploreClientEventsJobTesOnTweetClientColumn,
  unhydratedUserHistoryMHProdClientColumn: UnhydratedUserHistoryMHProdClientColumn,
  deserializedVideoAnnotationClientColumn: DeserializedVideoAnnotationClientColumn,
  @Named(MediaClusterId95Store) clusterIdStore: ReadableStore[Long, Long],
  @Named(MediaClipClusterIdInMemCache) mediaClipClusterIdInMemCache: InProcessCache[
    Long,
    Option[Option[Long]]
  ],
  statsReceiver: StatsReceiver)
    extends BaseUserHistoryEventsQueryFeatureHydrator
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "ScoredVideoTweetsUserHistoryEvents")

  override def onlyIf(
    query: PipelineQuery
  ): Boolean =
    query.params(EnableScoredVideoTweetsUserHistoryEventsQueryFeatureHydrationDeciderParam)

  override val historyFetcher: Fetcher[Long, Unit, UnhydratedUserHistory] =
    unhydratedUserHistoryMHProdClientColumn.fetcher

  val tesFetcher: Fetcher[Long, tp.GetTweetFieldsOptions, tp.GetTweetFieldsResult] =
    immersiveExploreClientEventsJobTesOnTweetClientColumn.fetcher

  val TweetypieContentHydrationFields: Set[tp.TweetInclude] = Set(
    tp.TweetInclude.TweetFieldId(tp.Tweet.CoreDataField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.IdField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.MediaField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.MediaKeysField.id)
  )

  private val scopedStatsReceiver: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val immersiveClientActionTweetIdsCounter =
    scopedStatsReceiver.counter("immersiveClientActionTweetIdsCounter")
  private val validMediaInfoCounter = scopedStatsReceiver.counter("validMediaInfoCounter")
  private val tesFetchCounter = statsReceiver.counter("tes_fetch_total")
  private val tesFetchSuccessCounter = statsReceiver.counter("tes_fetch_success")
  private val tesFetchFailureCounter = statsReceiver.counter("tes_fetch_failure")
  private val combinedTweetIdsCounter = scopedStatsReceiver.counter("combinedTweetIdsCounter")
  private val inValidMediaInfoCounter = scopedStatsReceiver.counter("inValidMediaInfoCounter")
  private val inMemCacheHitCounter = scopedStatsReceiver.counter("cache/hit")
  private val inMemCacheMissCounter = scopedStatsReceiver.counter("cache/miss")

  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyNotFoundCounter = scopedStatsReceiver.counter("key/notFound")
  private val keyFailureCounter = scopedStatsReceiver.counter("key/failure")

  private val clusterIdFoundCounter = scopedStatsReceiver.counter("clusterIdFoundCounter")
  private val clusterIdNotFoundCounter = scopedStatsReceiver.counter("clusterIdNotFoundCounter")
  private val clusterIdFailureCounter = scopedStatsReceiver.counter("clusterIdFailureCountere")

  override val adapter: BaseUserHistoryEventsAdapter = VideoUserHistoryEventsAdapter

  override def filterEvents(
    query: PipelineQuery,
    historyEvents: Seq[UserHistory]
  ): Stitch[Seq[UserHistory]] = {

    val immersiveActions = query match {
      case query: PipelineQuery with ScoredTweetsQuery =>
        query.immersiveClientMetadata
          .map(metadata => metadata.immersiveClientActions)
          .getOrElse(Seq.empty)
          .filter { action => action.tweetId.isDefined && action.watchTimeMs.isDefined }
      case _ =>
        Seq.empty
    }

    val immersiveActionMap: Map[Long, Int] = immersiveActions.map { action =>
      action.tweetId.get -> action.watchTimeMs.get
    }.toMap

    val finalHistoryEvents =
      historyEvents
        .filter { event =>
          (event.actionType == ActionType.ClientTweetVideoWatchTime ||
          event.actionType == ActionType.ClientTweetVideoHeartbeat) &&
          !immersiveActionMap.contains(event.tweetId)
        }
        .takeRight(query.params(UserHistoryEventsLengthParam))

    val combinedTweetIds =
      (finalHistoryEvents.map(_.tweetId) ++ immersiveActions.map(_.tweetId.get)).distinct
    combinedTweetIdsCounter.incr(combinedTweetIds.size)

    val fetchedData: Stitch[Map[Long, tp.GetTweetFieldsResult]] =
      Stitch
        .traverse(combinedTweetIds) { tweetId =>
          tesFetchCounter.incr()
          tesFetcher
            .fetch(
              tweetId,
              tp.GetTweetFieldsOptions(tweetIncludes = TweetypieContentHydrationFields)
            )
            .map {
              case Fetch.Result(Some(resolvedResult), _) =>
                tesFetchSuccessCounter.incr()
                (tweetId, resolvedResult)
              case _ =>
                tesFetchFailureCounter.incr()
                (
                  tweetId,
                  tp.GetTweetFieldsResult(
                    tweetId,
                    tp.TweetFieldsResultState.NotFound(tp.TweetFieldsResultNotFound())
                  )
                )
            }
        }.map(_.toMap)

    val grokMetadataFetched: Stitch[
      Map[Long, (Option[Seq[TagWithMetadata]], Option[Seq[EntityWithMetadata]])]
    ] =
      Stitch
        .traverse(combinedTweetIds) { tweetId =>
          deserializedVideoAnnotationClientColumn.fetcher
            .fetch(tweetId, Unit)
            .map {
              case Fetch.Result(response, _) =>
                if (response.nonEmpty) keyFoundCounter.incr()
                else keyNotFoundCounter.incr()
                (
                  tweetId,
                  (response.flatMap(_.tags), response.flatMap(_.entities))
                )
              case _ =>
                keyFailureCounter.incr()
                (
                  tweetId,
                  (None: Option[List[TagWithMetadata]], None: Option[List[EntityWithMetadata]])
                )
            }
            .handle {
              case _ =>
                keyFailureCounter.incr()
                (
                  tweetId,
                  (None: Option[List[TagWithMetadata]], None: Option[List[EntityWithMetadata]])
                )
            }
        }.map(_.toMap)

    val cluster95IdsFetched: Stitch[Map[Long, Long]] =
      Stitch
        .traverse(combinedTweetIds) { tweetId =>
          getFromCacheOrFetch(tweetId)
            .map {
              case Some(Some(clusterId)) =>
                clusterIdFoundCounter.incr()
                (tweetId, clusterId)
              case Some(None) | None =>
                clusterIdNotFoundCounter.incr()
                (tweetId, -1L)
            }
            .handle {
              case _ =>
                clusterIdFailureCounter.incr()
                (tweetId, -1L)
            }
        }.map(_.toMap)

    for {
      tweetResults <- fetchedData
      grokMetadata <- grokMetadataFetched
      clusterIds <- cluster95IdsFetched
    } yield {
      val tweetToMediaInfoMap = tweetResults.foldLeft(
        Map.empty[Long, (Long, Option[MediaCategory], Option[Int], Option[Long], Long)]
      ) {
        case (mediaInfoMap, (tweetId, tweetResult)) =>
          tweetResult.tweetResult match {
            case tp.TweetFieldsResultState.Found(found) =>
              val media = found.tweet.media.flatMap(_.headOption)
              val authorId = found.tweet.coreData.map(_.userId)
              val mediaId = media.map(_.mediaId)
              val mediaCategory = media.flatMap(_.mediaKey).map(_.mediaCategory)
              val videoDuration = media.flatMap(_.mediaInfo match {
                case Some(MediaInfo.VideoInfo(videoInfo)) => Some(videoInfo.durationMillis)
                case _ => None
              })
              val clusterId = clusterIds.getOrElse(tweetId, -1L)

              mediaId match {
                case Some(id) =>
                  mediaInfoMap + (tweetId -> (
                    (
                      id,
                      mediaCategory,
                      videoDuration,
                      authorId,
                      clusterId
                    )
                  ))
                case None => mediaInfoMap
              }
            case _ =>
              mediaInfoMap
          }
      }

      val immersiveActionsUserHistory = immersiveActions.map { action =>
        val tweetId = action.tweetId.get
        val (tagsOpt, entitiesOpt) = grokMetadata.getOrElse(tweetId, (None, None))
        val mediaInfo = tweetToMediaInfoMap.get(tweetId)
        val finalTags = tagsOpt.getOrElse(List.empty[TagWithMetadata])
        val finalEntities = entitiesOpt.getOrElse(List.empty[EntityWithMetadata])
        val finalAuthorId: Option[Long] = mediaInfo.flatMap(_._4)
        val finalClusterId: Long = mediaInfo.map(_._5).getOrElse(-1L)

        val (finalMediaId, finalMediaCategory, finalVideoDuration) = mediaInfo match {
          case Some((mediaId, mediaCategory, videoDuration, _, _)) =>
            validMediaInfoCounter.incr()
            (Some(mediaId), mediaCategory, videoDuration)
          case None =>
            inValidMediaInfoCounter.incr()
            (Some(-1L), Some(MediaCategory.TweetVideo), Some(-1))
        }

        UserHistory(
          userId = query.getRequiredUserId,
          tweetId = tweetId,
          authorId = finalAuthorId,
          actionType = ActionType.ClientTweetVideoWatchTime,
          engagedTimestampMs = query.queryTime.inMilliseconds,
          textTokens = None,
          sourceTweetId = Some(tweetId),
          metadata = Some(
            UserHistoryMetadata(
              source = Some(SourceType.ImmersiveVideo),
              watchTime = Some(action.watchTimeMs.get.toDouble),
              mediaId = finalMediaId,
              mediaCategory = finalMediaCategory,
              videoDuration = finalVideoDuration,
              tags = Some(finalTags),
              entities = Some(finalEntities),
              clusterId = Some(finalClusterId)
            )
          )
        )
      }

      immersiveClientActionTweetIdsCounter.incr(immersiveActionsUserHistory.size)

      (finalHistoryEvents ++ immersiveActionsUserHistory).map { event =>
        val tweetId = event.tweetId
        val (tagsOpt, entitiesOpt) = grokMetadata.getOrElse(tweetId, (None, None))
        val mediaInfo = tweetToMediaInfoMap.get(tweetId)
        val finalTags = tagsOpt.getOrElse(List.empty[TagWithMetadata])
        val finalEntities = entitiesOpt.getOrElse(List.empty[EntityWithMetadata])
        val finalAuthorId: Option[Long] = mediaInfo.flatMap(_._4)
        val finalClusterId: Long = mediaInfo.map(_._5).getOrElse(-1L)

        val (finalMediaId, finalMediaCategory, finalVideoDuration) = mediaInfo match {
          case Some((mediaId, mediaCategory, videoDuration, _, _)) =>
            validMediaInfoCounter.incr()
            (Some(mediaId), mediaCategory, videoDuration)
          case None =>
            inValidMediaInfoCounter.incr()
            (Some(-1L), Some(MediaCategory.TweetVideo), Some(-1))
        }

        event.copy(
          authorId = finalAuthorId.orElse(event.authorId),
          metadata = event.metadata.map { metadata =>
            metadata.copy(
              mediaId = finalMediaId,
              mediaCategory = finalMediaCategory,
              videoDuration = finalVideoDuration,
              tags = Some(finalTags),
              entities = Some(finalEntities),
              clusterId = Some(finalClusterId)
            )
          }
        )
      }
    }
  }

  private def getFromCacheOrFetch(tweetId: Long): Stitch[Option[Option[Long]]] = {
    mediaClipClusterIdInMemCache
      .get(tweetId)
      .map { cachedValue =>
        inMemCacheHitCounter.incr()
        Stitch.value(cachedValue)
      }.getOrElse {
        inMemCacheMissCounter.incr()
        Stitch
          .callFuture(clusterIdStore.get(tweetId))
          .map { result =>
            val finalResult = Some(result)
            mediaClipClusterIdInMemCache.set(tweetId, finalResult)
            finalResult
          }
          .handle {
            case _ =>
              None
          }
      }
  }
}
