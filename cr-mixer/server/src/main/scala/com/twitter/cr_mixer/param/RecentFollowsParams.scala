package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

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
