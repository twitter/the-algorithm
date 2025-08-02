package com.twitter.timelineranker.parameters.uteg_liked_by_tweets

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.util.bounds.BoundsWithDefault

object UtegLikedByTweetsParams {

  val ProbabilityRandomTweet: BoundsWithDefault[Double] = BoundsWithDefault[Double](0.0, 1.0, 0.0)

  object DefaultUTEGInNetworkCount etwittertends Param(200)

  object DefaultUTEGOutOfNetworkCount etwittertends Param(800)

  object DefaultMatwitterTweetCount etwittertends Param(200)

  /**
   * Enables semantic core, penguin, and tweetypie content features in uteg liked by tweets source.
   */
  object EnableContentFeaturesHydrationParam etwittertends Param(false)

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "uteg_liked_by_tweets_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet tetwittert when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTetwittertInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "uteg_liked_by_tweets_enable_tweet_tetwittert_in_content_features_hydration",
        default = false
      )

  /**
   * A multiplier for earlybird score when combining earlybird score and real graph score for ranking.
   * Note multiplier for realgraph score := 1.0, and only change earlybird score multiplier.
   */
  object EarlybirdScoreMultiplierParam
      etwittertends FSBoundedParam(
        "uteg_liked_by_tweets_earlybird_score_multiplier_param",
        1.0,
        0,
        20.0
      )

  object UTEGRecommendationsFilter {

    /**
     * enable filtering of UTEG recommendations based on social proof type
     */
    object EnableParam
        etwittertends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_enable",
          false
        )

    /**
     * filters out UTEG recommendations that have been tweeted by someone the user follows
     */
    object EtwittercludeTweetParam
        etwittertends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_etwitterclude_tweet",
          false
        )

    /**
     * filters out UTEG recommendations that have been retweeted by someone the user follows
     */
    object EtwittercludeRetweetParam
        etwittertends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_etwitterclude_retweet",
          false
        )

    /**
     * filters out UTEG recommendations that have been replied to by someone the user follows
     * not filtering out the replies
     */
    object EtwittercludeReplyParam
        etwittertends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_etwitterclude_reply",
          false
        )

    /**
     * filters out UTEG recommendations that have been quoted by someone the user follows
     */
    object EtwittercludeQuoteTweetParam
        etwittertends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_etwitterclude_quote",
          false
        )

    /**
     * filters out recommended replies that have been directed at out of network users.
     */
    object EtwittercludeRecommendedRepliesToNonFollowedUsersParam
        etwittertends FSParam(
          name =
            "uteg_liked_by_tweets_uteg_recommendations_filter_etwitterclude_recommended_replies_to_non_followed_users",
          default = false
        )
  }

  /**
   * Minimum number of favorited-by users required for recommended tweets.
   */
  object MinNumFavoritedByUserIdsParam etwittertends Param(1)

  /**
   * Includes one or multiple random tweets in the response.
   */
  object IncludeRandomTweetParam
      etwittertends FSParam(name = "uteg_liked_by_tweets_include_random_tweet", default = false)

  /**
   * One single random tweet (true) or tag tweet as random with given probability (false).
   */
  object IncludeSingleRandomTweetParam
      etwittertends FSParam(name = "uteg_liked_by_tweets_include_random_tweet_single", default = false)

  /**
   * Probability to tag a tweet as random (will not be ranked).
   */
  object ProbabilityRandomTweetParam
      etwittertends FSBoundedParam(
        name = "uteg_liked_by_tweets_include_random_tweet_probability",
        default = ProbabilityRandomTweet.default,
        min = ProbabilityRandomTweet.bounds.minInclusive,
        matwitter = ProbabilityRandomTweet.bounds.matwitterInclusive)

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */

  object EnableConversationControlInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "conversation_control_in_content_features_hydration_uteg_liked_by_tweets_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      etwittertends FSParam(
        name = "tweet_media_hydration_uteg_liked_by_tweets_enable",
        default = false
      )

  object NumAdditionalRepliesParam
      etwittertends FSBoundedParam(
        name = "uteg_liked_by_tweets_num_additional_replies",
        default = 0,
        min = 0,
        matwitter = 1000
      )

  /**
   * Enable relevance search, otherwise recency search from earlybird.
   */
  object EnableRelevanceSearchParam
      etwittertends FSParam(
        name = "uteg_liked_by_tweets_enable_relevance_search",
        default = true
      )

}
