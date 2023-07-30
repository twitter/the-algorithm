package com.X.cr_mixer.param

import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object ProducerBasedUserAdGraphParams {

  object MinCoOccurrenceParam
      extends FSBoundedParam[Int](
        name = "producer_based_user_ad_graph_min_co_occurrence",
        default = 2,
        min = 0,
        max = 500
      )

  object MinScoreParam
      extends FSBoundedParam[Double](
        name = "producer_based_user_ad_graph_min_score",
        default = 3.0,
        min = 0.0,
        max = 10.0
      )

  object MaxNumFollowersParam
      extends FSBoundedParam[Int](
        name = "producer_based_user_ad_graph_max_num_followers",
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
