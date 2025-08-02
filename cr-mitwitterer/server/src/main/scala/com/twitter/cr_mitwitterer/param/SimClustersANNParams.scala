package com.twitter.cr_mitwitterer.param

import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object SimClustersANNParams {

  // Different SimClusters ANN cluster has its own config id (model slot)
  object SimClustersANNConfigId
      etwittertends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_config_id",
        default = "Default"
      )

  object SimClustersANN1ConfigId
      etwittertends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_1_config_id",
        default = "20220810"
      )

  object SimClustersANN2ConfigId
      etwittertends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_2_config_id",
        default = "20220818"
      )

  object SimClustersANN3ConfigId
      etwittertends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_3_config_id",
        default = "20220819"
      )

  object SimClustersANN5ConfigId
      etwittertends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_5_config_id",
        default = "20221221"
      )
  object SimClustersANN4ConfigId
      etwittertends FSParam[String](
        name = "similarity_simclusters_ann_simclusters_ann_4_config_id",
        default = "20221220"
      )
  object EtwitterperimentalSimClustersANNConfigId
      etwittertends FSParam[String](
        name = "similarity_simclusters_ann_etwitterperimental_simclusters_ann_config_id",
        default = "20220801"
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    SimClustersANNConfigId,
    SimClustersANN1ConfigId,
    SimClustersANN2ConfigId,
    SimClustersANN3ConfigId,
    SimClustersANN5ConfigId,
    EtwitterperimentalSimClustersANNConfigId
  )

  lazy val config: BaseConfig = {
    val stringOverrides = FeatureSwitchOverrideUtil.getStringFSOverrides(
      SimClustersANNConfigId,
      SimClustersANN1ConfigId,
      SimClustersANN2ConfigId,
      SimClustersANN3ConfigId,
      SimClustersANN5ConfigId,
      EtwitterperimentalSimClustersANNConfigId
    )

    BaseConfigBuilder()
      .set(stringOverrides: _*)
      .build()
  }
}
