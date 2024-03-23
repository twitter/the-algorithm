package com.ExTwitter.cr_mixer.param

import com.ExTwitter.finagle.stats.NullStatsReceiver
import com.ExTwitter.logging.Logger
import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

object RecentNegativeSignalParams {
  object EnableSourceParam
      extends FSParam[Boolean](
        name = "twistly_recentnegativesignals_enable_source",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam
  )

  lazy val config: BaseConfig = {
    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
    )

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides()

    BaseConfigBuilder()
      .set(booleanOverrides: _*).set(doubleOverrides: _*).set(enumOverrides: _*).build()
  }
}
