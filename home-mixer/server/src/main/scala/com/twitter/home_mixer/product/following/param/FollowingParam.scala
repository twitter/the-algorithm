package com.twitter.home_mixer.product.following.param

import com.twitter.conversions.DurationOps._
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.WhoToFollowModuleDisplayType
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowUserDisplayType
import com.twitter.product_mixer.core.functional_component.configapi.StaticParam
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration

object FollowingParam {
  val SupportedClientFSName = "following_supported_client"
  val StaticParamValueZero = StaticParam(0)
  val StaticParamValueFive = StaticParam(5)

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

  object EnableFlipInjectionModuleCandidatePipelineParam
      extends FSParam[Boolean](
        name = "following_enable_flip_inline_injection_module",
        default = true
      )

  object EnablePostContextFeatureHydratorParam
      extends FSParam[Boolean](
        name = "following_enable_post_context_feature_hydrator",
        default = false
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

  object WhoToFollowUserDisplayTypeIdParam
      extends FSEnumParam[WhoToFollowUserDisplayType.type](
        name = "following_enable_who_to_follow_user_display_type_id",
        default = WhoToFollowUserDisplayType.User,
        enum = WhoToFollowUserDisplayType
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

  object EnableDependentAdsParam
      extends FSParam[Boolean](
        name = "following_enable_dependent_ads",
        default = true
      )

  object EnableNavigationInstructionParam
      extends FSParam[Boolean](
        name = "following_enable_navigation_instruction",
        default = false
      )

  object ClearCache {
    object PtrEnableParam
        extends FSParam[Boolean](
          name = "following_clear_cache_ptr_enable",
          default = false
        )

    object ColdStartEnableParam
        extends FSParam[Boolean](
          name = "following_clear_cache_cold_start_enable",
          default = false
        )

    object WarmStartEnableParam
        extends FSParam[Boolean](
          name = "following_clear_cache_warm_start_enable",
          default = false
        )

    object ManualRefreshEnableParam
        extends FSParam[Boolean](
          name = "following_clear_cache_manual_refresh_enable",
          default = false
        )

    object NavigateEnableParam
        extends FSParam[Boolean](
          name = "following_clear_cache_navigate_enable",
          default = false
        )

    case object MinEntriesParam
        extends FSBoundedParam[Int](
          name = "following_clear_cache_min_entries",
          default = 10,
          min = 0,
          max = 35
        )
  }

  object Navigation {
    object PtrEnableParam
        extends FSParam[Boolean](
          name = "following_navigation_ptr_enable",
          default = false
        )

    object ColdStartEnableParam
        extends FSParam[Boolean](
          name = "following_navigation_cold_start_enable",
          default = false
        )

    object WarmStartEnableParam
        extends FSParam[Boolean](
          name = "following_navigation_warm_start_enable",
          default = false
        )

    object ManualRefreshEnableParam
        extends FSParam[Boolean](
          name = "following_navigation_manual_refresh_enable",
          default = false
        )

    object NavigateEnableParam
        extends FSParam[Boolean](
          name = "following_navigation_navigate_enable",
          default = false
        )
  }
}
