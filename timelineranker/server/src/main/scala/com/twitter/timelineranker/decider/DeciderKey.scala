package com.twitter.timelineranker.decider

import com.twitter.servo.decider.DeciderKeyEnum

object DeciderKey extends DeciderKeyEnum {
  // Deciders that can be used to control load on TLR or its backends.
  val EnableMaxConcurrencyLimiting: Value = Value("enable_max_concurrency_limiting")

  // Deciders related to testing / debugging.
  val EnableRoutingToRankerDevProxy: Value = Value("enable_routing_to_ranker_dev_proxy")

  // Deciders related to authorization.
  val ClientRequestAuthorization: Value = Value("client_request_authorization")
  val ClientWriteWhitelist: Value = Value("client_write_whitelist")
  val AllowTimelineMixerRecapProd: Value = Value("allow_timeline_mixer_recap_prod")
  val AllowTimelineMixerRecycledProd: Value = Value("allow_timeline_mixer_recycled_prod")
  val AllowTimelineMixerHydrateProd: Value = Value("allow_timeline_mixer_hydrate_prod")
  val AllowTimelineMixerHydrateRecosProd: Value = Value("allow_timeline_mixer_hydrate_recos_prod")
  val AllowTimelineMixerSeedAuthorsProd: Value = Value("allow_timeline_mixer_seed_authors_prod")
  val AllowTimelineMixerSimclusterProd: Value = Value("allow_timeline_mixer_simcluster_prod")
  val AllowTimelineMixerEntityTweetsProd: Value = Value("allow_timeline_mixer_entity_tweets_prod")
  val AllowTimelineMixerListProd: Value = Value("allow_timeline_mixer_list_prod")
  val AllowTimelineMixerListTweetProd: Value = Value("allow_timeline_mixer_list_tweet_prod")
  val AllowTimelineMixerCommunityProd: Value = Value("allow_timeline_mixer_community_prod")
  val AllowTimelineMixerCommunityTweetProd: Value = Value(
    "allow_timeline_mixer_community_tweet_prod")
  val AllowTimelineScorerRecommendedTrendTweetProd: Value = Value(
    "allow_timeline_scorer_recommended_trend_tweet_prod")
  val AllowTimelineMixerUtegLikedByTweetsProd: Value = Value(
    "allow_timeline_mixer_uteg_liked_by_tweets_prod")
  val AllowTimelineMixerStaging: Value = Value("allow_timeline_mixer_staging")
  val AllowTimelineRankerProxy: Value = Value("allow_timeline_ranker_proxy")
  val AllowTimelineRankerWarmup: Value = Value("allow_timeline_ranker_warmup")
  val AllowTimelineScorerRecTopicTweetsProd: Value =
    Value("allow_timeline_scorer_rec_topic_tweets_prod")
  val AllowTimelineScorerPopularTopicTweetsProd: Value =
    Value("allow_timeline_scorer_popular_topic_tweets_prod")
  val AllowTimelineScorerHydrateTweetScoringProd: Value = Value(
    "allow_timelinescorer_hydrate_tweet_scoring_prod")
  val AllowTimelineServiceProd: Value = Value("allow_timeline_service_prod")
  val AllowTimelineServiceStaging: Value = Value("allow_timeline_service_staging")
  val RateLimitOverrideUnknown: Value = Value("rate_limit_override_unknown")

  // Deciders related to reverse-chron home timeline materialization.
  val MultiplierOfMaterializationTweetsFetched: Value = Value(
    "multiplier_of_materialization_tweets_fetched"
  )
  val BackfillFilteredEntries: Value = Value("enable_backfill_filtered_entries")
  val TweetsFilteringLossageThreshold: Value = Value("tweets_filtering_lossage_threshold")
  val TweetsFilteringLossageLimit: Value = Value("tweets_filtering_lossage_limit")
  val SupplementFollowsWithRealGraph: Value = Value("supplement_follows_with_real_graph")

  // Deciders related to recap.
  val RecapEnableContentFeaturesHydration: Value = Value("recap_enable_content_features_hydration")
  val RecapMaxCountMultiplier: Value = Value("recap_max_count_multiplier")
  val RecapEnableExtraSortingInResults: Value = Value("recap_enable_extra_sorting_in_results")

  // Deciders related to recycled tweets.
  val RecycledMaxCountMultiplier: Value = Value("recycled_max_count_multiplier")
  val RecycledEnableContentFeaturesHydration: Value = Value(
    "recycled_enable_content_features_hydration")

  // Deciders related to entity tweets.
  val EntityTweetsEnableContentFeaturesHydration: Value = Value(
    "entity_tweets_enable_content_features_hydration")

  // Deciders related to both recap and recycled tweets
  val EnableRealGraphUsers: Value = Value("enable_real_graph_users")
  val MaxRealGraphAndFollowedUsers: Value = Value("max_real_graph_and_followed_users")

  // Deciders related to recap author
  val RecapAuthorEnableNewPipeline: Value = Value("recap_author_enable_new_pipeline")
  val RecapAuthorEnableContentFeaturesHydration: Value = Value(
    "recap_author_enable_content_features_hydration")

  // Deciders related to recap hydration (rectweet and ranked organic).
  val RecapHydrationEnableContentFeaturesHydration: Value = Value(
    "recap_hydration_enable_content_features_hydration")

  // Deciders related to uteg liked by tweets
  val UtegLikedByTweetsEnableContentFeaturesHydration: Value = Value(
    "uteg_liked_by_tweets_enable_content_features_hydration")
}
