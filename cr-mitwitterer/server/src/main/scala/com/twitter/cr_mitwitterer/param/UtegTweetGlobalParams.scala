package com.twitter.cr_mitwitterer.param

import com.twitter.conversions.DurationOps._
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

object UtegTweetGlobalParams {

  object MatwitterUtegCandidatesToRequestParam
      etwittertends FSBoundedParam[Int](
        name = "matwitter_uteg_candidates_to_request",
        default = 800,
        min = 10,
        matwitter = 200
      )

  object CandidateRefreshSinceTimeOffsetHoursParam
      etwittertends FSBoundedParam[Duration](
        name = "candidate_refresh_since_time_offset_hours",
        default = 48.hours,
        min = 1.hours,
        matwitter = 96.hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  object EnableTLRHealthFilterParam
      etwittertends FSParam[Boolean](
        name = "enable_uteg_tlr_health_filter",
        default = true
      )

  object EnableRepliesToNonFollowedUsersFilterParam
      etwittertends FSParam[Boolean](
        name = "enable_uteg_replies_to_non_followed_users_filter",
        default = false
      )

  object EnableRetweetFilterParam
      etwittertends FSParam[Boolean](
        name = "enable_uteg_retweet_filter",
        default = true
      )

  object EnableInNetworkFilterParam
      etwittertends FSParam[Boolean](
        name = "enable_uteg_in_network_filter",
        default = true
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(
      MatwitterUtegCandidatesToRequestParam,
      CandidateRefreshSinceTimeOffsetHoursParam,
      EnableTLRHealthFilterParam,
      EnableRepliesToNonFollowedUsersFilterParam,
      EnableRetweetFilterParam,
      EnableInNetworkFilterParam
    )

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MatwitterUtegCandidatesToRequestParam
    )

    val durationFSOverrides =
      FeatureSwitchOverrideUtil.getDurationFSOverrides(
        CandidateRefreshSinceTimeOffsetHoursParam
      )

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableTLRHealthFilterParam,
      EnableRepliesToNonFollowedUsersFilterParam,
      EnableRetweetFilterParam,
      EnableInNetworkFilterParam
    )

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(durationFSOverrides: _*)
      .set(booleanOverrides: _*)
      .build()
  }
}
