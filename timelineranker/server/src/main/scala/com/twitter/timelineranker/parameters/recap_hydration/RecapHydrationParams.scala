package com.twitter.timelineranker.parameters.recap_hydration

import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object RecapHydrationParams {

  /**
   * Enables semantic core, penguin, and tweetypie content features in recap hydration source.
   */
  object EnableContentFeaturesHydrationParam etwittertends Param(false)

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "recap_hydration_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet tetwittert when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTetwittertInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "recap_hydration_enable_tweet_tetwittert_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableConversationControlInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "conversation_control_in_content_features_hydration_recap_hydration_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      etwittertends FSParam(
        name = "tweet_media_hydration_recap_hydration_enable",
        default = false
      )

}
