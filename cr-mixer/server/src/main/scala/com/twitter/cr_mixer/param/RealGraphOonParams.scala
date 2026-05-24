package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object RealGraphOonParams {
  object EnableSourceParam
      extends FSParam[Boolean](
        name = "signal_realgraphoon_enable_source",
        default = false
      )

  object EnableSourceGraphParam
      extends FSParam[Boolean](
        name = "graph_realgraphoon_enable_source",
        default = false
      )

  object MaxConsumerSeedsNumParam
      extends FSBoundedParam[Int](
        name = "graph_realgraphoon_max_user_seeds_num",
        default = 200,
        min = 0,
        max = 1000
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    EnableSourceGraphParam,
    MaxConsumerSeedsNumParam
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam,
      EnableSourceGraphParam
    )

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(MaxConsumerSeedsNumParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(intOverrides: _*)
      .build()
  }
}
