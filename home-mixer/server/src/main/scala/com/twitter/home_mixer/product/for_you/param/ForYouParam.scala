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

  object EnableTopicSocialContextFilterParam
      extends FSParam[Boolean](
        name = "for_you_enable_topic_social_context_filter",
        default = true
      )

  object EnableVerifiedAuthorSocialContextBypassParam
      extends FSParam[Boolean](
        name = "for_you_enable_verified_author_social_context_bypass",
        default = true
      )

  object EnableTimelineScorerCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_timeline_scorer_candidate_pipeline",
        default = false
      )

  object EnableScoredTweetsCandidatePipelineParam
      extends BooleanDeciderParam(DeciderKey.EnableForYouScoredTweetsCandidatePipeline)

  object EnableWhoToFollowCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_who_to_follow",
        default = true
      )

  object EnableWhoToSubscribeCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_who_to_subscribe",
        default = true
      )

  object EnableTweetPreviewsCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_tweet_previews_candidate_pipeline",
        default = true
      )

  object EnablePushToHomeMixerPipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_push_to_home_mixer_pipeline",
        default = false
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

  object WhoToFollowDisplayLocationParam
      extends FSParam[String](
        name = "for_you_who_to_follow_display_location",
        default = "timeline"
      )

  object WhoToSubscribePositionParam
      extends FSBoundedParam[Int](
        name = "for_you_who_to_subscribe_position",
        default = 7,
        min = 0,
        max = 99
      )

  object WhoToSubscribeMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_who_to_subscribe_min_injection_interval_in_minutes",
        default = 1800.minutes,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object WhoToSubscribeDisplayTypeIdParam
      extends FSEnumParam[WhoToFollowModuleDisplayType.type](
        name = "for_you_enable_who_to_subscribe_display_type_id",
        default = WhoToFollowModuleDisplayType.Vertical,
        enum = WhoToFollowModuleDisplayType
      )

  object TweetPreviewsPositionParam
      extends FSBoundedParam[Int](
        name = "for_you_tweet_previews_position",
        default = 3,
        min = 0,
        max = 99
      )

  object TweetPreviewsMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_tweet_previews_min_injection_interval_in_minutes",
        default = 2.hours,
        min = 0.minutes,
        max = 600.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object TweetPreviewsMaxCandidatesParam
      extends FSBoundedParam[Int](
        name = "for_you_tweet_previews_max_candidates",
        default = 1,
        min = 0,
        // NOTE: previews are injected at a fixed position, so max candidates = 1
        // to avoid bunching of previews.
        max = 1
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

  object EnableClearCacheOnPushToHome
      extends FSParam[Boolean](
        name = "for_you_enable_clear_cache_push_to_home",
        default = false
      )

  object EnableServedCandidateKafkaPublishingParam
      extends FSParam[Boolean](
        name = "for_you_enable_served_candidate_kafka_publishing",
        default = true
      )

  object ExperimentStatsParam
      extends FSParam[String](
        name = "for_you_experiment_stats",
        default = ""
      )
}
