package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

object ProducerBasedUserTweetGraphParams {

  object MinCoOccurrenceParam
      extends FSBoundedParam[Int](
        name = "producer_based_user_tweet_graph_min_co_occurrence",
        default = 4,
        min = 0,
        max = 500
      )

  object MinScoreParam
      extends FSBoundedParam[Double](
        name = "producer_based_user_tweet_graph_min_score",
        default = 3.0,
        min = 0.0,
        max = 10.0
      )

  object MaxNumFollowersParam
      extends FSBoundedParam[Int](
        name = "producer_based_user_tweet_graph_max_num_followers",
        default = 500,
        min = 100,
        max = 1000
      )

  val AllParams: Seq[Param[_] with FSName] =
    Seq(MinCoOccurrenceParam, MaxNumFollowersParam, MinScoreParam)

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MinCoOccurrenceParam,
      MaxNumFollowersParam,
    )

    val doubleOverrides = FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(MinScoreParam)

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}
