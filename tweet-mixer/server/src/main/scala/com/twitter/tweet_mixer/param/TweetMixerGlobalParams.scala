package com.twitter.tweet_mixer.param

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.simclusters_v2.common.VersionId
import com.twitter.simclusters_v2.thriftscala.TwhinEmbeddingDataset
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.decider.BooleanDeciderParam
import com.twitter.timelines.configapi.decider.DeciderBoundedParam
import com.twitter.tweet_mixer.param.decider.DeciderKey
import com.twitter.util.Duration

/**
 * Instantiate Params that do not relate to a specific product.
 *
 * @see [[com.twitter.product_mixer.core.product.ProductParamConfig.supportedClientFSName]]
 */
object TweetMixerGlobalParams {

  object BlendingEnum extends Enumeration {
    val RoundRobinBlending = Value
    val SignalPriorityBlending = Value
    val WeightedPriorityBlending = Value
    val RoundRobinSourceBlending = Value
    val RoundRobinSourceSignalBlending = Value
    val RankingOnly = Value
  }

  object MaxCandidateNumPerSourceKeyParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_candidate_per_sourcekey_max_num",
        default = 200,
        min = 0,
        max = 1000
      )

  object MaxTweetAgeHoursParam
      extends FSBoundedParam[Duration](
        name = "tweet_mixer_max_tweet_age_hours",
        default = 24.hours,
        min = 1.hours,
        max = 720.hours
      )
      with HasDurationConversion {

    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  object MinVideoDurationParam
      extends FSBoundedParam[Duration](
        name = "tweet_mixer_min_video_duration_seconds",
        default = 6.seconds,
        min = 1.seconds,
        max = 720.seconds
      )
      with HasDurationConversion {

    override val durationConversion: DurationConversion = DurationConversion.FromSeconds
  }

  object ShortFormMinDurationParam
      extends FSBoundedParam[Duration](
        name = "tweet_mixer_short_form_min_duration_seconds",
        default = 5.seconds,
        min = 1.seconds,
        max = 14400.seconds // 4 hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromSeconds
  }

  object LongFormMinDurationParam
      extends FSBoundedParam[Duration](
        name = "tweet_mixer_long_form_min_duration_seconds",
        default = 120.seconds,
        min = 1.seconds,
        max = 14400.seconds // 4 hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromSeconds
  }

  object ShortFormMaxDurationParam
      extends FSBoundedParam[Duration](
        name = "tweet_mixer_short_form_max_duration_seconds",
        default = 300.seconds,
        min = 1.seconds,
        max = 14400.seconds // 4 hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromSeconds
  }

  object LongFormMaxDurationParam
      extends FSBoundedParam[Duration](
        name = "tweet_mixer_long_form_max_duration_seconds",
        default = 14400.seconds,
        min = 1.seconds,
        max = 14400.seconds // 4 hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromSeconds
  }

  object ShortFormMinAspectRatioParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_short_form_min_aspect_ratio",
        default = 0.5625,
        min = 0.0,
        max = 4.0
      )

  object LongFormMinAspectRatioParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_long_form_min_aspect_ratio",
        default = 1.77, // 16:9
        min = 0.0,
        max = 4.0
      )

  object ShortFormMaxAspectRatioParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_short_form_max_aspect_ratio",
        default = 1.0,
        min = 0.0,
        max = 4.0
      )

  object LongFormMaxAspectRatioParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_long_form_max_aspect_ratio",
        default = 2.0, // 16:9
        min = 0.0,
        max = 4.0
      )

  object ShortFormMinWidthParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_short_form_min_width",
        default = 540,
        min = 1,
        max = 8192
      )

  object LongFormMinWidthParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_long_form_min_width",
        default = 1280,
        min = 1,
        max = 8192
      )

  object ShortFormMinHeightParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_short_form_min_height",
        default = 960,
        min = 1,
        max = 8192
      )

  object LongFormMinHeightParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_long_form_min_height",
        default = 720,
        min = 1,
        max = 8192
      )

  object ShortFormMaxResultParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_short_form_max_result",
        default = 200,
        min = 1,
        max = 2000
      )

  object LongFormMaxResultParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_long_form_max_result",
        default = 200,
        min = 1,
        max = 2000
      )

  object MemeMaxResultParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_meme_max_result",
        default = 200,
        min = 1,
        max = 2000
      )

  object EnableDebugMode
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_debug_mode",
        default = false
      )

  object EnableLowSignalUserCheck
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_low_signal_user_check",
        default = false
      )

  object EnableMaxFollowersGate
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_max_followers_gate",
        default = false
      )

  object MaxFollowersCountGateParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_max_followers_count_gate",
        default = 100000,
        min = 1,
        max = 1000000000
      )

  object LowSignalUserGrokTopicRatioParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_low_signal_user_grok_topic_ratio",
        default = 0.7,
        min = 0.0,
        max = 1.0
      )

  object LowSignalUserSimclustersRatioParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_low_signal_user_simclusters_ratio",
        default = 0.15,
        min = 0.0,
        max = 1.0
      )

  object LowSignalUserPopGeoRatioParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_low_signal_user_pop_geo_ratio",
        default = 0.15,
        min = 0.0,
        max = 1.0
      )

  object LowSignalUserBackfillRatioParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_low_signal_user_backfill_ratio",
        default = 0.01,
        min = 0.0,
        max = 1.0
      )

  object EnableNonEmptySearchHistoryUserCheck
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_non_empty_search_history_user_check",
        default = false
      )

  object EnableTweetEntityServiceMigration
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_tweet_entity_service_migration",
        default = false
      )

  object EnableTweetEntityServiceMigrationDiffy
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_tweet_entity_service_migration_diffy",
        default = false
      )

  object EnableUSSFeatureHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_uss_feature_hydrator",
        default = true
      )

  object EnableUSSGrokCategoryFeatureHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_uss_grok_category_feature_hydrator",
        default = false
      )

  object EnableUSSDeepRetrievalTweetEmbeddingFeatureHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_uss_deep_retrieval_tweet_embedding_feature_hydrator",
        default = false
      )

  object EnableImpressionBloomFilterHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_impression_bloom_filter_hydrator",
        default = false
      )

  object EnableGizmoduckQueryFeatureHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_gizmoduck_hydrator",
        default = false
      )

  object EnableVideoBloomFilterHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_video_bloom_filter_hydrator",
        default = false
      )

  object EnableSignalInfoHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_signal_info_hydrator",
        default = true
      )

  object EnableRequestCountryPlaceIdHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_request_country_place_id_hydrator",
        default = true
      )

  object EnableUserTopicIdsHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_user_topic_ids_hydrator",
        default = false
      )

  object EnableGrokFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_grok_filter",
        default = false
      )

  object EnableVideoTweetFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_video_tweet_filter",
        default = false
      )

  object EnableShortVideoFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_short_video_filter",
        default = false
      )

  object EnableLongFormVideoFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_long_form_video_filter",
        default = false
      )

  object EnableShortFormVideoFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_short_form_video_filter",
        default = false
      )

  object EnablePortraitVideoFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_portrait_video_filter",
        default = false
      )

  object EnableMediaIdDedupFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_media_id_dedup_filter",
        default = false
      )

  object EnableMediaMetadataCandidateFeatureHydrator
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_media_metadata_candidate_feature_hydrator",
        default = false
      )

  object EnableMediaClusterIdDedupFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_media_cluster_id_dedup_filter",
        default = false
      )

  object EnableHydraScoringSideEffect
      extends BooleanDeciderParam(decider = DeciderKey.EnableHydraScoringSideEffect)

  object EnableEvergreenVideosSideEffect
      extends BooleanDeciderParam(decider = DeciderKey.EnableEvergreenVideosSideEffect)

  object BlendingParam
      extends FSEnumParam[BlendingEnum.type](
        name = "tweet_mixer_blending_id",
        default = BlendingEnum.RoundRobinBlending,
        enum = BlendingEnum
      )

  object HaploliteTweetsBasedEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_haplolite_based_enabled",
        default = false
      )

  object MaxHaploliteTweetsParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_max_haplolite_tweets",
        default = 4500,
        min = 0,
        max = 10000
      )

  object TwhinConsumerBasedEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_twhin_consumer_based_enabled",
        default = true
      )

  object TwhinRebuildTweetSimilarityDatasetEnum extends Enumeration {
    private def normalizeDatasetName(originalName: String): String = {
      originalName.replace("_", "-").toLowerCase
    }

    val TwhinTweet: Value = Value(
      normalizeDatasetName(TwhinEmbeddingDataset.TwhinTweet.originalName))
    val RefreshedTwhinTweet: Value = Value(
      normalizeDatasetName(TwhinEmbeddingDataset.RefreshedTwhinTweet.originalName))
    val enumToTwhinRebuildVersionIdMap: Map[
      TwhinRebuildTweetSimilarityDatasetEnum.Value,
      VersionId
    ] = Map(
      TwhinTweet -> TwhinEmbeddingDataset.TwhinTweet.value,
      RefreshedTwhinTweet -> TwhinEmbeddingDataset.RefreshedTwhinTweet.value
    )
  }

  // Which VecDB dataset to use in the tweet based TwHIN rebuild ANN candidate source
  object TwhinRebuildTweetSimilarityDatasetParam
      extends FSEnumParam[TwhinRebuildTweetSimilarityDatasetEnum.type](
        name = "tweet_mixer_twhin_rebuild_tweet_similarity_dataset_id",
        default = TwhinRebuildTweetSimilarityDatasetEnum.TwhinTweet,
        enum = TwhinRebuildTweetSimilarityDatasetEnum
      )

  // Enables Tweet Based TwHIN rebuild ANN candidate source
  object TwhinRebuildTweetSimilarityEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_twhin_rebuild_tweet_similarity_enabled",
        default = false
      )

  // Enables User Tweet Based TwHIN rebuild ANN candidate source
  object TwhinRebuildUserTweetSimilarityEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_twhin_rebuild_user_tweet_similarity_enabled",
        default = false
      )

  object TwhinRebuildUserTweetSimilarityMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_twhin_rebuild_user_tweet_similarity_max_candidates",
        default = 100,
        min = 0,
        max = 2000
      )

  object TwhinRebuildUserTweetVectorDBName
      extends FSParam[String](
        name = "tweet_mixer_twhin_rebuild_user_tweet_vectordb_name",
        default = "refreshed-twhin-tweet"
      )

  // How many candidates to retrieve for each tweet signal in TwHIN rebuild ANN candidate source
  object TwhinRebuildTweetSimilarityMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_twhin_rebuild_tweet_similarity_max_candidates",
        default = 100,
        min = 0,
        max = 200
      )

  object TwhinTweetSimilarityEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_twhin_tweet_similarity_enabled",
        default = false
      )

  object SimclustersInterestedInEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_simclusters_interested_in_enabled",
        default = true
      )

  object SimclustersProducerBasedEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_simclusters_producer_based_enabled",
        default = true
      )

  object SimclustersTweetBasedEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_simclusters_tweet_based_enabled",
        default = true
      )

  object SimclustersPromotedCreatorEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_simclusters_promoted_creator_enabled",
        default = false
      )

  object UTEGEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_uteg_enabled",
        default = false
      )

  object UTGTweetBasedEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_utg_tweet_based_enabled",
        default = true
      )

  object UTGProducerBasedEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_utg_producer_based_enabled",
        default = true
      )

  object UTGTweetBasedExpansionEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_utg_tweet_based_expansion_enabled",
        default = true
      )

  object UVGTweetBasedEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_uvg_tweet_based_enabled",
        default = true
      )

  object UVGTweetBasedExpansionEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_uvg_tweet_based_expansion_enabled",
        default = true
      )

  object ContentExplorationEmbeddingSimilarityEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_embedding_similarity_enabled",
        default = false
      )

  object ContentExplorationMediaEmbeddingSimilarityEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_media_embedding_similarity_enabled",
        default = false
      )

  object ContentExplorationMultimodalEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_multimodal_enabled",
        default = false
      )

  object ContentExplorationNonVideoTweetFeaturesEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_non_video_tweet_features_enabled",
        default = false
      )

  object ContentExplorationVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_content_exploration_vectordb_collection_name",
        default = "content-exploration-text-emb"
      )

  object ContentExplorationEmbeddingANNMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_embedding_ann_max_candidates",
        default = 10,
        min = 0,
        max = 100
      )

  object ContentExplorationEmbeddingANNScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_content_exploration_embedding_ann_score_threshold",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object UserInterestSummarySimilarityEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_user_interest_summary_similarity_enabled",
        default = false
      )

  object UserInterestSummarySimilarityVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_user_interest_summary_similarity_vectordb_collection_name",
        default = "content-exploration-description-text-emb"
      )

  object UserInterestSummarySimilarityMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_user_interest_summary_similarity_max_candidates",
        default = 10,
        min = 0,
        max = 100
      )

  object UserInterestSummarySimilarityScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_user_interest_summary_similarity_score_threshold",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object ContentExplorationEmbeddingSimilarityTierTwoEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_embedding_similarity_tier_two_enabled",
        default = false
      )

  object ContentExplorationEmbeddingSimilarityTierTwoVectorDBCollectionName
      extends FSParam[String](
        name =
          "tweet_mixer_content_exploration_embedding_similarity_tier_two_vectordb_collection_name",
        default = "content-exploration-description-text-emb"
      )

  object ContentExplorationEmbeddingSimilarityTierTwoMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_embedding_similarity_tier_two_max_candidates",
        default = 10,
        min = 0,
        max = 100
      )

  object ContentExplorationEmbeddingSimilarityTierTwoScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_content_exploration_embedding_similarity_tier_two_score_threshold",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object ContentExplorationDRTweetTweetEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_enabled",
        default = false
      )

  object ContentExplorationDRTweetTweetVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_vectordb_collection_name",
        default = "content-exploration-deep-retrieval"
      )

  object ContentExplorationDRTweetTweetMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_max_candidates",
        default = 10,
        min = 0,
        max = 100
      )

  object ContentExplorationDRTweetTweetScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_score_threshold",
        default = -1.0,
        min = -1.0,
        max = 1.0
      )

  object ContentExplorationDRTweetTweetEnableRandomEmbedding
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_enable_random_embedding",
        default = false
      )

  object ContentExplorationEvergreenDRTweetTweetEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_evergreen_dr_tweet_tweet_enabled",
        default = false
      )

  object ContentExplorationEvergreenDRTweetTweetVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_content_exploration_evergreen_dr_tweet_tweet_vectordb_collection_name",
        default = "content-exploration-deep-retrieval-exp1"
      )

  object ContentExplorationEvergreenDRTweetTweetMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_evergreen_dr_tweet_tweet_max_candidates",
        default = 10,
        min = 0,
        max = 100
      )

  object ContentExplorationEvergreenDRTweetTweetScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_content_exploration_evergreen_dr_tweet_tweet_score_threshold",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object ContentExplorationDRTweetTweetTierTwoEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_tier_two_enabled",
        default = false
      )

  object ContentExplorationDRTweetTweetTierTwoVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_tier_two_vectordb_collection_name",
        default = "content-exploration-deep-retrieval"
      )

  object ContentExplorationDRTweetTweetTierTwoMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_tier_two_max_candidates",
        default = 10,
        min = 0,
        max = 100
      )

  object ContentExplorationDRTweetTweetTierTwoScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_content_exploration_dr_tweet_tweet_tier_two_score_threshold",
        default = -1.0,
        min = -1.0,
        max = 1.0
      )

  object ContentExplorationDRUserTweetEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_dr_user_tweet_enabled",
        default = false
      )

  object ContentExplorationDRUserEmbeddingModelName
      extends FSParam[String](
        name = "tweet_mixer_content_exploration_dr_user_embedding_model_name",
        default = "deep_retrieval_exp2"
      )

  object ContentExplorationDRUserTweetVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_content_exploration_dr_user_tweet_vectordb_collection_name",
        default = "content-exploration-deep-retrieval"
      )

  object ContentExplorationDRUserTweetMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_dr_user_tweet_max_candidates",
        default = 50,
        min = 0,
        max = 100
      )

  object ContentExplorationDRUserTweetTierTwoEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_dr_user_tweet_tier_two_enabled",
        default = false
      )

  object ContentExplorationDRUserTweetTierTwoMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_dr_user_tweet_tier_two_max_candidates",
        default = 50,
        min = 0,
        max = 100
      )

  object ContentExplorationMaxViewCountThreshold
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_max_view_count_threshold",
        default = 100,
        min = 0,
        max = 1000000000
      )

  object ContentExplorationTier2MaxViewCountThreshold
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_tier2_max_view_count_threshold",
        default = 900,
        min = 0,
        max = 1000000000
      )

  object ContentExplorationOnceInTimesShow
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_once_in_times_show",
        default = 3,
        min = 1,
        max = 20
      )

  object ContentExplorationMinHoursSinceLastRequestParam
      extends FSBoundedParam[Duration](
        name = "tweet_mixer_content_exploration_min_time_since_last_request_in_hours",
        default = 2.hours,
        min = 0.hours,
        max = 24.hours
      )
      with HasDurationConversion {

    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  object ContentExplorationSimclusterColdPostsEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_content_exploration_simcluster_cold_posts_enabled",
        default = false
      )

  object ContentExplorationSimclusterColdPostsMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_simcluster_cold_posts_max_candidates",
        default = 100,
        min = 0,
        max = 1000
      )

  object ContentExplorationSimclusterColdPostsPostsPerSimcluster
      extends FSBoundedParam[Int](
        name = "tweet_mixer_content_exploration_simcluster_cold_posts_posts_per_simcluster",
        default = 20,
        min = 0,
        max = 100
      )

  object LastNonPollingTimeFeatureHydratorEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_last_non_polling_time_feature_hydrator_enabled",
        default = false
      )

  object EvergreenDRUserTweetEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_evergreen_dr_user_tweet_enabled",
        default = false
      )

  object EvergreenDRUserEmbeddingModelName
      extends FSParam[String](
        name = "tweet_mixer_evergreen_dr_user_embedding_model_name",
        default = "deep_retrieval_exp3"
      )

  object EvergreenDRUserTweetVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_evergreen_dr_user_tweet_vectordb_collection_name",
        default = "content-exploration-deep-retrieval-exp1"
      )

  object EvergreenDRUserTweetMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_evergreen_dr_user_tweet_max_candidates",
        default = 50,
        min = 0,
        max = 1000
      )

  object EvergreenDRCrossBorderUserTweetEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_evergreen_dr_cross_border_user_tweet_enabled",
        default = false
      )

  object EvergreenDRCrossBorderUserEmbeddingModelName
      extends FSParam[String](
        name = "tweet_mixer_evergreen_dr_cross_border_user_embedding_model_name",
        default = "deep_retrieval_exp3"
      )

  object EvergreenDRCrossBorderUserTweetVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_evergreen_dr_cross_border_user_tweet_vectordb_collection_name",
        default = "content-exploration-cross-border-dr-exp1"
      )

  object EvergreenDRCrossBorderUserTweetMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_evergreen_dr_cross_border_user_tweet_max_candidates",
        default = 50,
        min = 0,
        max = 1000
      )

  // Enables User Based Deep Retrieval ANN candidate source
  object DeepRetrievalUserTweetANNEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_user_tweet_ann_enabled",
        default = false
      )

  object DeepRetrievalCategoricalUserTweetANNEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_categorical_user_tweet_ann_enabled",
        default = false
      )

  object DeepRetrievalIsHighQualityEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_is_high_quality_enabled",
        default = false
      )

  object DeepRetrievalIsLowNegEngRatioEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_is_low_neg_ratio_enabled",
        default = false
      )

  object DeepRetrievalTweetTweetANNEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_ann_enabled",
        default = false
      )

  object DeepRetrievalTweetTweetEmbeddingANNEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_ann_enabled",
        default = false
      )

  object DeepRetrievalTweetTweetEmbeddingRandomSize
      extends FSBoundedParam[Int](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_random_size",
        default = 7,
        min = 0,
        max = 100000
      )

  object DeepRetrievalTweetTweetEmbeddingTimeDecay
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_time_decay",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object DeepRetrievalTweetTweetEmbeddingSeedMaxAgeInDays
      extends FSBoundedParam[Duration](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_seed_max_age_in_days",
        default = 1.days,
        min = 0.days,
        max = 2000.days
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromDays
  }

  object DeepRetrievalTweetTweetEmbeddingTimeOfDayBoostWeight
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_time_of_day_boost_weight",
        default = 1.0,
        min = 0.0,
        max = 1000.0
      )

  object DeepRetrievalTweetTweetEmbeddingDayOfWeekBoostWeight
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_day_of_week_boost_weight",
        default = 1.0,
        min = 0.0,
        max = 1000.0
      )

  object DeepRetrievalTweetTweetEmbeddingEngagementBoostWeight
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_engagement_boost_weight",
        default = 1.0,
        min = 0.0,
        max = 1000.0
      )

  object DeepRetrievalTweetTweetEmbeddingMinPriority
      extends FSBoundedParam[Int](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_min_priority",
        default = 3,
        min = -1,
        max = 5
      )

  object DeepRetrievalVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_deep_retrieval_vectordb_collection_name",
        default = "tweet-deep-retrieval"
      )

  object DeepRetrievalI2iVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_deep_retrieval_i2i_vectordb_collection_name",
        default = "tweet-deep-retrieval-exp3"
      )

  object DeepRetrievalI2iEmbVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_deep_retrieval_i2i_emb_vectordb_collection_name",
        default = "content-exploration-deep-retrieval"
      )

  object DeepRetrievalEnableGPU
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_enable_gpu",
        default = false
      )

  object DeepRetrievalUserTweetANNMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_deep_retrieval_user_tweet_ann_max_candidates",
        default = 200,
        min = 0,
        max = 5000
      )

  object DeepRetrievalNonVideoTweetFeaturesEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_non_video_tweet_features_enabled",
        default = false
      )

  object DeepRetrievalFilterOldSignalsEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_filter_old_signals_enabled",
        default = false
      )

  object DeepRetrievalTweetTweetANNMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_ann_max_candidates",
        default = 50,
        min = 0,
        max = 500
      )

  object DeepRetrievalTweetTweetANNScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_ann_score_threshold_param",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object USSDeepRetrievalSimilarityTweetTweetANNScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_uss_deep_retrieval_similarity_embedding_threshold_param",
        default = 0.8,
        min = 0.0,
        max = 1.0
      )

  object USSDeepRetrievalStratoTimeout
      extends FSBoundedParam[Int](
        name = "tweet_mixer_uss_deep_retrieval_strato_timeout_param",
        default = 130,
        min = 100,
        max = 500
      )

  object OutlierDeepRetrievalStratoTimeout
      extends FSBoundedParam[Int](
        name = "tweet_mixer_outlier_deep_retrieval_strato_timeout_param",
        default = 200,
        min = 100,
        max = 500
      )

  object DeepRetrievalUserTweetANNScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_user_tweet_ann_score_threshold_param",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object DeepRetrievalTweetTweetEmbeddingANNScoreThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_ann_score_threshold_param",
        default = 0.5,
        min = 0.0,
        max = 1.0
      )

  object DeepRetrievalTweetTweetANNScoreMaxThreshold
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_tweet_embedding_score_max_threshold_param",
        default = 1.0,
        min = 0.0,
        max = 1.0
      )

  object DeepRetrievalTweetTweetEmbeddingANNMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_deep_retrieval_tweet_tweet_embedding_ann_max_candidates",
        default = 20,
        min = 0,
        max = 500
      )

  object MediaDeepRetrievalUserTweetANNEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_media_deep_retrieval_user_tweet_ann_enabled",
        default = false
      )

  object MediaDeepRetrievalIsHighQualityEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_media_deep_retrieval_is_high_quality_enabled",
        default = false
      )

  object MediaDeepRetrievalIsLowNegEngRatioEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_media_deep_retrieval_is_low_neg_ratio_enabled",
        default = false
      )

  object MediaEvergreenDeepRetrievalUserTweetANNEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_media_evergreen_deep_retrieval_user_tweet_ann_enabled",
        default = false
      )

  object MediaPromotedCreatorDeepRetrievalUserTweetANNEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_media_promoted_creator_deep_retrieval_user_tweet_ann_enabled",
        default = false
      )

  object MediaDeepRetrievalVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_media_deep_retrieval_vectordb_collection_name",
        default = "tweet-deep-retrieval-media"
      )

  object MediaEvergreenDeepRetrievalVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_media_evergreen_deep_retrieval_vectordb_collection_name",
        default = "tweet-deep-retrieval-media-exp2"
      )

  object MediaPromotedCreatorDeepRetrievalVectorDBCollectionName
      extends FSParam[String](
        name = "tweet_mixer_media_promoted_creator_deep_retrieval_vectordb_collection_name",
        default = "creator-incentive-retrieval-v1"
      )

  object MediaDeepRetrievalUserTweetANNMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_media_deep_retrieval_user_tweet_ann_max_candidates",
        default = 200,
        min = 0,
        max = 1000
      )

  object MediaEvergreenDeepRetrievalUserTweetANNMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_media_evergreen_deep_retrieval_user_tweet_ann_max_candidates",
        default = 200,
        min = 0,
        max = 1000
      )

  object MediaPromotedCreatorDeepRetrievalUserTweetANNMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_media_promoted_creator_deep_retrieval_user_tweet_ann_max_candidates",
        default = 200,
        min = 0,
        max = 1000
      )

  object EnableRelatedCreatorParam
      extends FSParam[Boolean](
        name = "tweet_mixer_related_creator_enabled",
        default = false
      )

  object MediaRelatedCreatorMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_media_related_creator_max_candidates",
        default = 100,
        min = 0,
        max = 200
      )

  object EnableDeepRetrievalAdhocDecider
      extends BooleanDeciderParam(
        DeciderKey.EnableDeepRetrievalAdhocSideEffect
      )

  object DeepRetreivalAdhocMaxResultsDivByTenDecider
      extends DeciderBoundedParam[Double](
        DeciderKey.DeepRetrievalSideEffectNumCandidatesDivByTen,
        100.0,
        0.1,
        10000.0)

  object EnableTESMigrationDiffy
      extends BooleanDeciderParam(
        DeciderKey.EnableTESMigrationDiffy
      )

  object ControlAiEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_control_ai_enabled",
        default = false
      )

  object ControlAiTopicEmbeddingANNMaxCandidates
      extends FSBoundedParam[Int](
        name = "tweet_mixer_control_ai_topic_embedding_ann_max_candidates",
        default = 300,
        min = 0,
        max = 1000
      )

  // Enables Hydra Scoring Pipeline
  object HydraScoringPipelineEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_hydra_scoring_pipeline_enabled",
        default = false
      )

  // Enables Hydra Based Sorting
  object HydraBasedSortingEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_hydra_based_sorting_enabled",
        default = false
      )

  //Hydra Model Nmae
  object HydraModelName
      extends FSParam[String](
        name = "tweet_mixer_hydra_model_name",
        default = "model1"
      )

  object DeepRetrievalModelName
      extends FSParam[String](
        name = "tweet_mixer_deep_retrieval_model_name",
        default = "deep_retrieval"
      )

  object DeepRetrievalI2iEmbModelName
      extends FSParam[String](
        name = "tweet_mixer_deep_retrieval_i2i_embmodel_name",
        default = "deep_retrieval_exp2"
      )

  object USSDeepRetrievalI2iEmbModelName
      extends FSParam[String](
        name = "tweet_mixer_uss_deep_retrieval_i2i_embmodel_name",
        default = "deep_retrieval_exp4"
      )

  object OutlierTweetEmbeddingModelNameParam
      extends FSParam[String](
        name = "tweet_mixer_outlier_tweet_embedding_model_name",
        default = "deep_retrieval_exp3"
      )

  object DeepRetrievalAddUserEmbeddingGaussianNoise
      extends FSParam[Boolean](
        name = "tweet_mixer_deep_retrieval_add_user_embedding_gaussian_noise",
        default = false
      )

  object DeepRetrievalUserEmbeddingGaussianNoiseParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_deep_retrieval_user_embedding_gaussian_noise_param",
        default = 1.0,
        min = 0.0,
        max = 100.0
      )

  object MediaDeepRetrievalModelName
      extends FSParam[String](
        name = "tweet_mixer_media_deep_retrieval_model_name",
        default = "deep_retrieval_media"
      )

  object MediaEvergreenDeepRetrievalModelName
      extends FSParam[String](
        name = "tweet_mixer_media_evergreen_deep_retrieval_model_name",
        default = "evergreen_retrieval_v1"
      )

  object MediaPromotedCreatorDeepRetrievalModelName
      extends FSParam[String](
        name = "tweet_mixer_media_promoted_creator_deep_retrieval_model_name",
        default = "creator_incentive_retrieval_v1"
      )

  object VideoScoreWeightParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_video_score_weight",
        default = 0.6,
        min = -1,
        max = 100
      )

  object ReplyScoreWeightParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_reply_score_weight",
        default = 0.0,
        min = -1,
        max = 100
      )

  object ControlAiShowMoreWeightParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_control_ai_show_more_weight",
        default = 0.0,
        min = -1000,
        max = 1000
      )

  object ControlAiShowLessWeightParam
      extends FSBoundedParam[Double](
        name = "tweet_mixer_control_ai_show_less_weight",
        default = 0.0,
        min = -1000,
        max = 1000
      )

  object ExperimentBucketIdentifierParam
      extends FSParam[String](
        name = "tweet_mixer_experiment_bucket_identifier",
        default = "Interleave"
      )

  object EvergreenVideosEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_evergreen_videos_enabled",
        default = false
      )

  object GrokFilterFeatureHydratorEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_grok_filter_feature_hydrator_enabled",
        default = false
      )

  object ViewCountInfoOnTweetFeatureHydratorEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_view_count_info_on_tweet_feature_hydrator_enabled",
        default = false
      )

  object SemanticVideoCandidatePipelineEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_semantic_video_candidate_pipeline_enabled",
        default = true
      )

  object TwitterClipV0LongVideoCandidatePipelineEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_twitter_clip_v0_long_video_candidate_pipeline_enabled",
        default = false
      )

  object TwitterClipV0ShortVideoCandidatePipelineEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_twitter_clip_v0_short_video_candidate_pipeline_enabled",
        default = false
      )

  object EvergreenVideosPaginationNumParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_evergreen_videos_pagination_num",
        default = 200,
        min = 0,
        max = 2000
      )

  object EvergreenVideosMaxFollowUsersParam
      extends FSBoundedParam[Int](
        name = "tweet_mixer_evergreen_videos_max_follow_users",
        default = 500,
        min = 0,
        max = 2000
      )

  object QigSearchHistoryTweetsEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_qig_search_history_tweets_enabled",
        default = false
      )

  object QigSearchHistoryCandidateSourceEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_qig_search_history_candidate_source_enabled",
        default = false
      )

  object QigSearchHistoryTweetsEnableLanguageFilter
      extends FSParam[Boolean](
        name = "tweet_mixer_qig_search_history_tweets_enable_language_filter",
        default = false
      )

  object QigSearchHistoryTopKLimit
      extends FSBoundedParam[Int](
        name = "tweet_mixer_qig_search_history_top_k_limit",
        default = 500,
        min = 0,
        max = 10000
      )

  object ScribeRetrievedCandidatesParam
      extends FSParam[Boolean](
        name = "tweet_mixer_scribe_retrieved_candidates",
        default = false
      )

  object EnableContentEmbeddingAnnTweets
      extends FSParam[Boolean](
        name = "tweet_mixer_enable_content_embedding_tweet_based",
        default = false
      )

  object UVGHighQualityTweetBasedExpansionEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_uvg_high_quality_tweet_based_expansion_enabled",
        default = false
      )

  val boundedDurationFSOverrides = Seq(
    MaxTweetAgeHoursParam,
    MinVideoDurationParam,
    ShortFormMinDurationParam,
    ShortFormMaxDurationParam,
    LongFormMinDurationParam,
    LongFormMaxDurationParam,
    ContentExplorationMinHoursSinceLastRequestParam,
    DeepRetrievalTweetTweetEmbeddingSeedMaxAgeInDays,
  )

  val boundedIntFSOverrides = Seq(
    ControlAiTopicEmbeddingANNMaxCandidates,
    ContentExplorationDRTweetTweetMaxCandidates,
    ContentExplorationEvergreenDRTweetTweetMaxCandidates,
    ContentExplorationDRTweetTweetTierTwoMaxCandidates,
    ContentExplorationEmbeddingANNMaxCandidates,
    UserInterestSummarySimilarityMaxCandidates,
    ContentExplorationEmbeddingSimilarityTierTwoMaxCandidates,
    ContentExplorationMaxViewCountThreshold,
    ContentExplorationTier2MaxViewCountThreshold,
    ContentExplorationDRUserTweetMaxCandidates,
    ContentExplorationDRUserTweetTierTwoMaxCandidates,
    ContentExplorationOnceInTimesShow,
    ContentExplorationSimclusterColdPostsMaxCandidates,
    ContentExplorationSimclusterColdPostsPostsPerSimcluster,
    EvergreenDRUserTweetMaxCandidates,
    EvergreenDRCrossBorderUserTweetMaxCandidates,
    DeepRetrievalTweetTweetANNMaxCandidates,
    USSDeepRetrievalStratoTimeout,
    OutlierDeepRetrievalStratoTimeout,
    DeepRetrievalTweetTweetEmbeddingANNMaxCandidates,
    DeepRetrievalTweetTweetEmbeddingRandomSize,
    DeepRetrievalUserTweetANNMaxCandidates,
    DeepRetrievalTweetTweetEmbeddingMinPriority,
    MaxHaploliteTweetsParam,
    MaxCandidateNumPerSourceKeyParam,
    MediaDeepRetrievalUserTweetANNMaxCandidates,
    MediaEvergreenDeepRetrievalUserTweetANNMaxCandidates,
    MediaPromotedCreatorDeepRetrievalUserTweetANNMaxCandidates,
    MediaRelatedCreatorMaxCandidates,
    EvergreenVideosPaginationNumParam,
    EvergreenVideosMaxFollowUsersParam,
    TwhinRebuildTweetSimilarityMaxCandidates,
    TwhinRebuildUserTweetSimilarityMaxCandidates,
    ShortFormMinWidthParam,
    ShortFormMinHeightParam,
    ShortFormMaxResultParam,
    LongFormMinWidthParam,
    LongFormMinHeightParam,
    LongFormMaxResultParam,
    MemeMaxResultParam,
    QigSearchHistoryTopKLimit,
    MaxFollowersCountGateParam,
  )

  val enumFSOverrides = Seq(BlendingParam, TwhinRebuildTweetSimilarityDatasetParam)

  val booleanDeciderOverrides = Seq(
    EnableHydraScoringSideEffect,
    EnableEvergreenVideosSideEffect,
    EnableDeepRetrievalAdhocDecider,
    EnableTESMigrationDiffy
  )

  val boundedDoubleDeciderOverrides = Seq(DeepRetreivalAdhocMaxResultsDivByTenDecider)

  val booleanFSOverrides = Seq(
    ControlAiEnabled,
    ContentExplorationDRTweetTweetEnabled,
    ContentExplorationDRTweetTweetEnableRandomEmbedding,
    ContentExplorationEvergreenDRTweetTweetEnabled,
    ContentExplorationDRTweetTweetTierTwoEnabled,
    ContentExplorationEmbeddingSimilarityEnabled,
    ContentExplorationMultimodalEnabled,
    UserInterestSummarySimilarityEnabled,
    ContentExplorationMediaEmbeddingSimilarityEnabled,
    ContentExplorationNonVideoTweetFeaturesEnabled,
    ContentExplorationEmbeddingSimilarityTierTwoEnabled,
    ContentExplorationDRUserTweetEnabled,
    ContentExplorationDRUserTweetTierTwoEnabled,
    ContentExplorationSimclusterColdPostsEnabled,
    LastNonPollingTimeFeatureHydratorEnabled,
    DeepRetrievalTweetTweetANNEnabled,
    DeepRetrievalTweetTweetEmbeddingANNEnabled,
    DeepRetrievalUserTweetANNEnabled,
    DeepRetrievalEnableGPU,
    DeepRetrievalCategoricalUserTweetANNEnabled,
    DeepRetrievalIsHighQualityEnabled,
    DeepRetrievalIsLowNegEngRatioEnabled,
    DeepRetrievalAddUserEmbeddingGaussianNoise,
    EnableDebugMode,
    EnableLowSignalUserCheck,
    EnableMaxFollowersGate,
    EnableNonEmptySearchHistoryUserCheck,
    EnableTweetEntityServiceMigration,
    EnableTweetEntityServiceMigrationDiffy,
    EnableImpressionBloomFilterHydrator,
    EnableGizmoduckQueryFeatureHydrator,
    EnableVideoBloomFilterHydrator,
    EnableShortVideoFilter,
    EnableLongFormVideoFilter,
    EnableShortFormVideoFilter,
    EnableMediaIdDedupFilter,
    EnableRelatedCreatorParam,
    EnableMediaMetadataCandidateFeatureHydrator,
    EnableMediaClusterIdDedupFilter,
    EnableRequestCountryPlaceIdHydrator,
    EnableSignalInfoHydrator,
    EnableGrokFilter,
    EnableUserTopicIdsHydrator,
    EnableUSSFeatureHydrator,
    EnableUSSGrokCategoryFeatureHydrator,
    EnableUSSDeepRetrievalTweetEmbeddingFeatureHydrator,
    EnableVideoTweetFilter,
    EvergreenDRUserTweetEnabled,
    EvergreenDRCrossBorderUserTweetEnabled,
    HaploliteTweetsBasedEnabled,
    HydraBasedSortingEnabled,
    HydraScoringPipelineEnabled,
    MediaDeepRetrievalUserTweetANNEnabled,
    MediaDeepRetrievalIsHighQualityEnabled,
    MediaDeepRetrievalIsLowNegEngRatioEnabled,
    MediaEvergreenDeepRetrievalUserTweetANNEnabled,
    MediaPromotedCreatorDeepRetrievalUserTweetANNEnabled,
    DeepRetrievalNonVideoTweetFeaturesEnabled,
    DeepRetrievalFilterOldSignalsEnabled,
    SimclustersInterestedInEnabled,
    SimclustersProducerBasedEnabled,
    SimclustersTweetBasedEnabled,
    SimclustersPromotedCreatorEnabled,
    TwhinConsumerBasedEnabled,
    TwhinRebuildTweetSimilarityEnabled,
    TwhinRebuildUserTweetSimilarityEnabled,
    TwhinTweetSimilarityEnabled,
    UTEGEnabled,
    UTGProducerBasedEnabled,
    UTGTweetBasedEnabled,
    UTGTweetBasedExpansionEnabled,
    UVGTweetBasedEnabled,
    UVGTweetBasedExpansionEnabled,
    UVGHighQualityTweetBasedExpansionEnabled,
    EnablePortraitVideoFilter,
    EvergreenVideosEnabled,
    GrokFilterFeatureHydratorEnabled,
    ViewCountInfoOnTweetFeatureHydratorEnabled,
    SemanticVideoCandidatePipelineEnabled,
    TwitterClipV0LongVideoCandidatePipelineEnabled,
    TwitterClipV0ShortVideoCandidatePipelineEnabled,
    QigSearchHistoryTweetsEnabled,
    QigSearchHistoryCandidateSourceEnabled,
    QigSearchHistoryTweetsEnableLanguageFilter,
    ScribeRetrievedCandidatesParam,
    EnableContentEmbeddingAnnTweets,
  )

  val stringFSOverrides = Seq(
    ExperimentBucketIdentifierParam,
    DeepRetrievalModelName,
    DeepRetrievalI2iEmbModelName,
    USSDeepRetrievalI2iEmbModelName,
    OutlierTweetEmbeddingModelNameParam,
    DeepRetrievalVectorDBCollectionName,
    DeepRetrievalI2iVectorDBCollectionName,
    DeepRetrievalI2iEmbVectorDBCollectionName,
    ContentExplorationDRTweetTweetVectorDBCollectionName,
    ContentExplorationEvergreenDRTweetTweetVectorDBCollectionName,
    ContentExplorationDRTweetTweetTierTwoVectorDBCollectionName,
    ContentExplorationVectorDBCollectionName,
    UserInterestSummarySimilarityVectorDBCollectionName,
    ContentExplorationEmbeddingSimilarityTierTwoVectorDBCollectionName,
    ContentExplorationDRUserTweetVectorDBCollectionName,
    ContentExplorationDRUserEmbeddingModelName,
    EvergreenDRUserEmbeddingModelName,
    EvergreenDRUserTweetVectorDBCollectionName,
    EvergreenDRCrossBorderUserEmbeddingModelName,
    EvergreenDRCrossBorderUserTweetVectorDBCollectionName,
    HydraModelName,
    MediaDeepRetrievalModelName,
    MediaEvergreenDeepRetrievalModelName,
    MediaPromotedCreatorDeepRetrievalModelName,
    MediaDeepRetrievalVectorDBCollectionName,
    MediaEvergreenDeepRetrievalVectorDBCollectionName,
    MediaPromotedCreatorDeepRetrievalVectorDBCollectionName,
    TwhinRebuildUserTweetVectorDBName,
  )

  val boundedDoubleFSOverrides = Seq(
    ControlAiShowMoreWeightParam,
    ControlAiShowLessWeightParam,
    ContentExplorationDRTweetTweetScoreThreshold,
    ContentExplorationEvergreenDRTweetTweetScoreThreshold,
    ContentExplorationDRTweetTweetTierTwoScoreThreshold,
    ContentExplorationEmbeddingANNScoreThreshold,
    UserInterestSummarySimilarityScoreThreshold,
    ContentExplorationEmbeddingSimilarityTierTwoScoreThreshold,
    DeepRetrievalUserEmbeddingGaussianNoiseParam,
    DeepRetrievalTweetTweetANNScoreThreshold,
    USSDeepRetrievalSimilarityTweetTweetANNScoreThreshold,
    DeepRetrievalUserTweetANNScoreThreshold,
    DeepRetrievalTweetTweetEmbeddingANNScoreThreshold,
    DeepRetrievalTweetTweetANNScoreMaxThreshold,
    DeepRetrievalTweetTweetEmbeddingTimeDecay,
    DeepRetrievalTweetTweetEmbeddingTimeOfDayBoostWeight,
    DeepRetrievalTweetTweetEmbeddingDayOfWeekBoostWeight,
    DeepRetrievalTweetTweetEmbeddingEngagementBoostWeight,
    ReplyScoreWeightParam,
    VideoScoreWeightParam,
    ShortFormMinAspectRatioParam,
    ShortFormMaxAspectRatioParam,
    LongFormMinAspectRatioParam,
    LongFormMaxAspectRatioParam,
    LowSignalUserGrokTopicRatioParam,
    LowSignalUserPopGeoRatioParam,
    LowSignalUserSimclustersRatioParam
  )
}
