package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object AdsParams {
  object AdsCandidateGenerationMatwitterCandidatesNumParam
      etwittertends FSBoundedParam[Int](
        name = "ads_candidate_generation_matwitter_candidates_num",
        default = 400,
        min = 0,
        matwitter = 2000
      )

  object EnableScoreBoost
      etwittertends FSParam[Boolean](
        name = "ads_candidate_generation_enable_score_boost",
        default = false
      )

  object AdsCandidateGenerationScoreBoostFactor
      etwittertends FSBoundedParam[Double](
        name = "ads_candidate_generation_score_boost_factor",
        default = 10000.0,
        min = 1.0,
        matwitter = 100000.0
      )

  object EnableScribe
      etwittertends FSParam[Boolean](
        name = "ads_candidate_generation_enable_scribe",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    AdsCandidateGenerationMatwitterCandidatesNumParam,
    EnableScoreBoost,
    AdsCandidateGenerationScoreBoostFactor
  )

  lazy val config: BaseConfig = {
    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      AdsCandidateGenerationMatwitterCandidatesNumParam)

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableScoreBoost,
      EnableScribe
    )

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(AdsCandidateGenerationScoreBoostFactor)

    BaseConfigBuilder()
      .set(intOverrides: _*)
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .build()
  }
}
