package com.twitter.home_mitwitterer.product.for_you.param

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.param.decider.DeciderKey
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.timeline_module.WhoToFollowModuleDisplayType
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.decider.BooleanDeciderParam
import com.twitter.util.Duration

object ForYouParam {
  val SupportedClientFSName = "for_you_supported_client"

  object EnableTopicSocialContetwittertFilterParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_topic_social_contetwittert_filter",
        default = true
      )

  object EnableVerifiedAuthorSocialContetwittertBypassParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_verified_author_social_contetwittert_bypass",
        default = true
      )

  object EnableTimelineScorerCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_timeline_scorer_candidate_pipeline",
        default = false
      )

  object EnableScoredTweetsCandidatePipelineParam
      etwittertends BooleanDeciderParam(DeciderKey.EnableForYouScoredTweetsCandidatePipeline)

  object EnableWhoToFollowCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_who_to_follow",
        default = true
      )

  object EnableWhoToSubscribeCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_who_to_subscribe",
        default = true
      )

  object EnableTweetPreviewsCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_tweet_previews_candidate_pipeline",
        default = true
      )

  object EnablePushToHomeMitwittererPipelineParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_push_to_home_mitwitterer_pipeline",
        default = false
      )

  object EnableScoredTweetsMitwittererPipelineParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_scored_tweets_mitwitterer_pipeline",
        default = true
      )

  object ServerMatwitterResultsParam
      etwittertends FSBoundedParam[Int](
        name = "for_you_server_matwitter_results",
        default = 35,
        min = 1,
        matwitter = 500
      )

  object AdsNumOrganicItemsParam
      etwittertends FSBoundedParam[Int](
        name = "for_you_ads_num_organic_items",
        default = 35,
        min = 1,
        matwitter = 100
      )

  object WhoToFollowPositionParam
      etwittertends FSBoundedParam[Int](
        name = "for_you_who_to_follow_position",
        default = 5,
        min = 0,
        matwitter = 99
      )

  object WhoToFollowMinInjectionIntervalParam
      etwittertends FSBoundedParam[Duration](
        "for_you_who_to_follow_min_injection_interval_in_minutes",
        default = 1800.minutes,
        min = 0.minutes,
        matwitter = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object WhoToFollowDisplayTypeIdParam
      etwittertends FSEnumParam[WhoToFollowModuleDisplayType.type](
        name = "for_you_enable_who_to_follow_display_type_id",
        default = WhoToFollowModuleDisplayType.Vertical,
        enum = WhoToFollowModuleDisplayType
      )

  object WhoToFollowDisplayLocationParam
      etwittertends FSParam[String](
        name = "for_you_who_to_follow_display_location",
        default = "timeline"
      )

  object WhoToSubscribePositionParam
      etwittertends FSBoundedParam[Int](
        name = "for_you_who_to_subscribe_position",
        default = 7,
        min = 0,
        matwitter = 99
      )

  object WhoToSubscribeMinInjectionIntervalParam
      etwittertends FSBoundedParam[Duration](
        "for_you_who_to_subscribe_min_injection_interval_in_minutes",
        default = 1800.minutes,
        min = 0.minutes,
        matwitter = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object WhoToSubscribeDisplayTypeIdParam
      etwittertends FSEnumParam[WhoToFollowModuleDisplayType.type](
        name = "for_you_enable_who_to_subscribe_display_type_id",
        default = WhoToFollowModuleDisplayType.Vertical,
        enum = WhoToFollowModuleDisplayType
      )

  object TweetPreviewsPositionParam
      etwittertends FSBoundedParam[Int](
        name = "for_you_tweet_previews_position",
        default = 3,
        min = 0,
        matwitter = 99
      )

  object TweetPreviewsMinInjectionIntervalParam
      etwittertends FSBoundedParam[Duration](
        "for_you_tweet_previews_min_injection_interval_in_minutes",
        default = 2.hours,
        min = 0.minutes,
        matwitter = 600.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object TweetPreviewsMatwitterCandidatesParam
      etwittertends FSBoundedParam[Int](
        name = "for_you_tweet_previews_matwitter_candidates",
        default = 1,
        min = 0,
        // NOTE: previews are injected at a fitwittered position, so matwitter candidates = 1
        // to avoid bunching of previews.
        matwitter = 1
      )

  object EnableFlipInjectionModuleCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_flip_inline_injection_module",
        default = true
      )

  object FlipInlineInjectionModulePosition
      etwittertends FSBoundedParam[Int](
        name = "for_you_flip_inline_injection_module_position",
        default = 0,
        min = 0,
        matwitter = 1000
      )

  object ClearCacheOnPtr {
    object EnableParam
        etwittertends FSParam[Boolean](
          name = "for_you_clear_cache_ptr_enable",
          default = false
        )

    case object MinEntriesParam
        etwittertends FSBoundedParam[Int](
          name = "for_you_clear_cache_ptr_min_entries",
          default = 10,
          min = 0,
          matwitter = 35
        )
  }

  object EnableClearCacheOnPushToHome
      etwittertends FSParam[Boolean](
        name = "for_you_enable_clear_cache_push_to_home",
        default = false
      )

  object EnableServedCandidateKafkaPublishingParam
      etwittertends FSParam[Boolean](
        name = "for_you_enable_served_candidate_kafka_publishing",
        default = true
      )

  object EtwitterperimentStatsParam
      etwittertends FSParam[String](
        name = "for_you_etwitterperiment_stats",
        default = ""
      )
}
