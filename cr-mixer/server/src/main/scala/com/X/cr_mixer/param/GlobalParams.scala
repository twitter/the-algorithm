package com.X.cr_mixer.param

import com.X.conversions.DurationOps._
import com.X.finagle.stats.NullStatsReceiver
import com.X.logging.Logger
import com.X.simclusters_v2.common.ModelVersions
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.DurationConversion
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSEnumParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.HasDurationConversion
import com.X.timelines.configapi.Param
import com.X.util.Duration

/**
 * Instantiate Params that do not relate to a specific product.
 * The params in this file correspond to config repo file
 * [[https://sourcegraph.X.biz/config-git.X.biz/config/-/blob/features/cr-mixer/main/twistly_core.yml]]
 */
object GlobalParams {

  object MaxCandidatesPerRequestParam
      extends FSBoundedParam[Int](
        name = "twistly_core_max_candidates_per_request",
        default = 100,
        min = 0,
        max = 9000
      )

  object ModelVersionParam
      extends FSEnumParam[ModelVersions.Enum.type](
        name = "twistly_core_simclusters_model_version_id",
        default = ModelVersions.Enum.Model20M145K2020,
        enum = ModelVersions.Enum
      )

  object UnifiedMaxSourceKeyNum
      extends FSBoundedParam[Int](
        name = "twistly_core_unified_max_sourcekey_num",
        default = 15,
        min = 0,
        max = 100
      )

  object MaxCandidateNumPerSourceKeyParam
      extends FSBoundedParam[Int](
        name = "twistly_core_candidate_per_sourcekey_max_num",
        default = 200,
        min = 0,
        max = 1000
      )

  // 1 hours to 30 days
  object MaxTweetAgeHoursParam
      extends FSBoundedParam[Duration](
        name = "twistly_core_max_tweet_age_hours",
        default = 720.hours,
        min = 1.hours,
        max = 720.hours
      )
      with HasDurationConversion {

    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  val AllParams: Seq[Param[_] with FSName] = Seq(
    MaxCandidatesPerRequestParam,
    UnifiedMaxSourceKeyNum,
    MaxCandidateNumPerSourceKeyParam,
    ModelVersionParam,
    MaxTweetAgeHoursParam
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides()

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MaxCandidatesPerRequestParam,
      UnifiedMaxSourceKeyNum,
      MaxCandidateNumPerSourceKeyParam
    )

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      ModelVersionParam
    )

    val boundedDurationFSOverrides =
      FeatureSwitchOverrideUtil.getBoundedDurationFSOverrides(MaxTweetAgeHoursParam)

    val seqOverrides = FeatureSwitchOverrideUtil.getLongSeqFSOverrides()

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(intOverrides: _*)
      .set(boundedDurationFSOverrides: _*)
      .set(enumOverrides: _*)
      .set(seqOverrides: _*)
      .build()
  }
}
