package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FSBoundedParam

object HighQualitySourceSignalParams {
  // Engagement Weights
  object HighQualitySourceSignalBookmarkWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_bookmark_weight",
        default = 5.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalFavWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_fav_weight",
        default = 10.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalReplyWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_reply_weight",
        default = 20.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalRetweetWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_retweet_weight",
        default = 15.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalQuoteWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_quote_weight",
        default = 15.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalShareWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_share_weight",
        default = 5.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalVideoQualityViewWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_video_quality_view_weight",
        default = 0.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalTweetDetailsClickWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_tweet_details_click_weight",
        default = 0.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalTweetDetailsImpressionWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_tweet_details_impression_weight",
        default = 0.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalNotInterestedWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_not_interested_weight",
        default = -10.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalBlockWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_block_weight",
        default = -20.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalMuteWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_mute_weight",
        default = -15.0,
        min = -100.0,
        max = 100.0,
      )

  object HighQualitySourceSignalReportWeight
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_report_weight",
        default = -20.0,
        min = -100.0,
        max = 100.0,
      )

  object EnableHighQualitySourceTweetV2
      extends FSParam[Boolean](
        name = "high_quality_source_signal_enable_high_quality_source_tweet",
        default = false
      )

  object EnableHighQualitySourceUserV2
      extends FSParam[Boolean](
        name = "high_quality_source_signal_enable_high_quality_source_user",
        default = false
      )

  object MaxHighQualitySourceSignalsV2
      extends FSBoundedParam[Int](
        name = "high_quality_source_signal_max_high_quality_source_signals",
        default = 15,
        min = 0,
        max = 50
      )

  // Decay Factor
  object EnableTimeDecay
      extends FSParam[Boolean](
        name = "high_quality_source_signal_enable_time_decay",
        default = false
      )

  object TimeDecayRate
      extends FSBoundedParam[Double](
        name = "high_quality_source_signal_time_decay_rate",
        default = 4.0,
        min = 0.0,
        max = 540.0,
      )

  // Grouped for convenience
  val boundedDoubleFSOverrides = Seq(
    HighQualitySourceSignalBookmarkWeight,
    HighQualitySourceSignalFavWeight,
    HighQualitySourceSignalReplyWeight,
    HighQualitySourceSignalRetweetWeight,
    HighQualitySourceSignalQuoteWeight,
    HighQualitySourceSignalShareWeight,
    HighQualitySourceSignalVideoQualityViewWeight,
    HighQualitySourceSignalTweetDetailsClickWeight,
    HighQualitySourceSignalTweetDetailsImpressionWeight,
    HighQualitySourceSignalNotInterestedWeight,
    HighQualitySourceSignalBlockWeight,
    HighQualitySourceSignalMuteWeight,
    HighQualitySourceSignalReportWeight,
    TimeDecayRate
  )

  val intFSOverrides = Seq(
    MaxHighQualitySourceSignalsV2
  )

  val booleanFSOverrides = Seq(
    EnableTimeDecay,
    EnableHighQualitySourceTweetV2,
    EnableHighQualitySourceUserV2
  )
}
