package com.twitter.timelineranker.parameters.in_network_tweets

import com.twitter.timelineranker.parameters.recap.RecapQueryContext
import com.twitter.timelines.configapi.decider._
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object InNetworkTweetParams {
  import RecapQueryContext._

  /**
   * Controls limit on the number of followed users fetched from SGS.
   *
   * The specific default value below is for blender-timelines parity.
   */
  object MaxFollowedUsersParam
      extends FSBoundedParam[Int](
        name = "recycled_max_followed_users",
        default = MaxFollowedUsers.default,
        min = MaxFollowedUsers.bounds.minInclusive,
        max = MaxFollowedUsers.bounds.maxInclusive
      )

  /**
   * Controls limit on the number of hits for Earlybird.
   *
   */
  object RelevanceOptionsMaxHitsToProcessParam
      extends FSBoundedParam[Int](
        name = "recycled_relevance_options_max_hits_to_process",
        default = 500,
        min = 100,
        max = 20000
      )

  /**
   * Fallback value for maximum number of search results, if not specified by query.maxCount
   */
  object DefaultMaxTweetCount extends Param(200)

  /**
   * We multiply maxCount (caller supplied value) by this multiplier and fetch those many
   * candidates from search so that we are left with sufficient number of candidates after
   * hydration and filtering.
   */
  object MaxCountMultiplierParam
      extends Param(MaxCountMultiplier.default)
      with DeciderValueConverter[Double] {
    override def convert: IntConverter[Double] =
      OutputBoundIntConverter[Double](divideDeciderBy100 _, MaxCountMultiplier.bounds)
  }

  /**
   * Enable [[SearchQueryBuilder.createExcludedSourceTweetIdsQuery]]
   */
  object EnableExcludeSourceTweetIdsQueryParam
      extends FSParam[Boolean](
        name = "recycled_exclude_source_tweet_ids_query_enable",
        default = false
      )

  object EnableEarlybirdReturnAllResultsParam
      extends FSParam[Boolean](
        name = "recycled_enable_earlybird_return_all_results",
        default = true
      )

  /**
   * FS-controlled param to enable anti-dilution transform for DDG-16198
   */
  object RecycledMaxFollowedUsersEnableAntiDilutionParam
      extends FSParam[Boolean](
        name = "recycled_max_followed_users_enable_anti_dilution",
        default = false
      )

  /**
   * Enables semantic core, penguin, and tweetypie content features in recycled source.
   */
  object EnableContentFeaturesHydrationParam extends Param(default = true)

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      extends FSParam(
        name = "recycled_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet text when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTextInContentFeaturesHydrationParam
      extends FSParam(
        name = "recycled_enable_tweet_text_in_content_features_hydration",
        default = false
      )

  /**
   * Enables hydrating root tweet of in-network replies and extended replies
   */
  object EnableReplyRootTweetHydrationParam
      extends FSParam(
        name = "recycled_enable_reply_root_tweet_hydration",
        default = true
      )

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableConversationControlInContentFeaturesHydrationParam
      extends FSParam(
        name = "conversation_control_in_content_features_hydration_recycled_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      extends FSParam(
        name = "tweet_media_hydration_recycled_enable",
        default = false
      )

  object EnableEarlybirdRealtimeCgMigrationParam
      extends FSParam(
        name = "recycled_enable_earlybird_realtime_cg_migration",
        default = false
      )

}
