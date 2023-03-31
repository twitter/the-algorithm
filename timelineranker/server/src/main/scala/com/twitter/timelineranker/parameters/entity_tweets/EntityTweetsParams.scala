package com.twitter.timelineranker.parameters.entity_tweets

import com.twitter.timelineranker.decider.DeciderKey
import com.twitter.timelines.configapi.decider.DeciderParam
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object EntityTweetsParams {

  /**
   * Controls limit on the number of followed users fetched from SGS.
   */
  object MaxFollowedUsersParam
      extends FSBoundedParam[Int](
        name = "entity_tweets_max_followed_users",
        default = 1000,
        min = 0,
        max = 5000
      )

  /**
   * Enables semantic core, penguin, and tweetypie content features in entity tweets source.
   */
  object EnableContentFeaturesHydrationParam
      extends DeciderParam[Boolean](
        decider = DeciderKey.EntityTweetsEnableContentFeaturesHydration,
        default = false
      )

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      extends FSParam(
        name = "entity_tweets_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet text when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTextInContentFeaturesHydrationParam
      extends FSParam(
        name = "entity_tweets_enable_tweet_text_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableConversationControlInContentFeaturesHydrationParam
      extends FSParam(
        name = "conversation_control_in_content_features_hydration_entity_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      extends FSParam(
        name = "tweet_media_hydration_entity_tweets_enable",
        default = false
      )

}
