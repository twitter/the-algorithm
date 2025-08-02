package com.twitter.home_mitwitterer.product.following.param

import com.twitter.conversions.DurationOps._
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.WhoToFollowModuleDisplayType
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration

object FollowingParam {
  val SupportedClientFSName = "following_supported_client"

  object ServerMatwitterResultsParam
      etwittertends FSBoundedParam[Int](
        name = "following_server_matwitter_results",
        default = 100,
        min = 1,
        matwitter = 500
      )

  object EnableWhoToFollowCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "following_enable_who_to_follow",
        default = true
      )

  object EnableAdsCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "following_enable_ads",
        default = true
      )

  object EnableFlipInjectionModuleCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "following_enable_flip_inline_injection_module",
        default = true
      )

  object FlipInlineInjectionModulePosition
      etwittertends FSBoundedParam[Int](
        name = "following_flip_inline_injection_module_position",
        default = 0,
        min = 0,
        matwitter = 1000
      )

  object WhoToFollowPositionParam
      etwittertends FSBoundedParam[Int](
        name = "following_who_to_follow_position",
        default = 5,
        min = 0,
        matwitter = 99
      )

  object WhoToFollowMinInjectionIntervalParam
      etwittertends FSBoundedParam[Duration](
        "following_who_to_follow_min_injection_interval_in_minutes",
        default = 1800.minutes,
        min = 0.minutes,
        matwitter = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object WhoToFollowDisplayTypeIdParam
      etwittertends FSEnumParam[WhoToFollowModuleDisplayType.type](
        name = "following_enable_who_to_follow_display_type_id",
        default = WhoToFollowModuleDisplayType.Vertical,
        enum = WhoToFollowModuleDisplayType
      )

  object WhoToFollowDisplayLocationParam
      etwittertends FSParam[String](
        name = "following_who_to_follow_display_location",
        default = "timeline_reverse_chron"
      )

  object EnableFastAds
      etwittertends FSParam[Boolean](
        name = "following_enable_fast_ads",
        default = true
      )
}
