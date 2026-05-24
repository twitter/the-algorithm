package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object ConsumersBasedUserAdGraphParams {

  object EnableSourceParam
      extends FSParam[Boolean](
        name = "consumers_based_user_ad_graph_enable_source",
        default = false
      )

  // UTG-Lookalike
  object MinCoOccurrenceParam
      extends FSBoundedParam[Int](
        name = "consumers_based_user_ad_graph_min_co_occurrence",
        default = 2,
        min = 0,
        max = 500
      )

  object MinScoreParam
      extends FSBoundedParam[Double](
        name = "consumers_based_user_ad_graph_min_score",
        default = 0.0,
        min = 0.0,
        max = 10.0
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    MinCoOccurrenceParam,
    MinScoreParam
  )

  lazy val config: BaseConfig = {

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(MinCoOccurrenceParam)
    val doubleOverrides = FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(MinScoreParam)
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(EnableSourceParam)

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}
