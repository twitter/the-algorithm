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

  object EnableServedCandidateKafkaPublishingParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_served_candidate_kafka_publishing",
        default = true
      )

  /**
   * This author ID list is used purely for realtime metrics collection around how often we
   * are serving Tweets from these authors and which candidate sources they are coming from.
   */
  object AuthorListForStatsParam
      extends FSParam[Set[Long]](
        name = "home_mixer_author_list_for_stats",
        default = Set.empty
      )

  object EnableSocialContextParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_social_context",
        default = false
      )

  object EnableGizmoduckAuthorSafetyFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_gizmoduck_author_safety_feature_hydrator",
        default = true
      )

  object EnableFeedbackFatigueParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_feedback_fatigue",
        default = true
      )

  object BlueVerifiedAuthorInNetworkMultiplierParam
      extends FSBoundedParam[Double](
        name = "home_mixer_blue_verified_author_in_network_multiplier",
        default = 4.0,
        min = 0.0,
        max = 100.0
      )

  object BlueVerifiedAuthorOutOfNetworkMultiplierParam
      extends FSBoundedParam[Double](
        name = "home_mixer_blue_verified_author_out_of_network_multiplier",
        default = 2.0,
        min = 0.0,
        max = 100.0
      )

  object EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
      extends FSParam[Boolean](
        name = "home_mixer_enable_advertiser_brand_safety_settings_feature_hydrator",
        default = true
      )
}
