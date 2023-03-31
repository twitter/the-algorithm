package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object TweetBasedUserVideoGraphParams {

  object MinCoOccurrenceParam
      extends FSBoundedParam[Int](
        name = "tweet_based_user_video_graph_min_co_occurrence",
        default = 5,
        min = 0,
        max = 500
      )

  object TweetBasedMinScoreParam
      extends FSBoundedParam[Double](
        name = "tweet_based_user_video_graph_tweet_based_min_score",
        default = 0.0,
        min = 0.0,
        max = 100.0
      )

  object ConsumersBasedMinScoreParam
      extends FSBoundedParam[Double](
        name = "tweet_based_user_video_graph_consumers_based_min_score",
        default = 4.0,
        min = 0.0,
        max = 10.0
      )

  object MaxConsumerSeedsNumParam
      extends FSBoundedParam[Int](
        name = "tweet_based_user_video_graph_max_user_seeds_num",
        default = 200,
        min = 0,
        max = 500
      )

  object EnableCoverageExpansionOldTweetParam
      extends FSParam[Boolean](
        name = "tweet_based_user_video_graph_enable_coverage_expansion_old_tweet",
        default = false
      )

  object EnableCoverageExpansionAllTweetParam
      extends FSParam[Boolean](
        name = "tweet_based_user_video_graph_enable_coverage_expansion_all_tweet",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    MinCoOccurrenceParam,
    MaxConsumerSeedsNumParam,
    TweetBasedMinScoreParam,
    EnableCoverageExpansionOldTweetParam,
    EnableCoverageExpansionAllTweetParam
  )

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MinCoOccurrenceParam,
      MaxConsumerSeedsNumParam
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(TweetBasedMinScoreParam)

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }

}
