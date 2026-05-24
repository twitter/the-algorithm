package com.twitter.visibility.configapi.configs.overrides

import com.twitter.decider.LocalOverrides

object VisibilityLibraryDeciderOverrides
    extends LocalOverrides.Namespace("visibility-library", "") {

  val EnableLocalizedTombstoneOnVisibilityResults = feature(
    "visibility_library_enable_localized_tombstones_on_visibility_results")

  val EnableLocalizedInterstitialGenerator: LocalOverrides.Override =
    feature("visibility_library_enable_localized_interstitial_generator")

  val EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRule: LocalOverrides.Override =
    feature("visibility_library_enable_inner_quoted_tweet_viewer_blocks_author_interstitial_rule")
  val EnableInnerQuotedTweetViewerMutesAuthorInterstitialRule: LocalOverrides.Override =
    feature("visibility_library_enable_inner_quoted_tweet_viewer_mutes_author_interstitial_rule")

  val EnableBackendLimitedActions: LocalOverrides.Override =
    feature("visibility_library_enable_backend_limited_actions")

  val disableLegacyInterstitialFilteredReason: LocalOverrides.Override = feature(
    "visibility_library_disable_legacy_interstitial_filtered_reason")
}
