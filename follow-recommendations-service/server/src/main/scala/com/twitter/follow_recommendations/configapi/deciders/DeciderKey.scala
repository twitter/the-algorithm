try {
package com.twitter.follow_recommendations.configapi.deciders

import com.twitter.servo.decider.DeciderKeyEnum

object DeciderKey extends DeciderKeyEnum {
  val EnableDiffyModuleDarkReading = Value("enable_diffy_module_dark_reading")
  val EnableRecommendations = Value("enable_recommendations")
  val EnableScoreUserCandidates = Value("enable_score_user_candidates")
  val EnableProfileSidebarProduct = Value("enable_profile_sidebar_product")
  val EnableMagicRecsProduct = Value("enable_magic_recs_product")
  val EnableRuxLandingPageProduct = Value("enable_rux_landing_page_product")
  val EnableRuxPymkProduct = Value("enable_rux_pymk_product")
  val EnableProfileBonusFollowProduct = Value("enable_profile_bonus_follow_product")
  val EnableElectionExploreWtfProduct = Value("enable_election_explore_wtf_product")
  val EnableClusterFollowProduct = Value("enable_cluster_follow_product")
  val EnableHomeTimelineProduct = Value("enable_home_timeline_product")
  val EnableHtlBonusFollowProduct = Value("enable_htl_bonus_follow_product")
  val EnableExploreTabProduct = Value("enable_explore_tab_product")
  val EnableSidebarProduct = Value("enable_sidebar_product")
  val EnableNuxPymkProduct = Value("enable_nux_pymk_product")
  val EnableNuxInterestsProduct = Value("enable_nux_interests_product")
  val EnableNuxTopicBonusFollowProduct = Value("enable_nux_topic_bonus_follow_product")
  val EnableCampaignFormProduct = Value("enable_campaign_form_product")
  val EnableReactiveFollowProduct = Value("enable_reactive_follow_product")
  val EnableIndiaCovid19CuratedAccountsWtfProduct = Value(
    "enable_india_covid19_curated_accounts_wtf_product")
  val EnableAbUploadProduct = Value("enable_ab_upload_product")
  val EnablePeolePlusPlusProduct = Value("enable_people_plus_plus_product")
  val EnableTweetNotificationRecsProduct = Value("enable_tweet_notification_recs_product")
  val EnableProfileDeviceFollow = Value("enable_profile_device_follow_product")
  val EnableRecosBackfillProduct = Value("enable_recos_backfill_product")
  val EnablePostNuxFollowTaskProduct = Value("enable_post_nux_follow_task_product")
  val EnableCuratedSpaceHostsProduct = Value("enable_curated_space_hosts_product")
  val EnableNuxGeoCategoryProduct = Value("enable_nux_geo_category_product")
  val EnableNuxInterestsCategoryProduct = Value("enable_nux_interests_category_product")
  val EnableNuxPymkCategoryProduct = Value("enable_nux_pymk_category_product")
  val EnableHomeTimelineTweetRecsProduct = Value("enable_home_timeline_tweet_recs_product")
  val EnableHtlBulkFriendFollowsProduct = Value("enable_htl_bulk_friend_follows_product")
  val EnableNuxAutoFollowProduct = Value("enable_nux_auto_follow_product")
  val EnableSearchBonusFollowProduct = Value("enable_search_bonus_follow_product")
  val EnableFetchUserInRequestBuilder = Value("enable_fetch_user_in_request_builder")
  val EnableProductMixerMagicRecsProduct = Value("enable_product_mixer_magic_recs_product")
  val EnableHomeTimelineReverseChronProduct = Value("enable_home_timeline_reverse_chron_product")
  val EnableProductMixerPipelineMagicRecsDarkRead = Value(
    "enable_product_mixer_pipeline_magic_recs_dark_read")
  val EnableExperimentalCaching = Value("enable_experimental_caching")
  val EnableDistributedCaching = Value("enable_distributed_caching")
  val EnableGizmoduckCaching = Value("enable_gizmoduck_caching")
  val EnableTrafficDarkReading = Value("enable_traffic_dark_reading")
  val EnableGraphFeatureServiceRequests = Value("enable_graph_feature_service_requests")
}

} catch {
  case e: Exception =>
}
