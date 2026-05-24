package com.twitter.home_mixer.product.for_you.param

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.product_mixer.component_library.decorator.urt.builder.timeline_module.WhoToFollowModuleDisplayType
import com.twitter.product_mixer.component_library.pipeline.candidate.communities_to_join.CommunityToJoinModuleDisplayType
import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.WhoToFollowUserDisplayType
import com.twitter.product_mixer.core.functional_component.configapi.StaticParam
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.decider.BooleanDeciderParam
import com.twitter.util.Duration

object ForYouParam {
  val SupportedClientFSName = "for_you_supported_client"
  val StaticParamValueZero = StaticParam(0)
  val StaticParamValueFive = StaticParam(5)

  object EnableWhoToFollowCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_who_to_follow",
        default = true
      )

  object EnableWhoToSubscribeCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_who_to_subscribe",
        default = false
      )

  object EnableTweetPreviewsCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_tweet_previews_candidate_pipeline",
        default = false
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

  object WhoToFollowUserDisplayTypeIdParam
      extends FSEnumParam[WhoToFollowUserDisplayType.type](
        name = "for_you_enable_who_to_follow_user_display_type_id",
        default = WhoToFollowUserDisplayType.User,
        enum = WhoToFollowUserDisplayType
      )

  object WhoToFollowDisplayLocationParam
      extends FSParam[String](
        name = "for_you_who_to_follow_display_location",
        default = "timeline"
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
        max = 1
      )

  object EnableFlipInjectionModuleCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_flip_inline_injection_module",
        default = true
      )

  object ClearCache {
    object PtrEnableParam
        extends FSParam[Boolean](
          name = "for_you_clear_cache_ptr_enable",
          default = false
        )

    object ColdStartEnableParam
        extends FSParam[Boolean](
          name = "for_you_clear_cache_cold_start_enable",
          default = false
        )

    object WarmStartEnableParam
        extends FSParam[Boolean](
          name = "for_you_clear_cache_warm_start_enable",
          default = false
        )

    object ManualRefreshEnableParam
        extends FSParam[Boolean](
          name = "for_you_clear_cache_manual_refresh_enable",
          default = false
        )

    object NavigateEnableParam
        extends FSParam[Boolean](
          name = "for_you_clear_cache_navigate_enable",
          default = false
        )

    object ColdStartRetainViewportParam
        extends FSParam[Boolean](
          name = "for_you_clear_cache_retain_viewport",
          default = false
        )

    case object MinEntriesParam
        extends FSBoundedParam[Int](
          name = "for_you_clear_cache_min_entries",
          default = 10,
          min = 0,
          max = 35
        )
  }

  object Navigation {
    object PtrEnableParam
        extends FSParam[Boolean](
          name = "for_you_navigation_ptr_enable",
          default = false
        )

    object ColdStartEnableParam
        extends FSParam[Boolean](
          name = "for_you_navigation_cold_start_enable",
          default = false
        )

    object WarmStartEnableParam
        extends FSParam[Boolean](
          name = "for_you_navigation_warm_start_enable",
          default = false
        )

    object ManualRefreshEnableParam
        extends FSParam[Boolean](
          name = "for_you_navigation_manual_refresh_enable",
          default = false
        )

    object NavigateEnableParam
        extends FSParam[Boolean](
          name = "for_you_navigation_navigate_enable",
          default = false
        )
  }

  /**
   * This author ID list is used purely for realtime metrics collection around how often we
   * are serving posts from these authors and which sources they are coming from.
   */
  object AuthorListForStatsParam
      extends FSParam[Set[Long]](
        name = "for_you_author_list_for_stats",
        default = Set.empty
      )

  object ExperimentStatsParam
      extends FSParam[String](
        name = "for_you_experiment_stats",
        default = ""
      )

  object CommunitiesToJoinMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_communities_to_join_min_injection_interval_in_minutes",
        default = 2100.minutes,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object MaxCommunitiesToJoinCandidatesParam
      extends FSBoundedParam[Int](
        name = "for_you_communities_to_join_max_candidates",
        default = 3,
        min = 1,
        max = 10)

  object CommunitiesToJoinDisplayTypeIdParam
      extends FSEnumParam[CommunityToJoinModuleDisplayType.type](
        name = "for_you_communities_to_join_display_type_id",
        default = CommunityToJoinModuleDisplayType.Carousel,
        enum = CommunityToJoinModuleDisplayType
      )

  object EnableCommunitiesToJoinCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_communities_to_join",
        default = false
      )

  object RecommendedJobMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_recommended_job_min_injection_interval_in_minutes",
        default = 2100.minutes,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object MaxRecommendedJobCandidatesParam
      extends FSBoundedParam[Int](
        name = "for_you_recommended_job_max_candidates",
        default = 3,
        min = 1,
        max = 10)

  object EnableRecommendedJobsParam
      extends FSParam[Boolean](
        name = "for_you_enable_recommended_jobs",
        default = false
      )

  object EnableRecommendedRecruitingOrganizationsParam
      extends FSParam[Boolean](
        name = "for_you_enable_recommended_recruiting_organizations",
        default = false
      )

  object RecommendedRecruitingOrganizationMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_recommended_recruiting_organization_min_injection_interval_in_minutes",
        default = 2100.minutes,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object MaxRecommendedRecruitingOrganizationCandidatesParam
      extends FSBoundedParam[Int](
        name = "for_you_recommended_recruiting_organization_max_candidates",
        default = 3,
        min = 1,
        max = 10
      )

  object EnableBookmarksCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_bookmarks_module",
        default = false
      )

  object EnablePinnedTweetsCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_pinned_tweets_pipeline",
        default = false
      )

  object EnableEntryPointPivotParam
      extends FSParam[Boolean](
        name = "for_you_enable_entry_point_pivot",
        default = false
      )

  object EnableGrokEntryPointPivotParam
      extends FSParam[Boolean](
        name = "for_you_enable_grok_entry_point_pivot",
        default = false
      )

  object PinnedTweetsModuleMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_pinned_tweets_module_min_injection_interval_in_minutes",
        default = 1440.minutes, // 1 day
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object EnableExplorationTweetsCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_exploration_tweets_pipeline",
        default = false
      )

  object EnableJetfuelFramePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_jetfuel_frame_pipeline",
        default = false
      )

  object FollowingSportsGateUsersParam
      extends FSParam[Set[Long]](
        name = "for_you_following_sports_users_list",
        default = Set.empty
      )

  object ExplorationTweetsTimelinePosition
      extends FSBoundedParam[Int](
        name = "for_you_exploration_tweets_position",
        default = 15,
        min = 0,
        max = 50
      )

  object SuperbowlModuleTimelinePosition
      extends FSBoundedParam[Int](
        name = "for_you_superbowl_position",
        default = 3,
        min = 0,
        max = 50
      )

  object GrokPivotModuleTimelinePosition
      extends FSBoundedParam[Int](
        name = "for_you_grok_pivot_position",
        default = 3,
        min = 0,
        max = 50
      )

  object EntryPointPivotMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_entry_point_pivot_min_injection_interval_in_minutes",
        default = 30.minutes,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object GrokEntryPointPivotMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_grok_entry_point_pivot_min_injection_interval_in_minutes",
        default = 30.minutes,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object MaxNumberExplorationTweetsParam
      extends FSBoundedParam[Int](
        name = "for_you_exploration_tweets_max_number",
        default = 2,
        min = 1,
        max = 10
      )

  object EnableBookmarksModuleWeekendGate
      extends FSParam[Boolean](
        name = "for_you_enable_bookmarks_module_weekend_gate",
        default = false
      )

  object BookmarksModuleMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_bookmarks_module_min_injection_interval_in_minutes",
        default = 2880.minutes, // 2 days
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object InNetworkExplorationTweetsMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        name = "for_you_in_network_exploration_tweets_min_injection_interval_in_minutes",
        default = 30.minutes,
        min = 0.minutes,
        max = 60.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object ExplorationTweetsMaxFollowerCountParam
      extends FSBoundedParam[Long](
        name = "for_you_exploration_tweets_max_follower_count",
        default = Long.MaxValue,
        min = 0L,
        max = Long.MaxValue
      )

  object EnableViewerHasJobRecommendationsFeatureParam
      extends FSParam[Boolean](
        name = "for_you_enable_viewer_has_job_recommendations_feature",
        default = false
      )

  object EnableTrendsParam
      extends FSParam[Boolean](
        name = "for_you_enable_trends",
        default = false
      )

  object EnableKeywordTrendsParam
      extends FSParam[Boolean](
        name = "for_you_enable_keyword_trends",
        default = false
      )

  object MaxNumberKeywordTrendsParam
      extends FSBoundedParam[Int](
        name = "for_you_keyword_trends_max_number",
        default = 5,
        min = 1,
        max = 10
      )

  object TrendsModuleMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_trends_min_injection_interval_in_minutes",
        default = 360.minutes,
        min = 0.minutes,
        max = 2880.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object KeywordTrendsModuleMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_keyword_trends_min_injection_interval_in_minutes",
        default = 360.minutes,
        min = 0.minutes,
        max = 2880.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object EnableScoredVideoTweetsCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_scored_video_tweets_pipeline",
        default = false
      )

  object VideoTweetsModuleMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_video_tweets_module_min_injection_interval_in_minutes",
        default = 1.hour,
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object VideoCarouselNumTweetCandidatesToDedupeAgainstParam
      extends FSBoundedParam[Int](
        name = "for_you_video_carousel_num_tweet_candidates_to_dedupe_against",
        default = 10,
        min = 0,
        max = 50
      )

  object VideoCarouselTimelinePosition
      extends FSBoundedParam[Int](
        name = "for_you_video_carousel_position",
        default = 5,
        min = 0,
        max = 50
      )

  object VideoCarouselNumCandidates
      extends FSBoundedParam[Int](
        name = "for_you_video_carousel_num_candidates",
        default = 5,
        min = 0,
        max = 50
      )

  object VideoCarouselEnableFooterParam
      extends FSParam[Boolean](
        name = "for_you_video_carousel_enable_footer",
        default = false
      )

  object VideoCarouselAllowVerticalVideos
      extends FSParam[Boolean](
        name = "for_you_video_carousel_allow_vertical_videos",
        default = true
      )

  object VideoCarouselAllowHorizontalVideos
      extends FSParam[Boolean](
        name = "for_you_video_carousel_allow_horizontal_videos",
        default = true
      )

  object EnableTLSHydrationParam
      extends FSParam[Boolean](
        name = "for_you_enable_tls_hydration",
        default = false
      )

  object EnableGetTweetsFromArchiveIndex
      extends BooleanDeciderParam(decider = DeciderKey.EnableGetTweetsFromArchiveIndex)

  // Currently only supported by rweb
  object EnableArticlePreviewTextHydrationParam
      extends FSParam[Boolean](
        name = "for_you_enable_article_preview_hydration",
        default = false
      )

  object EnableAdsDebugParam
      extends FSParam[Boolean](
        name = "for_you_enable_ads_debug",
        default = false
      )

  object RelevancePromptEnableParam
      extends FSParam[Boolean](
        name = "for_you_relevance_prompt_enable",
        default = false
      )

  object RelevancePromptMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        name = "for_you_relevance_prompt_min_injection_interval_minutes",
        default = 1440.minutes,
        min = 0.minutes,
        max = 144000.minutes
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object RelevancePromptTweetPositionParam
      extends FSBoundedParam[Int](
        name = "for_you_relevance_prompt_position",
        default = 15,
        min = 0,
        max = 10000
      )

  object RelevancePromptTitleParam
      extends FSParam[String](
        name = "for_you_relevance_prompt_title",
        default = ""
      )

  object RelevancePromptPositiveParam
      extends FSParam[String](
        name = "for_you_relevance_prompt_positive",
        default = ""
      )

  object RelevancePromptNegativeParam
      extends FSParam[String](
        name = "for_you_relevance_prompt_negative",
        default = ""
      )

  object RelevancePromptNeutralParam
      extends FSParam[String](
        name = "for_you_relevance_prompt_neutral",
        default = ""
      )

  object EnableForYouTopicSelectorParam
      extends FSParam[Boolean](
        name = "for_you_topic_selector_enabled",
        default = false
      )

  object ForYouTopicSelectorJetfuelRouteParam
      extends FSParam[String](
        name = "for_you_topic_selector_jetfuel_route",
        default = ""
      )

  object ForYouTopicSelectorPosition
      extends FSBoundedParam[Int](
        name = "for_you_topic_selector_position",
        default = 1,
        min = 0,
        max = 1000
      )

  object ForYouAppUpsellJetfuelRouteParam
      extends FSParam[String](
        name = "for_you_app_upsell_jetfuel_route",
        default = "/cards/pivots/appInstallPivot"
      )

  object EnableForYouAppUpsellParam
      extends FSParam[Boolean](
        name = "for_you_enable_app_upsell",
        default = false
      )

  object ForYouAppUpsellPosition
      extends FSBoundedParam[Int](
        name = "for_you_app_upsell_position",
        default = 1,
        min = 0,
        max = 1000
      )

  object EnableTuneFeedCandidatePipelineParam
      extends FSParam[Boolean](
        name = "for_you_enable_tune_feed_pipeline",
        default = false
      )

  object TuneFeedTimelinePosition
      extends FSBoundedParam[Int](
        name = "for_you_tune_feed_position",
        default = 7,
        min = 0,
        max = 50
      )

  object TuneFeedModuleMinInjectionIntervalParam
      extends FSBoundedParam[Duration](
        "for_you_tune_feed_module_min_injection_interval_in_minutes",
        default = 180.minutes, // 3 hours
        min = 0.minutes,
        max = 6000.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object EnableFollowedGrokTopicsHydrationParam
      extends FSParam[Boolean](
        name = "for_you_enable_followed_grok_topics_hydration",
        default = false
      )

  object EnableForYouTimelineAdsSurface
      extends FSParam[Boolean](
        name = "for_you_enable_timeline_ads_surface",
        default = false
      )

  object MinFollowingCountParam
      extends FSBoundedParam[Int](
        name = "for_you_user_following_range_gate_min_following_count",
        default = 0,
        min = 0,
        max = 1000000
      )

  object MaxFollowingCountParam
      extends FSBoundedParam[Int](
        name = "for_you_user_following_range_gate_max_following_count",
        default = 10000,
        min = 0,
        max = 1000000
      )
}
