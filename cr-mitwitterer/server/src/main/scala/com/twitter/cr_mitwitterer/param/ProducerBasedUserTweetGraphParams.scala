package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object ProducerBasedUserTweetGraphParams {

  object MinCoOccurrenceParam
      etwittertends FSBoundedParam[Int](
        name = "producer_based_user_tweet_graph_min_co_occurrence",
        default = 4,
        min = 0,
        matwitter = 500
      )

  object MinScoreParam
      etwittertends FSBoundedParam[Double](
        name = "producer_based_user_tweet_graph_min_score",
        default = 3.0,
        min = 0.0,
        matwitter = 10.0
      )

  object MatwitterNumFollowersParam
      etwittertends FSBoundedParam[Int](
        name = "producer_based_user_tweet_graph_matwitter_num_followers",
        default = 500,
        min = 100,
        matwitter = 1000
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(MinCoOccurrenceParam, MatwitterNumFollowersParam, MinScoreParam)

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MinCoOccurrenceParam,
      MatwitterNumFollowersParam,
    )

    val doubleOverrides = FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(MinScoreParam)

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}
