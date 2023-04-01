package com.twitter.home_mixer.product.following.param

import com.twitter.conversions.DurationOps._
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.WhoToFollowModuleDisplayType
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration

object FollowingParam {
  val SupportedClientFSName = "following_supported_client"

  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "following_server_max_results",
        default = 100,
        min = 1,
        max = 500
      )

  object EnableWhoToFollowCandidatePipelineParam
      extends FSParam[Boolean](
        name = "following_enable_who_to_follow",
        default = true
      )

  object EnableAdsCandidatePipelineParam
      extends FSParam[Boolean](
        name = "following_enable_ads",
        default = true
      )

  object EnableFlipInjectionModuleCandidatePipelineParam
      extends FSParam[Boolean](
        name = "following_enable_flip_inline_injection_module",
        default = true
      )

  object FlipInlineInjectionModulePosition
      extends FSBoundedParam[Int](
        name = "following_flip_inline_injection_module_position",
        default = 0,
        min = 0,
        max = 1000
      )

  object WhoToFollowPositionParam
      extends FSBoundedParam[Int](
        name = "following_who_to_follow_position",
        default = 5,
        min = 0,
        max = 99
      )

  object WhoToFollowMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "following_who_to_follow_min_injection_interval_in_minutes",
        default = 1800.minutes,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object WhoToFollowDisplayTypeIdParam
      extends FSEnumParam[WhoToFollowModuleDisplayType.type](
        name = "following_enable_who_to_follow_display_type_id",
        default = WhoToFollowModuleDisplayType.Vertical,
        enum = WhoToFollowModuleDisplayType
      )

  object WhoToFollowDisplayLocationParam
      extends FSParam[String](
        name = "following_who_to_follow_display_location",
        default = "timeline_reverse_chron"
      )

  object EnableFastAds
      extends FSParam[Boolean](
        name = "following_enable_fast_ads",
        default = true
      )
}
