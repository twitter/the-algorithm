package com.twitter.home_mixer.param

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.decider.BooleanDeciderParam
import com.twitter.timelines.configapi.decider.DeciderBoundedParam
import com.twitter.util.Duration

/**
 * Instantiate Params that do not relate to a specific product.
 *
 * @see [[com.twitter.product_mixer.core.product.ProductParamConfig.supportedClientFSName]]
 */
object HomeGlobalParams {

  /**
   * This param is used to disable ads injection for timelines served by home-mixer.
   * It is currently used to maintain user-role based no-ads lists for automation accounts,
   * and should NOT be used for other purposes.
   */
  object AdsDisableInjectionBasedOnUserRoleParam
      extends FSParam(
        name = "home_mixer_ads_disable_injection_based_on_user_role",
        default = false
      )

  object EnableTweetEntityServiceMigrationParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_tweet_entity_service_migration",
        default = false
      )

  object EnableTweetEntityServiceVisibilityMigrationParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_tweet_entity_service_visibility_migration",
        default = false
      )

  object EnableSendScoresToClient
      extends FSParam[Boolean](
        name = "home_mixer_enable_send_scores_to_client",
        default = false
      )

  object EnableDebugString
      extends FSParam[Boolean](
        name = "home_mixer_enable_debug_string",
        default = false
      )

  object EnablePersistenceDebug
      extends FSParam[Boolean](
        name = "home_mixer_enable_persistence_debug",
        default = false
      )

  object MaxNumberReplaceInstructionsParam
      extends FSBoundedParam[Int](
        name = "home_mixer_max_number_replace_instructions",
        default = 10,
        min = 1,
        max = 20
      )

  object TimelinesPersistenceStoreMaxEntriesPerClient
      extends FSBoundedParam[Int](
        name = "home_mixer_timelines_persistence_store_max_entries_per_client",
        default = 1800,
        min = 500,
        max = 5000
      )

  object EnableNewTweetsPillAvatarsParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_new_tweets_pill_avatars",
        default = true
      )

  object EnableSocialContextParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_social_context",
        default = false
      )

  object EnableCommunitiesContextParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_communities_context",
        default = true
      )

  object EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_advertiser_brand_safety_settings_feature_hydrator",
        default = true
      )

  object EnableBasketballContextFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_basketball_context_feature_hydrator",
        default = false
      )

  object EnablePostContextFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_post_context_feature_hydrator",
        default = false
      )

  object BasketballTeamAccountIdsParam
      extends FSParam[Set[Long]](
        name = "home_mixer_basketball_team_account_ids",
        default = Set()
      )

  object EnableSSPAdsBrandSafetySettingsFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_ssp_ads_brand_safety_settings_feature_hydrator",
        default = true
      )

  object ExcludeServedTweetIdsNumberParam
      extends FSBoundedParam[Int](
        name = "home_mixer_exclude_served_tweet_ids_number",
        default = 100,
        min = 0,
        max = 100
      )

  object ExcludeServedTweetIdsDurationParam
      extends FSBoundedParam[Duration](
        "home_mixer_exclude_served_tweet_ids_in_minutes",
        default = 10.minutes,
        min = 1.minute,
        max = 60.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object ExcludeServedAuthorIdsDurationParam
      extends FSBoundedParam[Duration](
        "home_mixer_exclude_served_author_ids_in_minutes",
        default = 60.minutes,
        min = 1.minute,
        max = 60.minutes)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromMinutes
  }

  object EnableServedFilterAllRequests
      extends FSParam[Boolean](
        name = "home_mixer_enable_served_filter_all_requests",
        default = false
      )

  object EnableScribeServedCandidatesParam
      extends FSParam[Boolean](
        name = "home_mixer_served_tweets_enable_scribing",
        default = false
      )

  object EnableServedCandidateFeatureKeysKafkaPublishingParam
      extends BooleanDeciderParam(
        decider = DeciderKey.EnableServedCandidateFeatureKeysKafkaPublishing)

  object RateLimitTestIdsParam
      extends FSParam[Set[Long]](
        name = "home_mixer_rate_limit_test_ids",
        default = Set.empty
      )

  object IsSelectedByHeavyRankerCountParam
      extends FSBoundedParam[Int](
        name = "home_mixer_is_selected_by_heavy_ranker_count",
        default = 100,
        min = 0,
        max = 2000
      )

  object EnableAdditionalChildFeedbackParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_additional_child_feedback",
        default = false
      )

  object EnableBlockMuteReportChildFeedbackParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_block_mute_report_child_feedback",
        default = false
      )

  object ListMandarinTweetsParams {
    object ListMandarinTweetsEnable
        extends FSParam[Boolean](
          name = "home_mixer_mandarin_list_tweets_enabled",
          default = false
        )

    object ListMandarinTweetsLists
        extends FSParam[Seq[Long]](
          name = "home_mixer_mandarin_tweets_lists",
          default = Seq.empty
        )
  }

  object FeatureHydration {
    object EnableLargeEmbeddingsFeatureHydrationParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_large_embeddings",
          default = false
        )

    object EnableSimClustersSimilarityFeaturesDeciderParam
        extends BooleanDeciderParam(
          decider = DeciderKey.EnableSimClustersSimilarityFeatureHydration
        )

    object EnableOnPremRealGraphQueryFeatures
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_on_prem_real_graph_query_features",
          default = false
        )

    object EnableRealGraphQueryFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_real_graph_query_features",
          default = false
        )

    object EnableRealGraphViewerRelatedUsersFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_real_graph_viewer_related_users_features",
          default = false
        )

    object EnableSimclustersSparseTweetFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_simclusters_sparse_tweet_features",
          default = false
        )

    object EnableTwhinUserPositiveFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_twhin_user_positive_features",
          default = false
        )

    object EnableTwhinVideoFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_twhin_video_features",
          default = false
        )

    object EnableTwhinUserNegativeFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_twhin_user_negative_features",
          default = false
        )

    object EnableTwhinVideoFeaturesOnlineParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_twhin_video_online_features",
          default = false
        )

    object EnableTwhinRebuildUserEngagementFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_twhin_rebuild_user_engagement_features",
          default = false
        )

    object EnableTwhinRebuildUserPositiveFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_twhin_rebuild_user_positive_features",
          default = false
        )

    object EnableClipEmbeddingFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_clip_embedding_features",
          default = false
        )

    object EnableClipEmbeddingMediaUnderstandingFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_clip_embedding_media_understanding_features",
          default = false
        )

    object EnableUserHistoryTransformerJointBlueEmbeddingFeaturesParam
        extends FSParam[Boolean](
          name =
            "home_mixer_feature_hydration_enable_user_history_transformer_joint_blue_embedding_features",
          default = false
        )

    object EnableTweetLanguageFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_tweet_language_features",
          default = false
        )

    object EnableTwhinTweetFeaturesOnlineParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_twhin_tweet_online_features",
          default = false
        )

    object EnableTwhinRebuildTweetFeaturesOnlineParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_twhin_rebuild_tweet_online_features",
          default = false
        )

    object EnableTransformerPostEmbeddingJointBlueFeaturesParam
        extends FSParam[Boolean](
          name =
            "home_mixer_feature_hydration_enable_transformer_post_embedding_features_joint_blue",
          default = false
        )

    object EnableTweetypieContentFeaturesDeciderParam
        extends BooleanDeciderParam(
          decider = DeciderKey.EnableTweetypieContentFeatures
        )

    object EnableTweetypieContentFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_tweetypie_content_features",
          default = true
        )

    object EnableTweetypieContentMediaEntityFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_tweetypie_content_media_entity_features",
          default = true
        )

    object EnableUserFavAvgTextEmbeddingsQueryFeatureParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_user_fav_avg_text_embeddings_query_feature",
          default = false
        )

    object EnableTweetTextTokensEmbeddingFeatureScribingParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_tweet_text_tokens_embedding_feature_scribing",
          default = false
        )

    object EnableTweetVideoAggregatedWatchTimeFeatureScribingParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_tweet_video_aggregated_watch_time",
          default = false
        )

    object EnableImmersiveClientActionsQueryFeatureHydrationParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_immersive_client_actions",
          default = false
        )

    object EnableImmersiveClientActionsClipEmbeddingQueryFeatureHydrationParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_immersive_client_actions_clip_embedding",
          default = false
        )

    object EnableGrokVideoMetadataFeatureHydrationParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_grok_video_metadata",
          default = false
        )

    object EnableDedupClusterIdFeatureHydrationParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_dedup_cluster_id",
          default = false
        )

    object EnableDedupClusterId88FeatureHydrationParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_dedup_cluster_id_88",
          default = false
        )

    object EnableGeoduckAuthorLocationHydatorParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_geoduck_author_location_hydrator",
          default = false
        )

    object EnableViewCountFeaturesParam
        extends FSParam[Boolean](
          name = "home_mixer_feature_hydration_enable_view_count_features",
          default = false
        )

    object EnableVideoSummaryEmbeddingFeatureDeciderParam
        extends BooleanDeciderParam(
          decider = DeciderKey.EnableVideoSummaryEmbeddingHydration
        )

    object EnableVideoClipEmbeddingFeatureHydrationDeciderParam
        extends BooleanDeciderParam(
          decider = DeciderKey.EnableVideoClipEmbeddingHydration
        )

    object EnableScoredVideoTweetsUserHistoryEventsQueryFeatureHydrationDeciderParam
        extends BooleanDeciderParam(
          decider =
            DeciderKey.EnableScoredVideoTweetsUserHistoryEventsQueryFeatureHydrationDeciderParam
        )

    object EnableVideoClipEmbeddingMediaUnderstandingFeatureHydrationDeciderParam
        extends BooleanDeciderParam(
          decider = DeciderKey.EnableVideoClipEmbeddingMediaUnderstandingHydration
        )
  }

  object Scoring {

    object AuthorListForDataCollectionParam
        extends FSParam[Set[Long]](
          name = "home_mixer_author_list_for_data_collection",
          default = Set.empty[Long]
        )

    object ModelNameParam
        extends FSParam[String](
          name = "home_mixer_model_name",
          default = ""
        )

    object ImpressedMediaClusterBasedRescoringParam
        extends FSBoundedParam[Double](
          name = "home_mixer_impressed_media_cluster_based_rescoring",
          default = 0.0,
          min = 0.0,
          max = 0.2
        )

    object ImpressedImageClusterBasedRescoringParam
        extends FSBoundedParam[Double](
          name = "home_mixer_impressed_image_cluster_based_rescoring",
          default = 0.0,
          min = 0.0,
          max = 1.0
        )

    object ModelIdParam
        extends FSParam[String](
          name = "home_mixer_model_id",
          default = "Home"
        )

    object ProdModelIdParam
        extends FSParam[String](
          name = "home_mixer_model_prod_model_id",
          default = "Home"
        )

    object UseRealtimeNaviClusterParam
        extends FSParam[Boolean](
          name = "home_mixer_model_use_realtime_navi_cluster",
          default = false
        )

    object UseGPUNaviClusterParam
        extends FSParam[Boolean](
          name = "home_mixer_model_use_gpu_navi_cluster",
          default = false
        )

    object UseSecondaryNaviClusterParam
        extends BooleanDeciderParam(decider = DeciderKey.EnableSecondaryNaviRecapCluster)

    object UseGPUNaviClusterTestUsersParam
        extends BooleanDeciderParam(decider = DeciderKey.EnableGPUNaviRecapClusterTestUsers)

    object UseVideoNaviClusterParam
        extends FSParam[Boolean]("home_mixer_model_use_video_navi_cluster", false)

    object NaviGPUBatchSizeParam
        extends DeciderBoundedParam[Double](
          decider = DeciderKey.NaviGPUClusterRequestBatchSize,
          default = 1800.0,
          min = 0.0,
          max = 10000.0
        )

    object AddNoiseInWeightsPerLabel
        extends FSParam[Boolean](
          name = "home_mixer_add_noise_in_weights_per_label",
          default = false
        )

    object EnableDailyFrozenNoisyWeights
        extends FSParam[Boolean](
          name = "home_mixer_enable_daily_frozen_weights",
          default = false
        )

    object NoisyWeightAlphaParam
        extends FSBoundedParam[Double](
          name = "home_mixer_noisy_weight_alpha_param",
          default = 2,
          min = 0.0,
          max = 10.0
        )

    object NoisyWeightBetaParam
        extends FSBoundedParam[Double](
          name = "home_mixer_noisy_weight_beta_param",
          default = 2,
          min = 0.0,
          max = 10.0
        )
    object NegativeScoreConstantFilterThresholdParam
        extends FSBoundedParam[Double](
          name = "home_mixer_negative_score_constant_filter_threshold",
          default = 1e-3,
          min = 0,
          max = 1
        )

    object NegativeScoreNormFilterThresholdParam
        extends FSBoundedParam[Double](
          name = "home_mixer_negative_score_norm_filter_threshold",
          default = 0.15,
          min = 0,
          max = 1
        )

    object RequestNormalizedScoresParam
        extends FSParam[Boolean](
          name = "home_mixer_request_normalized_scores",
          default = false
        )

    object NormalizedNegativeHead
        extends FSParam[Boolean](
          name = "home_mixer_normalized_negative_head",
          default = false
        )

    object UseWeightForNegHeadParam
        extends FSParam[Boolean](
          name = "home_mixer_use_weight_for_neg_head",
          default = false
        )

    object ConstantNegativeHead
        extends FSParam[Boolean](
          name = "home_mixer_constant_negative_head",
          default = false
        )

    object EnableNoNegHeuristicParam
        extends FSParam[Boolean](
          name = "home_mixer_no_neg_heuristic",
          default = false
        )

    object EnableNegSectionRankingParam
        extends FSParam[Boolean](
          name = "home_mixer_neg_section_ranking",
          default = false
        )

    object RequestRankDecayFactorParam
        extends FSBoundedParam[Double](
          name = "home_mixer_request_rank_decay_factor",
          default = 0.95,
          min = 0,
          max = 1
        )

    object ScoreThresholdForVQVParam
        extends FSBoundedParam[Double](
          name = "home_mixer_score_threshold_for_vqv",
          default = 0.0,
          min = 0.0,
          max = 1.0
        )

    object ScoreThresholdForDwellParam
        extends FSBoundedParam[Double](
          name = "home_mixer_score_threshold_for_dwell",
          default = 0.0,
          min = 0.0,
          max = 1.0
        )

    object EnableBinarySchemeForVQVParam
        extends FSParam[Boolean](
          name = "home_mixer_enable_binary_scheme_for_vqv",
          default = false
        )

    object BinarySchemeConstantForVQVParam
        extends FSBoundedParam[Double](
          name = "home_mixer_constant_binary_scheme_for_vqv",
          default = 0.0,
          min = 0.0,
          max = 1.0
        )

    object EnableBinarySchemeForDwellParam
        extends FSParam[Boolean](
          name = "home_mixer_enable_binary_scheme_for_dwell",
          default = false
        )

    object EnableDwellOrVQVParam
        extends FSParam[Boolean](
          name = "home_mixer_enable_dwell_or_video_watch_time",
          default = false
        )

    object TwhinDiversityRescoringParam
        extends FSParam[Boolean](
          name = "home_mixer_twhin_diversity_rescoring",
          default = false
        )

    object CategoryDiversityRescoringParam
        extends FSParam[Boolean](
          name = "home_mixer_category_diversity_rescoring",
          default = false
        )

    object ModelBiases {
      object VideoQualityViewParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_bias_video_quality_viewed",
            default = 0.0,
            min = 0.0,
            max = 100.0
          )

      object VideoQualityViewImmersiveParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_bias_video_quality_viewed_immersive",
            default = 0.0,
            min = 0.0,
            max = 100.0
          )

      object VideoQualityWatchParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_bias_video_quality_watched",
            default = 0.0,
            min = 0.0,
            max = 100.0
          )
    }

    object ModelDebiases {

      object FavParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_fav",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object RetweetParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_retweet",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ReplyParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_reply",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object DwellParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_dwell",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object GoodProfileClickParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_good_profile_click",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object VideoWatchTimeMsParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_video_watch_time_ms",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object VideoQualityViewParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_video_quality_viewed",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object VideoQualityViewImmersiveParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_video_quality_viewed_immersive",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ReplyEngagedByAuthorParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_reply_engaged_by_author",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object GoodClickV1Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_good_click_v1",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object GoodClickV2Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_good_click_v2",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object BookmarkParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_bookmark",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ShareParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_share",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object NegativeFeedbackV2Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_negative_feedback_v2",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object VideoQualityWatchParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_debias_video_quality_watched",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )
    }

    object ModelWeights {

      object FavParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_fav",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object RetweetParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_retweet",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ReplyParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_reply",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object GoodProfileClickParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_good_profile_click",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object VideoPlayback50Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_video_playback50",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object VideoQualityViewParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_video_quality_viewed",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object VideoQualityViewImmersiveParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_video_quality_viewed_immersive",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ReplyEngagedByAuthorParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_reply_engaged_by_author",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object GoodClickParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_good_click",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object GoodClickV1Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_good_click_v1",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object GoodClickV2Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_good_click_v2",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object TweetDetailDwellParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_tweet_detail_dwell",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ProfileDwelledParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_profile_dwelled",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object BookmarkParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_bookmark",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ShareParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_share",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ShareMenuClickParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_share_menu_click",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object NegativeFeedbackV2Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_negative_feedback_v2",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ReportParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_report",
            default = 0.0,
            min = -20000.0,
            max = 0.0
          )

      object WeakNegativeFeedbackParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_weak_negative_feedback",
            default = 0.0,
            min = -1000.0,
            max = 0.0
          )

      object StrongNegativeFeedbackParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_strong_negative_feedback",
            default = 0.0,
            min = -1000.0,
            max = 0.0
          )

      object DwellParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_dwell",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object OpenLinkParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_open_link",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object ScreenshotParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_screenshot",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      object VideoWatchTimeMsParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_video_watch_time_ms",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )

      // Categorical Dwell Params
      object Dwell0Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_dwell_0",
            default = 0.0,
            min = 0.0,
            max = 1000.0
          )

      object Dwell1Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_dwell_1",
            default = 0.0,
            min = 0.0,
            max = 1000.0
          )

      object Dwell2Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_dwell_2",
            default = 0.0,
            min = 0.0,
            max = 1000.0
          )

      object Dwell3Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_dwell_3",
            default = 0.0,
            min = 0.0,
            max = 1000.0
          )

      object Dwell4Param
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_dwell_4",
            default = 0.0,
            min = 0.0,
            max = 1000.0
          )

      object VideoQualityWatchParam
          extends FSBoundedParam[Double](
            name = "home_mixer_model_weight_video_quality_watched",
            default = 0.0,
            min = -10000.0,
            max = 10000.0
          )
    }

    object UseProdInPhoenixParams {
      object EnableProdFavForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_fav_for_phoenix",
            default = false
          )

      object EnableProdReplyForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_reply_for_phoenix",
            default = false
          )

      object EnableProdShareForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_share_for_phoenix",
            default = false
          )

      object EnableProdRetweetForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_retweet_for_phoenix",
            default = false
          )

      object EnableProdVQVForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_vqv_for_phoenix",
            default = false
          )

      object EnableProdDwellForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_dwell_for_phoenix",
            default = false
          )

      object EnableProdNegForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_neg_for_phoenix",
            default = false
          )

      object EnableProdProfileClickForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_profile_click_for_phoenix",
            default = false
          )

      object EnableProdGoodClickV1ForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_good_click_v1_for_phoenix",
            default = false
          )

      object EnableProdGoodClickV2ForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_good_click_v2_for_phoenix",
            default = false
          )

      object EnableProdOpenLinkForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_open_link_for_phoenix",
            default = false
          )

      object EnableProdScreenshotForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_screenshot_for_phoenix",
            default = false
          )

      object EnableProdBookmarkForPhoenixParam
          extends FSParam[Boolean](
            name = "home_mixer_enable_prod_bookmark_for_phoenix",
            default = false
          )
    }
  }

  object EnableTenSecondsLogicForVQV
      extends FSParam[Boolean](
        name = "home_mixer_enable_ten_seconds_logic_for_vqv",
        default = true
      )

  object EnableImmersiveVQV
      extends FSParam[Boolean](
        name = "home_mixer_enable_immersive_vqv",
        default = false
      )
  object EnableLandingPage
      extends FSParam[Boolean](
        name = "home_mixer_enable_landing_page",
        default = false
      )

  object EnableExploreSimclustersLandingPage
      extends FSParam[Boolean](
        name = "home_mixer_enable_explore_simclusters_landing_page",
        default = false
      )

  object EnableTopicBasedRealTimeAggregateFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_topic_based_real_time_aggregate_feature_hydrator_param",
        default = true
      )

  object EnableTopicCountryBasedRealTimeAggregateFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_topic_country_based_real_time_aggregate_feature_hydrator_param",
        default = true
      )

  object EnableTopicEdgeAggregateFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_topic_edge_aggregate_feature_hydrator_param",
        default = true
      )

  object FeedbackFatigueFilteringDurationParam
      extends FSBoundedParam[Duration](
        name = "home_mixer_feedback_fatigue_filtering_duration_in_days",
        default = 14.days,
        min = 0.days,
        max = 100.days
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromDays
  }

  object EnableCommonFeaturesDataRecordCopyDuringPldrConversionParam
      extends BooleanDeciderParam(
        decider = DeciderKey.EnableCommonFeaturesDataRecordCopyDuringPldrConversion)

  object EnablePinnedTweetsCarouselParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_pinned_tweets_carousel",
        default = false
      )

  object EnablePostFeedbackParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_post_feedback",
        default = false
      )

  object PostFeedbackThresholdParam
      extends FSBoundedParam[Double](
        name = "home_mixer_post_feedback_threshold",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object PostFeedbackPromptTitleParam
      extends FSParam[String](
        name = "home_mixer_post_feedback_prompt_title",
        default = "Are you interested in this post?"
      )

  object PostFeedbackPromptPositiveParam
      extends FSParam[String](
        name = "home_mixer_post_feedback_prompt_positive",
        default = "Yes"
      )

  object PostFeedbackPromptNegativeParam
      extends FSParam[String](
        name = "home_mixer_post_feedback_prompt_negative",
        default = "No"
      )

  object PostFeedbackPromptNeutralParam
      extends FSParam[String](
        name = "home_mixer_post_feedback_prompt_neutral",
        default = "Not sure"
      )

  object EnablePostFollowupParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_post_followup",
        default = false
      )

  object EnablePostDetailsNegativeFeedbackParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_post_details_negative_feedback",
        default = false
      )

  object PostFollowupThresholdParam
      extends FSBoundedParam[Double](
        name = "home_mixer_post_followup_threshold",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object EnableSlopFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_slop_filter",
        default = false
      )

  object EnableNsfwFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_nsfw_filter",
        default = false
      )

  object EnableSoftNsfwFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_soft_nsfw_filter",
        default = false
      )

  object EnableGrokSpamFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_grok_spam_filter",
        default = false
      )

  object EnableGrokViolentFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_grok_violent_filter",
        default = false
      )

  object EnableGrokGoreFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_grok_gore_filter",
        default = false
      )

  object EnableMinVideoDurationFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_min_video_duration_filter",
        default = false
      )

  object EnableMaxVideoDurationFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_max_video_duration_filter",
        default = false
      )

  object EnableClusterBasedDedupFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_cluster_based_dedup_filter",
        default = false
      )

  object EnableCountryFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_country_filter",
        default = false
      )

  object EnableRegionFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_region_filter",
        default = false
      )

  object EnableHasMultipleMediaFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_has_multiple_media_filter",
        default = false
      )

  object EnableClusterBased88DedupFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_cluster_based_88_dedup_filter",
        default = false
      )

  object EnableNoClusterFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_no_cluster_filter",
        default = false
      )

  object DedupHistoricalEventsTimeWindowParam
      extends FSBoundedParam[Long](
        name = "home_mixer_dedup_historical_events_time_window",
        default = 43200000L, // 12 * 60 * 60 * 1000 = 12hrs in milliseconds
        min = 0L,
        max = 604800000L // 7 days
      )

  object MinVideoDurationThresholdParam
      extends FSBoundedParam[Long](
        name = "home_mixer_min_video_duration_threshold",
        default = 0L, // 0 second
        min = 0L,
        max = 604800000L // 7 days
      )

  object MaxVideoDurationThresholdParam
      extends FSBoundedParam[Long](
        name = "home_mixer_max_video_duration_threshold",
        default = 604800000L, // 7 days
        min = 0L,
        max = 604800000L // 7 days
      )

  object EnableSlopFilterLowSignalUsers
      extends FSParam[Boolean](
        name = "home_mixer_enable_slop_filter_low_signal_users",
        default = false
      )

  object EnableSlopFilterEligibleUserStateParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_slop_filter_eligible_user_state_param",
        default = true
      )

  object SlopMaxScore
      extends FSBoundedParam[Double](
        name = "home_mixer_slop_max_score",
        default = 0.3,
        min = 0.0,
        max = 4.0
      )

  object SlopMinFollowers
      extends FSBoundedParam[Int](
        name = "home_mixer_slop_min_followers",
        default = 100,
        min = 0,
        max = 10000000
      )

  object EnableGrokAnnotations
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_grok_annotations",
        default = false
      )

  object UserActionsMaxCount
      extends FSBoundedParam[Int](
        name = "home_mixer_user_actions_max_count",
        default = 522,
        min = 0,
        max = 10000
      )

  object EnableTweetRTAMhOnlyParam
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_tweet_rta_read_from_mh",
        default = false
      )

  object EnableTweetRTAMhFallbackParam
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_tweet_rta_read_fallback_to_mh",
        default = false
      )

  object EnableTweetCountryRTAMhOnlyParam
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_tweet_country_rta_read_from_mh",
        default = false
      )

  object EnableTweetCountryRTAMhFallbackParam
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_tweet_country_rta_read_fallback_to_mh",
        default = false
      )

  object EnableUserRTAMhOnlyParam
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_user_rta_read_from_mh",
        default = false
      )

  object EnableUserRTAMhFallbackParam
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_user_rta_read_fallback_to_mh",
        default = false
      )

  object EnableUserAuthorRTAMhOnlyParam
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_user_author_rta_read_from_mh",
        default = false
      )

  object EnableUserAuthorRTAMhFallbackParam
      extends FSParam[Boolean](
        name = "home_mixer_feature_hydration_enable_user_author_rta_read_fallback_to_mh",
        default = false
      )

  object MaxPostContextPostsPerRequest
      extends FSParam[Int](
        name = "home_mixer_feature_hydration_max_post_context_posts",
        default = 5
      )

  object MaxPostContextDuplicatesPerRequest
      extends FSParam[Int](
        name = "home_mixer_feature_hydration_max_post_context_duplicates",
        default = 2
      )

  object PhoenixCluster extends Enumeration {
    val Prod = Value
    val Experiment1 = Value
    val Experiment2 = Value
    val Experiment3 = Value
    val Experiment4 = Value
    val Experiment5 = Value
    val Experiment6 = Value
    val Experiment7 = Value
    val Experiment8 = Value
  }

  object PhoenixInferenceClusterParam
      extends FSEnumParam[PhoenixCluster.type](
        name = "home_mixer_model_phoenix_inference_cluster_id",
        default = PhoenixCluster.Prod,
        enum = PhoenixCluster
      )

  object PhoenixTimeoutInMsParam
      extends FSBoundedParam[Int](
        name = "home_mixer_model_phoenix_timeout_in_ms",
        default = 500,
        min = 10,
        max = 10000
      )

  object EnablePhoenixScorerParam
      extends FSParam[Boolean](
        name = "home_mixer_model_enable_phoenix_scorer",
        default = false
      )

  object EnableUserActionsShadowScribeParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_user_actions_shadow_scribe",
        default = false
      )
}
