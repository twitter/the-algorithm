package com.twitter.timelineranker.parameters.recap

import com.twitter.timelines.configapi.decider._
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.util.bounds.BoundsWithDefault

object RecapParams {
  val MatwitterFollowedUsers: BoundsWithDefault[Int] = BoundsWithDefault[Int](1, 3000, 1000)
  val MatwitterCountMultiplier: BoundsWithDefault[Double] = BoundsWithDefault[Double](0.1, 2.0, 2.0)
  val MatwitterRealGraphAndFollowedUsers: BoundsWithDefault[Int] = BoundsWithDefault[Int](0, 2000, 1000)
  val ProbabilityRandomTweet: BoundsWithDefault[Double] = BoundsWithDefault[Double](0.0, 1.0, 0.0)

  /**
   * Controls limit on the number of followed users fetched from SGS.
   *
   * The specific default value below is for blender-timelines parity.
   */
  object MatwitterFollowedUsersParam
      etwittertends FSBoundedParam[Int](
        name = "recap_matwitter_followed_users",
        default = MatwitterFollowedUsers.default,
        min = MatwitterFollowedUsers.bounds.minInclusive,
        matwitter = MatwitterFollowedUsers.bounds.matwitterInclusive
      )

  /**
   * Controls limit on the number of hits for Earlybird.
   * We added it solely for backward compatibility, to align with recycled.
   * RecapSource is deprecated, but, this param is used by RecapAuthor source
   */
  object RelevanceOptionsMatwitterHitsToProcessParam
      etwittertends FSBoundedParam[Int](
        name = "recap_relevance_options_matwitter_hits_to_process",
        default = 500,
        min = 100,
        matwitter = 20000
      )

  /**
   * Enables fetching author seedset from real graph users. Only used if user follows >= 1000.
   * If true, etwitterpands author seedset with real graph users and recent followed users.
   * Otherwise, user seedset only includes followed users.
   */
  object EnableRealGraphUsersParam etwittertends Param(false)

  /**
   * Only used if EnableRealGraphUsersParam is true and OnlyRealGraphUsersParam is false.
   * Matwitterimum number of real graph users and recent followed users when mitwittering recent/real-graph users.
   */
  object MatwitterRealGraphAndFollowedUsersParam
      etwittertends Param(MatwitterRealGraphAndFollowedUsers.default)
      with DeciderValueConverter[Int] {
    override def convert: IntConverter[Int] =
      OutputBoundIntConverter(MatwitterRealGraphAndFollowedUsers.bounds)
  }

  /**
   * FS-controlled param to override the MatwitterRealGraphAndFollowedUsersParam decider value for etwitterperiments
   */
  object MatwitterRealGraphAndFollowedUsersFSOverrideParam
      etwittertends FSBoundedParam[Option[Int]](
        name = "matwitter_real_graph_and_followers_users_fs_override_param",
        default = None,
        min = Some(100),
        matwitter = Some(10000)
      )

  /**
   * Etwitterperimental params for leveling the playing field between user folowees received from
   * real-graph and follow-graph stores.
   * Author relevance scores returned by real-graph are currently being used for light-ranking
   * in-network tweet candidates.
   * Follow-graph store returns the most recent followees without any relevance scores
   * We are trying to impute the missing scores by using aggregated statistics (min, avg, p50, etc.)
   * of real-graph scores.
   */
  object ImputeRealGraphAuthorWeightsParam
      etwittertends FSParam(name = "impute_real_graph_author_weights", default = false)

  object ImputeRealGraphAuthorWeightsPercentileParam
      etwittertends FSBoundedParam[Int](
        name = "impute_real_graph_author_weights_percentile",
        default = 50,
        min = 0,
        matwitter = 99)

  /**
   * Enable running the new pipeline for recap author source
   */
  object EnableNewRecapAuthorPipeline etwittertends Param(false)

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
   * Enables return all results from search indetwitter.
   */
  object EnableReturnAllResultsParam
      etwittertends FSParam(name = "recap_enable_return_all_results", default = false)

  /**
   * Includes one or multiple random tweets in the response.
   */
  object IncludeRandomTweetParam
      etwittertends FSParam(name = "recap_include_random_tweet", default = false)

  /**
   * One single random tweet (true) or tag tweet as random with given probability (false).
   */
  object IncludeSingleRandomTweetParam
      etwittertends FSParam(name = "recap_include_random_tweet_single", default = true)

  /**
   * Probability to tag a tweet as random (will not be ranked).
   */
  object ProbabilityRandomTweetParam
      etwittertends FSBoundedParam(
        name = "recap_include_random_tweet_probability",
        default = ProbabilityRandomTweet.default,
        min = ProbabilityRandomTweet.bounds.minInclusive,
        matwitter = ProbabilityRandomTweet.bounds.matwitterInclusive)

  /**
   * Enable etwittertra sorting by score for search results.
   */
  object EnableEtwittertraSortingInSearchResultParam etwittertends Param(true)

  /**
   * Enables semantic core, penguin, and tweetypie content features in recap source.
   */
  object EnableContentFeaturesHydrationParam etwittertends Param(true)

  /**
   * additionally enables tokens when hydrating content features.
   */
  object EnableTokensInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "recap_enable_tokens_in_content_features_hydration",
        default = false
      )

  /**
   * additionally enables tweet tetwittert when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableTweetTetwittertInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "recap_enable_tweet_tetwittert_in_content_features_hydration",
        default = false
      )

  /**
   * Enables hydrating in-network inReplyToTweet features
   */
  object EnableInNetworkInReplyToTweetFeaturesHydrationParam
      etwittertends FSParam(
        name = "recap_enable_in_network_in_reply_to_tweet_features_hydration",
        default = false
      )

  /**
   * Enables hydrating root tweet of in-network replies and etwittertended replies
   */
  object EnableReplyRootTweetHydrationParam
      etwittertends FSParam(
        name = "recap_enable_reply_root_tweet_hydration",
        default = false
      )

  /**
   * Enable setting tweetTypes in search queries with TweetKindOption in RecapQuery
   */
  object EnableSettingTweetTypesWithTweetKindOption
      etwittertends FSParam(
        name = "recap_enable_setting_tweet_types_with_tweet_kind_option",
        default = false
      )

  /**
   * Enable relevance search, otherwise recency search from earlybird.
   */
  object EnableRelevanceSearchParam
      etwittertends FSParam(
        name = "recap_enable_relevance_search",
        default = true
      )

  object EnableEtwitterpandedEtwittertendedRepliesFilterParam
      etwittertends FSParam(
        name = "recap_enable_etwitterpanded_etwittertended_replies_filter",
        default = false
      )

  /**
   * additionally enables conversationControl when hydrating content features.
   * This only works if EnableContentFeaturesHydrationParam is set to true
   */
  object EnableConversationControlInContentFeaturesHydrationParam
      etwittertends FSParam(
        name = "conversation_control_in_content_features_hydration_recap_enable",
        default = false
      )

  object EnableTweetMediaHydrationParam
      etwittertends FSParam(
        name = "tweet_media_hydration_recap_enable",
        default = false
      )

  object EnableEtwittercludeSourceTweetIdsQueryParam
      etwittertends FSParam[Boolean](
        name = "recap_etwitterclude_source_tweet_ids_query_enable",
        default = false
      )
}
