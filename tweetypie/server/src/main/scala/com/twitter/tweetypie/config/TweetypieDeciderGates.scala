package com.twitter.tweetypie
package config

import com.twitter.decider.Decider
import com.twitter.tweetypie.decider.DeciderGates

object TweetypieDeciderGates {
  def apply(
    _decider: Decider,
    _overrides: Map[String, Boolean] = Map.empty
  ): TweetypieDeciderGates =
    new TweetypieDeciderGates {
      override def decider: Decider = _decider
      override def overrides: Map[String, Boolean] = _overrides
      override def prefix: String = "tweetypie"
    }
}

trait TweetypieDeciderGates extends DeciderGates {
  val checkSpamOnRetweet: Gate[Unit] = linear("check_spam_on_retweet")
  val checkSpamOnTweet: Gate[Unit] = linear("check_spam_on_tweet")
  val delayEraseUserTweets: Gate[Unit] = linear("delay_erase_user_tweets")
  val denyNonTweetPermalinks: Gate[Unit] = linear("deny_non_tweet_permalinks")
  val enableCommunityTweetCreates: Gate[Unit] = linear("enable_community_tweet_creates")
  val useConversationControlFeatureSwitchResults: Gate[Unit] = linear(
    "conversation_control_use_feature_switch_results")
  val enableExclusiveTweetControlValidation: Gate[Unit] = linear(
    "enable_exclusive_tweet_control_validation")
  val enableTrustedFriendsControlValidation: Gate[Unit] = linear(
    "enable_trusted_friends_control_validation"
  )
  val enableStaleTweetValidation: Gate[Unit] = linear(
    "enable_stale_tweet_validation"
  )
  val enforceRateLimitedClients: Gate[Unit] = linear("enforce_rate_limited_clients")
  val failClosedInVF: Gate[Unit] = linear("fail_closed_in_vf")
  val forkDarkTraffic: Gate[Unit] = linear("fork_dark_traffic")
  val hydrateConversationMuted: Gate[Unit] = linear("hydrate_conversation_muted")
  val hydrateCounts: Gate[Unit] = linear("hydrate_counts")
  val hydratePreviousCounts: Gate[Unit] = linear("hydrate_previous_counts")
  val hydrateDeviceSources: Gate[Unit] = linear("hydrate_device_sources")
  val hydrateEscherbirdAnnotations: Gate[Unit] = linear("hydrate_escherbird_annotations")
  val hydrateGnipProfileGeoEnrichment: Gate[Unit] = linear("hydrate_gnip_profile_geo_enrichment")
  val hydrateHasMedia: Gate[Unit] = linear("hydrate_has_media")
  val hydrateMedia: Gate[Unit] = linear("hydrate_media")
  val hydrateMediaRefs: Gate[Unit] = linear("hydrate_media_refs")
  val hydrateMediaTags: Gate[Unit] = linear("hydrate_media_tags")
  val hydratePastedMedia: Gate[Unit] = linear("hydrate_pasted_media")
  val hydratePerspectives: Gate[Unit] = linear("hydrate_perspectives")
  val hydratePerspectivesEditsForTimelines: Gate[Unit] = linear(
    "hydrate_perspectives_edits_for_timelines")
  val hydratePerspectivesEditsForTweetDetail: Gate[Unit] = linear(
    "hydrate_perspectives_edits_for_tweet_details")
  val hydratePerspectivesEditsForOtherSafetyLevels: Gate[Unit] =
    linear("hydrate_perspectives_edits_for_other_levels")
  val hydratePlaces: Gate[Unit] = linear("hydrate_places")
  val hydrateScrubEngagements: Gate[Unit] = linear("hydrate_scrub_engagements")
  val jiminyDarkRequests: Gate[Unit] = linear("jiminy_dark_requests")
  val logCacheExceptions: Gate[Unit] = linear("log_cache_exceptions")
  val logReads: Gate[Unit] = linear("log_reads")
  val logTweetCacheWrites: Gate[TweetId] = byId("log_tweet_cache_writes")
  val logWrites: Gate[Unit] = linear("log_writes")
  val logYoungTweetCacheWrites: Gate[TweetId] = byId("log_young_tweet_cache_writes")
  val maxRequestWidthEnabled: Gate[Unit] = linear("max_request_width_enabled")
  val mediaRefsHydratorIncludePastedMedia: Gate[Unit] = linear(
    "media_refs_hydrator_include_pasted_media")
  val rateLimitByLimiterService: Gate[Unit] = linear("rate_limit_by_limiter_service")
  val rateLimitTweetCreationFailure: Gate[Unit] = linear("rate_limit_tweet_creation_failure")
  val replicateReadsToATLA: Gate[Unit] = linear("replicate_reads_to_atla")
  val replicateReadsToPDXA: Gate[Unit] = linear("replicate_reads_to_pdxa")
  val disableInviteViaMention: Gate[Unit] = linear("disable_invite_via_mention")
  val shedReadTrafficVoluntarily: Gate[Unit] = linear("shed_read_traffic_voluntarily")
  val preferForwardedServiceIdentifierForClientId: Gate[Unit] =
    linear("prefer_forwarded_service_identifier_for_client_id")
  val enableRemoveUnmentionedImplicitMentions: Gate[Unit] = linear(
    "enable_remove_unmentioned_implicit_mentions")
  val validateCardRefAttachmentAndroid: Gate[Unit] = linear("validate_card_ref_attachment_android")
  val validateCardRefAttachmentNonAndroid: Gate[Unit] = linear(
    "validate_card_ref_attachment_non_android")
  val tweetVisibilityLibraryEnableParityTest: Gate[Unit] = linear(
    "tweet_visibility_library_enable_parity_test")
  val enableVfFeatureHydrationInQuotedTweetVLShim: Gate[Unit] = linear(
    "enable_vf_feature_hydration_in_quoted_tweet_visibility_library_shim")
  val disablePromotedTweetEdit: Gate[Unit] = linear("disable_promoted_tweet_edit")
  val shouldMaterializeContainers: Gate[Unit] = linear("should_materialize_containers")
  val checkTwitterBlueSubscriptionForEdit: Gate[Unit] = linear(
    "check_twitter_blue_subscription_for_edit")
  val hydrateBookmarksCount: Gate[Long] = byId("hydrate_bookmarks_count")
  val hydrateBookmarksPerspective: Gate[Long] = byId("hydrate_bookmarks_perspective")
  val setEditTimeWindowToSixtyMinutes: Gate[Unit] = linear("set_edit_time_window_to_sixty_minutes")
}
