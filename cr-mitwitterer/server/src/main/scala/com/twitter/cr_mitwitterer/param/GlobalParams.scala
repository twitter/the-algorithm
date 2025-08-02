package com.twitter.cr_mitwitterer.param

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

/**
 * Instantiate Params that do not relate to a specific product.
 * The params in this file correspond to config repo file
 * [[https://sourcegraph.twitter.biz/config-git.twitter.biz/config/-/blob/features/cr-mitwitterer/main/twistly_core.yml]]
 */
object GlobalParams {

  object MatwitterCandidatesPerRequestParam
      etwittertends FSBoundedParam[Int](
        name = "twistly_core_matwitter_candidates_per_request",
        default = 100,
        min = 0,
        matwitter = 9000
      )

  object ModelVersionParam
      etwittertends FSEnumParam[ModelVersions.Enum.type](
        name = "twistly_core_simclusters_model_version_id",
        default = ModelVersions.Enum.Model20M145K2020,
        enum = ModelVersions.Enum
      )

  object UnifiedMatwitterSourceKeyNum
      etwittertends FSBoundedParam[Int](
        name = "twistly_core_unified_matwitter_sourcekey_num",
        default = 15,
        min = 0,
        matwitter = 100
      )

  object MatwitterCandidateNumPerSourceKeyParam
      etwittertends FSBoundedParam[Int](
        name = "twistly_core_candidate_per_sourcekey_matwitter_num",
        default = 200,
        min = 0,
        matwitter = 1000
      )

  // 1 hours to 30 days
  object MatwitterTweetAgeHoursParam
      etwittertends FSBoundedParam[Duration](
        name = "twistly_core_matwitter_tweet_age_hours",
        default = 720.hours,
        min = 1.hours,
        matwitter = 720.hours
      )
      with HasDurationConversion {

    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  val AllParams: Seq[Param[_] with FSName] = Seq(
    MatwitterCandidatesPerRequestParam,
    UnifiedMatwitterSourceKeyNum,
    MatwitterCandidateNumPerSourceKeyParam,
    ModelVersionParam,
    MatwitterTweetAgeHoursParam
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides()

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MatwitterCandidatesPerRequestParam,
      UnifiedMatwitterSourceKeyNum,
      MatwitterCandidateNumPerSourceKeyParam
    )

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      ModelVersionParam
    )

    val boundedDurationFSOverrides =
      FeatureSwitchOverrideUtil.getBoundedDurationFSOverrides(MatwitterTweetAgeHoursParam)

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
