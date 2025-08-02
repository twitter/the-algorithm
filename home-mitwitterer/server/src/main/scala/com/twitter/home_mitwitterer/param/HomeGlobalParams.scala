package com.twitter.home_mitwitterer.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

/**
 * Instantiate Params that do not relate to a specific product.
 *
 * @see [[com.twitter.product_mitwitterer.core.product.ProductParamConfig.supportedClientFSName]]
 */
object HomeGlobalParams {

  /**
   * This param is used to disable ads injection for timelines served by home-mitwitterer.
   * It is currently used to maintain user-role based no-ads lists for automation accounts,
   * and should NOT be used for other purposes.
   */
  object AdsDisableInjectionBasedOnUserRoleParam
      etwittertends FSParam("home_mitwitterer_ads_disable_injection_based_on_user_role", false)

  object EnableSendScoresToClient
      etwittertends FSParam[Boolean](
        name = "home_mitwitterer_enable_send_scores_to_client",
        default = false
      )

  object EnableNahFeedbackInfoParam
      etwittertends FSParam[Boolean](
        name = "home_mitwitterer_enable_nah_feedback_info",
        default = false
      )

  object MatwitterNumberReplaceInstructionsParam
      etwittertends FSBoundedParam[Int](
        name = "home_mitwitterer_matwitter_number_replace_instructions",
        default = 100,
        min = 0,
        matwitter = 200
      )

  object TimelinesPersistenceStoreMatwitterEntriesPerClient
      etwittertends FSBoundedParam[Int](
        name = "home_mitwitterer_timelines_persistence_store_matwitter_entries_per_client",
        default = 1800,
        min = 500,
        matwitter = 5000
      )

  object EnableNewTweetsPillAvatarsParam
      etwittertends FSParam[Boolean](
        name = "home_mitwitterer_enable_new_tweets_pill_avatars",
        default = true
      )

  object EnableSocialContetwittertParam
      etwittertends FSParam[Boolean](
        name = "home_mitwitterer_enable_social_contetwittert",
        default = true
      )

  object EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
      etwittertends FSParam[Boolean](
        name = "home_mitwitterer_enable_advertiser_brand_safety_settings_feature_hydrator",
        default = true
      )

  object EnableImpressionBloomFilter
      etwittertends FSParam[Boolean](
        name = "home_mitwitterer_enable_impression_bloom_filter",
        default = false
      )

  object ImpressionBloomFilterFalsePositiveRateParam
      etwittertends FSBoundedParam[Double](
        name = "home_mitwitterer_impression_bloom_filter_false_positive_rate",
        default = 0.005,
        min = 0.001,
        matwitter = 0.01
      )

  object EnableScribeServedCandidatesParam
      etwittertends FSParam[Boolean](
        name = "home_mitwitterer_served_tweets_enable_scribing",
        default = true
      )
}
