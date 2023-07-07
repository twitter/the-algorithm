package com.twitter.home_mixer.param.decider

import com.twitter.servo.decider.DeciderKeyEnum

/**
 * These values must correspond to the deciders configured in the
 * home-mixer/server/src/main/resources/config/decider.yml file
 *
 * @see [[com.twitter.product_mixer.core.product.ProductParamConfig.enabledDeciderKey]]
 */
object DeciderKey extends DeciderKeyEnum {
  // Products
  val EnableForYouProduct = Value("enable_for_you_product")

  val EnableFollowingProduct = Value("enable_following_product")

  val EnableScoredTweetsProduct = Value("enable_scored_tweets_product")

  val EnableListTweetsProduct = Value("enable_list_tweets_product")

  val EnableListRecommendedUsersProduct = Value("enable_list_recommended_users_product")

  val EnableSubscribedProduct = Value("enable_subscribed_product")

  // Candidate Pipelines
  val EnableForYouScoredTweetsCandidatePipeline =
    Value("enable_for_you_scored_tweets_candidate_pipeline")

  val EnableScoredTweetsTweetMixerCandidatePipeline =
    Value("enable_scored_tweets_tweet_mixer_candidate_pipeline")

  val EnableScoredTweetsInNetworkCandidatePipeline =
    Value("enable_scored_tweets_in_network_candidate_pipeline")

  val EnableScoredTweetsUtegCandidatePipeline =
    Value("enable_scored_tweets_uteg_candidate_pipeline")

  val EnableScoredTweetsFrsCandidatePipeline =
    Value("enable_scored_tweets_frs_candidate_pipeline")

  val EnableScoredTweetsListsCandidatePipeline =
    Value("enable_scored_tweets_lists_candidate_pipeline")

  val EnableScoredTweetsPopularVideosCandidatePipeline =
    Value("enable_scored_tweets_popular_videos_candidate_pipeline")

  val EnableScoredTweetsBackfillCandidatePipeline =
    Value("enable_scored_tweets_backfill_candidate_pipeline")

  val EnableSimClustersSimilarityFeatureHydration =
    Value("enable_simclusters_similarity_feature_hydration")
}
