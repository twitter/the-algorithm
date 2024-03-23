package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

object AdsParams {
  object AdsCandidateGenerationMaxCandidatesNumParam
      extends FSBoundedParam[Int](
        name = "ads_candidate_generation_max_candidates_num",
        default = 400,
        min = 0,
        max = 2000
      )

  object EnableScoreBoost
      extends FSParam[Boolean](
        name = "ads_candidate_generation_enable_score_boost",
        default = false
      )

  object AdsCandidateGenerationScoreBoostFactor
      extends FSBoundedParam[Double](
        name = "ads_candidate_generation_score_boost_factor",
        default = 10000.0,
        min = 1.0,
        max = 100000.0
      )

  object EnableScribe
      extends FSParam[Boolean](
        name = "ads_candidate_generation_enable_scribe",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    AdsCandidateGenerationMaxCandidatesNumParam,
    EnableScoreBoost,
    AdsCandidateGenerationScoreBoostFactor
  )

  lazy val config: BaseConfig = {
    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      AdsCandidateGenerationMaxCandidatesNumParam)

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
