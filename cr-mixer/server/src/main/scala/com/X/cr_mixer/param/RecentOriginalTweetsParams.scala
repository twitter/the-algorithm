package com.X.cr_mixer.param

import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object RecentOriginalTweetsParams {

  // Source params
  object EnableSourceParam
      extends FSParam[Boolean](
        name = "twistly_recentoriginaltweets_enable_source",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(EnableSourceParam)

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(EnableSourceParam)

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .build()
  }
}
