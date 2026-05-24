package com.twitter.tweet_mixer.param

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.usersignalservice.thriftscala.SignalType
import com.twitter.util.Duration

/**
 * USS Store Related Params
 */
object USSParams {

  /**
   * Enabling Signals Params
   */
  object EnableRecentTweetFavorites
      extends FSParam[Boolean](
        name = "uss_enable_recent_tweet_favorites",
        default = false
      )

  object EnableRecentRetweets
      extends FSParam[Boolean](
        name = "uss_enable_recent_retweets",
        default = false
      )

  object EnableRecentReplies
      extends FSParam[Boolean](
        name = "uss_enable_recent_replies",
        default = false
      )

  object EnableRecentTweetBookmarks
      extends FSParam[Boolean](
        name = "uss_enable_recent_tweet_bookmarks",
        default = false
      )

  object EnableRecentTweetFeedbackRelevant
      extends FSParam[Boolean](
        name = "uss_enable_recent_tweet_feedback_relevant",
        default = false
      )

  object EnableRecentTweetFeedbackNotrelevant
      extends FSParam[Boolean](
        name = "uss_enable_recent_tweet_feedback_notrelevant",
        default = false
      )

  object EnableRecentOriginalTweets
      extends FSParam[Boolean](
        name = "uss_enable_recent_original_tweets",
        default = false
      )

  object EnableRecentFollows
      extends FSParam[Boolean](
        name = "uss_enable_recent_follows",
        default = false
      )

  object EnableRepeatedProfileVisits
      extends FSParam[Boolean](
        name = "uss_enable_repeated_profile_visits",
        default = false
      )

  object EnableRecentNotifications
      extends FSParam[Boolean](
        name = "uss_enable_recent_notifications",
        default = false
      )

  object EnableTweetShares
      extends FSParam[Boolean](
        name = "uss_enable_tweet_shares",
        default = false
      )

  object EnableTweetDetailGoodClick1Min
      extends FSParam[Boolean](
        name = "uss_enable_tweet_detail_good_click_1_min",
        default = false
      )

  object EnableVideoViewTweets // This is the cumulative of the bottom 3 signals + home
      extends FSParam[Boolean](
        name = "uss_enable_video_view_tweets",
        default = false
      )

  object EnableVideoViewVisibilityFilteredTweets
      extends FSParam[Boolean](
        name = "uss_enable_video_view_visibility_filtered_tweets",
        default = false
      )

  object EnableVideoViewVisibility75FilteredTweets
      extends FSParam[Boolean](
        name = "uss_enable_video_view_visibility_75_filtered_tweets",
        default = false
      )

  object EnableVideoViewVisibility100FilteredTweets
      extends FSParam[Boolean](
        name = "uss_enable_video_view_visibility_100_filtered_tweets",
        default = false
      )

  object EnableVideoViewHighResolutionFilteredTweets
      extends FSParam[Boolean](
        name = "uss_enable_video_view_high_resolution_filtered_tweets",
        default = false
      )

  object EnableImmersiveVideoViewTweets
      extends FSParam[Boolean](
        name = "uss_enable_immersive_video_view_tweets",
        default = false
      )

  object EnableMediaImmersiveVideoViewTweets
      extends FSParam[Boolean](
        name = "uss_enable_media_immersive_video_view_tweets",
        default = false
      )

  object EnableTvVideoViewTweets
      extends FSParam[Boolean](
        name = "uss_enable_tv_video_view_tweets",
        default = false
      )

  object EnableWatchTimeTweets // This is cumulative of the below 3 signals
      extends FSParam[Boolean](
        name = "uss_enable_watch_time_tweets",
        default = false
      )

  object EnableImmersiveWatchTimeTweets
      extends FSParam[Boolean](
        name = "uss_enable_immersive_watch_time_tweets",
        default = false
      )

  object EnableMediaImmersiveWatchTimeTweets
      extends FSParam[Boolean](
        name = "uss_enable_media_immersive_watch_time_tweets",
        default = false
      )

  object EnableTvWatchTimeTweets
      extends FSParam[Boolean](
        name = "uss_enable_tv_watch_time_tweets",
        default = false
      )

  object EnableSearcherRealtimeHistory
      extends FSParam[Boolean](
        name = "uss_enable_searcher_realtime_history",
        default = false
      )

  object EnableNegativeSignals
      extends FSParam[Boolean](
        name = "uss_enable_negative_signals",
        default = false
      )

  object EnableHighQualitySourceTweet
      extends FSParam[Boolean](
        name = "uss_enable_high_quality_source_tweet",
        default = false
      )

  object EnableHighQualitySourceUser
      extends FSParam[Boolean](
        name = "uss_enable_high_quality_source_user",
        default = false
      )

  object EnableTweetPhotoExpand
      extends FSParam[Boolean](
        name = "uss_enable_tweet_photo_expand",
        default = false
      )

  object EnableSearchTweetClick
      extends FSParam[Boolean](
        name = "uss_enable_search_tweet_click",
        default = false
      )

  object EnableProfileTweetClick
      extends FSParam[Boolean](
        name = "uss_enable_profile_tweet_click",
        default = false
      )

  object EnableTweetVideoOpen
      extends FSParam[Boolean](
        name = "uss_enable_tweet_video_open",
        default = false
      )

  /**
   * Unified params for all types of signals to fetch
   */
  object UnifiedMaxSourceKeyNum
      extends FSBoundedParam[Int](
        name = "uss_unified_max_source_key_num",
        default = 15,
        min = 0,
        max = 100
      )

  object UnifiedMinSignalFavCount
      extends FSBoundedParam[Int](
        name = "uss_unified_min_signal_fav_count",
        default = 10,
        min = 0,
        max = Int.MaxValue
      )

  object UnifiedMaxSignalFavCount
      extends FSBoundedParam[Int](
        name = "uss_unified_max_signal_fav_count",
        default = Int.MaxValue,
        min = 0,
        max = Int.MaxValue
      )

  object UnifiedMaxSignalAgeInHours
      extends FSBoundedParam[Int](
        name = "uss_unified_max_signal_age_in_hours",
        default = Int.MaxValue,
        min = 0,
        max = Int.MaxValue
      )

  object VQVMaxSignalAgeInDays
      extends FSBoundedParam[Int](
        name = "uss_vqv_max_signal_age_in_days",
        default = Int.MaxValue,
        min = 0,
        max = Int.MaxValue
      )

  object LowSignalUserMaxSignalCount
      extends FSBoundedParam[Int](
        name = "uss_low_signal_user_max_signal_count",
        default = 15,
        min = 0,
        max = Int.MaxValue
      )

  object LowSignalUserMaxSignalAge
      extends FSBoundedParam[Duration](
        "uss_low_signal_user_max_signal_age_in_days",
        default = 90.days,
        min = 1.days,
        max = 1000.days)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromDays
  }

  object EnableNegativeSentimentSignalFilter
      extends FSParam[Boolean](
        "uss_negative_sentiment_signal_filter",
        default = false
      )

  object EnableNegativeSourceSignal
      extends FSParam[Boolean](
        "uss_enable_negative_source_signal",
        default = false
      )

  object MaxNegativeSourceSignals
      extends FSBoundedParam[Int](
        name = "uss_max_negative_source_signals",
        default = 15,
        min = 0,
        max = 200
      )

  object EnableNegativeSourceSignalBucketFilter
      extends FSParam[Boolean](
        name = "uss_enable_negative_source_signal_bucket_filter",
        default = false
      )

  object NegativeSourceSignalEligible
      extends FSBoundedParam[Int](
        name = "uss_negative_source_signal_eligible",
        default = 2,
        min = 0,
        max = 100
      )

  object MaxHighQualitySourceSignals
      extends FSBoundedParam[Int](
        name = "uss_max_high_quality_source_signals",
        default = 15,
        min = 0,
        max = 50
      )

  object EnableHighQualitySourceSignalBucketFilter
      extends FSParam[Boolean](
        name = "uss_enable_high_quality_source_signal_bucket_filter",
        default = false
      )

  object HighQualitySourceTweetEligible
      extends FSBoundedParam[Int](
        name = "uss_high_quality_source_tweet_eligible",
        default = 0,
        min = 0,
        max = 100
      )

  object HighQualitySourceUserEligible
      extends FSBoundedParam[Int](
        name = "uss_high_quality_source_user_eligible",
        default = 0,
        min = 0,
        max = 100
      )

  object MaxVideoViewSourceSignals
      extends FSBoundedParam[Int](
        name = "uss_max_video_view_source_signals",
        default = 30,
        min = 0,
        max = 100
      )

  object MaxFavSignals
      extends FSBoundedParam[Int](
        name = "uss_max_fav_signals",
        default = 15,
        min = 0,
        max = 50
      )

  object MaxBookmarkSignals
      extends FSBoundedParam[Int](
        name = "uss_max_bookmark_signals",
        default = 15,
        min = 0,
        max = 50
      )

  object MaxVqvSignals
      extends FSBoundedParam[Int](
        name = "uss_max_vqv_signals",
        default = 15,
        min = 0,
        max = 50
      )

  object MaxPhotoExpandSignals
      extends FSBoundedParam[Int](
        name = "uss_max_photo_expand_signals",
        default = 15,
        min = 0,
        max = 50
      )

  object MaxSearchTweetClick
      extends FSBoundedParam[Int](
        name = "uss_max_search_tweet_click_signals",
        default = 0,
        min = 0,
        max = 50
      )

  object MaxProfileTweetClick
      extends FSBoundedParam[Int](
        name = "uss_max_profile_tweet_click_signals",
        default = 0,
        min = 0,
        max = 50
      )

  object MaxTweetVideoOpen
      extends FSBoundedParam[Int](
        name = "uss_max_tweet_video_open_signals",
        default = 0,
        min = 0,
        max = 50
      )

  /**
   * Type of signals to fetch related params
   */
  object ProfileMinVisitEnum extends Enumeration {

    val TotalVisitsInPast180Days = Value
    val TotalVisitsInPast90Days = Value
    val TotalVisitsInPast14Days = Value
    val TotalVisitsInPast180DaysNoNegative = Value
    val TotalVisitsInPast90DaysNoNegative = Value
    val TotalVisitsInPast14DaysNoNegative = Value
  }

  object VideoViewTweetTypeEnum extends Enumeration {
    val VideoTweetQualityView = Value
    val VideoTweetPlayback50 = Value
    val VideoTweetQualityViewAllSurfaces = Value
    val VideoTweetQualityViewV2 = Value
    val VideoTweetQualityViewV2Visibility75 = Value
    val VideoTweetQualityViewV2Visibility100 = Value
    val VideoTweetQualityViewV3 = Value
  }

  object ProfileMinVisitType
      extends FSEnumParam[ProfileMinVisitEnum.type](
        name = "uss_profile_min_visit_type_id",
        default = ProfileMinVisitEnum.TotalVisitsInPast180Days,
        enum = ProfileMinVisitEnum
      )

  object VideoViewTweetTypeParam
      extends FSEnumParam[VideoViewTweetTypeEnum.type](
        name = "uss_video_view_tweet_type_id",
        default = VideoViewTweetTypeEnum.VideoTweetQualityView,
        enum = VideoViewTweetTypeEnum
      )

  object VideoViewVisibilityFilteredTweetTypeParam
      extends FSEnumParam[VideoViewTweetTypeEnum.type](
        name = "uss_video_view_visibility_filtered_tweet_type_id",
        default = VideoViewTweetTypeEnum.VideoTweetQualityViewV2,
        enum = VideoViewTweetTypeEnum
      )

  object VideoViewVisibility75FilteredTweetTypeParam
      extends FSEnumParam[VideoViewTweetTypeEnum.type](
        name = "uss_video_view_visibility_75_filtered_tweet_type_id",
        default = VideoViewTweetTypeEnum.VideoTweetQualityViewV2Visibility75,
        enum = VideoViewTweetTypeEnum
      )

  object VideoViewVisibility100FilteredTweetTypeParam
      extends FSEnumParam[VideoViewTweetTypeEnum.type](
        name = "uss_video_view_visibility_100_filtered_tweet_type_id",
        default = VideoViewTweetTypeEnum.VideoTweetQualityViewV2Visibility100,
        enum = VideoViewTweetTypeEnum
      )

  object VideoViewHighResolutionFilteredTweetTypeParam
      extends FSEnumParam[VideoViewTweetTypeEnum.type](
        name = "uss_video_view_high_resolution_filtered_tweet_type_id",
        default = VideoViewTweetTypeEnum.VideoTweetQualityViewV3,
        enum = VideoViewTweetTypeEnum
      )

  def profileMinVisitParam(profileMinVisitEnum: ProfileMinVisitEnum.Value): SignalType = {
    profileMinVisitEnum match {
      case ProfileMinVisitEnum.TotalVisitsInPast180Days =>
        SignalType.RepeatedProfileVisit180dMinVisit6V1
      case ProfileMinVisitEnum.TotalVisitsInPast90Days =>
        SignalType.RepeatedProfileVisit90dMinVisit6V1
      case ProfileMinVisitEnum.TotalVisitsInPast14Days =>
        SignalType.RepeatedProfileVisit14dMinVisit2V1
      case ProfileMinVisitEnum.TotalVisitsInPast180DaysNoNegative =>
        SignalType.RepeatedProfileVisit180dMinVisit6V1NoNegative
      case ProfileMinVisitEnum.TotalVisitsInPast90DaysNoNegative =>
        SignalType.RepeatedProfileVisit90dMinVisit6V1NoNegative
      case ProfileMinVisitEnum.TotalVisitsInPast14DaysNoNegative =>
        SignalType.RepeatedProfileVisit14dMinVisit2V1NoNegative
    }
  }

  def videoViewTweetTypeParam(videoViewTweetTypeEnum: VideoViewTweetTypeEnum.Value): SignalType = {
    videoViewTweetTypeEnum match {
      case VideoViewTweetTypeEnum.VideoTweetQualityView => SignalType.VideoView90dQualityV1
      case VideoViewTweetTypeEnum.VideoTweetPlayback50 => SignalType.VideoView90dPlayback50V1
      case VideoViewTweetTypeEnum.VideoTweetQualityViewAllSurfaces =>
        SignalType.VideoView90dQualityV1AllSurfaces
      case VideoViewTweetTypeEnum.VideoTweetQualityViewV2 => SignalType.VideoView90dQualityV2
      case VideoViewTweetTypeEnum.VideoTweetQualityViewV2Visibility75 =>
        SignalType.VideoView90dQualityV2Visibility75
      case VideoViewTweetTypeEnum.VideoTweetQualityViewV2Visibility100 =>
        SignalType.VideoView90dQualityV2Visibility100
      case VideoViewTweetTypeEnum.VideoTweetQualityViewV3 => SignalType.VideoView90dQualityV3
    }
  }

  val booleanFSOverrides =
    Seq(
      EnableRecentTweetFavorites,
      EnableRecentRetweets,
      EnableRecentReplies,
      EnableRecentTweetBookmarks,
      EnableRecentTweetFeedbackRelevant,
      EnableRecentTweetFeedbackNotrelevant,
      EnableRecentOriginalTweets,
      EnableRecentFollows,
      EnableRepeatedProfileVisits,
      EnableTweetShares,
      EnableTweetPhotoExpand,
      EnableSearchTweetClick,
      EnableProfileTweetClick,
      EnableTweetVideoOpen,
      EnableTweetDetailGoodClick1Min,
      EnableVideoViewTweets,
      EnableVideoViewVisibilityFilteredTweets,
      EnableVideoViewVisibility75FilteredTweets,
      EnableVideoViewVisibility100FilteredTweets,
      EnableVideoViewHighResolutionFilteredTweets,
      EnableImmersiveVideoViewTweets,
      EnableMediaImmersiveVideoViewTweets,
      EnableTvVideoViewTweets,
      EnableWatchTimeTweets,
      EnableImmersiveWatchTimeTweets,
      EnableMediaImmersiveWatchTimeTweets,
      EnableTvWatchTimeTweets,
      EnableNegativeSignals,
      EnableRecentNotifications,
      EnableSearcherRealtimeHistory,
      EnableNegativeSentimentSignalFilter,
      EnableNegativeSourceSignal,
      EnableNegativeSourceSignalBucketFilter,
      EnableHighQualitySourceTweet,
      EnableHighQualitySourceUser,
      EnableHighQualitySourceSignalBucketFilter,
    )

  val boundedIntFSOverrides =
    Seq(
      UnifiedMaxSourceKeyNum,
      UnifiedMinSignalFavCount,
      UnifiedMaxSignalFavCount,
      UnifiedMaxSignalAgeInHours,
      LowSignalUserMaxSignalCount,
      VQVMaxSignalAgeInDays,
      MaxNegativeSourceSignals,
      NegativeSourceSignalEligible,
      MaxHighQualitySourceSignals,
      HighQualitySourceTweetEligible,
      HighQualitySourceUserEligible,
      MaxVideoViewSourceSignals,
      MaxFavSignals,
      MaxBookmarkSignals,
      MaxVqvSignals,
      MaxPhotoExpandSignals,
      MaxSearchTweetClick,
      MaxProfileTweetClick,
      MaxTweetVideoOpen,
    )

  val enumFSOverrides =
    Seq(
      ProfileMinVisitType,
      VideoViewTweetTypeParam,
      VideoViewVisibilityFilteredTweetTypeParam,
      VideoViewVisibility75FilteredTweetTypeParam,
      VideoViewVisibility100FilteredTweetTypeParam,
      VideoViewHighResolutionFilteredTweetTypeParam
    )

  val boundedDurationFSOverrides =
    Seq(
      LowSignalUserMaxSignalAge
    )
}
