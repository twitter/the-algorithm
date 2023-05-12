package com.twitter.tweetypie.decider.overrides

import com.twitter.decider.LocalOverrides

object TweetyPieDeciderOverrides extends LocalOverrides.Namespace("tweetypie", "tweetypie_") {
  val CheckSpamOnRetweet: LocalOverrides.Override = feature("check_spam_on_retweet")
  val CheckSpamOnTweet: LocalOverrides.Override = feature("check_spam_on_tweet")
  val ConversationControlUseFeatureSwitchResults: LocalOverrides.Override = feature(
    "conversation_control_use_feature_switch_results")
  val ConversationControlTweetCreateEnabled: LocalOverrides.Override = feature(
    "conversation_control_tweet_create_enabled")
  val EnableExclusiveTweetControlValidation: LocalOverrides.Override = feature(
    "enable_exclusive_tweet_control_validation")
  val EnableHotKeyCaches: LocalOverrides.Override = feature("enable_hot_key_caches")
  val HydrateConversationMuted: LocalOverrides.Override = feature("hydrate_conversation_muted")
  val HydrateExtensionsOnWrite: LocalOverrides.Override = feature("hydrate_extensions_on_write")
  val HydrateEscherbirdAnnotations: LocalOverrides.Override = feature(
    "hydrate_escherbird_annotations")
  val HydrateGnipProfileGeoEnrichment: LocalOverrides.Override = feature(
    "hydrate_gnip_profile_geo_enrichment")
  val HydratePastedPics: LocalOverrides.Override = feature("hydrate_pasted_pics")
  val HydratePerspectivesEditsForOtherSafetyLevels: LocalOverrides.Override = feature(
    "hydrate_perspectives_edits_for_other_levels")
  val HydrateScrubEngagements: LocalOverrides.Override = feature("hydrate_scrub_engagements")
  val LogRepoExceptions: LocalOverrides.Override = feature("log_repo_exceptions")
  val MediaRefsHydratorIncludePastedMedia: LocalOverrides.Override = feature(
    "media_refs_hydrator_include_pasted_media")
  val ShortCircuitLikelyPartialTweetReads: LocalOverrides.Override = feature(
    "short_circuit_likely_partial_tweet_reads_ms")
  val RateLimitByLimiterService: LocalOverrides.Override = feature("rate_limit_by_limiter_service")
  val RateLimitTweetCreationFailure: LocalOverrides.Override = feature(
    "rate_limit_tweet_creation_failure")
  val ReplyTweetConversationControlHydrationEnabled = feature(
    "reply_tweet_conversation_control_hydration_enabled"
  )
  val DisableInviteViaMention = feature(
    "disable_invite_via_mention"
  )
  val EnableRemoveUnmentionedImplicitMentions: LocalOverrides.Override = feature(
    "enable_remove_unmentioned_implicit_mentions")
  val useReplicatedDeleteTweet2: LocalOverrides.Override = feature("use_replicated_delete_tweet_2")
}
