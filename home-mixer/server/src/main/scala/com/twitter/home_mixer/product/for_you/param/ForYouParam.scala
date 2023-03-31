package com.twitter.home_mixer.product.for_you.param

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.WhoToFollowModuleDisplayType
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.decider.BooleanDeciderParam
import com.twitter.util.Duration

object ForYouParam {
  val SupportedClientFSName = "for_you_supported_client"

  object EnableTimelineScorerCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_timeline_scorer_candidate_pipeline",
        default = true
      )

  object EnableScoredTweetsCandidatePipelineParam
      extends BooleanDeciderParam(DeciderKey.EnableForYouScoredTweetsCandidatePipeline)

  object EnableWhoToFollowCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_who_to_follow",
        default = true
      )

  object EnableScoredTweetsMixerPipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_scored_tweets_mixer_pipeline",
        default = true
      )

  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "for_you_server_max_results",
        default = 35,
        min = 1,
        max = 500
      )

  object TimelineServiceMaxResultsParam
      extends FSBoundedParam[Int](
        name = "for_you_timeline_service_max_results",
        default = 800,
        min = 1,
        max = 800
      )

  object AdsNumOrganicItemsParam
      extends FSBoundedParam[Int](
        name = "for_you_ads_num_organic_items",
        default = 35,
        min = 1,
        max = 100
      )

  object WhoToFollowPositionParam
      extends FSBoundedParam[Int](
        name = "for_you_who_to_follow_position",
        default = 5,
        min = 0,
        max = 99
      )

  object WhoToFollowMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_who_to_follow_min_injection_interval_in_minutes",
        default = 1800.minutes,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object WhoToFollowDisplayTypeIdParam
      extends FSEnumParam[WhoToFollowModuleDisplayType.type](
        name = "for_you_enable_who_to_follow_display_type_id",
        default = WhoToFollowModuleDisplayType.Vertical,
        enum = WhoToFollowModuleDisplayType
      )

  object EnableFlipInjectionModuleCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_flip_inline_injection_module",
        default = true
      )

  object FlipInlineInjectionModulePosition
      extends FSBoundedParam[Int](
        name = "for_you_flip_inline_injection_module_position",
        default = 0,
        min = 0,
        max = 1000
      )

  object ClearCacheOnPtr {

    object EnableParam
        extends FSParam[Boolean](
          name = "for_you_clear_cache_ptr_enable",
          default = false
        )

    case object MinEntriesParam
        extends FSBoundedParam[Int](
          name = "for_you_clear_cache_ptr_min_entries",
          default = 10,
          min = 0,
          max = 35
        )
  }
}
