package com.twitter.follow_recommendations.common.feature_hydration.sources

object FeatureHydrationSourcesFeatureSwitchKeys {
  val EnableAlgorithmAggregateFeatures = "feature_store_source_enable_algorithm_aggregate_features"
  val EnableAuthorTopicAggregateFeatures =
    "feature_store_source_enable_author_topic_aggregate_features"
  val EnableCandidateClientFeatures = "feature_store_source_enable_candidate_client_features"
  val EnableCandidateNotificationFeatures =
    "feature_store_source_enable_candidate_notification_features"
  val EnableCandidatePrecomputedNotificationFeatures =
    "feature_store_source_enable_candidate_precomputed_notification_features"
  val EnableCandidateUserFeatures = "feature_store_source_enable_candidate_user_features"
  val EnableCandidateUserAuthorRealTimeAggregateFeatures =
    "feature_store_source_enable_candidate_user_author_rta_features"
  val EnableCandidateUserResurrectionFeatures =
    "feature_store_source_enable_candidate_user_resurrection_features"
  val EnableCandidateUserTimelinesAuthorAggregateFeatures =
    "feature_store_source_enable_candidate_user_timelines_author_aggregate_features"
  val EnableSimilarToUserFeatures = "feature_store_source_enable_similar_to_user_features"
  val EnableTargetUserFeatures = "feature_store_source_enable_target_user_features"
  val EnableTargetUserUserAuthorUserStateRealTimeAggregatesFeature =
    "feature_store_source_enable_target_user_user_author_user_state_rta_features"
  val EnableTargetUserResurrectionFeatures =
    "feature_store_source_enable_target_user_resurrection_features"
  val EnableTargetUserWtfImpressionFeatures =
    "feature_store_source_enable_target_user_wtf_impression_features"
  val EnableTopicAggregateFeatures = "feature_store_source_enable_topic_aggregate_features"
  val EnableUserCandidateEdgeFeatures = "feature_store_source_enable_user_candidate_edge_features"
  val EnableUserCandidateWtfImpressionCandidateFeatures =
    "feature_store_source_enable_user_candidate_wtf_impression_features"
  val EnableUserClientFeatures = "feature_store_source_enable_user_client_features"
  val EnableUserNotificationFeatures = "feature_store_source_enable_user_notification_features"
  val EnableUserTopicFeatures = "feature_store_source_enable_user_topic_features"
  val EnableUserWtfAlgEdgeFeatures = "feature_store_source_enable_user_wtf_alg_edge_features"
  val FeatureHydrationTimeout = "feature_store_source_hydration_timeout_in_millis"
  val UseSeparateClientForTimelinesAuthor =
    "feature_store_source_separate_client_for_timelines_author_data"
  val UseSeparateClientMetricCenterUserCounting =
    "feature_store_source_separate_client_for_mc_user_counting_data"
  val UseSeparateClientForNotifications = "feature_store_source_separate_client_for_notifications"
  val UseSeparateClientForGizmoduck = "feature_store_source_separate_client_for_gizmoduck"
}
