package com.ExTwitter.cr_mixer.param

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.DurationConversion
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.HasDurationConversion
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.util.Duration

object UtegTweetGlobalParams {

  object MaxUtegCandidatesToRequestParam
      extends FSBoundedParam[Int](
        name = "max_uteg_candidates_to_request",
        default = 800,
        min = 10,
        max = 200
      )

  object CandidateRefreshSinceTimeOffsetHoursParam
      extends FSBoundedParam[Duration](
        name = "candidate_refresh_since_time_offset_hours",
        default = 48.hours,
        min = 1.hours,
        max = 96.hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  object EnableTLRHealthFilterParam
      extends FSParam[Boolean](
        name = "enable_uteg_tlr_health_filter",
        default = true
      )

  object EnableRepliesToNonFollowedUsersFilterParam
      extends FSParam[Boolean](
        name = "enable_uteg_replies_to_non_followed_users_filter",
        default = false
      )

  object EnableRetweetFilterParam
      extends FSParam[Boolean](
        name = "enable_uteg_retweet_filter",
        default = true
      )

  object EnableInNetworkFilterParam
      extends FSParam[Boolean](
        name = "enable_uteg_in_network_filter",
        default = true
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(
      MaxUtegCandidatesToRequestParam,
      CandidateRefreshSinceTimeOffsetHoursParam,
      EnableTLRHealthFilterParam,
      EnableRepliesToNonFollowedUsersFilterParam,
      EnableRetweetFilterParam,
      EnableInNetworkFilterParam
    )

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MaxUtegCandidatesToRequestParam
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
