package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object TweetBasedUserAdGraphParams {

  object MinCoOccurrenceParam
      etwittertends FSBoundedParam[Int](
        name = "tweet_based_user_ad_graph_min_co_occurrence",
        default = 1,
        min = 0,
        matwitter = 500
      )

  object ConsumersBasedMinScoreParam
      etwittertends FSBoundedParam[Double](
        name = "tweet_based_user_ad_graph_consumers_based_min_score",
        default = 0.0,
        min = 0.0,
        matwitter = 10.0
      )

  object MatwitterConsumerSeedsNumParam
      etwittertends FSBoundedParam[Int](
        name = "tweet_based_user_ad_graph_matwitter_user_seeds_num",
        default = 100,
        min = 0,
        matwitter = 300
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    MinCoOccurrenceParam,
    MatwitterConsumerSeedsNumParam,
    ConsumersBasedMinScoreParam
  )

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MinCoOccurrenceParam,
      MatwitterConsumerSeedsNumParam
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(ConsumersBasedMinScoreParam)

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }

}
