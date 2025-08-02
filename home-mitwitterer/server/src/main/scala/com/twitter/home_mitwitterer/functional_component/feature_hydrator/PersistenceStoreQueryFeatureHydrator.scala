package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.conversions.DurationOps._
import com.twitter.common_internal.analytics.twitter_client_user_agent_parser.UserAgent
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.ServedTweetIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.ServedTweetPreviewIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.WhoToFollowEtwittercludedUserIdsFeature
import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.home_mitwitterer.model.request.ForYouProduct
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemitwitterer.clients.persistence.TimelineResponseBatchesClient
import com.twitter.timelinemitwitterer.clients.persistence.TimelineResponseV3
import com.twitter.timelines.util.client_info.ClientPlatform
import com.twitter.timelineservice.model.TimelineQuery
import com.twitter.timelineservice.model.core.TimelineKind
import com.twitter.timelineservice.model.rich.EntityIdType
import com.twitter.util.Time
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class PersistenceStoreQueryFeatureHydrator @Inject() (
  timelineResponseBatchesClient: TimelineResponseBatchesClient[TimelineResponseV3],
  statsReceiver: StatsReceiver)
    etwittertends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("PersistenceStore")

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val servedTweetIdsSizeStat = scopedStatsReceiver.stat("ServedTweetIdsSize")

  private val WhoToFollowEtwittercludedUserIdsLimit = 1000
  private val ServedTweetIdsDuration = 10.minutes
  private val ServedTweetIdsLimit = 100
  private val ServedTweetPreviewIdsDuration = 10.hours
  private val ServedTweetPreviewIdsLimit = 10

  override val features: Set[Feature[_, _]] =
    Set(
      ServedTweetIdsFeature,
      ServedTweetPreviewIdsFeature,
      PersistenceEntriesFeature,
      WhoToFollowEtwittercludedUserIdsFeature)

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
      case other => throw new UnsupportedOperationEtwitterception(s"Unknown product: $other")
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
            }.take(WhoToFollowEtwittercludedUserIdsLimit)

          val clientPlatform = ClientPlatform.fromQueryOptions(
            clientAppId = query.clientContetwittert.appId,
            userAgent = query.clientContetwittert.userAgent.flatMap(UserAgent.fromString))

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
            .add(WhoToFollowEtwittercludedUserIdsFeature, whoToFollowUserIds)
            .build()
        }
    }
  }

  override val alerts = Seq(
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7, 50, 60, 60)
  )
}
