package com.twitter.home_mixer.product.scored_tweets.param

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.decider.BooleanDeciderParam
import com.twitter.timelines.configapi.decider.DeciderBoundedParam
import com.twitter.util.Duration

object ScoredTweetsParam {
  val SupportedClientFSName = "scored_tweets_supported_client"

  object CandidateSourceParams {
    object EnableCommunitiesCandidateSourceParam
        extends FSParam[Boolean](
          name = "scored_tweets_enable_earlybird_communities_candidate_source",
          default = false
        )

    object EnableInNetworkCandidateSourceParam
        extends FSParam[Boolean](
          name = "scored_tweets_enable_in_network_candidate_source",
          default = true
        )

    object EnableStaticSourceParam
        extends FSParam[Boolean](
          name = "scored_tweets_enable_static_source",
          default = false
        )

    object EnableUTEGCandidateSourceParam
        extends FSParam[Boolean](
          name = "scored_tweets_enable_uteg_candidate_source",
          default = true
        )

    object InNetworkIncludeRepliesParam
        extends FSParam[Boolean](
          name = "scored_tweets_in_network_include_replies",
          default = true
        )

    object InNetworkIncludeRetweetsParam
        extends FSParam[Boolean](
          name = "scored_tweets_in_network_include_retweets",
          default = true
        )

    object InNetworkIncludeExtendedRepliesParam
        extends FSParam[Boolean](
          name = "scored_tweets_in_network_include_extended_replies",
          default = true
        )
  }

  object EnableBackfillCandidatePipelineParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_backfill_candidate_pipeline",
        default = true
      )

  object EnableContentExplorationCandidatePipelineParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_content_exploration_candidate_pipeline",
        default = false
      )

  object ContentExplorationCandidateVersionParam
      extends FSParam[String](
        name = "scored_tweets_enable_content_exploration_candidate_version",
        default = "v1_"
      )

  object EnableContentExplorationScoreScribingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_content_exploration_score_scribing",
        default = false
      )

  object EnableContentExplorationCandidateMaxCountParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_content_exploration_candidate_max_count",
        default = false
      )

  object EnableContentExplorationSimclusterColdPostsCandidateBoostingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_content_exploration_simcluster_cold_posts_candidate_boosting",
        default = false
      )

  object ContentExplorationBoostPosParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_content_exploration_boost_pos",
        default = 100,
        min = 0,
        max = 1000
      )

  object EnableDeepRetrievalMixedCandidateBoostingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_deep_retrieval_mixed_candidate_boosting",
        default = false
      )

  object CategoryColdStartTierOneProbabilityParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_category_cold_start_tier_one_probability",
        default = 0,
        min = 0,
        max = 1
      )

  object CategoryColdStartProbabilisticReturnParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_category_cold_start_probabilistic_return",
        default = 0,
        min = 0,
        max = 1
      )

  object ContentExplorationViewerMaxFollowersParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_content_exploration_viewer_max_followers",
        default = 100000,
        min = 0,
        max = 1000000000
      )

  object EnableContentExplorationMixedCandidateBoostingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_content_exploration_mixed_candidate_boosting",
        default = false
      )

  object DeepRetrievalBoostPosParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_deep_retrieval_boost_pos",
        default = 100,
        min = 0,
        max = 1000
      )

  object DeepRetrievalI2iProbabilityParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_deep_retrieval_i2i_probability",
        default = 0,
        min = 0,
        max = 1
      )

  object FetchParams {
    object FRSMaxTweetsToFetchParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_frs_max_tweets_to_fetch",
          default = 100,
          min = 0,
          max = 10000
        )

    object InNetworkMaxTweetsToFetchParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_in_network_max_tweets_to_fetch",
          default = 600,
          min = 0,
          max = 10000
        )

    object TweetMixerMaxTweetsToFetchParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_tweet_mixer_max_tweets_to_fetch",
          default = 400,
          min = 0,
          max = 10000
        )

    object UTEGMaxTweetsToFetchParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_uteg_max_tweets_to_fetch",
          default = 300,
          min = 0,
          max = 10000
        )
  }

  object QualityFactor {

    object InNetworkMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_earlybird_max_tweets_to_score",
          default = 600,
          min = 0,
          max = 10000
        )

    object UtegMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_uteg_max_tweets_to_score",
          default = 300,
          min = 0,
          max = 10000
        )

    object TweetMixerMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_tweet_mixer_max_tweets_to_score",
          default = 400,
          min = 0,
          max = 10000
        )

    object ListsMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_lists_max_tweets_to_score",
          default = 100,
          min = 0,
          max = 100
        )

    object BackfillMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_backfill_max_tweets_to_score",
          default = 200,
          min = 0,
          max = 10000
        )

    object CommunitiesMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_communities_max_tweets_to_score",
          default = 100,
          min = 0,
          max = 10000
        )
  }

  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_server_max_results",
        default = 50,
        min = 1,
        max = 1500
      )

  object DefaultRequestedMaxResultsParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_default_requested_max_results",
        default = 50,
        min = 1,
        max = 1500
      )

  object CachedScoredTweets {
    object TTLParam
        extends FSBoundedParam[Duration](
          name = "scored_tweets_cached_scored_tweets_ttl_minutes",
          default = 3.minutes,
          min = 0.minute,
          max = 60.minutes
        )
        with HasDurationConversion {
      override val durationConversion: DurationConversion = DurationConversion.FromMinutes
    }

    object MinCachedTweetsParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_cached_scored_tweets_min_cached_tweets",
          default = 30,
          min = 0,
          max = 1000
        )
  }

  object FeatureHydration {

    object EnableRealTimeEntityRealGraphFeaturesParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_real_time_entity_real_graph_features",
          default = false
        )

    object EnableFollowedUserScoreBackfillFeaturesParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_followed_user_score_backfill_features",
          default = false
        )

    object EnableSgsMutuallyFollowedUserFeaturesParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_sgs_mutually_followed_user_features",
          default = false
        )

    object EnableTopicSocialProofFeaturesParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_topic_social_proof_features",
          default = false
        )

    object EnableMediaClusterFeatureHydrationParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_media_cluster_feature",
          default = false
        )

    object EnableMediaCompletionRateFeatureHydrationParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_media_completion_rate_feature",
          default = false
        )

    object EnableImageClusterFeatureHydrationParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_image_cluster_feature",
          default = false
        )

    object EnableClipImagesClusterIdFeatureHydrationParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_clip_images_cluster_id_feature",
          default = false
        )

    object EnableMultiModalEmbeddingsFeatureHydratorParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_multi_modal_embeddings_feature_hydrator",
          default = false
        )

    object EnableTweetTextV8EmbeddingFeatureParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_tweet_text_v8_embedding_feature",
          default = false
        )

    object EnableUserEngagedLanguagesFeaturesParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_user_engaged_languages_features",
          default = false
        )

    object EnableUserIdentifierFeaturesParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_user_identifier_features",
          default = false
        )

    object EnableUserHistoryEventsFeaturesParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_user_history_events_features",
          default = false
        )

    object EnableUserActionsFeatureParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_user_actions_feature",
          default = false
        )

    object EnableDenseUserActionsHydrationParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_dense_user_actions_feature",
          default = false
        )

    object EnableMediaClusterDecayParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_media_cluster_decay",
          default = false
        )

    object EnableImageClusterDecayParam
        extends FSParam[Boolean](
          name = "scored_tweets_feature_hydration_enable_image_cluster_decay",
          default = false
        )

    object UserHistoryEventsLengthParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_feature_hydration_user_history_events_length",
          default = 50,
          min = 0,
          max = 1000
        )

    object TwhinDiversityRescoringWeightParam
        extends FSBoundedParam[Double](
          name = "scored_tweets_feature_hydration_twhin_diversity_rescoring_weight",
          default = 0.0,
          min = -100.0,
          max = 100.0
        )

    object TwhinDiversityRescoringRatioParam
        extends FSBoundedParam[Double](
          name = "scored_tweets_feature_hydration_twhin_diversity_rescoring_ratio",
          default = 0.0,
          min = 0.0,
          max = 1.0
        )

    object CategoryDiversityRescoringWeightParam
        extends FSBoundedParam[Double](
          name = "scored_tweets_feature_hydration_category_diversity_rescoring_weight",
          default = 0.0,
          min = -1.0,
          max = 1.0
        )

    object CategoryDiversityKParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_feature_hydration_category_diversity_k",
          default = 5,
          min = 1,
          max = 100
        )
  }

  object ControlAiShowLessScaleFactorParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_control_ai_show_less_scale_factor",
        default = 0.05,
        min = 0.0,
        max = 1.0
      )

  object ControlAiShowMoreScaleFactorParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_control_ai_show_more_scale_factor",
        default = 20.0,
        min = 0.0,
        max = 1000.0
      )

  object ControlAiEmbeddingSimilarityThresholdParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_control_ai_embedding_similarity_threshold",
        default = 0.67,
        min = 0.0,
        max = 1.0
      )

  object CreatorInNetworkMultiplierParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_creator_in_network_multiplier",
        default = 1.0,
        min = 0.0,
        max = 100.0
      )

  object CreatorOutOfNetworkMultiplierParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_creator_out_of_network_multiplier",
        default = 1.0,
        min = 0.0,
        max = 100.0
      )

  object OutOfNetworkScaleFactorParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_out_of_network_scale_factor",
        default = 0.75,
        min = 0.0,
        max = 100.0
      )

  object ReplyScaleFactorParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_reply_scale_factor",
        default = 0.75,
        min = 0.0,
        max = 100.0
      )

  object EnableMediaDedupingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_media_deduping",
        default = false
      )

  object EnableMediaClusterDedupingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_media_cluster_deduping",
        default = false
      )

  object EnableClipImageClusterDedupingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_clip_image_cluster_deduping",
        default = false
      )

  object EnableScribeScoredCandidatesParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_scribing",
        default = false
      )

  object EnableCacheRetrievalSignalParam
      extends FSParam[Boolean](
        name = "scored_tweets_cache_retrieval_signal",
        default = false
      )

  object EnableCacheRequestInfoParam
      extends FSParam[Boolean](
        name = "scored_tweets_cache_request_info_signal",
        default = false
      )

  object EnableScoredPhoenixCandidatesKafkaSideEffectParam
      extends FSParam[Boolean](
        name = "scored_tweets_scored_phoenix_candidates_kafka_side_effect",
        default = false
      )

  object LiveContentScaleFactorParam
      extends DeciderBoundedParam[Double](
        DeciderKey.LiveSpacesFactor,
        default = 1.0,
        min = 0.1,
        max = 10000.0
      )

  object EarlybirdTensorflowModel {
    object InNetworkParam
        extends FSParam[String](
          name = "scored_tweets_in_network_earlybird_tensorflow_model",
          default = "timelines_recap_replica"
        )

    object FrsParam
        extends FSParam[String](
          name = "scored_tweets_frs_earlybird_tensorflow_model",
          default = "timelines_rectweet_replica"
        )

    object UtegParam
        extends FSParam[String](
          name = "scored_tweets_uteg_earlybird_tensorflow_model",
          default = "timelines_rectweet_replica"
        )
  }

  object MtlNormalization {

    object EnableMtlNormalizationParam
        extends FSParam[Boolean](
          name = "scored_tweets_enable_mtl_normalization",
          default = true
        )

    object AlphaParam
        extends DeciderBoundedParam[Double](
          decider = DeciderKey.MtlNormalizationAlpha,
          default = 100.0,
          min = 0.0,
          max = 100.0
        )

    object BetaParam
        extends FSBoundedParam[Long](
          name = "scored_tweets_mtl_normalization_beta",
          default = 100000000L,
          min = 0L,
          max = 1000000000L
        )

    object GammaParam
        extends FSBoundedParam[Long](
          name = "scored_tweets_mtl_normalization_gamma",
          default = 5000000L,
          min = 1L,
          max = 100000000L
        )
  }

  object EarlybirdMaxResultsPerPartitionParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_earlybird_max_results_per_partition",
        default = 300,
        min = 0,
        max = 1000
      )

  object TweetMixerRankingModeForStatsRecallAtKParam
      extends FSParam[String](
        name = "scored_tweets_tweet_mixer_ranking_mode_for_stats_recall_at_k",
        default = "Interleave"
      )

  object EnablePublishCommonFeaturesKafkaDeciderParam
      extends BooleanDeciderParam(decider = DeciderKey.EnablePublishCommonFeaturesKafka)

  object AuthorDiversityDecayFactor
      extends FSBoundedParam[Double](
        name = "scored_tweets_author_diversity_decay_factor",
        default = 0.5,
        min = 0.0,
        max = 1.0,
      )

  object AuthorDiversityOutNetworkDecayFactor
      extends FSBoundedParam[Double](
        name = "scored_tweets_author_diversity_out_network_decay_factor",
        default = 0.5,
        min = 0.0,
        max = 1.0,
      )
  object AuthorDiversityInNetworkDecayFactor
      extends FSBoundedParam[Double](
        name = "scored_tweets_author_diversity_in_network_decay_factor",
        default = 0.5,
        min = 0.0,
        max = 1.0,
      )

  object AuthorDiversityFloor
      extends FSBoundedParam[Double](
        name = "scored_tweets_author_diversity_floor",
        default = 0.25,
        min = 0.0,
        max = 1.0,
      )

  object AuthorDiversityOutNetworkFloor
      extends FSBoundedParam[Double](
        name = "scored_tweets_author_diversity_out_network_floor",
        default = 0.25,
        min = 0.0,
        max = 1.0,
      )
  object AuthorDiversityInNetworkFloor
      extends FSBoundedParam[Double](
        name = "scored_tweets_author_diversity_in_network_floor",
        default = 0.25,
        min = 0.0,
        max = 1.0,
      )

  object SmallFollowGraphAuthorDiversityDecayFactor
      extends FSBoundedParam[Double](
        name = "scored_tweets_small_follow_graph_author_diversity_decay_factor",
        default = 0.5,
        min = 0.0,
        max = 1.0,
      )

  object SmallFollowGraphAuthorDiversityFloor
      extends FSBoundedParam[Double](
        name = "scored_tweets_small_follow_graph_author_diversity_floor",
        default = 0.25,
        min = 0.0,
        max = 1.0,
      )

  object EnableDeepRetrievalMaxCountParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_deep_retrieval_max_count",
        default = false
      )

  object DeepRetrievalMaxCountParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_deep_retrieval_max_count",
        default = 1,
        min = 0,
        max = 1000
      )

  object EnableEvergreenDeepRetrievalMaxCountParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_evergreen_deep_retrieval_max_count",
        default = false
      )

  object EvergreenDeepRetrievalMaxCountParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_evergreen_deep_retrieval_max_count",
        default = 1,
        min = 0,
        max = 1000
      )

  object EnableEvergreenDeepRetrievalCrossBorderMaxCountParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_evergreen_deep_retrieval_cross_border_max_count",
        default = false
      )

  object EvergreenDeepRetrievalCrossBorderMaxCountParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_evergreen_deep_retrieval_cross_border_max_count",
        default = 1,
        min = 0,
        max = 1000
      )
  object EnableControlAiParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_control_ai",
        default = false
      )

  object EnableHeartbeatOptimizerWeightsParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_heartbeat_optimizer_weights",
        default = false
      )

  object HeartbeatOptimizerParamsMHPkey
      extends FSParam[String](
        name = "scored_tweets_heartbeat_optimizer_params_mh_pkey",
        default = "0"
      )

  object EnableHeuristicScoringPipeline
      extends FSParam[Boolean](
        name = "scored_tweets_enable_heuristic_scoring_pipeline",
        default = true
      )

  object EnablePhoenixScoreParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_phoenix_score",
        default = false
      )

  object EnablePhoenixRescoreParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_phoenix_rescore",
        default = false
      )

  object EnableColdStartFilterParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_cold_start_filter",
        default = false
      )

  object EnableImpressionBasedAuthorDecay
      extends FSParam[Boolean](
        name = "scored_tweets_enable_impression_based_author_decay",
        default = false
      )

  object EnableCandidateSourceDiversityDecay
      extends FSParam[Boolean](
        name = "scored_tweets_enable_candidate_source_diversity_decay",
        default = false
      )

  object CandidateSourceDiversityDecayFactor
      extends FSBoundedParam[Double](
        name = "scored_tweets_candidate_source_diversity_decay_factor",
        default = 0.9,
        min = 0.0,
        max = 1.0,
      )

  object CandidateSourceDiversityFloor
      extends FSBoundedParam[Double](
        name = "scored_tweets_candidate_source_diversity_floor",
        default = 0.8,
        min = 0.0,
        max = 1.0,
      )

  object EnableHomeMixerFeaturesService
      extends FSParam[Boolean](
        name = "scored_tweets_enable_home_mixer_features_service",
        default = false
      )

  object GrokSlopScoreDecayValueParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_grok_slop_score_decay_value",
        default = 1.0,
        min = 0.0,
        max = 1.0
      )

  object MultiModalEmbeddingRescorerGammaParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_multi_modal_embedding_rescorer_gamma",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object MultiModalEmbeddingRescorerMinScoreParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_multi_modal_embedding_rescorer_min_score",
        default = 1.0,
        min = 0.0,
        max = 1.0
      )

  object EnableContentFeatureFromTesService
      extends FSParam[Boolean](
        name = "scored_tweets_enable_home_mixer_feature_tweet_entity_service",
        default = false
      )

  object EnableLowSignalUserCheck
      extends FSParam[Boolean](
        name = "scored_tweets_enable_low_signal_user_check",
        default = false
      )

  object LowSignalUserMaxSignalCount
      extends FSBoundedParam[Int](
        name = "scored_tweets_low_signal_user_max_signal_count",
        default = 10,
        min = 0,
        max = 100000
      )

  object MultiModalEmbeddingRescorerNumCandidatesParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_multi_modal_embedding_rescorer_num_candidates",
        default = 70,
        min = 1,
        max = 500
      )

  object EnableScoredCandidateFeatureKeysKafkaPublishingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_scored_candidate_feature_keys_kafka_publishing",
        default = false
      )

  object EnableEarlybirdCommunitiesQueryLinearRankingParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_earlybird_communities_query_linear_ranking",
        default = false
      )

  object EarlyBirdCommunitiesMaxSearchResultsParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_earlybird_communities_max_search_results",
        default = 100,
        min = 0,
        max = 1000
      )

  object EnableRecentFeedbackCheckParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_recent_feedback_check",
        default = false
      )

  object ScribedScoredCandidateNumParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_scribed_scored_candidate_num",
        default = 2,
        min = 0,
        max = 2000
      )

  object EnableRecentEngagementCacheRefreshParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_recent_engagement_cache_refresh",
        default = false
      )

  object EnableLanguageFilter
      extends FSParam[Boolean](
        name = "scored_tweets_enable_language_filter",
        default = false
      )

  object EnableGrokAutoTranslateLanguageFilter
      extends FSParam[Boolean](
        name = "scored_tweets_enable_grok_auto_translate_language_filter",
        default = false
      )
}
