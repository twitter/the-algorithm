package com.twitter.timelineranker.parameters.recap

import com.twitter.timelines.configapi.decider._
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.util.bounds.BoundsWithDefault

object RecapParams {
  val MaxFollowedUsers: BoundsWithDefault[Int] = BoundsWithDefault[Int](1, 3000, 1000)
  val MaxCountMultiplier: BoundsWithDefault[Double] = BoundsWithDefault[Double](0.1, 2.0, 2.0)
  val MaxRealGraphAndFollowedUsers: BoundsWithDefault[Int] = BoundsWithDefault[Int](0, 2000, 1000)
  val ProbabilityRandomTweet: BoundsWithDefault[Double] = BoundsWithDefault[Double](0.0, 1.0, 0.0)

  /**
   * Controls limit on the number of followed users fetched from SGS.
   *
   * The specific default value below is for blender-timelines parity.
   */
  object MaxFollowedUsersParam
      extends FSBoundedParam[Int](
        name = "recap_max_followed_users",
        default = MaxFollowedUsers.default,
        min = MaxFollowedUsers.bounds.minInclusive,
        max = MaxFollowedUsers.bounds.maxInclusive
      )

  /**
   * Controls limit on the number of hits for Earlybird.
   * We added it solely for backward compatibility, to align with recycled.
   * RecapSource is deprecated, but, this param is used by RecapAuthor source
   */
  object RelevanceOptionsMaxHitsToProcessParam
      extends FSBoundedParam[Int](
        name = "recap_relevance_options_max_hits_to_process",
        default = 500,
        min = 100,
        max = 20000
      )

  /**
   * Enables fetching author seedset from real graph users. Only used if user follows >= 1000.
   * If true, expands author seedset with real graph users and recent followed users.
   * Otherwise, user seedset only includes followed users.
   */
  object EnableRealGraphUsersParam extends Param(false)

  /**
   * Only used if EnableRealGraphUsersParam is true and OnlyRealGraphUsersParam is false.
   * Maximum number of real graph users and recent followed users when mixing recent/real-graph users.
   */
  object MaxRealGraphAndFollowedUsersParam
      extends Param(MaxRealGraphAndFollowedUsers.default)
      with DeciderValueConverter[Int] {
    override def convert: IntConverter[Int] =
      OutputBoundIntConverter(MaxRealGraphAndFollowedUsers.bounds)
  }

  /**
   * FS-controlled param to override the MaxRealGraphAndFollowedUsersParam decider value for experiments
   */
  object MaxRealGraphAndFollowedUsersFSOverrideParam
      extends FSBoundedParam[Option[Int]](
        name = "max_real_graph_and_followers_users_fs_override_param",
        default = None,
        min = Some(100),
        max = Some(10000)
      )

  /**
   * Experimental params for leveling the playing field between user folowees received from
   * real-graph and follow-graph stores.
   * Author relevance scores returned by real-graph are currently being used for light-ranking
   * in-network tweet candidates.
   * Follow-graph store returns the most recent followees without any relevance scores
   * We are trying to impute the missing scores by using aggregated statistics (min, avg, p50, etc.)
   * of real-graph scores.
   */
  object ImputeRealGraphAuthorWeightsParam
      extends FSParam(name = "impute_real_graph_author_weights", default = false)

  object ImputeRealGraphAuthorWeightsPercentileParam
      extends FSBoundedParam[Int](
        name = "impute_real_graph_author_weights_percentile",
        default = 50,
        min = 0,
        max = 99)

  /**
   * Enable running the new pipeline for recap author source
   */
  object EnableNewRecapAuthorPipeline extends Param(false)

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
   * Enables return all results from search index.
   */
  object EnableReturnAllResultsParam
      extends FSParam(name = "recap_enable_return_all_results", default = false)

  /**
   * Includes one or multiple random tweets in the response.
   */
  object IncludeRandomTweetParam
      extends FSParam(name = "recap_include_random_tweet", default = false)

  /**
   * One single random tweet (true) or tag tweet as random with given probability (false).
   */
  object IncludeSingleRandomTweetParam
      extends FSParam(name = "recap_include_random_tweet_single", default = true)

  /**
   * Probability to tag a tweet as random (will not be ranked).
   */
  object ProbabilityRandomTweetParam
      extends FSBoundedParam(
        name = "recap_include_random_tweet_probability",
        default = ProbabilityRandomTweet.default,
        min = ProbabilityRandomTweet.bounds.minInclusive,
        max = ProbabilityRandomTweet.bounds.maxInclusive)

  /**
   * Enable extra sorting by score for search results.
   */
  object EnableExtraSortingInSearchResultParam extends Param(true)

  /**
   * Enables semantic core, penguin, and tweetypie content features in recap source.
   */
  object EnableContentFeaturesHydrationParam extends Param(true)

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      extends FSParam(
        name = "recap_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet text when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTextInContentFeaturesHydrationParam
      extends FSParam(
        name = "recap_enable_tweet_text_in_content_features_hydration",
        default = false
      )

  /**
   * Enables hydrating in-network inReplyToTweet features
   */
  object EnableInNetworkInReplyToTweetFeaturesHydrationParam
      extends FSParam(
        name = "recap_enable_in_network_in_reply_to_tweet_features_hydration",
        default = false
      )

  /**
   * Enables hydrating root tweet of in-network replies and extended replies
   */
  object EnableReplyRootTweetHydrationParam
      extends FSParam(
        name = "recap_enable_reply_root_tweet_hydration",
        default = false
      )

  /**
   * Enable setting tweetTypes in search queries with TweetKindOption in RecapQuery
   */
  object EnableSettingTweetTypesWithTweetKindOption
      extends FSParam(
        name = "recap_enable_setting_tweet_types_with_tweet_kind_option",
        default = false
      )

  /**
   * Enable relevance search, otherwise recency search from earlybird.
   */
  object EnableRelevanceSearchParam
      extends FSParam(
        name = "recap_enable_relevance_search",
        default = true
      )

  object EnableExpandedExtendedRepliesFilterParam
      extends FSParam(
        name = "recap_enable_expanded_extended_replies_filter",
        default = false
      )

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableConversationControlInContentFeaturesHydrationParam
      extends FSParam(
        name = "conversation_control_in_content_features_hydration_recap_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      extends FSParam(
        name = "tweet_media_hydration_recap_enable",
        default = false
      )

  object EnableExcludeSourceTweetIdsQueryParam
      extends FSParam[Boolean](
        name = "recap_exclude_source_tweet_ids_query_enable",
        default = false
      )
}
