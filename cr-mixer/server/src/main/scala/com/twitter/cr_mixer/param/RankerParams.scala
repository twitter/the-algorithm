package com.ExTwitter.cr_mixer.param

import com.ExTwitter.finagle.stats.NullStatsReceiver
import com.ExTwitter.logging.Logger
import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

object RankerParams {

  object MaxCandidatesToRank
      extends FSBoundedParam[Int](
        name = "twistly_core_max_candidates_to_rank",
        default = 2000,
        min = 0,
        max = 9999
      )

  object EnableBlueVerifiedTopK
      extends FSParam[Boolean](
        name = "twistly_core_blue_verified_top_k",
        default = true
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    MaxCandidatesToRank,
    EnableBlueVerifiedTopK
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(EnableBlueVerifiedTopK)

    val boundedDurationFSOverrides =
      FeatureSwitchOverrideUtil.getBoundedDurationFSOverrides()

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MaxCandidatesToRank
    )

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
    )
    val stringFSOverrides = FeatureSwitchOverrideUtil.getStringFSOverrides()

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(boundedDurationFSOverrides: _*)
      .set(intOverrides: _*)
      .set(enumOverrides: _*)
      .set(stringFSOverrides: _*)
      .build()
  }
}
