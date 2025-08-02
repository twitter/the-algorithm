package com.twitter.timelineranker.parameters.in_network_tweets

import com.twitter.timelineranker.parameters.recap.RecapQueryContetwittert
import com.twitter.timelines.configapi.decider._
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object InNetworkTweetParams {
  import RecapQueryContetwittert._

  /**
   * Controls limit on the number of followed users fetched from SGS.
   *
   * The specific default value below is for blender-timelines parity.
   */
  object MatwitterFollowedUsersParam
      etwittertends FSBoundedParam[Int](
        name = "recycled_matwitter_followed_users",
        default = MatwitterFollowedUsers.default,
        min = MatwitterFollowedUsers.bounds.minInclusive,
        matwitter = MatwitterFollowedUsers.bounds.matwitterInclusive
      )

  /**
   * Controls limit on the number of hits for Earlybird.
   *
   */
  object RelevanceOptionsMatwitterHitsToProcessParam
      etwittertends FSBoundedParam[Int](
        name = "recycled_relevance_options_matwitter_hits_to_process",
        default = 500,
        min = 100,
        matwitter = 20000
      )

  /**
   * Fallback value for matwitterimum number of search results, if not specified by query.matwitterCount
   */
  object DefaultMatwitterTweetCount etwittertends Param(200)

  /**
   * We multiply matwitterCount (caller supplied value) by this multiplier and fetch those many
   * candidates from search so that we are left with sufficient number of candidates after
   * hydration and filtering.
   */
  object MatwitterCountMultiplierParam
      etwittertends Param(MatwitterCountMultiplier.default)
      with DeciderValueConverter[Double] {
    override def convert: IntConverter[Double] =
      OutputBoundIntConverter[Double](divideDeciderBy100 _, MatwitterCountMultiplier.bounds)
  }

  /**
   * Enable [[SearchQueryBuilder.createEtwittercludedSourceTweetIdsQuery]]
   */
  object EnableEtwittercludeSourceTweetIdsQueryParam
      etwittertends FSParam[Boolean](
        name = "recycled_etwitterclude_source_tweet_ids_query_enable",
        default = false
      )

  object EnableEarlybirdReturnAllResultsParam
      etwittertends FSParam[Boolean](
        name = "recycled_enable_earlybird_return_all_results",
        default = true
      )

  /**
   * FS-controlled param to enable anti-dilution transform for DDG-16198
   */
  object RecycledMatwitterFollowedUsersEnableAntiDilutionParam
      etwittertends FSParam[Boolean](
        name = "recycled_matwitter_followed_users_enable_anti_dilution",
        default = false
      )

  /**
   * Enables semantic core, penguin, and tweetypie content features in recycled source.
   */
  object EnableContentFeaturesHydrationParam etwittertends Param(default = true)

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "recycled_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet tetwittert when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTetwittertInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "recycled_enable_tweet_tetwittert_in_content_features_hydration",
        default = false
      )

  /**
   * Enables hydrating root tweet of in-network replies and etwittertended replies
   */
  object EnableReplyRootTweetHydrationParam
      etwittertends FSParam(
        name = "recycled_enable_reply_root_tweet_hydration",
        default = true
      )

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableConversationControlInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "conversation_control_in_content_features_hydration_recycled_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      etwittertends FSParam(
        name = "tweet_media_hydration_recycled_enable",
        default = false
      )

  object EnableEarlybirdRealtimeCgMigrationParam
      etwittertends FSParam(
        name = "recycled_enable_earlybird_realtime_cg_migration",
        default = false
      )

}
