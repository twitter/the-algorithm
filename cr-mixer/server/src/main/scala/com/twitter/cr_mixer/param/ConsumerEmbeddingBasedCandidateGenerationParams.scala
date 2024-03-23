package com.ExTwitter.cr_mixer.param

import com.ExTwitter.timelines.configapi.BaseConfig
import com.ExTwitter.timelines.configapi.BaseConfigBuilder
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.ExTwitter.timelines.configapi.Param

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
