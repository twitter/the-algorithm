package com.twitter.frigate.pushservice.params

import com.twitter.servo.decider.DeciderKeyEnum

object DeciderKey extends DeciderKeyEnum {
  val disableAllRelevance = Value("frigate_pushservice_disable_all_relevance")
  val disableHeavyRanking = Value("frigate_pushservice_disable_heavy_ranking")
  val restrictLightRanking = Value("frigate_pushservice_restrict_light_ranking")
  val downSampleLightRankingScribeCandidates = Value(
    "frigate_pushservice_down_sample_light_ranking_scribe_candidates")
  val entityGraphTweetRecsDeciderKey = Value("user_tweet_entity_graph_tweet_recs")
  val enablePushserviceWritesToNotificationServiceDeciderKey = Value(
    "frigate_pushservice_enable_writes_to_notification_service")
  val enablePushserviceWritesToNotificationServiceForAllEmployeesDeciderKey = Value(
    "frigate_pushservice_enable_writes_to_notification_service_for_employees")
  val enablePushserviceWritesToNotificationServiceForEveryoneDeciderKey = Value(
    "frigate_pushservice_enable_writes_to_notification_service_for_everyone")
  val enablePromptFeedbackFatigueResponseNoPredicateDeciderKey = Value(
    "frigate_pushservice_enable_ntab_feedback_prompt_response_no_filter_predicate")
  val enablePushserviceDeepbirdv2CanaryClusterDeciderKey = Value(
    "frigate_pushservice_canary_enable_deepbirdv2_canary_cluster")
  val enableUTEGSCForEarlybirdTweetsDecider = Value(
    "frigate_pushservice_enable_uteg_sc_for_eb_tweets")
  val enableTweetFavRecs = Value("frigate_pushservice_enable_tweet_fav_recs")
  val enableTweetRetweetRecs = Value("frigate_pushservice_enable_tweet_retweet_recs")
  val enablePushSendEventBus = Value("frigate_pushservice_enable_push_send_eventbus")

  val enableModelBasedPushcapAssignments = Value(
    "frigate_pushservice_enable_model_based_pushcap_assignments")

  val enableTweetAnnotationFeatureHydration = Value(
    "frigate_pushservice_enable_tweet_annotation_features_hydration")
  val enableMrRequestScribing = Value("frigate_pushservice_enable_mr_request_scribing")
  val enableHighQualityCandidateScoresScribing = Value(
    "frigate_pushservice_enable_high_quality_candidate_scribing")
  val enableHtlUserAuthorRealTimeAggregateFeatureHydration = Value(
    "frigate_pushservice_enable_htl_new_user_user_author_rta_hydration")
  val enableMrUserSemanticCoreFeaturesHydration = Value(
    "frigate_pushservice_enable_mr_user_semantic_core_feature_hydration")
  val enableMrUserSemanticCoreNoZeroFeaturesHydration = Value(
    "frigate_pushservice_enable_mr_user_semantic_core_no_zero_feature_hydration")
  val enableHtlOfflineUserAggregateExtendedFeaturesHydration = Value(
    "frigate_pushservice_enable_htl_offline_user_aggregate_extended_features_hydration")
  val enableNerErgFeaturesHydration = Value("frigate_pushservice_enable_ner_erg_features_hydration")
  val enableDaysSinceRecentResurrectionFeatureHydration = Value(
    "frigate_pushservice_enable_days_since_recent_resurrection_features_hydration")
  val enableUserPastAggregatesFeatureHydration = Value(
    "frigate_pushservice_enable_user_past_aggregates_features_hydration")
  val enableUserSignalLanguageFeatureHydration = Value(
    "frigate_pushservice_enable_user_signal_language_features_hydration")
  val enableUserPreferredLanguageFeatureHydration = Value(
    "frigate_pushservice_enable_user_preferred_language_features_hydration")
  val enablePredicateDetailedInfoScribing = Value(
    "frigate_pushservice_enable_predicate_detailed_info_scribing")
  val enablePushCapInfoScribing = Value("frigate_pushservice_enable_push_cap_info_scribing")
  val disableMLInFiltering = Value("frigate_pushservice_disable_ml_in_filtering")
  val useHydratedLabeledSendsForFeaturesDeciderKey = Value(
    "use_hydrated_labeled_sends_for_features")
  val verifyHydratedLabeledSendsForFeaturesDeciderKey = Value(
    "verify_hydrated_labeled_sends_for_features")
  val trainingDataDeciderKey = Value("frigate_notifier_quality_model_training_data")
  val skipMlModelPredicateDeciderKey = Value("skip_ml_model_predicate")
  val scribeModelFeaturesDeciderKey = Value("scribe_model_features")
  val scribeModelFeaturesWithoutHydratingNewFeaturesDeciderKey = Value(
    "scribe_model_features_without_hydrating_new_features")
  val scribeModelFeaturesForRequestScribe = Value("scribe_model_features_for_request_scribe")
  val enableMrUserSimclusterV2020FeaturesHydration = Value(
    "frigate_pushservice_enable_mr_user_simcluster_v2020_hydration")
  val enableMrUserSimclusterV2020NoZeroFeaturesHydration = Value(
    "frigate_pushservice_enable_mr_user_simcluster_v2020_no_zero_feature_hydration")
  val enableMrUserEngagedTweetTokensFeaturesHydration = Value(
    "frigate_pushservice_enable_mr_user_engaged_tweet_tokens_feature_hydration")
  val enableMrCandidateTweetTokensFeaturesHydration = Value(
    "frigate_pushservice_enable_mr_candidate_tweet_tokens_feature_hydration")
  val enableTopicEngagementRealTimeAggregatesFeatureHydration = Value(
    "frigate_pushservice_enable_topic_engagement_real_time_aggregates_feature_hydration"
  )
  val enableUserTopicAggregatesFeatureHydration = Value(
    "frigate_pushservice_enable_user_topic_aggregates_feature_hydration"
  )
  val enableDurationSinceLastVisitFeatureHydration = Value(
    "frigate_pushservice_enable_duration_since_last_visit_features_hydration"
  )
  val enableTwistlyAggregatesFeatureHydration = Value(
    "frigate_pushservice_enable_twistly_agg_feature_hydration"
  )
  val enableTwHINUserEngagementFeaturesHydration = Value(
    "frigate_pushservice_enable_twhin_user_engagement_features_hydration"
  )
  val enableTwHINUserFollowFeaturesHydration = Value(
    "frigate_pushservice_enable_twhin_user_follow_features_hydration"
  )
  val enableTwHINAuthorFollowFeaturesHydration = Value(
    "frigate_pushservice_enable_twhin_author_follow_features_hydration"
  )
  val enableTweetTwHINFavFeaturesHydration = Value(
    "frigate_pushservice_enable_tweet_twhin_fav_features_hydration"
  )
  val enableSpaceVisibilityLibraryFiltering = Value(
    "frigate_pushservice_enable_space_visibility_library_filtering"
  )
  val enableVfFeatureHydrationSpaceShim = Value(
    "frigate_pushservice_enable_visibility_filtering_feature_hydration_in_space_shim")
  val enableUserTopicFollowFeatureSet = Value(
    "frigate_pushservice_enable_user_topic_follow_feature_hydration")
  val enableOnboardingNewUserFeatureSet = Value(
    "frigate_pushservice_enable_onboarding_new_user_feature_hydration")
  val enableMrUserTopicSparseContFeatureSet = Value(
    "frigate_pushservice_enable_mr_user_topic_sparse_cont_feature_hydration"
  )
  val enableUserPenguinLanguageFeatureSet = Value(
    "frigate_pushservice_enable_user_penguin_language_feature_hydration")
  val enableMrUserHashspaceEmbeddingFeatureSet = Value(
    "frigate_pushservice_enable_mr_user_hashspace_embedding_feature_hydration")
  val enableMrUserAuthorSparseContFeatureSet = Value(
    "frigate_pushservice_enable_mr_user_author_sparse_cont_feature_hydration"
  )
  val enableMrTweetSentimentFeatureSet = Value(
    "frigate_pushservice_enable_mr_tweet_sentiment_feature_hydration"
  )
  val enableMrTweetAuthorAggregatesFeatureSet = Value(
    "frigate_pushservice_enable_mr_tweet_author_aggregates_feature_hydration"
  )
  val enableUserGeoFeatureSet = Value("frigate_pushservice_enable_user_geo_feature_hydration")
  val enableAuthorGeoFeatureSet = Value("frigate_pushservice_enable_author_geo_feature_hydration")

  val rampupUserGeoFeatureSet = Value("frigate_pushservice_ramp_up_user_geo_feature_hydration")
  val rampupAuthorGeoFeatureSet = Value("frigate_pushservice_ramp_up_author_geo_feature_hydration")

  val enablePopGeoTweets = Value("frigate_pushservice_enable_pop_geo_tweets")
  val enableTrendsTweets = Value("frigate_pushservice_enable_trends_tweets")
  val enableTripGeoTweetCandidates = Value("frigate_pushservice_enable_trip_geo_tweets")
  val enableContentRecommenderMixerAdaptor = Value(
    "frigate_pushservice_enable_content_recommender_mixer_adaptor")
  val enableGenericCandidateAdaptor = Value("frigate_pushservice_enable_generic_candidate_adaptor")
  val enableTripGeoTweetContentMixerDarkTraffic = Value(
    "frigate_pushservice_enable_trip_geo_tweets_content_mixer_dark_traffic")

  val enableInsTraffic = Value("frigate_pushservice_enable_ins_traffic")
  val enableIsTweetTranslatable = Value("frigate_pushservice_enable_is_tweet_translatable")

  val enableMrTweetSimClusterFeatureSet = Value(
    "frigate_pushservice_enable_mr_tweet_simcluster_feature_hydration")

  val enableMrOfflineUserTweetTopicAggregate = Value(
    "frigate_pushservice_enable_mr_offline_user_tweet_topic_aggregate_hydration")

  val enableMrOfflineUserTweetSimClusterAggregate = Value(
    "frigate_pushservice_enable_mr_offline_user_tweet_simcluster_aggregate_hydration"
  )
  val enableRealGraphV2FeatureHydration = Value(
    "frigate_pushservice_enable_real_graph_v2_features_hydration")

  val enableTweetBeTFeatureHydration = Value(
    "frigate_pushservice_enable_tweet_bet_features_hydration")

  val enableInvalidatingCachedHistoryStoreAfterWrites = Value(
    "frigate_pushservice_enable_invalidating_cached_history_store_after_writes")

  val enableInvalidatingCachedLoggedOutHistoryStoreAfterWrites = Value(
    "frigate_pushservice_enable_invalidating_cached_logged_out_history_store_after_writes")

  val enableUserSendTimeFeatureHydration = Value(
    "frigate_pushservice_enable_user_send_time_feature_hydration"
  )

  val enablePnegMultimodalPredictionForF1Tweets = Value(
    "frigate_pushservice_enable_pneg_multimodal_prediction_for_f1_tweets"
  )

  val enableScribingOonFavScoreForF1Tweets = Value(
    "frigate_pushservice_enable_oon_fav_scribe_for_f1_tweets"
  )

  val enableMrUserUtcSendTimeAggregateFeaturesHydration = Value(
    "frigate_pushservice_enable_mr_user_utc_send_time_aggregate_hydration"
  )

  val enableMrUserLocalSendTimeAggregateFeaturesHydration = Value(
    "frigate_pushservice_enable_mr_user_local_send_time_aggregate_hydration"
  )

  val enableBqmlReportModelPredictionForF1Tweets = Value(
    "frigate_pushservice_enable_bqml_report_model_prediction_for_f1_tweets"
  )

  val enableUserTwhinEmbeddingFeatureHydration = Value(
    "frigate_pushservice_enable_user_twhin_embedding_feature_hydration"
  )

  val enableAuthorFollowTwhinEmbeddingFeatureHydration = Value(
    "frigate_pushservice_enable_author_follow_twhin_embedding_feature_hydration"
  )

  val enableScribingMLFeaturesAsDataRecord = Value(
    "frigate_pushservice_enable_scribing_ml_features_as_datarecord"
  )

  val enableDirectHydrationForUserFeatures = Value(
    "frigate_pushservice_enable_direct_hydration_for_user_features"
  )

  val enableAuthorVerifiedFeatureHydration = Value(
    "frigate_pushservice_enable_author_verified_feature_hydration"
  )

  val enableAuthorCreatorSubscriptionFeatureHydration = Value(
    "frigate_pushservice_enable_author_creator_subscription_feature_hydration"
  )
}
