package com.X.cr_mixer.param

import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object ConsumerEmbeddingBasedCandidateGenerationParams {

  object EnableTwHINParam
      extends FSParam[Boolean](
        name = "consumer_embedding_based_candidate_generation_enable_twhin",
        default = false
      )

  object EnableTwoTowerParam
      extends FSParam[Boolean](
        name = "consumer_embedding_based_candidate_generation_enable_two_tower",
        default = false
      )

  object EnableLogFavBasedSimClustersTripParam
      extends FSParam[Boolean](
        name = "consumer_embedding_based_candidate_generation_enable_logfav_based_simclusters_trip",
        default = false
      )

  object EnableFollowBasedSimClustersTripParam
      extends FSParam[Boolean](
        name = "consumer_embedding_based_candidate_generation_enable_follow_based_simclusters_trip",
        default = false
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableTwHINParam,
    EnableTwoTowerParam,
    EnableFollowBasedSimClustersTripParam,
    EnableLogFavBasedSimClustersTripParam
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableTwHINParam,
      EnableTwoTowerParam,
      EnableFollowBasedSimClustersTripParam,
      EnableLogFavBasedSimClustersTripParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .build()
  }
}
