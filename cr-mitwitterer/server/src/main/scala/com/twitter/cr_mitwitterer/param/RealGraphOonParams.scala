package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object RealGraphOonParams {
  object EnableSourceParam
      etwittertends FSParam[Boolean](
        name = "signal_realgraphoon_enable_source",
        default = false
      )

  object EnableSourceGraphParam
      etwittertends FSParam[Boolean](
        name = "graph_realgraphoon_enable_source",
        default = false
      )

  object MatwitterConsumerSeedsNumParam
      etwittertends FSBoundedParam[Int](
        name = "graph_realgraphoon_matwitter_user_seeds_num",
        default = 200,
        min = 0,
        matwitter = 1000
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    EnableSourceGraphParam,
    MatwitterConsumerSeedsNumParam
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam,
      EnableSourceGraphParam
    )

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(MatwitterConsumerSeedsNumParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(intOverrides: _*)
      .build()
  }
}
