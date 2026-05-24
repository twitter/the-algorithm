package com.twitter.tweet_mixer.param.decider

import com.twitter.servo.decider.DeciderKeyEnum

object DeciderKey extends DeciderKeyEnum {
  val EnableHomeRecommendedTweetsProduct = Value("enable_home_recommended_tweets_product")
  val EnableNotificationsRecommendedTweetsProduct = Value(
    "enable_notifications_recommended_tweets_product")
  val EnableIMVRecommendedTweetsProduct = Value("enable_imv_recommended_tweets_product")
  val EnableIMVRelatedTweetsProduct = Value("enable_imv_related_tweets_product")
  val EnableRUXRelatedTweetsProduct = Value("enable_rux_related_tweets_product")
  val EnableDebuggerTweetsProduct = Value("enable_debugger_tweets_product")
  val EnableVideoRecommendedTweetsProduct = Value("enable_video_recommended_tweets_product")
  val EnableHydraScoringSideEffect = Value("enable_hydra_scoring_side_effect")
  val EnableEvergreenVideosSideEffect = Value("enable_evergreen_videos_side_effect")
  val EnableLoggedOutVideoRecommendedTweetsProduct = Value(
    "enable_logged_out_video_recommended_tweets_product")
  val EnableTopicTweetsProduct = Value("enable_topic_tweets_product")
  val EnableDeepRetrievalAdhocSideEffect = Value("enable_deep_retrieval_adhoc_side_effect")
  val DeepRetrievalSideEffectNumCandidatesDivByTen = Value(
    "deep_retrieval_side_effect_num_candidates_div_by_10")
  val EnableTESMigrationDiffy = Value("enable_tes_migration_diffy")
}
