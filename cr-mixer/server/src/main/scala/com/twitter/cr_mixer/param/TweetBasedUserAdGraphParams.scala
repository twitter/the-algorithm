package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

object TweetBasedUserAdGraphParams {

  object MinCoOccurrenceParam
      extends FSBoundedParam[Int](
        name = "tweet_based_user_ad_graph_min_co_occurrence",
        default = 1,
        min = 0,
        max = 500
      )

  object ConsumersBasedMinScoreParam
      extends FSBoundedParam[Double](
        name = "tweet_based_user_ad_graph_consumers_based_min_score",
        default = 0.0,
        min = 0.0,
        max = 10.0
      )

  object MaxConsumerSeedsNumParam
      extends FSBoundedParam[Int](
        name = "tweet_based_user_ad_graph_max_user_seeds_num",
        default = 100,
        min = 0,
        max = 300
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    MinCoOccurrenceParam,
    MaxConsumerSeedsNumParam,
    ConsumersBasedMinScoreParam
  )

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MinCoOccurrenceParam,
      MaxConsumerSeedsNumParam
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(ConsumersBasedMinScoreParam)

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }

}
