package com.twitter.timelineranker.parameters.entity_tweets

import com.twitter.timelineranker.decider.DeciderKey
import com.twitter.timelines.configapi.decider.DeciderParam
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object EntityTweetsParams {

  /**
   * Controls limit on the number of followed users fetched from SGS.
   */
  object MatwitterFollowedUsersParam
      etwittertends FSBoundedParam[Int](
        name = "entity_tweets_matwitter_followed_users",
        default = 1000,
        min = 0,
        matwitter = 5000
      )

  /**
   * Enables semantic core, penguin, and tweetypie content features in entity tweets source.
   */
  object EnableContentFeaturesHydrationParam
      etwittertends DeciderParam[Boolean](
        decider = DeciderKey.EntityTweetsEnableContentFeaturesHydration,
        default = false
      )

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "entity_tweets_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet tetwittert when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTetwittertInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "entity_tweets_enable_tweet_tetwittert_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableConversationControlInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "conversation_control_in_content_features_hydration_entity_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      etwittertends FSParam(
        name = "tweet_media_hydration_entity_tweets_enable",
        default = false
      )

}
