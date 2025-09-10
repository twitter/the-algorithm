package com.twitter.tweet_mixer.feature

import com.twitter.conversions.DurationOps._
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.tweet_mixer.feature.EntityTypes._
import com.twitter.usersignalservice.thriftscala.SignalType
import com.twitter.util.Time

class USSFeature[T] extends FeatureWithDefaultOnFailure[PipelineQuery, Map[T, Seq[SignalInfo]]] {
  val defaultValue = Map.empty[T, Seq[SignalInfo]]
  def getValue(query: PipelineQuery): Map[T, Seq[SignalInfo]] = {
    query.features
      .getOrElse(FeatureMap.empty)
      .getOrElse(this, defaultValue)
  }
}

object TweetFavorites extends USSFeature[TweetId]
object TweetFeedbackRelevant extends USSFeature[TweetId]
object TweetFeedbackNotrelevant extends USSFeature[TweetId]
object Retweets extends USSFeature[TweetId]
object TweetReplies extends USSFeature[TweetId]
object TweetBookmarks extends USSFeature[TweetId]
object OriginalTweets extends USSFeature[TweetId]
object AccountFollows extends USSFeature[UserId]
object RepeatedProfileVisits extends USSFeature[UserId]
object TweetShares extends USSFeature[TweetId]
object TweetPhotoExpands extends USSFeature[TweetId]
object SearchTweetClicks extends USSFeature[TweetId]
object ProfileTweetClicks extends USSFeature[TweetId]
object TweetVideoOpens extends USSFeature[TweetId]
object TweetDetailGoodClick1Min extends USSFeature[TweetId]
object VideoViewTweets extends USSFeature[TweetId]
object VideoViewVisibilityFilteredTweets extends USSFeature[TweetId]
object VideoViewVisibility75FilteredTweets extends USSFeature[TweetId]
object VideoViewVisibility100FilteredTweets extends USSFeature[TweetId]
object VideoViewHighResolutionFilteredTweets extends USSFeature[TweetId]
object ImmersiveVideoViewTweets extends USSFeature[TweetId]
object MediaImmersiveVideoViewTweets extends USSFeature[TweetId]
object TvVideoViewTweets extends USSFeature[TweetId]
object WatchTimeTweets extends USSFeature[TweetId]
object ImmersiveWatchTimeTweets extends USSFeature[TweetId]
object TvWatchTimeTweets extends USSFeature[TweetId]
object MediaImmersiveWatchTimeTweets extends USSFeature[TweetId]
object RecentNotifications extends USSFeature[TweetId]
object AccountBlocks extends USSFeature[UserId]
object AccountMutes extends USSFeature[UserId]
object TweetReports extends USSFeature[TweetId]
object TweetDontLikes extends USSFeature[TweetId]
object SearcherRealtimeHistory extends USSFeature[SearchQuery]
object NegativeSourceSignal extends USSFeature[TweetId]
object HighQualitySourceTweet extends USSFeature[TweetId]
object HighQualitySourceUser extends USSFeature[UserId]
object HighQualitySourceTweetV2 extends USSFeature[TweetId]
object HighQualitySourceUserV2 extends USSFeature[UserId]

object USSFeatures {
  // Not used by default, testing use for VideoView signal
  final val MaxSignalAge = 24.hours

  final val TweetFeatures = Set(
    TweetFavorites,
    Retweets,
    TweetReplies,
    TweetBookmarks,
    OriginalTweets,
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
    ImmersiveVideoViewTweets,
    ImmersiveWatchTimeTweets,
    RecentNotifications,
    TweetDetailGoodClick1Min,
    TweetFeedbackRelevant,
    HighQualitySourceTweet,
    HighQualitySourceTweetV2
  )

  final val ExplicitEngagementTweetFeatures = Set(
    TweetFavorites,
    Retweets,
    TweetReplies,
    TweetBookmarks,
    OriginalTweets,
    TweetShares,
    TweetFeedbackNotrelevant
  )

  final val NonVideoTweetFeatures = Set(
    TweetFavorites,
    Retweets,
    TweetReplies,
    TweetBookmarks,
    OriginalTweets,
    TweetShares,
    RecentNotifications,
    TweetFeedbackRelevant
  )

  final val VideoTweetFeatures: Set[USSFeature[Long]] = Set(VideoViewTweets)
  final val ImmersiveVideoTweetFeatures: Set[USSFeature[Long]] =
    Set(ImmersiveVideoViewTweets, ImmersiveWatchTimeTweets)
  final val ProducerFeatures =
    Set(AccountFollows, RepeatedProfileVisits, HighQualitySourceUser, HighQualitySourceUserV2)
  final val HighQualityTweetFeatures: Set[USSFeature[Long]] = Set(HighQualitySourceTweet)
  final val NegativeFeatures = Set(
    AccountMutes,
    AccountBlocks,
    TweetReports,
    TweetDontLikes,
    TweetFeedbackNotrelevant,
    NegativeSourceSignal
  )
  final val NegativeFeaturesTweetBased =
    Set(TweetReports, TweetDontLikes, TweetFeedbackNotrelevant)
  final val SearcherRealtimeHistoryFeatures: Set[USSFeature[SearchQuery]] =
    Set(SearcherRealtimeHistory)

  private val DefaultPriority = 3
  private def getSignalPriority(signalInfo: SignalInfo): Int = {
    signalInfo.signalType match {
      case SignalType.HighQualitySourceTweet => 4
      case SignalType.HighQualitySourceUser => 4
      case _ => 3
    }
  }

  def getPriority(signalInfos: Seq[SignalInfo]): Int = {
    if (signalInfos.isEmpty) DefaultPriority
    else signalInfos.map(getSignalPriority).max
  }

  private def getWeightedSignalPriority(signalInfo: SignalInfo): (Option[SignalType], Double) = {
    signalInfo.signalType match {
      case SignalType.TweetFavorite => (Some(SignalType.TweetFavorite), 0.2)
      case SignalType.TweetBookmarkV1 => (Some(SignalType.TweetBookmarkV1), 0.15)
      case SignalType.TweetShareV1 => (Some(SignalType.TweetShareV1), 0.15)
      case SignalType.Retweet => (Some(SignalType.Retweet), 0.15)
      case SignalType.Reply => (Some(SignalType.Reply), 0.15)
      case _ => (None, 0.2)
    }
  }

  def getWeightedPriority(
    signalInfos: Seq[SignalInfo]
  ): (Option[SignalType], Double) = {
    signalInfos
      .map(getWeightedSignalPriority).maxBy {
        case (_, weight) => weight
      }
  }

  def getSignals[T](
    query: PipelineQuery,
    features: Set[USSFeature[T]],
    filterNegative: Boolean = true,
    filterOldSignals: Boolean = false
  ): Seq[T] = {
    // Value of each feature is a map of [EntityId, Seq of Timestamps], here _._1 signifies the entityId
    val negativeSignals: Map[T, Time] = NegativeFeatures
      .flatMap(_.getValue(query))
      .map {
        case (signalId, signalInfos) =>
          (
            signalId.asInstanceOf[T],
            signalInfos.flatMap(_.sourceEventTime).foldLeft(Time.Zero)(_ max _)
          )
      }.toMap

    val positiveSignals = features.flatMap {
      _.getValue(query)
        .map {
          case (signalId, signalInfos) =>
            val mostRecentTimeStamp =
              signalInfos.flatMap(_.sourceEventTime).foldLeft(Time.Zero)(_ max _)
            (signalId, Option(signalInfos.headOption.flatMap(_.authorId)), mostRecentTimeStamp)
        }
    }

    val timeFilteredSignals = if (filterOldSignals) {
      positiveSignals.filter { case (_, _, time) => Time.now - time < MaxSignalAge }
    } else positiveSignals

    val filteredSignals = if (filterNegative) {
      timeFilteredSignals
        .filterNot {
          case (signalId, authorIdOpt, _) =>
            negativeSignals.contains(signalId) ||
              authorIdOpt.exists(authorId => negativeSignals.contains(authorId.asInstanceOf[T]))
        }
      // Filter signalId that has a negative engagement that happens later than this signalId
    } else timeFilteredSignals

    filteredSignals.toSeq
      .sortWith(_._3 > _._3) // Sort by MostRecentTimestamp
      .map(_._1) // Get only keys which are entityIds (tweet/userIds)
  }

  def getSignalsWithInfo[T](
    query: PipelineQuery,
    features: Set[USSFeature[T]]
  ): Map[T, Seq[SignalInfo]] = {
    features
      .flatMap(_.getValue(query))
      .groupBy(_._1)
      .mapValues(_.map(_._2).toSeq.flatten)
  }
}
