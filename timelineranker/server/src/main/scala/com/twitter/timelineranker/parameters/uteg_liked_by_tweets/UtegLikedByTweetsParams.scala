package com.twitter.timelineranker.parameters.uteg_liked_by_tweets

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.util.bounds.BoundsWithDefault

object UtegLikedByTweetsParams {

  val ProbabilityRandomTweet: BoundsWithDefault[Double] = BoundsWithDefault[Double](0.0, 1.0, 0.0)

  object DefaultUTEGInNetworkCount extends Param(200)

  object DefaultUTEGOutOfNetworkCount extends Param(800)

  object DefaultMaxTweetCount extends Param(200)

  /**
   * Enables semantic core, penguin, and tweetypie content features in uteg liked by tweets source.
   */
  object EnableContentFeaturesHydrationParam extends Param(false)

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      extends FSParam(
        name = "uteg_liked_by_tweets_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet text when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTextInContentFeaturesHydrationParam
      extends FSParam(
        name = "uteg_liked_by_tweets_enable_tweet_text_in_content_features_hydration",
        default = false
      )

  /**
   * A multiplier for earlybird score when combining earlybird score and real graph score for ranking.
   * Note multiplier for realgraph score := 1.0, and only change earlybird score multiplier.
   */
  object EarlybirdScoreMultiplierParam
      extends FSBoundedParam(
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
        extends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_enable",
          false
        )

    /**
     * filters out UTEG recommendations that have been tweeted by someone the user follows
     */
    object ExcludeTweetParam
        extends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_exclude_tweet",
          false
        )

    /**
     * filters out UTEG recommendations that have been retweeted by someone the user follows
     */
    object ExcludeRetweetParam
        extends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_exclude_retweet",
          false
        )

    /**
     * filters out UTEG recommendations that have been replied to by someone the user follows
     * not filtering out the replies
     */
    object ExcludeReplyParam
        extends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_exclude_reply",
          false
        )

    /**
     * filters out UTEG recommendations that have been quoted by someone the user follows
     */
    object ExcludeQuoteTweetParam
        extends FSParam(
          "uteg_liked_by_tweets_uteg_recommendations_filter_exclude_quote",
          false
        )

    /**
     * filters out recommended replies that have been directed at out of network users.
     */
    object ExcludeRecommendedRepliesToNonFollowedUsersParam
        extends FSParam(
          name =
            "uteg_liked_by_tweets_uteg_recommendations_filter_exclude_recommended_replies_to_non_followed_users",
          default = false
        )
  }

  /**
   * Minimum number of favorited-by users required for recommended tweets.
   */
  object MinNumFavoritedByUserIdsParam extends Param(1)

  /**
   * Includes one or multiple random tweets in the response.
   */
  object IncludeRandomTweetParam
      extends FSParam(name = "uteg_liked_by_tweets_include_random_tweet", default = false)

  /**
   * One single random tweet (true) or tag tweet as random with given probability (false).
   */
  object IncludeSingleRandomTweetParam
      extends FSParam(name = "uteg_liked_by_tweets_include_random_tweet_single", default = false)

  /**
   * Probability to tag a tweet as random (will not be ranked).
   */
  object ProbabilityRandomTweetParam
      extends FSBoundedParam(
        name = "uteg_liked_by_tweets_include_random_tweet_probability",
        default = ProbabilityRandomTweet.default,
        min = ProbabilityRandomTweet.bounds.minInclusive,
        max = ProbabilityRandomTweet.bounds.maxInclusive)

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */

  object EnableConversationControlInContentFeaturesHydrationParam
      extends FSParam(
        name = "conversation_control_in_content_features_hydration_uteg_liked_by_tweets_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      extends FSParam(
        name = "tweet_media_hydration_uteg_liked_by_tweets_enable",
        default = false
      )

  object NumAdditionalRepliesParam
      extends FSBoundedParam(
        name = "uteg_liked_by_tweets_num_additional_replies",
        default = 0,
        min = 0,
        max = 1000
      )

  /**
   * Enable relevance search, otherwise recency search from earlybird.
   */
  object EnableRelevanceSearchParam
      extends FSParam(
        name = "uteg_liked_by_tweets_enable_relevance_search",
        default = true
      )

}
