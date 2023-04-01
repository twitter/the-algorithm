package com.twitter.timelineranker.parameters.recap_author

import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object RecapAuthorParams {

  /**
   * Enables semantic core, penguin, and tweetypie content features in recap author source.
   */
  object EnableContentFeaturesHydrationParam extends Param(false)

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      extends FSParam(
        name = "recap_author_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet text when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTextInContentFeaturesHydrationParam
      extends FSParam(
        name = "recap_author_enable_tweet_text_in_content_features_hydration",
        default = false
      )

  object EnableEarlybirdRealtimeCgMigrationParam
      extends FSParam(
        name = "recap_author_enable_earlybird_realtime_cg_migration",
        default = false
      )
  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableConversationControlInContentFeaturesHydrationParam
      extends FSParam(
        name = "conversation_control_in_content_features_hydration_recap_author_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      extends FSParam(
        name = "tweet_media_hydration_recap_author_enable",
        default = false
      )

}
