package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object RecentFollowsParams {
  object EnableSourceParam
      extends FSParam[Boolean](
        name = "twistly_recentfollows_enable_source",
        default = true
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(EnableSourceParam)
  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .build()
  }
}
