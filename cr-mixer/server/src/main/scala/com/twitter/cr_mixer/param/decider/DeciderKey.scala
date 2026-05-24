package com.twitter.cr_mixer.param.decider

import com.twitter.servo.decider.DeciderKeyEnum

object DeciderConstants {
  val enableHealthSignalsScoreDeciderKey = "enable_tweet_health_score"
  val enableUTGRealTimeTweetEngagementScoreDeciderKey = "enable_utg_realtime_tweet_engagement_score"
  val enableUserAgathaScoreDeciderKey = "enable_user_agatha_score"
  val enableUserTweetEntityGraphTrafficDeciderKey = "enable_user_tweet_entity_graph_traffic"
  val enableUserTweetGraphTrafficDeciderKey = "enable_user_tweet_graph_traffic"
  val enableUserVideoGraphTrafficDeciderKey = "enable_user_video_graph_traffic"
  val enableUserAdGraphTrafficDeciderKey = "enable_user_ad_graph_traffic"
  val enableSimClustersANN2DarkTrafficDeciderKey = "enable_simclusters_ann_2_dark_traffic"
  val enableQigSimilarTweetsTrafficDeciderKey = "enable_qig_similar_tweets_traffic"
  val enableFRSTrafficDeciderKey = "enable_frs_traffic"
  val upperFunnelPerStepScribeRate = "upper_funnel_per_step_scribe_rate"
  val kafkaMessageScribeSampleRate = "kafka_message_scribe_sample_rate"
  val enableRealGraphMhStoreDeciderKey = "enable_real_graph_mh_store"
  val topLevelApiDdgMetricsScribeRate = "top_level_api_ddg_metrics_scribe_rate"
  val adsRecommendationsPerExperimentScribeRate = "ads_recommendations_per_experiment_scribe_rate"
  val enableScribeForBlueVerifiedTweetCandidates =
    "enable_scribe_for_blue_verified_tweet_candidates"

  val enableUserStateStoreDeciderKey = "enable_user_state_store"
  val enableUserMediaRepresentationStoreDeciderKey =
    "enable_user_media_representation_store"
  val enableMagicRecsRealTimeAggregatesStoreDeciderKey =
    "enable_magic_recs_real_time_aggregates_store"

  val enableEarlybirdTrafficDeciderKey = "enable_earlybird_traffic"

  val enableTopicTweetTrafficDeciderKey = "enable_topic_tweet_traffic"

  val getTweetRecommendationsCacheRate = "get_tweet_recommendations_cache_rate"
}

object DeciderKey extends DeciderKeyEnum {

  val enableHealthSignalsScoreDeciderKey: Value = Value(
    DeciderConstants.enableHealthSignalsScoreDeciderKey
  )

  val enableUtgRealTimeTweetEngagementScoreDeciderKey: Value = Value(
    DeciderConstants.enableUTGRealTimeTweetEngagementScoreDeciderKey
  )
  val enableUserAgathaScoreDeciderKey: Value = Value(
    DeciderConstants.enableUserAgathaScoreDeciderKey
  )
  val enableUserMediaRepresentationStoreDeciderKey: Value = Value(
    DeciderConstants.enableUserMediaRepresentationStoreDeciderKey
  )

  val enableMagicRecsRealTimeAggregatesStore: Value = Value(
    DeciderConstants.enableMagicRecsRealTimeAggregatesStoreDeciderKey
  )

  val enableUserStateStoreDeciderKey: Value = Value(
    DeciderConstants.enableUserStateStoreDeciderKey
  )

  val enableRealGraphMhStoreDeciderKey: Value = Value(
    DeciderConstants.enableRealGraphMhStoreDeciderKey
  )

  val enableEarlybirdTrafficDeciderKey: Value = Value(
    DeciderConstants.enableEarlybirdTrafficDeciderKey)
}
