package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object SimClustersANNParams {

  // Different SimClusters ANN cluster has its own config id (model slot)
  object SimClustersANNConfigId
      extends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_config_id",
        default = "Default"
      )

  object SimClustersANN1ConfigId
      extends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_1_config_id",
        default = "20220810"
      )

  object SimClustersANN2ConfigId
      extends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_2_config_id",
        default = "20220818"
      )

  object SimClustersANN3ConfigId
      extends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_3_config_id",
        default = "20220819"
      )

  object SimClustersANN5ConfigId
      extends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_5_config_id",
        default = "20221221"
      )
  object SimClustersANN4ConfigId
      extends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_4_config_id",
        default = "20221220"
      )
  object ExperimentalSimClustersANNConfigId
      extends FSParam[String](
        name = "similarity_simclusters_ann_experimental_simclusters_ann_config_id",
        default = "20220801"
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    SimClustersANNConfigId,
    SimClustersANN1ConfigId,
    SimClustersANN2ConfigId,
    SimClustersANN3ConfigId,
    SimClustersANN5ConfigId,
    ExperimentalSimClustersANNConfigId
  )

  lazy val config: BaseConfig = {
    val stringOverrides = FeatureSwitchOverrideUtil.getStringFSOverrides(
      SimClustersANNConfigId,
      SimClustersANN1ConfigId,
      SimClustersANN2ConfigId,
      SimClustersANN3ConfigId,
      SimClustersANN5ConfigId,
      ExperimentalSimClustersANNConfigId
    )

    BaseConfigBuilder()
      .set(stringOverrides: _*)
      .build()
  }
}
