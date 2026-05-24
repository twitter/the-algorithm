package com.twitter.home_mixer.param.decider

import com.twitter.servo.decider.DeciderKeyEnum

/**
 * These values must correspond to the deciders configured in the
 * home-mixer/server/src/main/resources/config/decider.yml file
 *
 * @see [[com.twitter.product_mixer.core.product.ProductParamConfig.enabledDeciderKey]]
 */
object DeciderKey extends DeciderKeyEnum {

  val EnableForYouProduct = Value("enable_for_you_product")

  val EnableFollowingProduct = Value("enable_following_product")

  val EnableScoredTweetsProduct = Value("enable_scored_tweets_product")

  val EnableScoredVideoTweetsProduct = Value("enable_scored_video_tweets_product")

  val EnableSubscribedProduct = Value("enable_subscribed_product")

  val EnableHeavyRankerScoresProduct = Value("enable_heavy_ranker_scores_product")

  val EnableSimClustersSimilarityFeatureHydration =
    Value("enable_simclusters_similarity_feature_hydration")

  val EnableServedCandidateFeatureKeysKafkaPublishing =
    Value("enable_served_candidate_feature_keys_kafka_publishing")

  val EnablePublishCommonFeaturesKafka =
    Value("enable_publish_common_features_kafka")

  val LiveSpacesFactor = Value("live_spaces_factor")

  val MtlNormalizationAlpha = Value("mtl_normalization_alpha")

  val EnableTweetypieContentFeatures = Value("enable_tweetypie_content_features")

  val EnableCommonFeaturesDataRecordCopyDuringPldrConversion =
    Value("enable_common_features_data_record_copy_during_pldr_conversion")

  val EnableGetTweetsFromArchiveIndex = Value("enable_get_tweets_from_archive_index")

  val EnableSecondaryNaviRecapCluster = Value("enable_secondary_navi_recap_cluster")

  val EnableGPUNaviRecapClusterTestUsers = Value("enable_gpu_navi_recap_cluster_test_users")

  val NaviGPUClusterRequestBatchSize = Value("navi_gpu_cluster_request_batch_size")

  val EnableVideoSummaryEmbeddingHydration = Value(
    "enable_video_summary_embedding_feature_hydration")

  val EnableVideoClipEmbeddingHydration = Value("enable_video_clip_embedding_feature_hydration")

  val EnableScoredVideoTweetsUserHistoryEventsQueryFeatureHydrationDeciderParam = Value(
    "enable_scored_video_tweets_user_history_events_query_feature_hydration")

  val EnableVideoClipEmbeddingMediaUnderstandingHydration = Value(
    "enable_video_clip_embedding_media_understanding_feature_hydration")
}
