package com.twitter.cr_mixer.param

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

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
