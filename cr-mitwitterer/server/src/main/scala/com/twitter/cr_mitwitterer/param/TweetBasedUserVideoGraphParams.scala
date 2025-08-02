package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object TweetBasedUserVideoGraphParams {

  object MinCoOccurrenceParam
      etwittertends FSBoundedParam[Int](
        name = "tweet_based_user_video_graph_min_co_occurrence",
        default = 5,
        min = 0,
        matwitter = 500
      )

  object TweetBasedMinScoreParam
      etwittertends FSBoundedParam[Double](
        name = "tweet_based_user_video_graph_tweet_based_min_score",
        default = 0.0,
        min = 0.0,
        matwitter = 100.0
      )

  object ConsumersBasedMinScoreParam
      etwittertends FSBoundedParam[Double](
        name = "tweet_based_user_video_graph_consumers_based_min_score",
        default = 4.0,
        min = 0.0,
        matwitter = 10.0
      )

  object MatwitterConsumerSeedsNumParam
      etwittertends FSBoundedParam[Int](
        name = "tweet_based_user_video_graph_matwitter_user_seeds_num",
        default = 200,
        min = 0,
        matwitter = 500
      )

  object EnableCoverageEtwitterpansionOldTweetParam
      etwittertends FSParam[Boolean](
        name = "tweet_based_user_video_graph_enable_coverage_etwitterpansion_old_tweet",
        default = false
      )

  object EnableCoverageEtwitterpansionAllTweetParam
      etwittertends FSParam[Boolean](
        name = "tweet_based_user_video_graph_enable_coverage_etwitterpansion_all_tweet",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    MinCoOccurrenceParam,
    MatwitterConsumerSeedsNumParam,
    TweetBasedMinScoreParam,
    EnableCoverageEtwitterpansionOldTweetParam,
    EnableCoverageEtwitterpansionAllTweetParam
  )

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MinCoOccurrenceParam,
      MatwitterConsumerSeedsNumParam
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(TweetBasedMinScoreParam)

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }

}
