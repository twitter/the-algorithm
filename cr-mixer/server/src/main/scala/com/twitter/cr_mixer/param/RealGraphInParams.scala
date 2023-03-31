package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi._

object RealGraphInParams {
  object EnableSourceGraphParam
      extends FSParam[Boolean](
        name = "graph_realgraphin_enable_source",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceGraphParam,
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceGraphParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .build()
  }
}
