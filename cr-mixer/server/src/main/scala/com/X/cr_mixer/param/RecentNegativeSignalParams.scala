package com.X.cr_mixer.param

import com.X.finagle.stats.NullStatsReceiver
import com.X.logging.Logger
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

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
