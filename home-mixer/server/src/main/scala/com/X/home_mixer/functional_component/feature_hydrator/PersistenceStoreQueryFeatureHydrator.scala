package com.X.home_mixer.functional_component.feature_hydrator

import com.X.conversions.DurationOps._
import com.X.common_internal.analytics.X_client_user_agent_parser.UserAgent
import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.X.home_mixer.model.HomeFeatures.ServedTweetIdsFeature
import com.X.home_mixer.model.HomeFeatures.ServedTweetPreviewIdsFeature
import com.X.home_mixer.model.HomeFeatures.WhoToFollowExcludedUserIdsFeature
import com.X.home_mixer.model.request.FollowingProduct
import com.X.home_mixer.model.request.ForYouProduct
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelinemixer.clients.persistence.TimelineResponseBatchesClient
import com.X.timelinemixer.clients.persistence.TimelineResponseV3
import com.X.timelines.util.client_info.ClientPlatform
import com.X.timelineservice.model.TimelineQuery
import com.X.timelineservice.model.core.TimelineKind
import com.X.timelineservice.model.rich.EntityIdType
import com.X.util.Time
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class PersistenceStoreQueryFeatureHydrator @Inject() (
  timelineResponseBatchesClient: TimelineResponseBatchesClient[TimelineResponseV3],
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("PersistenceStore")

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val servedTweetIdsSizeStat = scopedStatsReceiver.stat("ServedTweetIdsSize")

  private val WhoToFollowExcludedUserIdsLimit = 1000
  private val ServedTweetIdsDuration = 10.minutes
  private val ServedTweetIdsLimit = 100
  private val ServedTweetPreviewIdsDuration = 10.hours
  private val ServedTweetPreviewIdsLimit = 10

  override val features: Set[Feature[_, _]] =
    Set(
      ServedTweetIdsFeature,
      ServedTweetPreviewIdsFeature,
      PersistenceEntriesFeature,
      WhoToFollowExcludedUserIdsFeature)

  private val supportedClients = Seq(
    ClientPlatform.IPhone,
    ClientPlatform.IPad,
    ClientPlatform.Mac,
    ClientPlatform.Android,
    ClientPlatform.Web,
    ClientPlatform.RWeb,
    ClientPlatform.TweetDeckGryphon
  )

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val timelineKind = query.product match {
      case FollowingProduct => TimelineKind.homeLatest
      case ForYouProduct => TimelineKind.home
      case other => throw new UnsupportedOperationException(s"Unknown product: $other")
    }
    val timelineQuery = TimelineQuery(id = query.getRequiredUserId, kind = timelineKind)

    Stitch.callFuture {
      timelineResponseBatchesClient
        .get(query = timelineQuery, clientPlatforms = supportedClients)
        .map { timelineResponses =>
          // Note that the WTF entries are not being scoped by ClientPlatform
          val whoToFollowUserIds = timelineResponses
            .flatMap { timelineResponse =>
              timelineResponse.entries
                .filter(_.entityIdType == EntityIdType.WhoToFollow)
                .flatMap(_.itemIds.toSeq.flatMap(_.flatMap(_.userId)))
            }.take(WhoToFollowExcludedUserIdsLimit)

          val clientPlatform = ClientPlatform.fromQueryOptions(
            clientAppId = query.clientContext.appId,
            userAgent = query.clientContext.userAgent.flatMap(UserAgent.fromString))

          val servedTweetIds = timelineResponses
            .filter(_.clientPlatform == clientPlatform)
            .filter(_.servedTime >= Time.now - ServedTweetIdsDuration)
            .sortBy(-_.servedTime.inMilliseconds)
            .flatMap(
              _.entries.flatMap(_.tweetIds(includeSourceTweets = true)).take(ServedTweetIdsLimit))

          servedTweetIdsSizeStat.add(servedTweetIds.size)

          val servedTweetPreviewIds = timelineResponses
            .filter(_.clientPlatform == clientPlatform)
            .filter(_.servedTime >= Time.now - ServedTweetPreviewIdsDuration)
            .sortBy(-_.servedTime.inMilliseconds)
            .flatMap(_.entries
              .filter(_.entityIdType == EntityIdType.TweetPreview)
              .flatMap(_.tweetIds(includeSourceTweets = true)).take(ServedTweetPreviewIdsLimit))

          FeatureMapBuilder()
            .add(ServedTweetIdsFeature, servedTweetIds)
            .add(ServedTweetPreviewIdsFeature, servedTweetPreviewIds)
            .add(PersistenceEntriesFeature, timelineResponses)
            .add(WhoToFollowExcludedUserIdsFeature, whoToFollowUserIds)
            .build()
        }
    }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7, 50, 60, 60)
  )
}
