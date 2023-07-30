package com.X.cr_mixer.param

import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

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
