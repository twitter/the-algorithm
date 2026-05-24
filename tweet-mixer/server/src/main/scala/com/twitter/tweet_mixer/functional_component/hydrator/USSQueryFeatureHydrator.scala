package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.FeatureHydrationFailed
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Params
import com.twitter.tweet_mixer.candidate_source.uss_service.USSSignalCandidateSource
import com.twitter.tweet_mixer.feature.EntityTypes._
import com.twitter.tweet_mixer.feature._
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableUSSFeatureHydrator
import com.twitter.tweet_mixer.param.USSParams._
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.tweet_mixer.utils.SignalUtils
import com.twitter.twistly.thriftscala.Sentiment
import com.twitter.usersignalservice.thriftscala.SignalType
import com.twitter.usersignalservice.{thriftscala => uss}
import com.twitter.util.Time
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Hydrate user signal store features
 *
 * @param ussSignalCandidateSource the uss candidate source
 */
@Singleton
class USSQueryFeatureHydrator @Inject() (
  ussSignalCandidateSource: USSSignalCandidateSource,
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery]
    with Logging {

  import USSQueryFeatureHydrator._

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("USSSignals")

  private val scopedStatsReceiver = statsReceiver.scope("USSQueryFeatureHydrator")
  private val signalScopedStatsReceiver = scopedStatsReceiver.scope("signal")
  private val signalRequestsCount = signalScopedStatsReceiver.counter("requests")
  private val allSignalCount = signalScopedStatsReceiver.counter("all")
  private val timeFilterSignalCount = signalScopedStatsReceiver.counter("timeFilter")
  private val vqvTimeFilterSignalCount = signalScopedStatsReceiver.counter("vqvTimeFilter")
  private val negativeSentimentFilterSignalCount =
    signalScopedStatsReceiver.counter("negativeSentimentFilter")
  private val inUseSignalCount = signalScopedStatsReceiver.counter("inUse")

  private val negativeSignalTypes = Set[uss.SignalType](
    uss.SignalType.AccountBlock,
    uss.SignalType.AccountMute,
    uss.SignalType.TweetReport,
    uss.SignalType.TweetDontLike,
    uss.SignalType.NegativeSourceSignal,
    uss.SignalType.NotificationOpenAndClickV1,
    uss.SignalType.FeedbackNotrelevant,
    uss.SignalType.FeedbackRelevant
  )

  private def bucketSignalStats(signals: Seq[uss.Signal], signalType: SignalType): Unit = {
    val currentTimeMs = Time.now
    val dayMs = 24 * 60 * 60 * 1000
    signals.foreach { signal =>
      signal.targetInternalId.foreach {
        case InternalId.TweetId(tweetId) =>
          SnowflakeId.timeFromIdOpt(tweetId).foreach { time =>
            val ageDays = (currentTimeMs - time).inMillis / dayMs
            val age =
              if (ageDays <= 1) "1_day"
              else if (ageDays <= 2) "2_days"
              else if (ageDays <= 7) "7_days"
              else if (ageDays <= 30) "30_days"
              else "over_30_days"
            signalScopedStatsReceiver.scope(signalType.toString).counter(age).incr()
          }
        case _: InternalId =>
          signalScopedStatsReceiver.scope(signalType.toString).counter("total").incr()
      }
    }
  }

  override val features: Set[Feature[_, _]] = Set(
    TweetFavorites,
    Retweets,
    TweetReplies,
    TweetBookmarks,
    OriginalTweets,
    AccountFollows,
    RepeatedProfileVisits,
    TweetShares,
    TweetPhotoExpands,
    SearchTweetClicks,
    ProfileTweetClicks,
    TweetVideoOpens,
    VideoViewTweets,
    VideoViewVisibilityFilteredTweets,
    VideoViewVisibility75FilteredTweets,
    VideoViewVisibility100FilteredTweets,
    VideoViewHighResolutionFilteredTweets,
    AccountBlocks,
    AccountMutes,
    TweetReports,
    TweetDontLikes,
    RecentNotifications,
    LowSignalUserFeature,
    ImmersiveVideoViewTweets,
    MediaImmersiveVideoViewTweets,
    TvVideoViewTweets,
    WatchTimeTweets,
    ImmersiveWatchTimeTweets,
    TvWatchTimeTweets,
    MediaImmersiveWatchTimeTweets,
    SearcherRealtimeHistory,
    TweetDetailGoodClick1Min,
    TweetFeedbackRelevant,
    TweetFeedbackNotrelevant,
    NegativeSourceSignal,
    HighQualitySourceTweet,
    HighQualitySourceUser
  )

  override def onlyIf(query: PipelineQuery): Boolean = query.params(EnableUSSFeatureHydrator)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val featureMapBuilder = FeatureMapBuilder()
    query.getOptionalUserId match {
      case Some(userId) =>
        getUSSSignals(userId, query.params).map { nestedSignals =>
          nestedSignals.map {
            case (signalType, ussSignals) =>
              allSignalCount.incr(ussSignals.length)
              val timeFilteredSignals = ussSignals.filter { signal =>
                signal.timestamp >= (Time.now - Time.fromHours(
                  query.params(UnifiedMaxSignalAgeInHours))).inMillis
              }
              timeFilterSignalCount.incr(ussSignals.length - timeFilteredSignals.length)
              val vqvSignals: Set[SignalType] = Set(
                uss.SignalType.VideoView90dPlayback50V1,
                uss.SignalType.VideoView90dQualityV1,
                uss.SignalType.VideoView90dQualityV1AllSurfaces,
                uss.SignalType.VideoView90dQualityV2,
                uss.SignalType.VideoView90dQualityV2Visibility75,
                uss.SignalType.VideoView90dQualityV2Visibility100,
                uss.SignalType.VideoView90dQualityV3
              )
              val vqvTimeFilteredSignals = timeFilteredSignals.filter { signal =>
                (!vqvSignals.contains(signalType) && !vqvSignals.contains(signal.signalType)) ||
                signal.timestamp >= (Time.now - Time.fromDays(
                  query.params(VQVMaxSignalAgeInDays)
                )).inMillis
              }
              vqvTimeFilterSignalCount
                .incr(timeFilteredSignals.length - vqvTimeFilteredSignals.length)
              val negativeSentimentFilteredSignals = vqvTimeFilteredSignals.filterNot { signal =>
                query.params(EnableNegativeSentimentSignalFilter) &&
                signal.sentiment.contains(Sentiment.Negative)
              }
              negativeSentimentFilterSignalCount
                .incr(vqvTimeFilteredSignals.length - negativeSentimentFilteredSignals.length)
              inUseSignalCount.incr(negativeSentimentFilteredSignals.length)

              // Track age stats for all signal types
              if (negativeSignalTypes.contains(signalType))
                bucketSignalStats(ussSignals, signalType)
              else bucketSignalStats(negativeSentimentFilteredSignals, signalType)

              signalType match {
                case uss.SignalType.TweetFavorite =>
                  featureMapBuilder.add(
                    TweetFavorites,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.Retweet =>
                  featureMapBuilder.add(
                    Retweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.Reply =>
                  featureMapBuilder.add(
                    TweetReplies,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.TweetBookmarkV1 =>
                  featureMapBuilder.add(
                    TweetBookmarks,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.OriginalTweet =>
                  featureMapBuilder.add(
                    OriginalTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.AccountFollow =>
                  featureMapBuilder.add(
                    AccountFollows,
                    getSignalMap[UserId](negativeSentimentFilteredSignals))
                case uss.SignalType.RepeatedProfileVisit14dMinVisit2V1 |
                    uss.SignalType.RepeatedProfileVisit90dMinVisit6V1 |
                    uss.SignalType.RepeatedProfileVisit180dMinVisit6V1 =>
                  featureMapBuilder.add(
                    RepeatedProfileVisits,
                    getSignalMap[UserId](negativeSentimentFilteredSignals))
                case uss.SignalType.TweetShareV1 =>
                  featureMapBuilder.add(
                    TweetShares,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.TweetPhotoExpand =>
                  featureMapBuilder.add(
                    TweetPhotoExpands,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.SearchTweetClick =>
                  featureMapBuilder.add(
                    SearchTweetClicks,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.ProfileTweetClick =>
                  featureMapBuilder.add(
                    ProfileTweetClicks,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.TweetVideoOpen =>
                  featureMapBuilder.add(
                    TweetVideoOpens,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.TweetDetailGoodClick1Min =>
                  featureMapBuilder.add(
                    TweetDetailGoodClick1Min,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.VideoView90dPlayback50V1 |
                    uss.SignalType.VideoView90dQualityV1 |
                    uss.SignalType.VideoView90dQualityV1AllSurfaces =>
                  featureMapBuilder.add(
                    VideoViewTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.VideoView90dQualityV2 =>
                  featureMapBuilder.add(
                    VideoViewVisibilityFilteredTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.VideoView90dQualityV2Visibility75 =>
                  featureMapBuilder.add(
                    VideoViewVisibility75FilteredTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.VideoView90dQualityV2Visibility100 =>
                  featureMapBuilder.add(
                    VideoViewVisibility100FilteredTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.VideoView90dQualityV3 =>
                  featureMapBuilder.add(
                    VideoViewHighResolutionFilteredTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.ImmersiveVideoQualityView =>
                  featureMapBuilder.add(
                    ImmersiveVideoViewTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.ImmersiveMediaVideoQualityView =>
                  featureMapBuilder.add(
                    MediaImmersiveVideoViewTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.TvHomeVideoQualityView =>
                  featureMapBuilder.add(
                    TvVideoViewTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.VideoWatchTimeAllSurfaces =>
                  featureMapBuilder.add(
                    WatchTimeTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.ImmersiveVideoWatchTime =>
                  featureMapBuilder.add(
                    ImmersiveWatchTimeTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.ImmersiveMediaVideoWatchTime =>
                  featureMapBuilder.add(
                    MediaImmersiveWatchTimeTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.TvHomeVideoWatchTime =>
                  featureMapBuilder.add(
                    TvWatchTimeTweets,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                case uss.SignalType.SearcherRealtimeHistory =>
                  featureMapBuilder.add(
                    SearcherRealtimeHistory,
                    getSignalMap[SearchQuery](negativeSentimentFilteredSignals)
                  )
                case uss.SignalType.HighQualitySourceTweet =>
                  val highQualitySourceTweetCount = negativeSentimentFilteredSignals.length
                  if (highQualitySourceTweetCount >= query.params(
                      HighQualitySourceTweetEligible) && query.params(
                      EnableHighQualitySourceSignalBucketFilter)) {
                    featureMapBuilder.add(
                      HighQualitySourceTweet,
                      getSignalMap[TweetId](negativeSentimentFilteredSignals)
                    )
                  }
                case uss.SignalType.HighQualitySourceUser =>
                  val highQualitySourceUserCount = negativeSentimentFilteredSignals.length
                  if (highQualitySourceUserCount >= query.params(
                      HighQualitySourceUserEligible) && query.params(
                      EnableHighQualitySourceSignalBucketFilter)) {
                    featureMapBuilder.add(
                      HighQualitySourceUser,
                      getSignalMap[UserId](negativeSentimentFilteredSignals)
                    )
                  }
                // Negative signals
                case uss.SignalType.AccountBlock =>
                  featureMapBuilder.add(AccountBlocks, getSignalMap[UserId](ussSignals))
                case uss.SignalType.AccountMute =>
                  featureMapBuilder.add(AccountMutes, getSignalMap[UserId](ussSignals))
                case uss.SignalType.TweetReport =>
                  featureMapBuilder.add(TweetReports, getSignalMap[TweetId](ussSignals))
                case uss.SignalType.TweetDontLike =>
                  featureMapBuilder.add(TweetDontLikes, getSignalMap[TweetId](ussSignals))
                case uss.SignalType.NegativeSourceSignal =>
                  featureMapBuilder.add(NegativeSourceSignal, getSignalMap[TweetId](ussSignals))
                // Tweet feedback signals
                // Notification-specific Signals
                // Aggregated Signals
                case uss.SignalType.NotificationOpenAndClickV1 =>
                  featureMapBuilder.add(RecentNotifications, getSignalMap[TweetId](ussSignals))

                // Survey feedback Signals
                case uss.SignalType.FeedbackNotrelevant =>
                  featureMapBuilder.add(TweetFeedbackNotrelevant, getSignalMap[TweetId](ussSignals))
                case uss.SignalType.FeedbackRelevant =>
                  featureMapBuilder.add(
                    TweetFeedbackRelevant,
                    getSignalMap[TweetId](negativeSentimentFilteredSignals))
                // Default
                case _ => ()

              }
          }
          featureMapBuilder.add(LowSignalUserFeature, lowSignalUser(nestedSignals, query.params))
          defaultFeatureMap ++ featureMapBuilder.build()
        }
      case _ => Stitch.value(defaultFeatureMap)
    }
  }

  def lowSignalUser(
    signals: Seq[(uss.SignalType, Seq[uss.Signal])],
    params: Params
  ): Boolean = {
    val explicitSignals = signals.collect {
      case (signalType, signals) if SignalUtils.ExplicitSignals.contains(signalType) => signals
    }.flatten

    val timeFilteredExplicitSignals = explicitSignals.filter { signal =>
      getTimestamp(signal).exists { signalTime =>
        Time.now.since(signalTime) < params(LowSignalUserMaxSignalAge)
      }
    }

    timeFilteredExplicitSignals.size < params(LowSignalUserMaxSignalCount)
  }

  def getUSSSignals(
    userId: Long,
    params: Params
  ): Stitch[Seq[(uss.SignalType, Seq[uss.Signal])]] = {
    val batchSignalRequest =
      uss.BatchSignalRequest(userId, buildRequests(params), Some(uss.ClientIdentifier.CrMixerHome))

    signalRequestsCount.incr()
    ussSignalCandidateSource(batchSignalRequest)
  }

  def buildSignalRequest(
    signalType: uss.SignalType,
    params: Params,
    sourceSignalParam: FSParam[Boolean],
    maxResultsParam: FSBoundedParam[Int] = UnifiedMaxSourceKeyNum
  ): Option[uss.SignalRequest] = {
    if (params(sourceSignalParam)) {
      Some(
        uss.SignalRequest(
          maxResults = Some(params(maxResultsParam)),
          signalType = signalType,
          minFavCount = Some(params(UnifiedMinSignalFavCount)),
          maxFavCount = Some(params(UnifiedMaxSignalFavCount))
        ))
    } else None
  }

  def buildRequests(params: Params): Seq[uss.SignalRequest] = {
    // Tweet-based Signals
    val maybeTweetFavorite = buildSignalRequest(
      uss.SignalType.TweetFavorite,
      params,
      EnableRecentTweetFavorites,
      MaxFavSignals
    )
    val maybeRetweet = buildSignalRequest(
      uss.SignalType.Retweet,
      params,
      EnableRecentTweetFavorites
    )
    val maybeTweetReply = buildSignalRequest(
      uss.SignalType.Reply,
      params,
      EnableRecentReplies
    )
    val maybeTweetBookmark = buildSignalRequest(
      uss.SignalType.TweetBookmarkV1,
      params,
      EnableRecentTweetBookmarks,
      MaxBookmarkSignals
    )
    val maybeOriginalTweet = buildSignalRequest(
      uss.SignalType.OriginalTweet,
      params,
      EnableRecentOriginalTweets
    )
    val maybeTweetShares = buildSignalRequest(
      uss.SignalType.TweetShareV1,
      params,
      EnableTweetShares
    )
    val maybeTweetPhotoExpand = buildSignalRequest(
      uss.SignalType.TweetPhotoExpand,
      params,
      EnableTweetPhotoExpand,
      MaxPhotoExpandSignals
    )
    val maybeSearchTweetClick = buildSignalRequest(
      uss.SignalType.SearchTweetClick,
      params,
      EnableSearchTweetClick,
      MaxSearchTweetClick
    )
    val maybeProfileTweetClick = buildSignalRequest(
      uss.SignalType.ProfileTweetClick,
      params,
      EnableProfileTweetClick,
      MaxProfileTweetClick
    )
    val maybeTweetVideoOpen = buildSignalRequest(
      uss.SignalType.TweetVideoOpen,
      params,
      EnableTweetVideoOpen,
      MaxTweetVideoOpen
    )
    val maybeTweetDetailGoodClick1Min = buildSignalRequest(
      uss.SignalType.TweetDetailGoodClick1Min,
      params,
      EnableTweetDetailGoodClick1Min
    )
    val maybeTweetFeedbackRelevant = buildSignalRequest(
      uss.SignalType.FeedbackRelevant,
      params,
      EnableRecentTweetFeedbackRelevant
    )
    val maybeTweetFeedbackNotrelevant = buildSignalRequest(
      uss.SignalType.FeedbackNotrelevant,
      params,
      EnableRecentTweetFeedbackRelevant
    )
    val maybeVideoViewTweets = buildSignalRequest(
      videoViewTweetTypeParam(params(VideoViewTweetTypeParam)),
      params,
      EnableVideoViewTweets,
      MaxVideoViewSourceSignals
    )
    val maybeVideoViewVisibilityFilteredTweets = buildSignalRequest(
      videoViewTweetTypeParam(params(VideoViewVisibilityFilteredTweetTypeParam)),
      params,
      EnableVideoViewVisibilityFilteredTweets,
      MaxVqvSignals
    )
    val maybeVideoViewVisibility75FilteredTweets = buildSignalRequest(
      videoViewTweetTypeParam(params(VideoViewVisibility75FilteredTweetTypeParam)),
      params,
      EnableVideoViewVisibility75FilteredTweets,
      MaxVqvSignals
    )
    val maybeVideoViewVisibility100FilteredTweets = buildSignalRequest(
      videoViewTweetTypeParam(params(VideoViewVisibility100FilteredTweetTypeParam)),
      params,
      EnableVideoViewVisibility100FilteredTweets,
      MaxVqvSignals
    )
    val maybeVideoViewHighResolutionFilteredTweets = buildSignalRequest(
      videoViewTweetTypeParam(params(VideoViewHighResolutionFilteredTweetTypeParam)),
      params,
      EnableVideoViewHighResolutionFilteredTweets,
      MaxVqvSignals
    )
    val maybeImmersiveVideoViewTweets = buildSignalRequest(
      uss.SignalType.ImmersiveVideoQualityView,
      params,
      EnableImmersiveVideoViewTweets
    )
    val maybeMediaImmersiveVideoViewTweets = buildSignalRequest(
      uss.SignalType.ImmersiveMediaVideoQualityView,
      params,
      EnableMediaImmersiveVideoViewTweets
    )
    val maybeTvVideoViewTweets = buildSignalRequest(
      uss.SignalType.TvHomeVideoQualityView,
      params,
      EnableTvVideoViewTweets
    )
    val maybeWatchTimeTweets = buildSignalRequest(
      uss.SignalType.VideoWatchTimeAllSurfaces,
      params,
      EnableWatchTimeTweets
    )
    val maybeImmersiveWatchTimeTweets = buildSignalRequest(
      uss.SignalType.ImmersiveVideoWatchTime,
      params,
      EnableImmersiveWatchTimeTweets
    )
    val maybeMediaImmersiveWatchTimeTweets = buildSignalRequest(
      uss.SignalType.ImmersiveMediaVideoWatchTime,
      params,
      EnableMediaImmersiveWatchTimeTweets
    )
    val maybeTvWatchTimeTweets = buildSignalRequest(
      uss.SignalType.TvHomeVideoWatchTime,
      params,
      EnableTvWatchTimeTweets
    )
    val maybeSearcherRealtimeHistory = buildSignalRequest(
      uss.SignalType.SearcherRealtimeHistory,
      params,
      EnableSearcherRealtimeHistory
    )
    val maybeHighQualitySourceTweet = buildSignalRequest(
      uss.SignalType.HighQualitySourceTweet,
      params,
      EnableHighQualitySourceTweet,
      MaxHighQualitySourceSignals
    )
    val maybeHighQualitySourceUser = buildSignalRequest(
      uss.SignalType.HighQualitySourceUser,
      params,
      EnableHighQualitySourceUser,
      MaxHighQualitySourceSignals
    )

    // Producer-based Signals
    val maybeAccountFollow = buildSignalRequest(
      uss.SignalType.AccountFollow,
      params,
      EnableRecentFollows
    )
    val maybeRepeatedProfileVisits = buildSignalRequest(
      profileMinVisitParam(params(ProfileMinVisitType)),
      params,
      EnableRepeatedProfileVisits
    )

    // Notification-specific Signals
    val maybeRecentNotifications = buildSignalRequest(
      uss.SignalType.NotificationOpenAndClickV1,
      params,
      EnableRecentNotifications
    )

    val enabledNegativeSignalTypes = Set(
      uss.SignalType.AccountBlock,
      uss.SignalType.AccountMute,
      uss.SignalType.TweetReport,
      uss.SignalType.TweetDontLike,
    )

    // negative signals
    val maybeNegativeSignals = enabledNegativeSignalTypes
      .flatMap(negativeSignal => buildSignalRequest(negativeSignal, params, EnableNegativeSignals))

    val maybeNegativeSourceSignals = buildSignalRequest(
      uss.SignalType.NegativeSourceSignal,
      params,
      EnableNegativeSourceSignal,
      MaxNegativeSourceSignals
    )

    val allPositiveSignals = Seq(
      maybeTweetFavorite,
      maybeRetweet,
      maybeTweetReply,
      maybeTweetBookmark,
      maybeOriginalTweet,
      maybeAccountFollow,
      maybeRepeatedProfileVisits,
      maybeRecentNotifications,
      maybeTweetShares,
      maybeTweetPhotoExpand,
      maybeSearchTweetClick,
      maybeProfileTweetClick,
      maybeTweetVideoOpen,
      maybeTweetFeedbackRelevant,
      maybeTweetDetailGoodClick1Min,
      maybeVideoViewTweets,
      maybeVideoViewVisibilityFilteredTweets,
      maybeVideoViewVisibility75FilteredTweets,
      maybeVideoViewVisibility100FilteredTweets,
      maybeVideoViewHighResolutionFilteredTweets,
      maybeImmersiveVideoViewTweets,
      maybeMediaImmersiveVideoViewTweets,
      maybeTvVideoViewTweets,
      maybeWatchTimeTweets,
      maybeImmersiveWatchTimeTweets,
      maybeMediaImmersiveWatchTimeTweets,
      maybeTvWatchTimeTweets,
      maybeSearcherRealtimeHistory,
      maybeHighQualitySourceTweet,
      maybeHighQualitySourceUser
    )

    allPositiveSignals.flatten ++ maybeNegativeSignals ++ maybeTweetFeedbackNotrelevant ++ maybeNegativeSourceSignals
  }
}

object USSQueryFeatureHydrator {

  val defaultFeatureMap = FeatureMapBuilder()
    .add(TweetFavorites, Map.empty[TweetId, Seq[SignalInfo]])
    .add(Retweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TweetReplies, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TweetBookmarks, Map.empty[TweetId, Seq[SignalInfo]])
    .add(OriginalTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(AccountFollows, Map.empty[UserId, Seq[SignalInfo]])
    .add(RepeatedProfileVisits, Map.empty[UserId, Seq[SignalInfo]])
    .add(TweetShares, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TweetPhotoExpands, Map.empty[TweetId, Seq[SignalInfo]])
    .add(SearchTweetClicks, Map.empty[TweetId, Seq[SignalInfo]])
    .add(ProfileTweetClicks, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TweetVideoOpens, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TweetDetailGoodClick1Min, Map.empty[TweetId, Seq[SignalInfo]])
    .add(VideoViewTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(VideoViewVisibilityFilteredTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(VideoViewVisibility75FilteredTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(VideoViewVisibility100FilteredTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(VideoViewHighResolutionFilteredTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(ImmersiveVideoViewTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(MediaImmersiveVideoViewTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TvVideoViewTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(WatchTimeTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(ImmersiveWatchTimeTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(MediaImmersiveWatchTimeTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TvWatchTimeTweets, Map.empty[TweetId, Seq[SignalInfo]])
    .add(SearcherRealtimeHistory, Map.empty[SearchQuery, Seq[SignalInfo]])
    .add(AccountBlocks, Map.empty[UserId, Seq[SignalInfo]])
    .add(AccountMutes, Map.empty[UserId, Seq[SignalInfo]])
    .add(TweetReports, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TweetDontLikes, Map.empty[TweetId, Seq[SignalInfo]])
    .add(RecentNotifications, Map.empty[TweetId, Seq[SignalInfo]])
    .add(LowSignalUserFeature, false)
    .add(TweetFeedbackRelevant, Map.empty[TweetId, Seq[SignalInfo]])
    .add(TweetFeedbackNotrelevant, Map.empty[TweetId, Seq[SignalInfo]])
    .add(NegativeSourceSignal, Map.empty[TweetId, Seq[SignalInfo]])
    .add(HighQualitySourceTweet, Map.empty[TweetId, Seq[SignalInfo]])
    .add(HighQualitySourceUser, Map.empty[UserId, Seq[SignalInfo]])
    .build()

  private def getTimestamp(signal: uss.Signal): Option[Time] = {
    if (signal.timestamp == 0L) None else Some(Time.fromMilliseconds(signal.timestamp))
  }

  private def extractAuthorId(signal: uss.Signal): Option[Long] = {
    signal.authorId match {
      case Some(InternalId.UserId(userId)) => Some(userId)
      case _ => None
    }
  }

  def getSignalMap[T](
    ussSignals: Seq[uss.Signal]
  ): Map[T, Seq[SignalInfo]] = {
    ussSignals
      .map { signal =>
        signal.targetInternalId match {
          case Some(InternalId.TweetId(tweetId)) =>
            (
              tweetId.asInstanceOf[T],
              SignalInfo(
                signalEntity = uss.SignalEntity.Tweet,
                signalType = signal.signalType,
                sourceEventTime = getTimestamp(signal),
                authorId = extractAuthorId(signal)
              )
            )
          case Some(InternalId.UserId(userId)) =>
            (
              userId.asInstanceOf[T],
              SignalInfo(
                signalEntity = uss.SignalEntity.User,
                signalType = signal.signalType,
                sourceEventTime = getTimestamp(signal),
                authorId = Some(userId)
              )
            )
          case Some(InternalId.SearchQuery(searchQuery)) =>
            (
              searchQuery.asInstanceOf[T],
              SignalInfo(
                signalEntity = uss.SignalEntity.SearchQuery,
                signalType = signal.signalType,
                sourceEventTime = getTimestamp(signal),
                authorId = None
              )
            )
          case _ => throw PipelineFailure(FeatureHydrationFailed, "Unsupported Internal ID")
        }
      }.groupBy(_._1).mapValues(_.map(_._2))
  }
}
