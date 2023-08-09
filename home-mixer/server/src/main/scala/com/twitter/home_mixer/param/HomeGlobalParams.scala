package com.twitter.home_mixer.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

/**
 * Instantiate Params that do not relate to a specific product.
 *
 * @see [[com.twitter.product_mixer.core.product.ProductParamConfig.supportedClientFSName]]
 */
object HomeGlobalParams {

  /**
   * This param is used to disable ads injection for timelines served by home-mixer.
   * It is currently used to maintain user-role based no-ads lists for automation accounts,
   * and should NOT be used for other purposes.
   */
  object AdsDisableInjectionBasedOnUserRoleParam
      extends FSParam("home_mixer_ads_disable_injection_based_on_user_role", false)

  object EnableSendScoresToClient
      extends FSParam[Boolean](
        name = "home_mixer_enable_send_scores_to_client",
        default = false
      )

  object EnableNahFeedbackInfoParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_nah_feedback_info",
        default = false
      )

  object MaxNumberReplaceInstructionsParam
      extends FSBoundedParam[Int](
        name = "home_mixer_max_number_replace_instructions",
        default = 100,
        min = 0,
        max = 200
      )

  object TimelinesPersistenceStoreMaxEntriesPerClient
      extends FSBoundedParam[Int](
        name = "home_mixer_timelines_persistence_store_max_entries_per_client",
        default = 1800,
        min = 500,
        max = 5000
      )

  object EnableNewTweetsPillAvatarsParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_new_tweets_pill_avatars",
        default = true
      )

  object EnableSocialContextParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_social_context",
        default = true
      )

  object EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_advertiser_brand_safety_settings_feature_hydrator",
        default = true
      )

  object EnableImpressionBloomFilter
      extends FSParam[Boolean](
        name = "home_mixer_enable_impression_bloom_filter",
        default = false
      )

  object ImpressionBloomFilterFalsePositiveRateParam
      extends FSBoundedParam[Double](
        name = "home_mixer_impression_bloom_filter_false_positive_rate",
        default = 0.005,
        min = 0.001,
        max = 0.01
      )

  object EnableScribeServedCandidatesParam
      extends FSParam[Boolean](
        name = "home_mixer_served_tweets_enable_scribing",
        default = true
      )
}
